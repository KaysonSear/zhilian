import os
import time

from flask_socketio import SocketIO, emit
from flask import Flask, request, jsonify
from flask_cors import CORS
import joblib
import numpy as np
import pandas as pd
import keras

app = Flask(__name__)
CORS(app)

socketio = SocketIO()
socketio.init_app(app, cors_allowed_origins='*')

data = [{
    "Flow ID": 0,
    "Src IP": 0,
    "Src Port": 0,
    "Dst IP": 0,
    "Dst Port": 0,
    "Protocol": 0,
    "Timestamp": 0,
    "Flow Duration": 0,
    "Total Fwd Packet": 0,
    "Total Bwd packets": 0,
    "Total Length of Fwd Packet": 0,
    "Total Length of Bwd Packet": 0,
    "Fwd Packet Length Max": 0,
    "Fwd Packet Length Min": 0,
    "Fwd Packet Length Mean": 0,
    "Fwd Packet Length Std": 0,
    "Bwd Packet Length Max": 0,
    "Bwd Packet Length Min": 0,
    "Bwd Packet Length Mean": 0,
    "Bwd Packet Length Std": 0,
    "Flow Bytes/s": 0,
    "Flow Packets/s": 0,
    "Flow IAT Mean": 0,
    "Flow IAT Std": 0,
    "Flow IAT Max": 0,
    "Flow IAT Min": 0,
    "Fwd IAT Total": 0,
    "Fwd IAT Mean": 0,
    "Fwd IAT Std": 0,
    "Fwd IAT Max": 0,
    "Fwd IAT Min": 0,
    "Bwd IAT Total": 0,
    "Bwd IAT Mean": 0,
    "Bwd IAT Std": 0,
    "Bwd IAT Max": 0,
    "Bwd IAT Min": 0,
    "Fwd PSH Flags": 0,
    "Bwd PSH Flags": 0,
    "Fwd URG Flags": 0,
    "Bwd URG Flags": 0,
    "Fwd Header Length": 0,
    "Bwd Header Length": 0,
    "Fwd Packets/s": 0,
    "Bwd Packets/s": 0,
    "Packet Length Min": 0,
    "Packet Length Max": 0,
    "Packet Length Mean": 0,
    "Packet Length Std": 0,
    "Packet Length Variance": 0,
    "FIN Flag Count": 0,
    "SYN Flag Count": 0,
    "RST Flag Count": 0,
    "PSH Flag Count": 0,
    "ACK Flag Count": 0,
    "URG Flag Count": 0,
    "CWR Flag Count": 0,
    "ECE Flag Count": 0,
    "Down/Up Ratio": 0,
    "Average Packet Size": 0,
    "Fwd Segment Size Avg": 0,
    "Bwd Segment Size Avg": 0,
    "Fwd Bytes/Bulk Avg": 0,
    "Fwd Packet/Bulk Avg": 0,
    "Fwd Bulk Rate Avg": 0,
    "Bwd Bytes/Bulk Avg": 0,
    "Bwd Packet/Bulk Avg": 0,
    "Bwd Bulk Rate Avg": 0,
    "Subflow Fwd Packets": 0,
    "Subflow Fwd Bytes": 0,
    "Subflow Bwd Packets": 0,
    "Subflow Bwd Bytes": 0,
    "FWD Init Win Bytes": 0,
    "Bwd Init Win Bytes": 0,
    "Fwd Act Data Pkts": 0,
    "Fwd Seg Size Min": 0,
    "Active Mean": 0,
    "Active Std": 0,
    "Active Max": 0,
    "Active Min": 0,
    "Idle Mean": 0,
    "Idle Std": 0,
    "Idle Max": 0,
    "Idle Min": 0,
    "Label": 0,

}]

# Define the directory where CSV files will be saved
UPLOAD_FOLDER = 'ipdata'
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER

# Ensure the upload folder exists
os.makedirs(UPLOAD_FOLDER, exist_ok=True)

# Load preprocessing models
pca = joblib.load("../../pretreatment-model/pca.pkl")
ss = joblib.load("../../pretreatment-model/ss.pkl")
norm = joblib.load("../../pretreatment-model/norm.pkl")

# Load the classification model
classifier_model = keras.models.load_model('../../models/100_classifier_standalone.h5')

# Load the critic model
# critic_model = keras.models.load_model("../../../ecgan/critic_model.h5")
critic_model = keras.models.load_model("../../../models/50_critic_ecgan.h5")

# Label mapping
labels = {
    0: 'BENIGN', 1: 'Bot', 2: 'DDoS', 3: 'DoS_GoldenEye',
    4: 'DoS_Hulk', 5: 'DoS_Slowhttptest', 6: 'DoS_slowloris',
    7: 'FTPPatator', 8: 'Heartbleed', 9: 'Infiltration',
    10: 'PortScan', 11: 'SSHPatator', 12: 'Web_Attack_Brute_Force',
    13: 'Web_Attack_Sql_Injection', 14: 'Web_Attack_XSS'
}


# BENIGN -> 0
# Bot -> 1
# DDoS -> 2
# DoS_GoldenEye -> 3
# DoS_Hulk -> 4
# DoS_Slowhttptest -> 5
# DoS_slowloris -> 6
# FTPPatator -> 7
# Heartbleed -> 8
# Infiltration -> 9
# PortScan -> 10
# SSHPatator -> 11
# Web_Attack_Brute_Force -> 12
# Web_Attack_Sql_Injection -> 13
# Web_Attack_XSS -> 14

# BENIGN                      2271320
# DoS_Hulk                     230124
# PortScan                     158804
# DDoS                         128025
# DoS_GoldenEye                 10293
# FTPPatator                     7935
# SSHPatator                     5897
# DoS_slowloris                  5796
# DoS_Slowhttptest               5499
# Bot                            1956
# Web_Attack_Brute_Force         1507
# Web_Attack_XSS                  652
# Infiltration                     36
# Web_Attack_Sql_Injection         21
# Heartbleed                       11
# Name: label, dtype: int64
# =========================
# BENIGN                      2271320
# DoS_Hulk                     230124
# DoS_GoldenEye                 10293
# FTPPatator                     7935
# SSHPatator                     5897
# DoS_slowloris                  5796
# DoS_Slowhttptest               5499
# Bot                            1956
# Web_Attack_Brute_Force         1507
# Web_Attack_XSS                  652
# Infiltration                     36
# Web_Attack_Sql_Injection         21
# Heartbleed                       11

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


def preprocess_data(dataset):
    # Drop unnecessary columns
    columns_to_drop = ['Flow ID', 'Src IP', 'Dst IP', 'Timestamp', 'Protocol', 'Src Port']
    dataset.drop(columns=columns_to_drop, inplace=True, errors='ignore')

    # Insert a new column at a specific position
    insert_position = dataset.columns.get_loc('Bwd Segment Size Avg') + 1
    dataset.insert(insert_position, 'fwd_header_length', 0)

    # Clean data for NaN or infinite values
    dataset.replace([np.inf, -np.inf], np.nan, inplace=True)
    dataset.dropna(inplace=True)

    # Separate features and labels
    x = dataset.iloc[:, :-1]

    # Standardization and PCA
    x = ss.transform(x)
    x = pca.transform(x)

    # Normalization
    x = norm.transform(x)

    return x


def apply_critic_model(x, predictions, critic_model, threshold=0.5):
    valid_predictions = []
    for i in range(len(predictions)):
        confidence = predictions[i, np.argmax(predictions[i])]

        if confidence < 0.9:
            critic_result = critic_model.predict([x[i:i + 1], np.array([np.argmax(predictions[i])])])
            if -critic_result > threshold:
                valid_predictions.append(np.argmax(predictions[i]))
            else:
                valid_predictions.append(-1)  # Mark as unknown attack or handle as needed
        else:
            valid_predictions.append(np.argmax(predictions[i]))

    return np.array(valid_predictions)


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
    # Preprocess the data
    x = preprocess_data(data)

    # Make predictions using the classification model
    predictions = classifier_model.predict(x)

    # Apply critic model for validation
    valid_predictions = apply_critic_model(x, predictions, critic_model)

    # Update 'Label' column based on valid_predictions
    data['Label'] = [labels[pred] if pred != -1 else 'Unknow_attack' for pred in valid_predictions]

    # Count predictions
    result_counts = data['Label'].value_counts()

    # Prepare JSON response with prediction results

    prediction_results = get_prediction_results(predictions, labels, data_copy)
    prediction_results_json = prediction_results.to_dict(orient='records')
    return jsonify({
        'message': 'File uploaded and processed successfully',
        'predictions': prediction_results_json,
        'prediction_counts': result_counts.to_dict()
    }), 200


flow_list = []  # Global variable to store flow data


@app.route('/get_flow', methods=['POST'])
def get_flow():
    global flow_list
    flow_data = request.data.decode('utf-8')  # Assuming plain text data
    flow_list = flow_data.split(',')
    # Process flow_list as needed
    # print("Received flow data:")
    # print(flow_list)

    return jsonify({'message': 'Flow data received'}), 200


@app.route('/real_time', methods=['GET'])
def get_real_time():
    global flow_list
    flow_list_copy = flow_list
    # print(flow_list)

    # if data['Flow ID'] == 0:
    #     return jsonify({'message': 'error'}),200

    # print(len(flow_list_copy))
    # print(len(data))

    for key, value in zip(data[0].keys(), flow_list_copy):
        # print(key, value)
        data[0][key] = value

    # print(data)
    data1 = pd.DataFrame(data)
    data_copy = data1.copy()

    # Insert a new column at a specific position
    insert_position = data_copy.columns.get_loc('Bwd Segment Size Avg') + 1
    data_copy.insert(insert_position, 'fwd_header_length', 0)

    # Clean data for NaN or infinite values
    data_copy.replace([np.inf, -np.inf], np.nan, inplace=True)
    data_copy.dropna(inplace=True)
    # Preprocess the data
    x = preprocess_data(data1)

    # Make predictions using the classification model
    predictions = classifier_model.predict(x)

    # Apply critic model for validation
    valid_predictions = apply_critic_model(x, predictions, critic_model)

    # Update 'Label' column based on valid_predictions
    data1['Label'] = [labels[pred] if pred != -1 else 'Unknow_attack' for pred in valid_predictions]

    # Count predictions
    result_counts = data1['Label'].value_counts()

    # Prepare JSON response with prediction results

    prediction_results = get_prediction_results(predictions, labels, data_copy)
    prediction_results_json = prediction_results.to_dict(orient='records')
    return jsonify({
        'message': 'real_time_flow successfully',
        'predictions': prediction_results_json,
        'prediction_counts': result_counts.to_dict()
    }), 200

@socketio.on('connect')
def handle_connect():
    print('Client connected')

@socketio.on('disconnect')
def handle_disconnect():
    print('Client disconnected')


@socketio.on('start_real_time')
def start_real_time():
    global flow_list
    print('Starting real-time flow monitoring')

    while True:
        # Simulate or wait for flow_list update event
        time.sleep(5)  # Example: wait for 5 seconds

        # Process flow_list data
        flow_list_copy = flow_list

        for key, value in zip(data[0].keys(), flow_list_copy):
            # print(key, value)
            data[0][key] = value

        # print(data)
        data1 = pd.DataFrame(data)
        data_copy = data1.copy()

        # Insert a new column at a specific position
        insert_position = data_copy.columns.get_loc('Bwd Segment Size Avg') + 1
        data_copy.insert(insert_position, 'fwd_header_length', 0)

        # Clean data for NaN or infinite values
        data_copy.replace([np.inf, -np.inf], np.nan, inplace=True)
        data_copy.dropna(inplace=True)
        # Preprocess the data
        x = preprocess_data(data1)

        # Make predictions using the classification model
        predictions = classifier_model.predict(x)

        # Apply critic model for validation
        valid_predictions = apply_critic_model(x, predictions, critic_model)

        # Update 'Label' column based on valid_predictions
        data1['Label'] = [labels[pred] if pred != -1 else 'Unknow_attack' for pred in valid_predictions]

        # Count predictions
        result_counts = data1['Label'].value_counts()

        # Prepare JSON response with prediction results

        prediction_results = get_prediction_results(predictions, labels, data_copy)
        prediction_results_json = prediction_results.to_dict(orient='records')
        emit('real_time_response', {
            'message': '实时流量成功',
            'predictions': prediction_results_json,
            'prediction_counts': result_counts.to_dict()
        })


@socketio.on('websocket_flow')
def websocket_flow():
    print('Websocket')
    global flow_list
    flow_list_copy = flow_list

    # print(len(flow_list_copy))
    # print(len(data))

    for key, value in zip(data[0].keys(), flow_list_copy):
        # print(key, value)
        data[0][key] = value

    # print(data)
    data1 = pd.DataFrame(data)
    data_copy = data1.copy()

    # Insert a new column at a specific position
    insert_position = data_copy.columns.get_loc('Bwd Segment Size Avg') + 1
    data_copy.insert(insert_position, 'fwd_header_length', 0)

    # Clean data for NaN or infinite values
    data_copy.replace([np.inf, -np.inf], np.nan, inplace=True)
    data_copy.dropna(inplace=True)
    # Preprocess the data
    x = preprocess_data(data1)

    # Make predictions using the classification model
    predictions = classifier_model.predict(x)

    # Apply critic model for validation
    valid_predictions = apply_critic_model(x, predictions, critic_model)

    # Update 'Label' column based on valid_predictions
    data1['Label'] = [labels[pred] if pred != -1 else 'Unknow_attack' for pred in valid_predictions]

    # Count predictions
    result_counts = data1['Label'].value_counts()

    # Prepare JSON response with prediction results

    prediction_results = get_prediction_results(predictions, labels, data_copy)
    prediction_results_json = prediction_results.to_dict(orient='records')
    emit('real_time_response', {
        'message': '实时流量成功',
        'predictions': prediction_results_json,
        'prediction_counts': result_counts.to_dict()
    })



if __name__ == '__main__':
    socketio.run(app,debug=True, host='0.0.0.0', port=5000)
