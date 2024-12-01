from flask import Flask, request, jsonify, Blueprint
from firebase_utils import fetch_data, save_data
from model import train_model, predict_next
import numpy as np

# Blueprint 정의
main = Blueprint('main', __name__)

@main.route("/update-model", methods=["POST"])
def update_model():
    # Firebase에서 데이터 가져오기
    data = fetch_data()
    
    # X (입력 변수)와 y (타겟 변수) 준비
    X = np.array([[d["sunshine_hours"], d["activity_level"]] for d in data])
    y = np.array([d["depression_score"] for d in data])
    
    # 모델 학습
    train_model(X, y)
    return jsonify({"message": "Model updated successfully"})

@main.route("/predict", methods=["POST"])
def predict():
    # JSON으로 새 데이터 받기
    new_data = request.json
    X_new = np.array([[new_data["sunshine_hours"], new_data["activity_level"]]])
    
    # 예측
    predicted_score = predict_next(X_new)
    
    # Firebase에 새 데이터 저장
    save_data(new_data)
    
    return jsonify({"predicted_score": predicted_score.tolist()[0]})

@main.route('/')
def home():
    return "Hello, Flask!"

# Flask 앱 설정
def create_app():
    app = Flask(__name__)

    # Blueprint 등록
    app.register_blueprint(main)

    return app

# 앱 실행
if __name__ == '__main__':
    app = create_app()
    app.run(debug=True)
