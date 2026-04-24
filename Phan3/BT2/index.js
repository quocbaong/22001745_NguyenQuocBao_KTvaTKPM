const express = require('express');
const mongoose = require('mongoose');
const app = express();
const port = 3000;

const mongoUrl = process.env.MONGO_URL || 'mongodb://mongodb:27017/mydb';

mongoose.connect(mongoUrl)
  .then(() => console.log('Kết nối MongoDB thành công!'))
  .catch(err => console.error('Lỗi kết nối MongoDB:', err));

app.get('/', (req, res) => {
  res.send('<h1>Node.js + MongoDB API</h1><p>Trạng thái: Đã kết nối Database thành công.</p>');
});

app.listen(port, () => {
  console.log(`Server Bài 2 đang chạy tại http://localhost:3003 (mapped from 3000)`);
});
