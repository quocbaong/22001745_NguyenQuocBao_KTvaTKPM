import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useCart } from '../context/CartContext';
import { useAuth } from '../context/AuthContext';
import api from '../api/axios';
import { toast } from 'react-toastify';
import { Trash2, Plus, Minus, ShoppingBag, CreditCard, Truck, Loader2, ArrowRight, MapPin, Tag } from 'lucide-react';
import { motion, AnimatePresence } from 'framer-motion';

const Cart = () => {
  const { cartItems, removeFromCart, updateQuantity, cartTotal, clearCart } = useCart();
  const { user } = useAuth();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [paymentMethod, setPaymentMethod] = useState('COD');
  const [notes, setNotes] = useState('');

  const formatPrice = (price) => {
    return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price);
  };

  const handleCheckout = async () => {
    if (!user) {
      toast.warning('Vui lòng đăng nhập để đặt hàng!');
      navigate('/login');
      return;
    }

    if (cartItems.length === 0) return;

    setLoading(true);
    try {
      const orderRequest = {
        userId: user.id,
        items: cartItems.map(item => ({
          foodId: item.id,
          quantity: item.quantity
        })),
        deliveryAddress: user.address || 'HCM City',
        paymentMethod: paymentMethod,
        notes: notes
      };

      const orderResponse = await api.post('/orders', orderRequest);
      const order = orderResponse.data;

      await api.post('/payments', {
        orderId: order.id,
        userId: user.id,
        amount: order.totalAmount,
        paymentMethod: paymentMethod,
        notes: `Thanh toán cho đơn hàng #${order.id}`
      });

      toast.success('Đặt hàng thành công! Đang chuẩn bị giao cho bạn.');
      clearCart();
      navigate('/orders');
    } catch (error) {
      toast.error(error.response?.data?.error || 'Có lỗi khi xử lý đơn hàng');
    } finally {
      setLoading(false);
    }
  };

  if (cartItems.length === 0) {
    return (
      <div className="min-h-screen bg-background flex flex-col items-center justify-center pt-20 px-6">
        <motion.div 
          initial={{ opacity: 0, scale: 0.9 }}
          animate={{ opacity: 1, scale: 1 }}
          className="text-center"
        >
          <div className="w-40 h-40 bg-white rounded-full flex items-center justify-center mx-auto mb-10 shadow-soft">
            <ShoppingBag size={64} className="text-gray-200" />
          </div>
          <h2 className="text-4xl font-black mb-4 tracking-tighter text-secondary">Giỏ hàng đang trống.</h2>
          <p className="text-gray-400 font-medium mb-12 max-w-xs mx-auto">Có vẻ như bạn chưa chọn món ăn nào. Hãy quay lại thực đơn để tìm món ngon nhé!</p>
          <Link to="/" className="btn-primary px-12 py-4 rounded-3xl inline-flex items-center gap-3">
             <span>Khám phá ngay</span>
             <ArrowRight size={20} />
          </Link>
        </motion.div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-background pt-32 pb-20 px-6">
      <div className="max-w-7xl mx-auto">
        <div className="flex items-center gap-4 mb-12">
           <div className="bg-primary p-3 rounded-2xl text-white shadow-lg shadow-primary/20">
              <ShoppingBag size={28} />
           </div>
           <h1 className="text-5xl font-black tracking-tighter text-secondary">Giỏ hàng.</h1>
        </div>
        
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-12">
          {/* Cart Items List */}
          <div className="lg:col-span-2 space-y-6">
            <AnimatePresence>
              {cartItems.map((item, index) => (
                <motion.div 
                  key={item.id}
                  initial={{ opacity: 0, x: -20 }}
                  animate={{ opacity: 1, x: 0 }}
                  exit={{ opacity: 0, x: -50 }}
                  transition={{ delay: index * 0.1 }}
                  className="bg-white p-6 rounded-[2.5rem] shadow-soft border border-gray-50 flex flex-col sm:flex-row items-center gap-8 relative group"
                >
                  <div className="w-32 h-32 rounded-3xl overflow-hidden shrink-0 shadow-md">
                    <img src={item.imageUrl || 'https://images.unsplash.com/photo-1504674900247-0877df9cc836?auto=format&fit=crop&q=80&w=300'} alt={item.name} className="w-full h-full object-cover" />
                  </div>
                  
                  <div className="flex-1 text-center sm:text-left">
                    <span className="text-[10px] font-black uppercase tracking-widest text-primary mb-1 block">{item.category}</span>
                    <h3 className="text-2xl font-black text-secondary mb-2 leading-tight">{item.name}</h3>
                    <p className="text-lg font-black text-secondary">{formatPrice(item.price)}</p>
                  </div>
                  
                  <div className="flex items-center gap-4 bg-gray-50 p-2 rounded-2xl border border-gray-100">
                    <button 
                      onClick={() => updateQuantity(item.id, item.quantity - 1)}
                      className="w-10 h-10 flex items-center justify-center bg-white rounded-xl shadow-sm hover:text-primary transition-colors"
                    >
                      <Minus size={18} strokeWidth={3} />
                    </button>
                    <span className="w-8 text-center font-black text-lg">{item.quantity}</span>
                    <button 
                      onClick={() => updateQuantity(item.id, item.quantity + 1)}
                      className="w-10 h-10 flex items-center justify-center bg-white rounded-xl shadow-sm hover:text-primary transition-colors"
                    >
                      <Plus size={18} strokeWidth={3} />
                    </button>
                  </div>
                  
                  <div className="text-right min-w-[120px]">
                    <p className="text-2xl font-black text-primary">{formatPrice(item.price * item.quantity)}</p>
                  </div>

                  <button 
                    onClick={() => removeFromCart(item.id)}
                    className="absolute top-4 right-6 p-2 text-gray-200 hover:text-red-500 transition-colors"
                  >
                    <Trash2 size={20} />
                  </button>
                </motion.div>
              ))}
            </AnimatePresence>

            <Link to="/" className="inline-flex items-center gap-3 text-sm font-black text-gray-400 hover:text-secondary transition-colors py-4 px-2">
               <ArrowRight size={18} className="rotate-180" />
               <span>Tiếp tục chọn món</span>
            </Link>
          </div>

          {/* Checkout Details Sidebar */}
          <div className="space-y-8">
            {/* Delivery Info Card */}
            <div className="bg-white p-8 rounded-[2.5rem] shadow-soft border border-gray-50">
               <h2 className="text-xl font-black mb-6 flex items-center gap-3">
                  <MapPin size={20} className="text-primary" />
                  <span>Địa chỉ giao hàng</span>
               </h2>
               <div className="bg-gray-50 p-6 rounded-2xl border border-gray-100">
                  <p className="text-sm font-black text-secondary mb-1">{user?.fullName || user?.username || 'Khách hàng'}</p>
                  <p className="text-xs text-gray-500 leading-relaxed">{user?.address || 'Vui lòng cập nhật địa chỉ giao hàng trong hồ sơ.'}</p>
               </div>
            </div>

            {/* Payment Method Card */}
            <div className="bg-white p-8 rounded-[2.5rem] shadow-soft border border-gray-50">
               <h2 className="text-xl font-black mb-6 flex items-center gap-3">
                  <CreditCard size={20} className="text-primary" />
                  <span>Thanh toán</span>
               </h2>
               <div className="grid grid-cols-2 gap-4">
                  <button 
                    onClick={() => setPaymentMethod('COD')}
                    className={`p-5 border-2 rounded-2xl flex flex-col items-center gap-3 transition-all ${paymentMethod === 'COD' ? 'border-primary bg-primary/5 text-primary' : 'border-gray-50 hover:border-gray-200'}`}
                  >
                    <Truck size={24} />
                    <span className="text-[10px] font-black uppercase tracking-widest">Tiền mặt</span>
                  </button>
                  <button 
                    onClick={() => setPaymentMethod('BANKING')}
                    className={`p-5 border-2 rounded-2xl flex flex-col items-center gap-3 transition-all ${paymentMethod === 'BANKING' ? 'border-primary bg-primary/5 text-primary' : 'border-gray-50 hover:border-gray-200'}`}
                  >
                    <CreditCard size={24} />
                    <span className="text-[10px] font-black uppercase tracking-widest">Chuyển khoản</span>
                  </button>
               </div>
            </div>

            {/* Order Summary Card */}
            <div className="bg-secondary p-8 rounded-[2.5rem] shadow-2xl text-white">
              <h2 className="text-2xl font-black mb-8 tracking-tighter">Chi tiết hóa đơn</h2>
              
              <div className="space-y-5 mb-10">
                <div className="flex justify-between items-center text-gray-400 font-bold">
                  <span className="text-sm">Tổng cộng ({cartItems.length} món)</span>
                  <span>{formatPrice(cartTotal)}</span>
                </div>
                <div className="flex justify-between items-center text-gray-400 font-bold">
                  <span className="text-sm">Phí vận chuyển</span>
                  <span className="text-green-400 uppercase text-[10px] tracking-widest">Miễn phí</span>
                </div>
                <div className="flex justify-between items-center text-gray-400 font-bold">
                  <span className="text-sm flex items-center gap-2">
                     <Tag size={14} /> Giảm giá
                  </span>
                  <span>- {formatPrice(0)}</span>
                </div>
                <div className="pt-5 border-t border-white/10 flex justify-between items-end">
                  <span className="text-lg font-black uppercase tracking-tighter">Thanh toán</span>
                  <span className="text-3xl font-black text-primary tracking-tighter">{formatPrice(cartTotal)}</span>
                </div>
              </div>

              <div className="space-y-4 mb-8">
                 <label className="text-[10px] font-black uppercase tracking-[0.2em] text-gray-500 ml-2">Ghi chú (Tùy chọn)</label>
                 <textarea 
                   className="w-full bg-white/5 border border-white/10 p-4 rounded-2xl h-24 resize-none text-white text-sm focus:border-primary/50 outline-none transition-all placeholder:text-gray-600 font-medium"
                   placeholder="Ví dụ: Không hành, nhiều ớt..."
                   value={notes}
                   onChange={(e) => setNotes(e.target.value)}
                 />
              </div>

              <button 
                onClick={handleCheckout}
                disabled={loading}
                className="w-full bg-primary py-5 rounded-[2rem] text-lg font-black flex items-center justify-center gap-3 hover:bg-primary-dark transition-all shadow-xl shadow-primary/30"
              >
                {loading ? <Loader2 className="animate-spin" /> : (
                   <>
                      <span>Xác nhận đặt hàng</span>
                      <ArrowRight size={20} strokeWidth={3} />
                   </>
                )}
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Cart;
