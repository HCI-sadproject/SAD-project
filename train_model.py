from sklearn.linear_model import SGDRegressor
from sklearn.preprocessing import StandardScaler
import numpy as np

# 온라인 학습 모델 초기화
model = SGDRegressor(max_iter=1000, tol=1e-3)
scaler = StandardScaler()

def train_online_model(X_train, y_train):
    global model, scaler
    # 데이터 스케일링
    X_train_scaled = scaler.fit_transform(X_train)
    # 온라인 학습
    model.partial_fit(X_train_scaled, y_train)
    return model
