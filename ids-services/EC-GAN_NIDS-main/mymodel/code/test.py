import joblib
import numpy as np
import pandas as pd
import keras
from sklearn.preprocessing import StandardScaler, Normalizer, LabelEncoder


critic_model2 = keras.models.load_model("../../models/50_critic_ecgan.h5")
# critic_model.summary()
# generated_data = np.random.random((10, 31))
# labels = np.random.randint(0, 15, (10, 1))
# valid = critic_model([generated_data, labels])

# print(valid)

critic_model = keras.models.load_model("../../ecgan/critic_model.h5")
critic_model.summary()
critic_model2.summary()