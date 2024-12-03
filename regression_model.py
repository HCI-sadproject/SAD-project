import numpy as np
from sklearn.linear_model import LinearRegression
import joblib
import statsmodels.api as sm
import statsmodels.formula.api as smf
import firebase_admin
from firebase_admin import credentials, firestore
import pandas as pd

db = firestore.client()

# 사전 학습된 모델 로드
regression_model = joblib.load("path_to_regression_model.pkl")

def predict_regression(uid):
    user_ref = db.collection("dummy_users").document(uid).collection("time_series")
    data = []

    for doc in user_ref.stream():
        record = doc.to_dict()
        record['date'] = doc.id  # 날짜를 필드로 추가
        data.append(record)

    # 데이터프레임 생성
    df = pd.DataFrame(data)
    print(df.head())

    # 데이터 확인
    df = df[['time', 'depression_score', 'daily_sleep', 'daily_steps', 'daily_sunlight']]
    df['time'] = pd.to_datetime(df['date'])  # 날짜를 날짜 형식으로 변환
    df = df.sort_values(by='time')  # 날짜 순으로 정렬

    # 내일 우울 점수 예측을 위한 데이터 준비
    df['next_depression_score'] = df['depression_score'].shift(-1)  # 내일의 우울 점수 (shift를 사용)

    # 마지막 행을 제외한 데이터 (내일의 우울 점수가 없는 마지막 데이터는 제외)
    df = df.dropna()

    # 데이터 확인
    print(df.head())

    # 혼합 효과 회귀 모델 설정
    # 고정 효과로 depression_score, daily_sleep, daily_steps, daily_sunlight
    mer_model = smf.mixedlm(
        formula="next_depression_score ~ depression_score + daily_sleep + daily_steps + daily_sunlight",
        groups="UID",  # 여기서는 UID가 하나이므로 랜덤 효과를 구분
        data=df
        )
    
    # 모델 피팅
    mer_result = mer_model.fit()
    print(mer_result.summary())
    # 오늘 데이터 (마지막 행) 사용
    today_data = df.iloc[-1:]

    # 오늘의 독립 변수 값
    X_today = today_data[['depression_score', 'daily_sleep', 'daily_steps', 'daily_sunlight']]

    # 예측 수행
    predicted_score = mer_result.predict(X_today)[0]
    print(f"예측된 내일의 우울 점수: {predicted_score}")



    return float(predicted_score)  # 마지막 예측값 반환

def preprocess_data(data):
    # 회귀 모델 입력 형태로 변환
    return np.array(data)