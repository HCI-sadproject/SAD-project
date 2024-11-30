import firebase_admin
from firebase_admin import firestore
from firebase_admin import credentials
import numpy as np
from datetime import datetime

cred = credentials.Certificate("sadhci-firebase-adminsdk-zityr-c12b160839.json")
firebase_admin.initialize_app(cred)
db = firestore.client()

def fetch_data():
    docs = db.collection("your_collection_name").stream()
    data = []
    for doc in docs:
        data.append(doc.to_dict())
    return data

def fetch_user_data(uid, db):
    time_series_ref = db.collection("dummy_user").document(uid).collection("time_series")
    docs = time_series_ref.order_by("time").stream()

    # 데이터 저장용 리스트
    data = []
    for doc in docs:
        doc_data = doc.to_dict()
        # daily_sleep, daily_steps, daily_sunlight, depression_score 순으로 저장
        data.append([
            doc_data.get("daily_sleep", 0),       # 수면 시간
            doc_data.get("daily_steps", 0),       # 걸음 수
            doc_data.get("daily_sunlight", 0),    # 일조 시간
            doc_data.get("depression_score", 0)   # 우울감 점수
        ])
    return np.array(data)

def save_data(new_data):
    db.collection("your_collection_name").add(new_data)

def save_to_firestore(data):
    global db
    user_collection = db.collection("users")  # Firestore 컬렉션 이름

    for uid, records in data.items():
        user_doc = user_collection.document(uid)
        user_doc.set({"uid": uid})  # 사용자 메타데이터
        for record in records:
            user_doc.collection("weekly_data").add(record)    


    # 예측 결과를 Firebase에 저장
def save_prediction_to_firebase(uid, final_prediction, current_date = datetime.now().strftime("%Y-%m-%d")):
    
    global db
    current_date = datetime.now().strftime("%Y-%m-%d")
    db.collection("dummy_users").document(uid).collection("time_series").document(current_date.strftime("%Y-%m-%d")).set({
                "time": current_date.strftime("%Y-%m-%d"),  # 저장 날짜
                "final_prediction" : final_prediction
            })
    
def get_user_timeseries(uid):
    user_ref = db.collection('dummy_users').document(uid).collection("time_series")
    user_data = user_ref.get().to_dict()
    return user_data
