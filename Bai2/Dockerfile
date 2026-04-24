# Sử dụng node:16-alpine làm base image
FROM node:16-alpine

# Thiết lập thư mục làm việc
WORKDIR /app

# Sao chép package.json và cài đặt dependencies
COPY package.json .
RUN npm install

# Sao chép toàn bộ mã nguồn
COPY . .

# Mở cổng 5000
EXPOSE 5000

# Lệnh chạy ứng dụng
CMD ["npm", "start"]
