import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { toast } from 'react-toastify';
import { User, Mail, Lock, Phone, MapPin, Loader2, ArrowRight, Sparkles } from 'lucide-react';
import { motion } from 'framer-motion';

const Register = () => {
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: '',
    fullName: '',
    phone: '',
    address: ''
  });
  const [loading, setLoading] = useState(false);
  const { register } = useAuth();
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      await register(formData);
      toast.success('Chào mừng thành viên mới!');
      navigate('/');
    } catch (error) {
      toast.error(error.response?.data?.error || 'Đã có lỗi xảy ra');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-background pt-32 pb-20 px-6">
      <div className="w-full max-w-6xl grid grid-cols-1 lg:grid-cols-5 bg-white rounded-[3.5rem] shadow-2xl overflow-hidden border border-gray-50">
        
        {/* Left Side - Info (2 cols) */}
        <div className="hidden lg:flex lg:col-span-2 relative bg-secondary overflow-hidden p-16 flex-col justify-between text-white">
           <div className="relative z-10">
              <Link to="/" className="inline-flex items-center gap-3 text-2xl font-black tracking-tighter mb-16">
                 <div className="bg-primary p-2 rounded-2xl text-white shadow-xl">
                    <Sparkles size={24} className="fill-white" />
                 </div>
                 <span>MiniFood</span>
              </Link>
              <h2 className="text-6xl font-black leading-[0.9] tracking-tighter mb-10">
                 Bắt đầu <br />
                 Hành trình <br />
                 <span className="text-primary">Vị ngon.</span>
              </h2>
              <div className="space-y-8 mt-12">
                 {[
                    { title: 'Tích điểm đổi quà', desc: 'Mỗi đơn hàng đều mang lại điểm thưởng hấp dẫn.' },
                    { title: 'Ưu đãi đặc quyền', desc: 'Nhận thông báo về các món mới và khuyến mãi sớm nhất.' },
                    { title: 'Hỗ trợ 24/7', desc: 'Đội ngũ chăm sóc khách hàng luôn sẵn sàng giúp đỡ.' }
                 ].map((item, i) => (
                    <div key={i} className="flex gap-4">
                       <div className="w-1.5 h-1.5 bg-primary rounded-full mt-2 shrink-0"></div>
                       <div>
                          <p className="font-black text-sm uppercase tracking-widest mb-1">{item.title}</p>
                          <p className="text-gray-400 text-xs font-medium">{item.desc}</p>
                       </div>
                    </div>
                 ))}
              </div>
           </div>
           
           <div className="relative z-10 text-[10px] font-black text-gray-500 uppercase tracking-[0.3em]">
              © 2024 MiniFood Platform
           </div>
           
           {/* Abstract element */}
           <div className="absolute top-1/2 left-0 -translate-x-1/2 -translate-y-1/2 w-96 h-96 bg-primary/20 rounded-full blur-[120px]"></div>
        </div>

        {/* Right Side - Form (3 cols) */}
        <div className="lg:col-span-3 p-12 lg:p-20">
          <motion.div 
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.6 }}
          >
            <div className="flex justify-between items-end mb-12">
               <div>
                  <h3 className="text-4xl font-black tracking-tighter text-secondary">Đăng ký.</h3>
                  <p className="text-gray-400 font-medium mt-1">Chỉ mất 1 phút để trở thành hội viên.</p>
               </div>
               <Link to="/login" className="text-xs font-black uppercase tracking-widest text-primary hover:underline pb-1">Đã có tài khoản?</Link>
            </div>

            <form onSubmit={handleSubmit} className="grid grid-cols-1 md:grid-cols-2 gap-x-8 gap-y-6">
              <div className="space-y-2">
                <label className="text-[10px] font-black uppercase tracking-widest text-gray-400 ml-4">Tên đăng nhập *</label>
                <div className="relative group">
                  <User className="absolute left-6 top-1/2 -translate-y-1/2 text-gray-300 group-focus-within:text-primary transition-colors" size={18} />
                  <input type="text" name="username" required className="input-field pl-16 py-3.5" placeholder="user123" onChange={handleChange} />
                </div>
              </div>

              <div className="space-y-2">
                <label className="text-[10px] font-black uppercase tracking-widest text-gray-400 ml-4">Email *</label>
                <div className="relative group">
                  <Mail className="absolute left-6 top-1/2 -translate-y-1/2 text-gray-300 group-focus-within:text-primary transition-colors" size={18} />
                  <input type="email" name="email" required className="input-field pl-16 py-3.5" placeholder="abc@gmail.com" onChange={handleChange} />
                </div>
              </div>

              <div className="space-y-2">
                <label className="text-[10px] font-black uppercase tracking-widest text-gray-400 ml-4">Mật khẩu *</label>
                <div className="relative group">
                  <Lock className="absolute left-6 top-1/2 -translate-y-1/2 text-gray-300 group-focus-within:text-primary transition-colors" size={18} />
                  <input type="password" name="password" required className="input-field pl-16 py-3.5" placeholder="••••••••" onChange={handleChange} />
                </div>
              </div>

              <div className="space-y-2">
                <label className="text-[10px] font-black uppercase tracking-widest text-gray-400 ml-4">Họ và tên</label>
                <div className="relative group">
                  <User className="absolute left-6 top-1/2 -translate-y-1/2 text-gray-300 group-focus-within:text-primary transition-colors" size={18} />
                  <input type="text" name="fullName" className="input-field pl-16 py-3.5" placeholder="Nguyen Van A" onChange={handleChange} />
                </div>
              </div>

              <div className="space-y-2">
                <label className="text-[10px] font-black uppercase tracking-widest text-gray-400 ml-4">Số điện thoại</label>
                <div className="relative group">
                  <Phone className="absolute left-6 top-1/2 -translate-y-1/2 text-gray-300 group-focus-within:text-primary transition-colors" size={18} />
                  <input type="text" name="phone" className="input-field pl-16 py-3.5" placeholder="0901234567" onChange={handleChange} />
                </div>
              </div>

              <div className="space-y-2">
                <label className="text-[10px] font-black uppercase tracking-widest text-gray-400 ml-4">Địa chỉ giao hàng</label>
                <div className="relative group">
                  <MapPin className="absolute left-6 top-1/2 -translate-y-1/2 text-gray-300 group-focus-within:text-primary transition-colors" size={18} />
                  <input type="text" name="address" className="input-field pl-16 py-3.5" placeholder="Quận 1, TP. HCM" onChange={handleChange} />
                </div>
              </div>

              <div className="md:col-span-2 mt-8">
                <button 
                  type="submit" 
                  disabled={loading}
                  className="btn-primary w-full py-5 rounded-[2rem] text-lg shadow-primary/20"
                >
                  {loading ? <Loader2 className="animate-spin" /> : (
                     <>
                        <span>Hoàn tất đăng ký</span>
                        <ArrowRight size={22} strokeWidth={3} />
                     </>
                  )}
                </button>
                <p className="text-center text-[10px] text-gray-400 mt-6 px-10 leading-relaxed font-medium">
                   Bằng việc nhấn "Hoàn tất đăng ký", bạn đồng ý với các <span className="text-secondary font-bold underline">Điều khoản dịch vụ</span> và <span className="text-secondary font-bold underline">Chính sách bảo mật</span> của chúng tôi.
                </p>
              </div>
            </form>
          </motion.div>
        </div>
      </div>
    </div>
  );
};

export default Register;
