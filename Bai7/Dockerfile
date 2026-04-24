# Sử dụng python:3.9-slim làm base image
FROM python:3.9-slim

# Thiết lập thư mục làm việc
WORKDIR /app

# Sao chép file mã nguồn vào container
COPY main.py .

# Thiết lập biến môi trường mặc định bằng lệnh ENV
ENV APP_ENV=development

# Lệnh khởi chạy ứng dụng
CMD ["python", "main.py"]
