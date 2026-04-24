<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>PHP Docker App</title>
    <style>
        body { font-family: 'Segoe UI', Tahoma, sans-serif; text-align: center; background: #eff6ff; padding: 50px; }
        .card { background: white; padding: 30px; border-radius: 15px; box-shadow: 0 10px 25px rgba(0,0,0,0.05); display: inline-block; }
        h1 { color: #4F5B93; margin-top: 0; }
        .version { background: #4F5B93; color: white; padding: 5px 10px; border-radius: 5px; font-size: 0.9rem; }
    </style>
</head>
<body>
    <div class="card">
        <h1>PHP Apache Docker</h1>
        <p>Đây là ứng dụng PHP chạy trên Apache server bên trong Docker.</p>
        <p><strong>PHP Version:</strong> <span class="version"><?php echo phpversion(); ?></span></p>
        <p><strong>Server Time:</strong> <?php echo date('H:i:s d-m-Y'); ?></p>
        <hr>
        <p style="color: #2563eb; font-weight: bold;">
            💡 Mẹo: Hãy thử sửa file index.php này trên máy host,<br>
            trình duyệt sẽ cập nhật ngay lập tức nhờ vào Mount Volume!
        </p>
    </div>
</body>
</html>
