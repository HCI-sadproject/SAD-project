import pickle
from sklearn.linear_model import LinearRegression
import numpy as np

# 초기 모델 설정
try:
    with open("sad_model.pkl", "rb") as f:
        model = pickle.load(f)
except FileNotFoundError:
    model = LinearRegression()

def train_model(X, y):
    model.fit(X, y)
    with open("sad_model.pkl", "wb") as f:
        pickle.dump(model, f)

def predict_next(data):
    return model.predict(data)
>>>>>>> user-weather
