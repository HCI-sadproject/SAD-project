import tensorflow as tf
from sklearn.preprocessing import MinMaxScaler

# Prepare data for LSTM (sliding window approach)
def create_lstm_data(data, features, target, window_size=4):
    X, y = [], []
    for i in range(len(data) - window_size):
        X.append(data[features].iloc[i:i+window_size].values)
        y.append(data[target].iloc[i+window_size])
    return np.array(X), np.array(y)

# Normalize features for LSTM input
scaler = MinMaxScaler()
scaled_features = scaler.fit_transform(person_data[features])
scaled_person_data = person_data.copy()
scaled_person_data[features] = scaled_features

# Split into initial training and remaining data
train_data = scaled_person_data[scaled_person_data["Week"].isin(autumn_winter_weeks)]
test_data = scaled_person_data[~scaled_person_data["Week"].isin(autumn_winter_weeks)]

# Create LSTM training data
window_size = 4
X_train, y_train = create_lstm_data(train_data, features, target, window_size)

# Build LSTM model
lstm_model = tf.keras.Sequential([
    tf.keras.layers.LSTM(50, activation='relu', input_shape=(window_size, len(features))),
    tf.keras.layers.Dense(1)
])
lstm_model.compile(optimizer='adam', loss='mse')

# Train the model on initial data
lstm_model.fit(X_train, y_train, epochs=50, verbose=0)

# Simulate week-by-week update and prediction
predictions = []
for week in test_data["Week"].unique():
    # Use sliding window approach to predict for the current week
    week_index = test_data[test_data["Week"] == week].index[0]
    if week_index < window_size:
        continue  # Skip weeks where we don't have enough prior data
    
    X_week = np.array([scaled_person_data[features].iloc[week_index-window_size:week_index].values])
    predicted = lstm_model.predict(X_week)[0][0]
    
    # Record prediction and actual value
    actual = scaled_person_data.loc[week_index, target]
    predictions.append({
        "Week": week,
        "Actual_Depression_Score": actual,
        "Predicted_Depression_Score": predicted
    })
    
    # Update model with new week's data
    new_X, new_y = create_lstm_data(scaled_person_data.iloc[:week_index+1], features, target, window_size)
    lstm_model.fit(new_X, new_y, epochs=5, verbose=0)  # Train incrementally

# Convert predictions to DataFrame
lstm_results = pd.DataFrame(predictions)
lstm_results.head()  # Show the first few predictions
