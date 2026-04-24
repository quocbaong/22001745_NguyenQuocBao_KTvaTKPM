# Sử dụng node:18-alpine làm base image
FROM node:18-alpine

# Thiết lập thư mục làm việc trong container
WORKDIR /app

# Sao chép package.json vào trước để install dependencies
COPY package.json .

# Cài đặt dependencies
RUN npm install

# Sao chép toàn bộ mã nguồn vào container
COPY . .

# Mở cổng 3000 (cổng mặc định của React)
EXPOSE 3000

# Biến môi trường để chạy trong container (tránh lỗi watch files)
ENV WDS_SOCKET_PORT=0

# Lệnh để chạy ứng dụng
CMD ["npm", "start"]
