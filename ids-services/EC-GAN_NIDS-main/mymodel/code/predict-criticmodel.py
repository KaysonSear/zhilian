import joblib
import numpy as np
import pandas as pd
import keras
from sklearn.preprocessing import StandardScaler, Normalizer, LabelEncoder

# 加载评论模型
# critic_model = keras.models.load_model("../../ecgan/critic_model.h5")
# critic_model = keras.models.load_model("../models/critic_model.h5")
critic_model = keras.models.load_model("../models/drop-portscan-ddos/critic_model.h5")

# 加载分类模型
# classifier_model = keras.models.load_model('../models/100_classifier_standalone.h5')
classifier_model = keras.models.load_model('../models/drop-portscan-ddos/classifier.h5')

# 加载数据
data = pd.read_csv("../data/test.csv", encoding="GBK")
columns_to_drop = ['Flow ID', 'Src IP', 'Dst IP', 'Timestamp', 'Protocol', 'Src Port']
data.drop(columns=columns_to_drop, inplace=True, errors='ignore')

# 插入新列
insert_position = data.columns.get_loc('Bwd Segment Size Avg') + 1
data.insert(insert_position, 'fwd_header_length', 0)

# 处理数据中的无穷值和缺失值
data.replace([np.inf, -np.inf], np.nan, inplace=True)
data.dropna(inplace=True)

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


# 数据预处理函数
def preproc_data(dataset):
    le = LabelEncoder()
    dataset['Label'] = le.fit_transform(dataset['Label'])

    x = dataset.iloc[:, :-1]
    y = dataset['Label']

    x = ss.transform(x)
    x = pca.transform(x)
    x = norm.transform(x)

    return x, y


# 准备数据
x, y = preproc_data(data)



# 进行预测
predictions = classifier_model.predict(x)

# 获取预测的类别索引
predicted_classes = np.argmax(predictions, axis=1)


# 根据置信度阈值和评论模型进行预测验证
def apply_critic_model(x, predictions, critic_model, threshold=0.4):
    valid_predictions = []
    for i in range(len(predictions)):
        confidence = predictions[i, predicted_classes[i]]

        if confidence < 0.9:
            critic_result = critic_model.predict([x[i:i + 1], np.array([predicted_classes[i]])])
            print(critic_result,"\n")
            if -critic_result > threshold:
                valid_predictions.append(predicted_classes[i])
            else:
                valid_predictions.append(-1)  # 标记为异常或根据需要进行处理
        else:
            valid_predictions.append(predicted_classes[i])

    return np.array(valid_predictions)


# 应用评论模型验证预测结果
valid_predictions = apply_critic_model(x, predictions, critic_model)

# 根据验证结果更新数据的Label列
data['Label'] = [labels[pred] if pred != -1 else '未知攻击' for pred in valid_predictions]

# 打印更新后的数据标签

print(data['Label'])
df = data.Label.value_counts()
print(df)