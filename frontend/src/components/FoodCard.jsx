import React from 'react';
import { Plus, ShoppingCart, Star, Clock, Flame } from 'lucide-react';
import { useCart } from '../context/CartContext';
import { toast } from 'react-toastify';
import { motion } from 'framer-motion';

const FoodCard = ({ food, index }) => {
  const { addToCart } = useCart();

  const handleAddToCart = () => {
    addToCart(food);
    toast.success(`Đã thêm ${food.name}!`, {
      position: "bottom-right",
      autoClose: 2000,
      hideProgressBar: true,
      closeOnClick: true,
      pauseOnHover: true,
      draggable: true,
      progress: undefined,
      style: { 
        borderRadius: '20px',
        background: '#1A1A1A',
        color: 'white',
        fontWeight: 'bold',
        fontSize: '14px',
        padding: '16px 24px'
      }
    });
  };

  const formatPrice = (price) => {
    return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price);
  };

  return (
    <motion.div 
      initial={{ opacity: 0, y: 30 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.6, delay: index * 0.1, ease: "easeOut" }}
      className="card-premium group"
    >
      <div className="relative aspect-[4/3] overflow-hidden">
        {/* Badge */}
        <div className="absolute top-4 left-4 z-20 flex flex-col gap-2">
          <div className="bg-white/90 backdrop-blur-md px-3 py-1.5 rounded-xl font-black text-[9px] uppercase tracking-widest text-secondary shadow-sm flex items-center gap-1.5">
            <Flame size={12} className="text-primary fill-primary" />
            <span>Hot Deal</span>
          </div>
        </div>

        {/* Favorite Button Overlay (Visual only) */}
        <div className="absolute top-4 right-4 z-20 opacity-0 group-hover:opacity-100 transition-all duration-300">
           <div className="bg-white/90 backdrop-blur-md w-10 h-10 rounded-xl flex items-center justify-center shadow-md cursor-pointer hover:bg-white transition-colors">
              <Star size={18} className="text-gray-300 hover:text-amber-400 transition-colors" />
           </div>
        </div>

        <img 
          src={food.imageUrl || 'https://images.unsplash.com/photo-1504674900247-0877df9cc836?auto=format&fit=crop&q=80&w=800'} 
          alt={food.name}
          className="w-full h-full object-cover group-hover:scale-110 transition-transform duration-700 ease-in-out"
        />
        
        {/* Overlay gradient */}
        <div className="absolute inset-0 bg-gradient-to-t from-black/60 via-transparent to-transparent opacity-0 group-hover:opacity-100 transition-all duration-500"></div>
        
        <div className="absolute bottom-4 left-4 right-4 z-20 translate-y-4 group-hover:translate-y-0 opacity-0 group-hover:opacity-100 transition-all duration-500">
           <div className="flex justify-between items-end">
              <div className="flex items-center gap-1.5 text-white">
                 <Clock size={14} className="text-primary" />
                 <span className="text-[10px] font-black uppercase tracking-widest">Giao ngay: 15p</span>
              </div>
           </div>
        </div>
      </div>
      
      <div className="p-8">
        <div className="flex justify-between items-start mb-4">
          <div>
            <span className="text-[10px] font-black uppercase tracking-[0.2em] text-primary mb-2 block">{food.category}</span>
            <h3 className="text-xl font-black text-secondary leading-tight line-clamp-1">
              {food.name}
            </h3>
          </div>
          <div className="text-right">
            <span className="text-lg font-black text-primary block">{formatPrice(food.price)}</span>
          </div>
        </div>
        
        <div className="flex items-center gap-6 mb-6 text-xs font-bold text-gray-400">
          <div className="flex items-center gap-1.5">
            <Star size={16} className="text-amber-400 fill-amber-400" />
            <span className="text-secondary">4.9</span>
            <span className="font-medium">(120+)</span>
          </div>
          <div className="w-1 h-1 bg-gray-200 rounded-full"></div>
          <div className="flex items-center gap-1.5">
             <Clock size={16} />
             <span>15-20p</span>
          </div>
        </div>
        
        <button 
          onClick={handleAddToCart}
          className="w-full btn-primary !rounded-3xl group/btn overflow-hidden relative"
        >
          <div className="absolute inset-0 bg-white/20 translate-x-[-100%] group-hover/btn:translate-x-[100%] transition-transform duration-500 skew-x-[-20deg]"></div>
          <Plus size={20} className="group-hover/btn:rotate-90 transition-transform duration-300" strokeWidth={3} />
          <span className="relative z-10">Thêm vào giỏ hàng</span>
        </button>
      </div>
    </motion.div>
  );
};

export default FoodCard;
