# Sử dụng php:8.2-apache làm base image
FROM php:8.2-apache

# Thiết lập thư mục làm việc mặc định của Apache
WORKDIR /var/www/html/

# Sao chép mã nguồn vào container (để chạy mặc định)
COPY index.php .

# Mở cổng 80
EXPOSE 80
