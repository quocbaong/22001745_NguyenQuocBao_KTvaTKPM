const express = require('express');
const app = express();
const port = 3000;

app.get('/', (req, res) => {
  res.send('<h1>Hello from Node.js Express in Docker!</h1><p>Bài 4 đã chạy thành công.</p>');
});

app.listen(port, () => {
  console.log(`App đang chạy tại http://localhost:${port}`);
});
