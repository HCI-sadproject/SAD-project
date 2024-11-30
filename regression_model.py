import numpy as np
from sklearn.linear_model import LinearRegression
import joblib

# 사전 학습된 모델 로드
regression_model = joblib.load("path_to_regression_model.pkl")

def predict_regression(user_data):
    """
    다변량 회귀 모델로 사용자 데이터를 기반으로 예측
    user_data: 사용자 데이터 (numpy 배열 또는 Pandas DataFrame)
    """
    # 데이터 전처리
    processed_data = preprocess_data(user_data)

    # 모델 예측
    prediction = regression_model.predict(processed_data)

    return float(prediction[-1])  # 마지막 예측값 반환

def preprocess_data(data):
    # 회귀 모델 입력 형태로 변환
    return np.array(data)