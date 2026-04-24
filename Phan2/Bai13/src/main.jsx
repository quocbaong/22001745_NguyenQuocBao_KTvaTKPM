import React from 'react'
import ReactDOM from 'react-dom/client'

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <div style={{ textAlign: 'center', marginTop: '50px', fontFamily: 'Arial' }}>
      <h1 style={{ color: '#61dafb' }}>Chào mừng đến với React + Nginx trong Docker!</h1>
      <p>Bài 13 đã chạy thành công qua quy trình Multi-stage build.</p>
    </div>
  </React.StrictMode>
)
