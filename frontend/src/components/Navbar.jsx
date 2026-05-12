import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import { ShoppingCart, User, LogOut, UtensilsCrossed, Package, ChevronDown } from 'lucide-react';
import { useAuth } from '../context/AuthContext';
import { useCart } from '../context/CartContext';
import { motion, AnimatePresence } from 'framer-motion';

const Navbar = () => {
  const { user, logout } = useAuth();
  const { cartCount } = useCart();
  const navigate = useNavigate();
  const location = useLocation();
  const [isScrolled, setIsScrolled] = useState(false);

  useEffect(() => {
    const handleScroll = () => setIsScrolled(window.scrollY > 10);
    window.addEventListener('scroll', handleScroll);
    return () => window.removeEventListener('scroll', handleScroll);
  }, []);

  const handleLogout = () => {
    logout();
    navigate('/');
  };

  return (
    <nav className={`fixed top-0 left-0 right-0 z-[100] transition-all duration-500 ${isScrolled ? 'py-3 glass-nav shadow-sm' : 'py-6 bg-transparent'}`}>
      <div className="max-w-7xl mx-auto px-6 flex justify-between items-center">
        {/* Logo */}
        <Link to="/" className="flex items-center gap-3 group">
          <div className="bg-primary p-2.5 rounded-2xl text-white shadow-xl shadow-primary/20 group-hover:scale-110 transition-transform duration-300">
            <UtensilsCrossed size={24} strokeWidth={2.5} />
          </div>
          <span className="text-2xl font-extrabold tracking-tighter text-secondary">
            Mini<span className="text-primary">Food</span>
          </span>
        </Link>

        {/* Desktop Menu */}
        <div className="hidden md:flex items-center bg-white/50 backdrop-blur-md px-2 py-1.5 rounded-3xl border border-gray-100 shadow-sm">
          <Link 
            to="/" 
            className={`px-6 py-2 rounded-2xl text-sm font-bold transition-all ${location.pathname === '/' ? 'bg-white text-primary shadow-sm' : 'text-gray-500 hover:text-secondary'}`}
          >
            Trang chủ
          </Link>
          {user && (
            <Link 
              to="/orders" 
              className={`px-6 py-2 rounded-2xl text-sm font-bold transition-all ${location.pathname === '/orders' ? 'bg-white text-primary shadow-sm' : 'text-gray-500 hover:text-secondary'}`}
            >
              Đơn hàng
            </Link>
          )}
          <Link 
            to="/about" 
            className="px-6 py-2 rounded-2xl text-sm font-bold text-gray-500 hover:text-secondary transition-all"
          >
            Về chúng tôi
          </Link>
        </div>
        
        {/* Right Section */}
        <div className="flex items-center gap-4">
          {/* Cart */}
          <Link 
            to="/cart" 
            className="relative p-3 bg-white rounded-2xl shadow-sm border border-gray-100 hover:border-primary/50 hover:bg-primary/5 transition-all group"
          >
            <ShoppingCart size={22} className="text-secondary group-hover:text-primary transition-colors" />
            <AnimatePresence>
              {cartCount > 0 && (
                <motion.span 
                  initial={{ scale: 0 }}
                  animate={{ scale: 1 }}
                  exit={{ scale: 0 }}
                  className="absolute -top-1.5 -right-1.5 bg-primary text-white text-[10px] w-5 h-5 flex items-center justify-center rounded-full font-black shadow-lg border-2 border-white"
                >
                  {cartCount}
                </motion.span>
              )}
            </AnimatePresence>
          </Link>

          {/* User Profile */}
          {user ? (
            <div className="relative group ml-2">
              <button className="flex items-center gap-3 bg-white p-1.5 pr-4 rounded-2xl border border-gray-100 shadow-sm hover:shadow-md transition-all">
                <div className="w-9 h-9 rounded-[14px] overflow-hidden border-2 border-primary/10">
                  <img src={`https://ui-avatars.com/api/?name=${user.username}&background=FF385C&color=fff&bold=true`} alt="Avatar" />
                </div>
                <div className="flex flex-col items-start leading-none hidden sm:flex">
                  <span className="text-xs font-black text-secondary">{user.fullName || user.username}</span>
                  <span className="text-[9px] text-gray-400 uppercase font-black mt-1">Hội viên</span>
                </div>
                <ChevronDown size={14} className="text-gray-400 group-hover:text-primary transition-colors" />
              </button>
              
              <div className="absolute top-full right-0 mt-3 w-56 bg-white rounded-3xl shadow-2xl border border-gray-100 opacity-0 invisible group-hover:opacity-100 group-hover:visible transition-all duration-300 py-3 z-[110] overflow-hidden">
                <div className="px-6 py-3 border-b border-gray-50 mb-2">
                  <p className="text-xs text-gray-400 font-bold uppercase tracking-widest">Tài khoản</p>
                </div>
                <Link to="/orders" className="flex items-center gap-4 px-6 py-3 hover:bg-gray-50 text-sm font-bold text-secondary transition-colors">
                  <Package size={18} className="text-gray-400" /> Đơn hàng của tôi
                </Link>
                <div className="mx-6 my-2 h-[1px] bg-gray-50"></div>
                <button 
                  onClick={handleLogout} 
                  className="w-full flex items-center gap-4 px-6 py-3 hover:bg-red-50 text-sm font-bold text-primary transition-colors"
                >
                  <LogOut size={18} /> Đăng xuất
                </button>
              </div>
            </div>
          ) : (
            <div className="flex gap-2 ml-2">
              <Link to="/login" className="hidden sm:flex btn-outline py-2.5 px-6 rounded-2xl text-sm border-none bg-gray-50 hover:bg-gray-100">Đăng nhập</Link>
              <Link to="/register" className="btn-primary py-2.5 px-8 rounded-2xl text-sm shadow-primary/20">Đăng ký</Link>
            </div>
          )}
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
