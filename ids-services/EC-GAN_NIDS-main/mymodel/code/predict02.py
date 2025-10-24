import joblib
import numpy as np
import pandas as pd
import keras
from sklearn.preprocessing import StandardScaler, Normalizer, LabelEncoder

# 加载数据和模型
data = pd.read_csv("../data/全部流量.csv", encoding="GBK")

# 删除指定的列，假设列名包含了逗号
columns_to_drop = ['Flow ID', 'Src IP', 'Dst IP', 'Timestamp', 'Protocol', 'Src Port']  # 列名列表

data['Label'] = 0
# 删除列
data.drop(columns=columns_to_drop, inplace=True, errors='ignore')

# 找到插入位置
insert_position = data.columns.get_loc('Bwd Segment Size Avg') + 1

# 在指定位置插入新列，并将其值初始化为 0
data.insert(insert_position, 'fwd_header_length', 0)

np.all(np.isfinite(data.iloc[:, :-1]))
nonfinite = [col for col in data.iloc[:, :-1] if not np.all(np.isfinite(data[col]))]

data.iloc[:, :-1] = data.iloc[:, :-1].replace([np.inf, -np.inf], np.nan)
np.any(np.isnan(data.iloc[:, :-1]))

data.dropna(inplace=True)
np.all(np.isfinite(data.iloc[:, :-1]))

# 标签映射
labels = {
    0: 'BENIGN',
    1: 'Bot',
    2: 'DDoS',
    3: 'DoS_GoldenEye',
    4: 'DoS_Hulk',
    5: 'DoS_Slowhttptest',
    6: 'DoS_slowloris',
    7: 'FTPPatator',
    8: 'Heartbleed',
    9: 'Infiltration',
    10: 'PortScan',
    11: 'SSHPatator',
    12: 'Web_Attack_Brute_Force',
    13: 'Web_Attack_Sql_Injection',
    14: 'Web_Attack_XSS'
}

# 加载预处理模型
pca = joblib.load("../pretreatment-model/pca.pkl")
ss = joblib.load("../pretreatment-model/ss.pkl")
norm = joblib.load("../pretreatment-model/norm.pkl")


def preproc_data(dataset):
    # Label encode
    le = LabelEncoder()
    dataset['Label'] = le.fit_transform(dataset['Label'])

    # 将特征和标签分开
    x = dataset.iloc[:, :-1]
    y = dataset['Label']

    # 标准化和PCA处理
    x = ss.transform(x)
    x = pca.transform(x)

    # 归一化处理
    x = norm.transform(x)

    return x, y


# 准备数据
x, y = preproc_data(data)
print("Shape of preprocessed data:", x.shape)

# 加载模型
model = keras.models.load_model('../models/100_classifier_standalone.h5')

# 进行预测
predictions = model.predict(x)
print("Raw predictions:\n", predictions)


def get_predicted_classes(predictions):
    # 获取每行最高概率对应的类别索引
    predicted_classes = np.argmax(predictions, axis=1)
    return predicted_classes


# 获取预测的类别索引
predicted_classes = get_predicted_classes(predictions)
print("Predicted classes:\n", predicted_classes)


def get_prediction_results(predictions, labels):
    # 获取预测的类别标签和置信度
    max_confidences = np.max(predictions, axis=1)
    predicted_labels = [labels[class_index] for class_index in np.argmax(predictions, axis=1)]

    prediction_results = pd.DataFrame({
        "Predicted_Label": predicted_labels,
        "Confidence": max_confidences
    })

    return prediction_results


# 获取预测的标签和置信度
prediction_results = get_prediction_results(predictions, labels)
print("Prediction results:\n", prediction_results)

# 统计每一个预测结果的个数
result_counts = prediction_results['Predicted_Label'].value_counts()
print("Prediction counts:\n", result_counts)

# Todo
# 分离标签 识别未知攻击
