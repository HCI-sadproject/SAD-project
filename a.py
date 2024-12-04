import random

steps = []
sunlight = []
sleep = []
for i in range(1,8):
    daily_steps = random.randint(100,9000)   # 2000~10000 걸음
    daily_sunlight = round(random.uniform(0, 24),2)  # 1~9시간 (가을,겨울임을 고려)
    daily_sleep = round(random.uniform(4, 12),2)   # 4~12시간
    steps.append(daily_steps)
    sunlight.append(daily_sunlight)
    sleep.append(daily_sleep)

print(steps)
print(sunlight)
print(sleep)

