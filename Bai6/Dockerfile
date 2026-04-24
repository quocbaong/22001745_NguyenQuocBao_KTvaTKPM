# Stage 1: Build stage (Sử dụng node:18 để build)
FROM node:18 AS builder

# Thiết lập thư mục làm việc
WORKDIR /app

# Sao chép package.json và cài đặt dependencies
COPY package*.json ./
RUN npm install

# Sao chép toàn bộ mã nguồn
COPY . .

# Bước này có thể là build TypeScript, Babel, v.v.
RUN echo "Building the application..."

# Stage 2: Production stage (Sử dụng node:18-alpine để chạy)
FROM node:18-alpine

# Thiết lập thư mục làm việc
WORKDIR /app

# Chỉ sao chép những file cần thiết từ stage builder sang
COPY --from=builder /app/package*.json ./
COPY --from=builder /app/app.js ./

# Chỉ cài đặt dependencies cho production
RUN npm install --only=production

# Mở cổng 3000
EXPOSE 3000

# Lệnh khởi chạy ứng dụng
CMD ["node", "app.js"]
