# Sử dụng postgres:15 làm base image
FROM postgres:15

# Sao chép file SQL vào thư mục tự động chạy của Postgres
# Các file trong thư mục này sẽ được thực thi khi container khởi tạo DB lần đầu
COPY init.sql /docker-entrypoint-initdb.d/

# Thiết lập các biến môi trường mặc định
ENV POSTGRES_DB=my_database
ENV POSTGRES_USER=my_user
ENV POSTGRES_PASSWORD=my_password
