import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import { AuthProvider, useAuth } from './context/AuthContext';
import { CartProvider } from './context/CartContext';

import Navbar from './components/Navbar';
import Home from './pages/Home';
import Login from './pages/Login';
import Register from './pages/Register';
import Cart from './pages/Cart';
import Orders from './pages/Orders';

const ProtectedRoute = ({ children }) => {
  const { user, loading } = useAuth();
  if (loading) return null;
  if (!user) return <Navigate to="/login" />;
  return children;
};

const AppContent = () => {
  return (
    <div className="min-h-screen flex flex-col">
      <Navbar />
      <div className="flex-1">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/cart" element={<Cart />} />
          <Route 
            path="/orders" 
            element={
              <ProtectedRoute>
                <Orders />
              </ProtectedRoute>
            } 
          />
        </Routes>
      </div>
      
      {/* Footer */}
      <footer className="bg-secondary text-white py-12 mt-20">
        <div className="container grid grid-cols-1 md:grid-cols-4 gap-12">
          <div className="col-span-2">
            <h2 className="text-2xl font-bold text-primary mb-6">MiniFood</h2>
            <p className="text-gray-400 max-w-sm">
              Hệ thống đặt món ăn nội bộ chuyên nghiệp, nhanh chóng và tin cậy. 
              Mang đến trải nghiệm ẩm thực tuyệt vời nhất cho nhân viên.
            </p>
          </div>
          <div>
            <h3 className="font-bold mb-6">Liên kết</h3>
            <ul className="space-y-4 text-gray-400">
              <li><a href="#" className="hover:text-primary transition-colors">Về chúng tôi</a></li>
              <li><a href="#" className="hover:text-primary transition-colors">Điều khoản dịch vụ</a></li>
              <li><a href="#" className="hover:text-primary transition-colors">Chính sách bảo mật</a></li>
            </ul>
          </div>
          <div>
            <h3 className="font-bold mb-6">Liên hệ</h3>
            <ul className="space-y-4 text-gray-400">
              <li>Hotline: 1900 1234</li>
              <li>Email: support@minifood.com</li>
              <li>Địa chỉ: Quận 1, TP. HCM</li>
            </ul>
          </div>
        </div>
        <div className="container border-t border-gray-700 mt-12 pt-8 text-center text-gray-500 text-sm">
          © 2024 MiniFood System. All rights reserved.
        </div>
      </footer>
      
      <ToastContainer theme="colored" />
    </div>
  );
};

const App = () => {
  return (
    <BrowserRouter>
      <AuthProvider>
        <CartProvider>
          <AppContent />
        </CartProvider>
      </AuthProvider>
    </BrowserRouter>
  );
};

export default App;
