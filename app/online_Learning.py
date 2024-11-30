from flask import Flask, request, jsonify

# Flask 앱 생성
app = Flask(__name__)

# 기본 루트 경로 설정 (예: http://localhost:5000)
@app.route('/')
def home():
    return "Hello, Flask!"

# 서버 실행
if __name__ == "__main__":
    app.run(debug=True)

    # 모델 로드
model = joblib.load('model.pkl')

@app.route('/predict', methods=['POST'])
def predict():
    data = request.json  # 요청 데이터 받기
    features = data['features']
    prediction = model.predict([features])  # 모델 예측
    return jsonify({'prediction': prediction[0]})

if __name__ == '__main__':
    app.run(debug=True)
