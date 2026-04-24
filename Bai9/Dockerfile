# Sử dụng redis:latest làm base image
FROM redis:latest

# Sao chép file cấu hình tùy chỉnh vào container
COPY redis.conf /usr/local/etc/redis/redis.conf

# Thông báo cổng 6379
EXPOSE 6379

# Khởi chạy Redis server sử dụng file cấu hình tùy chỉnh
CMD ["redis-server", "/usr/local/etc/redis/redis.conf"]
