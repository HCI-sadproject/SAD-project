import xgboost as xgb
import numpy as np

# 사전 학습된 모델 로드
xgboost_model = xgb.Booster()
xgboost_model.load_model("path_to_xgboost_model.json")

def predict_xgboost(user_data):
    """
    XGBoost 모델로 사용자 데이터를 기반으로 예측
    user_data: 사용자 데이터 (numpy 배열 또는 Pandas DataFrame)
    """
    # 데이터 전처리
    processed_data = preprocess_data(user_data)

    # 모델 예측
    dmatrix = xgb.DMatrix(processed_data)
    prediction = xgboost_model.predict(dmatrix)

    return float(prediction[-1])  # 마지막 예측값 반환

def preprocess_data(data):
    # XGBoost 입력 형태로 변환
    return np.array(data)