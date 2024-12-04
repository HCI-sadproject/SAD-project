import pandas as pd
import numpy as np
from statsmodels.tsa.api import VAR
from statsmodels.tools.eval_measures import aic, bic
from datetime import datetime, timedelta
from firebase_admin import firestore

# Firestore 클라이언트 초기화
db = firestore.client()

def fetch_user_data(uid):
    time_series_ref = db.collection("dummy_users").document(uid).collection("time_series")

    try:
        # Firestore에서 time 필드 기준 정렬 후 데이터 가져오기
        docs = time_series_ref.order_by("time").stream()

        # 데이터 저장용 리스트
        data = []
        dates = []  # 날짜를 저장할 리스트
        for doc in docs:
            doc_data = doc.to_dict()
            #print(f"Fetched document: {doc.id}, Data: {doc_data}")

            # 필수 필드 확인
            if "time" not in doc_data:
                print(f"Missing 'time' field in document: {doc.id}")
            else:
                #print(f"Document {doc.id}, time field: {doc_data['time']}")
                dates.append(doc_data['time'])

            # daily_sleep, daily_steps, daily_sunlight, depression_score 순으로 저장
            data.append([
                doc_data.get("daily_sleep", 0),       # 수면 시간
                doc_data.get("daily_steps", 0),       # 걸음 수
                doc_data.get("daily_sunlight", 0),    # 일조 시간
                doc_data.get("depression_score", 0)   # 우울감 점수
            ])

        # Numpy 배열로 변환
        user_data = pd.DataFrame(data)

        # 배열이 비어 있는지 확인
        if user_data.size == 0:
            print(f"No data found for UID: {uid}")
            return None
        else:
            return user_data
        

    except Exception as e:
        print(f"Error fetching data for UID {uid}: {e}")
        return None


def prepare_var_data(user_data):
    # 적절한 컬럼 이름을 할당
    user_data.columns = ['daily_sunshine_hours', 'daily_sleep', 'daily_steps', 'depression_score']
    
    # 'depression_score'에 대한 lag 생성
    user_data['depression_score_lag'] = user_data['depression_score'].shift(1)

    # 결측치 처리
    user_data['depression_score'] = user_data['depression_score'].fillna(method='ffill')

    # NaN 값 제거 (첫 번째 행은 lag 값이 없으므로 삭제)
    user_data.dropna(inplace=True)

    # 예측하려는 변수 (Y: depression_score)
    Y = user_data['depression_score']

    # 입력 변수 (X: sunshine_hours, sleep, steps, lagged depression_score)
    X = user_data[['daily_sunshine_hours', 'daily_sleep', 'daily_steps', 'depression_score_lag']]

    return X, Y


# VAR 모델을 여러 차수에 대해 학습하고 AIC/BIC 값을 계산하여 최적 차수 선택
def find_best_lag_order(X,Y):
    # 데이터 준비

    model_data = pd.concat([X, Y], axis=1)
    
    # 가능한 차수 범위 설정 (예: 1부터 30까지)
    best_aic = np.inf
    best_bic = np.inf
    best_lag = 0
    
    for lag in range(1, 31):  # 1~30 차수 시도
        model = VAR(model_data)
        var_model = model.fit(lag)
        
        current_aic = var_model.aic
        current_bic = var_model.bic
        
        if current_aic < best_aic:
            best_aic = current_aic
            best_lag = lag
        
        if current_bic < best_bic:
            best_bic = current_bic
            best_lag = lag
    print("Best lag : ", best_lag)
    
    return best_lag


def train_var_model(X,Y):
    """
    data: pd.DataFrame (각 사용자 데이터)
    반환 값: 학습된 VAR 모델
    """
    # 데이터 준비 (X, Y)

    # X와 Y를 합친 데이터프레임 (VAR 모델에 입력될 데이터)
    model_data = pd.concat([X, Y], axis=1)
    best_lag_order = find_best_lag_order(X,Y)

    # VAR 모델 학습
    model = VAR(model_data)
    var_model = model.fit(best_lag_order)  # best_lag_order는 모델 차수 (시점 수), 데이터를 기반으로  AIC 또는 BIC 등을 사용하여 자동으로 선택

    # 학습된 모델 정보 출력
    print("VAR model trained.")

    return var_model

def predict_var(uid):
    #"""주어진 UID에 대해 VAR 모델을 사용해 예측을 수행하는 함수."""
    user_data = fetch_user_data(uid)
    
    if user_data is None:
        print("No data found for user.")
        return None  # 사용자의 데이터가 없으면 None 반환
    #print(user_data)
    print(user_data.columns)

    # 데이터 준비
    X, Y = prepare_var_data(user_data)

    #print(X)
    #print(Y)
    #print(X.columns)

    # VAR 모델 학습
    var_model = train_var_model(X,Y)

    # 예측: 마지막 데이터를 사용하여 1일 예측
    forecast = var_model.forecast(user_data.values[-var_model.k_ar:], steps=1)

    # 예측된 값 (예: depression_score) 확인
    print(f"Forecasted values: {forecast}")
    
    # 예측된 값 (depression_score) 반환
    predicted_value = forecast[0][0]
    print(f"Predicted depression score: {predicted_value}")

    # 예측된 값 (예: depression_score) 반환
    predicted_value = forecast[0][0]  # 예시에서는 depression_score 예측
    return predicted_value
