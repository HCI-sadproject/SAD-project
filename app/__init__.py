from flask import Flask
from firebase_admin import credentials, initialize_app

def create_app():
    app = Flask(__name__)

    # Firebase 초기화
    #cred = credentials.Certificate('sadhci-firebase-adminsdk-brw57-32beea99c0.json')
    #initialize_app(cred)

    # 블루프린트 등록
    from routes import main
    app.register_blueprint(main)

    return app
