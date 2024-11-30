from firebase_admin import firestore

# Firebase 초기화
cred = credentials.Certificate("serviceAccountKey.json")
firebase_admin.initialize_app(cred)
db = firestore.client()

def fetch_data():
    docs = db.collection("your_collection_name").stream()
    data = []
    for doc in docs:
        data.append(doc.to_dict())
    return data

def save_data(new_data):
    db.collection("your_collection_name").add(new_data)