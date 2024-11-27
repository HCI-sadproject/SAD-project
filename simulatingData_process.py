import pandas as pd
import numpy as np

# Set seed for reproducibility
np.random.seed(42)

# Parameters
n_weeks = 52  # Number of weeks
n_people = 30  # Number of participants

# Generate time-series data
weeks = np.arange(1, n_weeks + 1)

# Functions to simulate seasonal patterns
def simulate_sunshine(week):
    return 8 + 4 * np.sin(2 * np.pi * (week - 1) / 52) + np.random.normal(0, 1)

def simulate_seasonal_score(week):
    return 50 + 20 * np.cos(2 * np.pi * (week - 1) / 52) + np.random.normal(0, 5)

# Data generation for each individual
data = []
for person in range(1, n_people + 1):
    sunshine_hours = [simulate_sunshine(week) for week in weeks]
    seasonal_scores = [simulate_seasonal_score(week) for week in weeks]
    steps = [max(2000, 8000 + np.random.normal(0, 1500)) for _ in weeks]
    sleep_hours = [max(4, np.random.normal(7, 1.5)) for _ in weeks]
    
    # Assume the depression score is influenced by the other variables
    depression_scores = [
        70 - 0.5 * s + 0.3 * ss - 0.01 * step - 1.5 * sleep + np.random.normal(0, 5)
        for s, ss, step, sleep in zip(sunshine_hours, seasonal_scores, steps, sleep_hours)
    ]

    for week, sunshine, seasonal, step, sleep, depression in zip(
        weeks, sunshine_hours, seasonal_scores, steps, sleep_hours, depression_scores
    ):
        data.append({
            "Person": person,
            "Week": week,
            "Sunshine_Hours": sunshine,
            "Seasonal_Score": seasonal,
            "Steps": step,
            "Sleep_Hours": sleep,
            "Depression_Score": depression
        })

# Convert to DataFrame
df = pd.DataFrame(data)

# Save to CSV
output_path = "C:/Users/KJY/SAD_Management/simulated_sad_data.csv"
df.to_csv(output_path, index=False)

output_path

