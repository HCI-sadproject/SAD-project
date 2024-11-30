import requests

url = "http://127.0.0.1:5000/save-test-data"
response = requests.get(url)

print(response.text)  # "Test data successfully created and stored in Firestore!"