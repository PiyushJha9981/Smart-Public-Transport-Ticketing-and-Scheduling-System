import pandas as pd
import numpy as np
from sklearn.linear_model import LinearRegression
import joblib

# Create dummy dataset
np.random.seed(42)
data = {
    "distance_km": np.random.randint(5, 50, 200),
    "traffic_level": np.random.randint(1, 10, 200),
    "weather_factor": np.random.randint(1, 5, 200),
}

df = pd.DataFrame(data)
df["delay_minutes"] = (
    df["distance_km"] * 0.5 +
    df["traffic_level"] * 2 +
    df["weather_factor"] * 3 +
    np.random.randint(0, 10, 200)
)

X = df[["distance_km", "traffic_level", "weather_factor"]]
y = df["delay_minutes"]

model = LinearRegression()
model.fit(X, y)

joblib.dump(model, "punctuality_model.pkl")

print("Model trained & saved.")