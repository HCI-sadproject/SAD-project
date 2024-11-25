from sklearn.linear_model import LinearRegression
import joblib
import pandas as pd

# 데이터 로드
data = pd.read_csv('training_data.csv')
X = data[['solar_radiation', 'spaq_score']]
y = data['sad_symptoms']

# 모델 학습
model = LinearRegression()
model.fit(X, y)

# 학습된 모델 저장
joblib.dump(model, 'sad_model.pkl')
