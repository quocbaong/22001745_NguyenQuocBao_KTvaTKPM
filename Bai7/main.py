import os

# Đọc biến môi trường APP_ENV
app_env = os.getenv('APP_ENV', 'unknown')

print("-" * 40)
print(f"DOCKER STATUS: SUCCESS")
print(f"APP_ENV VALUE: {app_env}")
print(f"MESSAGE: Ứng dụng đang chạy trong môi trường {app_env}")
print("-" * 40)
