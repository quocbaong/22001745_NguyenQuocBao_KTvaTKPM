const express = require('express');
const mysql = require('mysql2');
const app = express();
const port = 3000;

// Cấu hình kết nối tới service "db" trong docker-compose
const connection = mysql.createConnection({
  host: 'db',
  user: 'user',
  password: 'password',
  database: 'mydb'
});

app.get('/', (req, res) => {
  connection.query('SELECT 1 + 1 AS solution', (error, results) => {
    if (error) {
      res.status(500).send('Lỗi kết nối database: ' + error.message);
      return;
    }
    res.send('<h1>Kết nối Node.js với MySQL thành công!</h1><p>Kết quả truy vấn thử nghiệm (1+1) từ MySQL là: ' + results[0].solution + '</p>');
  });
});

app.listen(port, () => {
  console.log(`App Bài 8 đang chạy tại http://localhost:${port}`);
});
