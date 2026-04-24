# Stage 1: Build stage
FROM golang:1.21-alpine AS builder

# Thiết lập thư mục làm việc
WORKDIR /app

# Sao chép mã nguồn vào container
COPY . .

# Build ứng dụng Go thành file thực thi (binary)
RUN go build -o main main.go

# Stage 2: Runtime stage
FROM alpine:latest

WORKDIR /root/

# Sao chép file binary từ build stage sang runtime stage
COPY --from=builder /app/main .

# Thông báo cổng 8081
EXPOSE 8081

# Lệnh khởi chạy ứng dụng
CMD ["./main"]
