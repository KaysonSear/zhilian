import keras
import pandas as pd
import numpy as np
from sklearn.preprocessing import StandardScaler, Normalizer, LabelEncoder
from sklearn.decomposition import PCA

# model = keras.models.load_model('../models/100_classifier_standalone.h5')
model = keras.models.load_model('models/classifier.h5')
# 读取原始数据
data = pd.read_csv("../data/clean2.csv")

# 预处理函数
def preproc_data(dataset, pca_dim=31):
    # Label encode
    le = LabelEncoder()
    dataset['label'] = le.fit_transform(dataset['label'])

    # 将特征和标签分开
    x = dataset.iloc[:, :-1]
    y = dataset['label']

    # Standard scaling
    ss = StandardScaler().fit(x)
    x = ss.transform(x)

    # 动态设置 PCA 的 n_components
    n_components = min(pca_dim, min(x.shape[0], x.shape[1]))

    # PCA
    pca = PCA(n_components=n_components).fit(x)
    x = pca.transform(x)

    # Normalization
    norm = Normalizer().fit(x)
    x = norm.transform(x)

    return x, y

# 对原始数据进行预处理
x_train, y_train = preproc_data(data, pca_dim=31)
print(x_train.shape)
print(y_train.shape)
predictions = model.predict(x_train)
print(predictions)

def get_predicted_classes(predictions):
    # predictions: 二维数组，每行是一个样本的预测概率向量

    # 获取每行最高概率对应的类别索引
    predicted_classes = np.argmax(predictions, axis=1)

    return predicted_classes

# 使用方法：
# predictions 是你模型预测后得到的结果，即每个样本对应每个类别的预测概率
# 调用 get_predicted_classes 函数获取每个样本的预测类别索引
predicted_classes = get_predicted_classes(predictions)

# 打印预测类别索引
print(predicted_classes)