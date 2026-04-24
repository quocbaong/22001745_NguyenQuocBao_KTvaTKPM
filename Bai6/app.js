const express = require('express');
const app = express();
const port = 3000;

app.get('/', (req, res) => {
  res.send('<h1>Bài 6: Multi-stage Node.js App</h1><p>Image này được tối ưu bằng Multi-stage build.</p>');
});

app.listen(port, () => {
  console.log(`App listening at http://localhost:${port}`);
});
