# Mini Food Ordering System - Microservices

Hệ thống đặt món ăn nội bộ được xây dựng trên kiến trúc Microservices với Spring Boot, ReactJS và Docker.

## Kiến trúc hệ thống

Hệ thống bao gồm 8 microservices:

1.  **API Gateway (8080)**: Điểm truy cập duy nhất, xử lý Routing, Circuit Breaker, Rate Limiting và Retry.
2.  **User Service (8081)**: Quản lý người dùng, Đăng ký, Đăng nhập (JWT).
3.  **Food Service (8082)**: Quản lý danh sách món ăn, CRUD.
4.  **Order Service (8083)**: Xử lý đặt hàng, gọi Food & User service để validate.
5.  **Payment Service (8084)**: Xử lý thanh toán (COD/Banking), cập nhật trạng thái đơn hàng.
6.  **Notification Service (8085)**: Gửi thông báo khi đặt hàng thành công (Log console).
7.  **Inventory Service (8086)**: Quản lý tồn kho, giữ hàng khi đặt.
8.  **Shipping Service (8087)**: Quản lý giao hàng và mã vận đơn.
9.  **Frontend (3000)**: Giao diện ReactJS hiện đại, premium.

## Các tính năng Resiliency (Fault Tolerance)

- **Circuit Breaker**: Sử dụng Resilience4j trong API Gateway và Order Service. Nếu một service bị treo, hệ thống sẽ tự động ngắt kết nối và trả về phản hồi fallback nhanh chóng.
- **Retry**: Tự động thử lại các yêu cầu thất bại (tối đa 3 lần) đối với các lỗi mạng tạm thời.
- **Rate Limiter**: Giới hạn số lượng yêu cầu mỗi giây để bảo vệ hệ thống khỏi bị quá tải.
- **Time Limiter**: Đặt timeout cho các yêu cầu quá chậm.

## Hướng dẫn chạy hệ thống

### Yêu cầu
- Docker và Docker Compose
- Java 21 (nếu muốn chạy local không qua Docker)
- Node.js 20+ (nếu muốn chạy frontend local)

### Cách 1: Chạy bằng Docker Compose (Khuyên dùng)

1.  Mở terminal tại thư mục gốc của project.
2.  Chạy lệnh build và khởi động:
    ```bash
    docker-compose up --build
    ```
3.  Truy cập giao diện tại: `http://localhost:3000`
4.  Truy cập API Gateway tại: `http://localhost:8080`

### Cách 2: Chạy local từng service

Bạn cần chạy lần lượt các service theo thứ tự:
1.  Khởi động các service phụ trợ: `inventory`, `notification`, `shipping`.
2.  Khởi động `user-service`, `food-service`.
3.  Khởi động `order-service` và `payment-service`.
4.  Khởi động `api-gateway`.
5.  Chạy frontend: `cd frontend && npm install && npm run dev`.

## Tài khoản Demo
- **Admin**: `admin` / `admin123`
- **User**: `user1` / `user123` hoặc `user2` / `user123`

## Kịch bản Test (Demo)
1.  **Đăng ký/Đăng nhập**: Sử dụng tài khoản `user1`.
2.  **Xem món ăn**: Danh sách món ăn được seed sẵn 10 món.
3.  **Đặt hàng**: Thêm món vào giỏ hàng và tiến hành Thanh toán.
4.  **Thanh toán**: Chọn phương thức COD hoặc Banking.
5.  **Theo dõi**: Kiểm tra tab "Đơn hàng" để xem trạng thái đơn hàng và mã vận đơn (Shipping).
6.  **Thông báo**: Kiểm tra log của `notification-service` hoặc console của Docker để thấy dòng chữ "User X đã đặt đơn #Y thành công".

## Docker Desktop Screenshot
(Sau khi chạy `docker-compose up`, tất cả container sẽ hiện màu xanh trong Docker Desktop)
