-- Khởi tạo cấu trúc bảng cho database
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Thêm dữ liệu mẫu
INSERT INTO users (username, email) VALUES 
('admin', 'admin@example.com'),
('quocbao', 'bao@example.com'),
('docker_expert', 'expert@example.com');
