import os
from flask import Flask, request, jsonify
from flask_cors import CORS
import joblib
import numpy as np
import pandas as pd
import keras
from sklearn.preprocessing import StandardScaler, Normalizer, LabelEncoder

app = Flask(__name__)
CORS(app)

# Define the directory where CSV files will be saved
UPLOAD_FOLDER = 'ipdata'
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER

# Ensure the upload folder exists
os.makedirs(UPLOAD_FOLDER, exist_ok=True)


def get_predicted_classes(predictions):
    # Get the index of the class with the highest probability for each row
    predicted_classes = np.argmax(predictions, axis=1)
    return predicted_classes


def get_prediction_results(predictions, labels, data):
    # Get predicted labels and confidence
    max_confidences = np.max(predictions, axis=1)
    predicted_labels = [labels[class_index] for class_index in np.argmax(predictions, axis=1)]

    # Prepare additional fields from the original data
    additional_fields = {
        'timestamp': data['Timestamp'].tolist(),
        'packetLengthMean': data['Packet Length Mean'].tolist(),
        'srcIp': data['Src IP'].tolist(),
        'srcPort': data['Src Port'].tolist(),
        'dstIp': data['Dst IP'].tolist(),
        'dstPort': data['Dst Port'].tolist(),
        'protocol': data['Protocol'].tolist(),
        'label': predicted_labels,
        'confidence': max_confidences.tolist()
    }

    prediction_results = pd.DataFrame(additional_fields)

    return prediction_results


labels = {
    0: 'BENIGN', 1: 'Bot', 2: 'DDoS', 3: 'DoS_GoldenEye',
    4: 'DoS_Hulk', 5: 'DoS_Slowhttptest', 6: 'DoS_slowloris',
    7: 'FTPPatator', 8: 'Heartbleed', 9: 'Infiltration',
    10: 'PortScan', 11: 'SSHPatator', 12: 'Web_Attack_Brute_Force',
    13: 'Web_Attack_Sql_Injection', 14: 'Web_Attack_XSS'
}

# Load preprocessing models
pca = joblib.load("../../pretreatment-model/pca.pkl")
ss = joblib.load("../../pretreatment-model/ss.pkl")
norm = joblib.load("../../pretreatment-model/norm.pkl")
# Load the model
model = keras.models.load_model('../../models/100_classifier_standalone.h5')
def preprocess_data(dataset):


    # Separate features and labels
    x = dataset.iloc[:, :-1]


    # Standardization and PCA
    x = ss.transform(x)
    x = pca.transform(x)

    # Normalization
    x = norm.transform(x)

    return x


@app.route('/ipPredict', methods=['POST'])
def ipPredict():
    # Check if the POST request has the file part
    if 'file' not in request.files:
        return jsonify({'error': 'No file part in the request'}), 400

    file = request.files['file']

    # If the user does not select a file, the browser submits an empty file without a filename
    if file.filename == '':
        return jsonify({'error': 'No selected file'}), 400

    # Save the uploaded file to the upload folder
    file.save(os.path.join(app.config['UPLOAD_FOLDER'], "ipdata.csv"))

    # Load the data
    data = pd.read_csv("ipdata/ipdata.csv", encoding="GBK")
    data_copy = data.copy()

    # Insert a new column at a specific position
    insert_position = data_copy.columns.get_loc('Bwd Segment Size Avg') + 1
    data_copy.insert(insert_position, 'fwd_header_length', 0)

    # Clean data for NaN or infinite values
    data_copy.replace([np.inf, -np.inf], np.nan, inplace=True)
    data_copy.dropna(inplace=True)

    # Drop specified columns
    columns_to_drop = ['Flow ID', 'Src IP', 'Dst IP', 'Timestamp', 'Protocol', 'Src Port']
    # columns_to_drop = ['Flow ID', ' Source IP', ' Destination IP', ' Timestamp', ' Protocol', ' Source Port']
    data.drop(columns=columns_to_drop, inplace=True, errors='ignore')

    # Insert a new column at a specific position
    insert_position = data.columns.get_loc('Bwd Segment Size Avg') + 1
    data.insert(insert_position, 'fwd_header_length', 0)

    # Clean data for NaN or infinite values
    data.replace([np.inf, -np.inf], np.nan, inplace=True)
    data.dropna(inplace=True)

    # Label mapping


    # Prepare data
    x = preprocess_data(data)



    # Make predictions
    predictions = model.predict(x)

    # Get predicted class indices
    predicted_classes = get_predicted_classes(predictions)
    # print(predicted_classes)

    # Get predicted labels and confidence
    prediction_results = get_prediction_results(predictions, labels,data_copy)

    # Count predictions
    result_counts = prediction_results['label'].value_counts()

    # Prepare JSON response with prediction results
    prediction_results_json = prediction_results.to_dict(orient='records')

    # 统计每一个预测结果的个数
    # print("Prediction counts:\n", result_counts)

    return jsonify({
        'message': 'File uploaded and processed successfully',
        'predictions': prediction_results_json,
        'prediction_counts': result_counts.to_dict()
    }), 200


if __name__ == '__main__':
    app.run(debug=True,host='0.0.0.0')
