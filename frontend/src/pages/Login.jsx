import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { toast } from 'react-toastify';
import { Lock, User as UserIcon, Loader2, ArrowRight, Sparkles } from 'lucide-react';
import { motion } from 'framer-motion';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      await login(username, password);
      toast.success('Chào mừng bạn quay trở lại!');
      navigate('/');
    } catch (error) {
      toast.error(error.response?.data?.error || 'Sai tên đăng nhập hoặc mật khẩu');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-background pt-20 px-6">
      <div className="w-full max-w-5xl grid grid-cols-1 lg:grid-cols-2 bg-white rounded-[3rem] shadow-2xl overflow-hidden border border-gray-50">
        
        {/* Left Side - Visual */}
        <div className="hidden lg:block relative bg-primary overflow-hidden p-16">
          <div className="relative z-10 h-full flex flex-col justify-between text-white">
             <div>
                <Link to="/" className="inline-flex items-center gap-3 text-2xl font-black tracking-tighter mb-12">
                   <div className="bg-white p-2 rounded-2xl text-primary shadow-xl">
                      <Sparkles size={24} className="fill-primary" />
                   </div>
                   <span>MiniFood</span>
                </Link>
                <h2 className="text-5xl font-black leading-tight tracking-tighter mb-6">
                   Cơm ngon <br />
                   Bếp nóng <br />
                   Đợi bạn đây.
                </h2>
                <p className="text-white/80 font-medium text-lg max-w-xs">
                   Hàng trăm món ngon đang chờ bạn khám phá. Đăng nhập ngay thôi!
                </p>
             </div>
             
             <div className="flex gap-10">
                <div>
                   <p className="text-3xl font-black">10k+</p>
                   <p className="text-white/60 text-[10px] font-black uppercase tracking-widest mt-1">Hội viên</p>
                </div>
                <div>
                   <p className="text-3xl font-black">50+</p>
                   <p className="text-white/60 text-[10px] font-black uppercase tracking-widest mt-1">Đầu bếp</p>
                </div>
             </div>
          </div>
          
          {/* Abstract blobs */}
          <div className="absolute top-0 right-0 w-96 h-96 bg-white/10 rounded-full -mr-32 -mt-32 blur-3xl"></div>
          <div className="absolute bottom-0 left-0 w-96 h-96 bg-black/10 rounded-full -ml-32 -mb-32 blur-3xl"></div>
        </div>

        {/* Right Side - Form */}
        <div className="p-12 lg:p-20 flex flex-col justify-center">
          <motion.div 
            initial={{ opacity: 0, x: 20 }}
            animate={{ opacity: 1, x: 0 }}
            transition={{ duration: 0.6 }}
          >
            <h3 className="text-4xl font-black mb-2 tracking-tighter text-secondary">Đăng nhập.</h3>
            <p className="text-gray-400 font-medium mb-12">Rất vui khi thấy bạn quay lại!</p>

            <form onSubmit={handleSubmit} className="space-y-6">
              <div className="space-y-2">
                <label className="text-[10px] font-black uppercase tracking-widest text-gray-400 ml-4">Tên đăng nhập</label>
                <div className="relative group">
                  <UserIcon className="absolute left-6 top-1/2 -translate-y-1/2 text-gray-300 group-focus-within:text-primary transition-colors" size={20} />
                  <input 
                    type="text" 
                    required 
                    className="input-field pl-16"
                    placeholder="Nhập username của bạn"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                  />
                </div>
              </div>

              <div className="space-y-2">
                <div className="flex justify-between items-center px-4">
                   <label className="text-[10px] font-black uppercase tracking-widest text-gray-400">Mật khẩu</label>
                   <Link to="#" className="text-[10px] font-black uppercase tracking-widest text-primary hover:underline">Quên mật khẩu?</Link>
                </div>
                <div className="relative group">
                  <Lock className="absolute left-6 top-1/2 -translate-y-1/2 text-gray-300 group-focus-within:text-primary transition-colors" size={20} />
                  <input 
                    type="password" 
                    required 
                    className="input-field pl-16"
                    placeholder="Nhập mật khẩu"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                  />
                </div>
              </div>

              <button 
                type="submit" 
                disabled={loading}
                className="btn-primary w-full py-5 text-lg shadow-primary/20 mt-4 rounded-2xl"
              >
                {loading ? <Loader2 className="animate-spin" /> : (
                   <>
                      <span>Tiếp tục ngay</span>
                      <ArrowRight size={20} strokeWidth={3} />
                   </>
                )}
              </button>
            </form>

            <div className="mt-12 text-center">
              <p className="text-gray-400 font-medium">
                Bạn chưa có tài khoản? <br />
                <Link to="/register" className="text-primary font-black uppercase tracking-widest text-xs hover:underline mt-2 inline-block">Đăng ký thành viên mới</Link>
              </p>
            </div>
          </motion.div>
        </div>
      </div>
    </div>
  );
};

export default Login;
