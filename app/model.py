import joblib
import numpy as np

def load_model():
    return joblib.load('sad_model.pkl')

def predict_sad(features, model):
    features = np.array(features).reshape(1, -1)
    return model.predict(features)[0]

