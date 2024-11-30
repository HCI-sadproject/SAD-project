from flask import Flask, request, jsonify, Blueprint
from firebase_utils import fetch_data, save_data, save_to_firestore, fetch_user_data
from model import train_model, predict_next
import numpy as np
from dummy_data import generate_dummy_data
from flask import Flask, request, jsonify
from lstm_model import predict_lstm, prepare_data, train_lstm
from xgboost_model import predict_xgboost
from regression_model import predict_regression
from var_model import predict_var
from firebase_utils import fetch_time_series_data, save_prediction_to_firebase
from firebase_admin import firestore
from datetime import datetime, timedelta

# Blueprint 정의
main = Blueprint('main', __name__)
db = firestore.client()

@main.route("/update-model", methods=["POST"])
def update_model():
    # Firebase에서 데이터 가져오기
    data = fetch_data()
    
    # X (입력 변수)와 y (타겟 변수) 준비
    X = np.array([[d["sunshine_hours"], d["activity_level"]] for d in data])
    y = np.array([d["depression_score"] for d in data])
    
    # 모델 학습
    train_model(X, y)
    return jsonify({"message": "Model updated successfully"})

@main.route("/predict", methods=["POST"])
def predict():
    # 요청 데이터에서 UID 가져오기
    request_data = request.get_json()
    uid = request_data.get("uid")

    if not uid:
        return jsonify({"error": "UID not provided"}), 400

    # Firestore에서 데이터 가져오기
    user_data = fetch_user_data(uid, db)
    if not user_data:
        return jsonify({'error': 'No data found for this user'}), 404
    
    # 날짜 설정
    start_index = 0                  # 9월 1일부터 시작
    end_index = 30                   # 10월 1일까지 학습
    target_index = 31                # 10월 2일 예측

    current_date += timedelta(days=1)

    # 데이터 준비
    X_train, Y_train, X_input = prepare_data(user_data, start_index, end_index, target_index)

    # 모델 학습
    input_shape = (X_train.shape[1], X_train.shape[2])  # (30일, 3 features)
    lstm_model = train_lstm(X_train, Y_train, input_shape)

    # 예측
    lstm_prediction = predict_lstm(lstm_model, X_input)
    

    # XGBoost 모델로 예측 수행 
    #xgboost_prediction = predict_xgboost(uid)

    # 다변량 회귀 모델로 예측 수행
    #regression_prediction = predict_regression(uid)

    # VAR 모델로 예측 수행
    #var_prediction = predict_var(uid)

    # (선택적으로) 결과 평균 또는 특정 기준에 따라 최종 결과 결정
    #final_prediction = (lstm_prediction + xgboost_prediction + regression_prediction + var_prediction) / 4
    final_prediction = lstm_prediction


    # 예측 결과를 Firebase에 저장
    save_prediction_to_firebase(uid, final_prediction)

    return jsonify({"uid": uid, "prediction": final_prediction})




@main.route('/')
def home():
    return "Hello, Flask!"

# Flask 앱 설정
def create_app():
    app = Flask(__name__)

    # Blueprint 등록
    app.register_blueprint(main)

    return app

def configure_routes(app: Flask):
    @app.route("/create-dummy-data", methods=["GET"])
    def create_dummy_data():
        # 더미 데이터 생성
        dummy_data = generate_dummy_data(num_users=5, num_weeks=4)
        
        # Firestore에 저장
        save_to_firestore(dummy_data)

        return "Dummy data successfully created and stored in Firestore!"


@main.route("/save-test-data", methods=["GET"])    
def save_test_data():
        # 사용 예시
    #profile_data = {
    #"name": "John Doe",
    #"age": 25,
    #"seasonal_score": 18,
    #}
    generate_dummy_data(5)
    return "Dummy data successfully created and stored in Firestore!"

# 앱 실행
if __name__ == '__main__':
    app = create_app()
    app.run(debug=True)
