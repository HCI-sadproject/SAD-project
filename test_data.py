import random
import firebase_admin
from firebase_admin import credentials, firestore, auth
from datetime import datetime, timedelta
import json
import uuid
from faker import Faker
db = firestore.client()

def generate_test_data():
    data = {}
    user_data = []
    # 데이터 저장
    user_data.append({
        "week": 1,
        "depression_score": 15,
        "avg_steps": 6000,
        "avg_sunlight": 5.5,
        "avg_sleep": 10
        })
    data['xdLYWgNkWoaME8nkPDQIdBFqTAb2'] = user_data

    return data


def create_user_and_store_in_firestore(email, password, profile_data):
    # Firebase Authentication에 사용자 생성
    user = auth.create_user(
        email=email,
        password=password
    )
    uid = user.uid  # 생성된 사용자의 UID 가져오기
    
    # 주간 평균 계산
    #avg_steps = sum(daily_steps) / len(daily_steps)
    #avg_sunlight = sum(daily_sunlight) / len(daily_sunlight)
    #avg_sleep = sum(daily_sleep) / len(daily_sleep)
    
    # Firestore에 사용자 데이터 저장
    db.collection("users").document(uid).set({
        "profile": profile_data
    })
    print(f"User created with UID: {uid}")


