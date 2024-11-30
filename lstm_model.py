from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import LSTM, Dense, Dropout
import numpy as np
from firebase_utils import fetch_user_data

# LSTM 모델 정의
def build_lstm_model(input_shape):
    model = Sequential([
        LSTM(64, activation='tanh', return_sequences=True, input_shape=input_shape),
        Dropout(0.2),
        LSTM(32, activation='tanh', return_sequences=False),
        Dropout(0.2),
        Dense(1)  # 다음 주 우울감 점수를 예측
    ])
    model.compile(optimizer='adam', loss='mean_squared_error', metrics=['mae'])
    return model

# 데이터 준비 함수
def prepare_data(data, start_index, end_index, target_index):
    """
    데이터를 슬라이딩 윈도우 방식으로 분리하여 학습 및 예측 데이터 생성.
    """
    # 학습 데이터 슬라이싱
    X = []
    Y = []
    for i in range(start_index, end_index):
        X.append(data[i:i+30, :-1])  # 입력 데이터: daily_sleep, daily_steps, daily_sunlight
        Y.append(data[i+30, -1])    # 타겟 데이터: depression_score

    X = np.array(X)
    Y = np.array(Y)

    # 예측 입력 데이터
    X_input = data[target_index-30:target_index, :-1]  # 마지막 30일 데이터

    return X, Y, X_input.reshape(1, X_input.shape[0], X_input.shape[1])

# 학습 함수
def train_lstm(X_train, Y_train, input_shape):
    model = build_lstm_model(input_shape)
    model.fit(
        X_train, Y_train,
        epochs=50,
        batch_size=32,
        shuffle=False
    )
    return model



# 슬라이딩 윈도우 방식으로 x_train과 y_train 생성
def create_train_data(data, time_steps=30):
    x_train, y_train = [], []

    for i in range(len(data) - time_steps):
        x_train.append(data[i:i+time_steps, :-1])  # 마지막 컬럼 제외 (입력: 수면, 걸음, 일조)
        y_train.append(data[i+time_steps, -1])    # 마지막 컬럼 (타겟: 우울감 점수)

    return np.array(x_train), np.array(y_train)


# 예측 함수
def predict_lstm(model, X_input):
    return model.predict(X_input)