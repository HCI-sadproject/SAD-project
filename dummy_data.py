import random
import firebase_admin
from firebase_admin import credentials, firestore, auth
from datetime import datetime, timedelta
import json
import uuid
from faker import Faker

# Faker 인스턴스 생성 (이메일과 이름 등을 무작위로 생성)
fake = Faker()

# UID 생성기 (uuid를 사용해 고유한 UID 생성)
def generate_uid():
    return str(uuid.uuid4())  # 고유한 UUID를 생성하여 반환

# 이메일 생성기 (Faker를 이용한 예시 이메일 생성)
def generate_email(name):

    
    return fake.email()  # 무작위 이메일 생성

# 계절성 점수는 46점 만점 중에서 랜덤으로 설정
def generate_seasonal_score():
    return random.randint(5, 46)

# 각 주별 우울감 점수 생성 (주간 단위로)
def generate_depression_score(sunshine_hours, sleep_hours, steps, seasonal_score):
    # 간단한 선형 관계로 우울감 점수를 계산 (계절성 점수, 일조시간, 활동량을 기반)
    base_score = 20  # 기본 우울감 점수
    sunshine_impact = (sunshine_hours - 5) * 2  # 일조시간이 5시간을 초과하면 긍정적인 영향
    sleep_impact = (sleep_hours - 7) * 1.5  # 수면 시간이 7시간을 넘으면 긍정적인 영향
    steps_impact = (steps - 3000) * 0.5  # 걸음 수가 3000보를 넘으면 긍정적인 영향
    seasonal_impact = (seasonal_score - 23) * 0.5  # 계절성 점수는 중립적으로 영향을 미친다고 가정
    
    # 최종 우울감 점수 (간단한 예시로 가정)
    depression_score = base_score + sunshine_impact + sleep_impact + steps_impact + seasonal_impact
    depression_score = max(0, min(depression_score, 46))  # 우울감 점수는 0 ~ 46 사이로 제한
    
    return round(depression_score, 1)

def generate_dummy_data(num_users=5):
    db = firestore.client()
    for num in range(1, num_users + 1):
        # Firebase Authentication에 사용자 생성

        name = fake.name()
        GSS = round(random.randint(5, 46),2)
        age = round(random.randint(18, 40),0)
        user = auth.create_user(
            email=f"{name.replace(' ', '')}{num}@example.com",
            password=f"secure123"
        )

        profile_data = {
            "name": name,
            "age": age,
            "seasonal_score": GSS,
        }
        uid = user.uid  # 생성된 사용자의 UID 가져오기
        uid = "LY40eqAQxBOPTS0EvINK5YbHcRs1"  # 생성된 사용자의 UID 가져오기


        # Firestore에 사용자 데이터 저장
        #db.collection("dummy_users").document(uid).set({
        #    "profile": profile_data
        #})
        print(f"User created with UID: {uid}")

        start_date = datetime(2024, 9, 1)
        end_date = datetime(2024, 12, 5)
        previous_depression_score = random.uniform(11, 24)  # 초기 우울감 점수 랜덤 설정

        # Firestore 저장
        current_date = start_date
        weekday = 7 
        week_steps = []
        week_sunlight = []
        week_sleep = []
        while current_date <= end_date:
            # 요일
            if weekday == 7: 
                weekday = 1
            else:
                weekday += 1

            # 매일 걸음수, 수면시간, 일조시간 생성
            daily_steps = random.randint(100,9000)   # 2000~10000 걸음
            daily_sunlight = random.uniform(1, 7)  # 1~9시간 (가을,겨울임을 고려)
            daily_sleep = random.uniform(4, 12)   # 4~12시간

            week_steps.append(daily_steps)
            week_sunlight.append(daily_sunlight)
            week_sleep.append(daily_sleep)

            if weekday == 1:
                # 우울감 점수 계산 (간단한 선형 모델 기반)

                # 주간 평균 계산
                avg_steps = sum(week_steps) / len(week_steps)
                avg_sunlight = sum(week_sunlight) / len(week_sunlight)
                avg_sleep = sum(week_sleep) / len(week_sleep)                
                depression_score = (
                    previous_depression_score *0.97  # 가중치 0.97
                        + (avg_steps-6000) * -0.00001 * (1 + GSS / 46)  # 계절성 점수로 가중
                        + (avg_sunlight-4) * -0.02 * (1 + GSS / 46)  # 계절성 점수로 가중
                        + (9-avg_sleep) * -0.01 * (1 + GSS / 92)  # 계절성 점수로 가중 (영향력 낮음)
                        + random.uniform(-5, 5)  # 랜덤 노이즈
                    )
                depression_score = max(0, min(24, depression_score))  # 0~24로 제한
                # 수정된 부분: `previous_depression_score`를 여기서만 업데이트
                previous_depression_score = depression_score  # 다음 주에 사용할 값으로 저장
                                # 주간 데이터 초기화
                week_steps.clear()
                week_sunlight.clear()




            # 데이터 저장 (Firestore 하위 컬렉션에 저장)
            # 수정된 부분: `time_series`라는 하위 컬렉션에 데이터를 저장
            db.collection("dummy_users").document(uid).collection("time_series").document(current_date.strftime("%Y-%m-%d")).set({
                "time": current_date.strftime("%Y-%m-%d"),  # 저장 날짜
                "depression_score": round(depression_score, 2),  # 마지막 주의 우울감 점수
                "daily_steps": round(daily_steps, 2),
                "daily_sunlight": round(daily_sunlight, 2),
                "daily_sleep": round(daily_sleep, 2),
                "week_day":weekday
            })

            previous_depression_score = depression_score  # 다음 주에 사용
            # 다음 날짜로 이동
            current_date += timedelta(days=1)





