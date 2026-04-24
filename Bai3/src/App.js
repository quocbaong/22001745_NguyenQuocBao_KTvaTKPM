import React from 'react';
import './App.css';

function App() {
  return (
    <div className="container">
      <div className="blob blob-1"></div>
      <div className="blob blob-2"></div>
      
      <div className="logo-container">
        <svg fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M19.428 15.428a2 2 0 00-1.022-.547l-2.387-.477a6 6 0 00-3.86.517l-.642.316a6 6 0 01-3.86.517L6.05 15.21a2 2 0 00-1.823.362 2.25 2.25 0 00-1.027 1.832V18a2.25 2.25 0 002.25 2.25h13.125A2.25 2.25 0 0021 18v-1.125a2.25 2.25 0 00-1.572-2.147zM12 7a4 4 0 110-8 4 4 0 010 8z"></path>
        </svg>
      </div>

      <h1>React Docker</h1>
      <p>Ứng dụng React của bạn đã sẵn sàng và đang chạy mượt mà bên trong Docker Container.</p>
      
      <div className="status-badge">
        Container Status: Running
      </div>

      <div style={{ marginTop: '40px', fontSize: '0.8rem', color: '#64748b' }}>
        Built with Node 18 Alpine
      </div>
    </div>
  );
}

export default App;
