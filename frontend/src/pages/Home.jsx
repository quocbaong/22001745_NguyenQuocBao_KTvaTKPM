import React, { useState, useEffect } from 'react';
import api from '../api/axios';
import FoodCard from '../components/FoodCard';
import { Search, Loader2, Sparkles, TrendingUp, Clock, ShieldCheck, ArrowRight, UtensilsCrossed } from 'lucide-react';
import { motion } from 'framer-motion';

const Home = () => {
  const [foods, setFoods] = useState([]);
  const [loading, setLoading] = useState(true);
  const [search, setSearch] = useState('');
  const [activeCategory, setActiveCategory] = useState('Tất cả');

  const categories = ['Tất cả', 'Cơm', 'Phở', 'Bún', 'Đồ uống', 'Tráng miệng'];

  useEffect(() => {
    fetchFoods();
  }, []);

  const fetchFoods = async (keyword = '') => {
    setLoading(true);
    try {
      const response = await api.get('/foods', {
        params: { search: keyword, available: 'true' }
      });
      setFoods(response.data);
    } catch (error) {
      console.error('Error fetching foods:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = (e) => {
    e.preventDefault();
    fetchFoods(search);
  };

  return (
    <main className="min-h-screen">
      {/* Hero Section */}
      <section className="relative pt-32 pb-40 overflow-hidden bg-white">
        <div className="max-w-7xl mx-auto px-6 grid grid-cols-1 lg:grid-cols-2 gap-20 items-center relative z-10">
          <motion.div 
            initial={{ opacity: 0, y: 30 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.8 }}
          >
            <div className="inline-flex items-center gap-2 bg-primary/5 text-primary px-5 py-2.5 rounded-full font-black text-xs uppercase tracking-[0.2em] mb-10 border border-primary/10">
              <Sparkles size={14} className="fill-primary" />
              <span>Hệ thống ẩm thực nội bộ</span>
            </div>
            
            <h1 className="text-6xl lg:text-8xl font-black mb-8 leading-[0.9] tracking-tighter text-secondary">
              Năng lượng <br />
              <span className="text-primary">Mỗi ngày</span> <br />
              Từ món ngon.
            </h1>
            
            <p className="text-xl text-gray-500 mb-12 max-w-lg leading-relaxed font-medium">
              Thực đơn đa dạng, nguyên liệu tươi sạch và hương vị tuyệt hảo chuẩn đầu bếp chuyên nghiệp dành riêng cho bạn.
            </p>
            
            <form onSubmit={handleSearch} className="flex gap-3 bg-white p-2 rounded-[2.5rem] shadow-2xl shadow-gray-200/50 border border-gray-100 max-w-xl group focus-within:border-primary/30 transition-all">
              <div className="flex-1 relative">
                <Search className="absolute left-6 top-1/2 -translate-y-1/2 text-gray-300 group-focus-within:text-primary transition-colors" size={24} />
                <input 
                  type="text" 
                  placeholder="Gõ tên món bạn thèm..." 
                  className="w-full border-none py-5 pl-16 pr-4 focus:ring-0 text-lg font-bold bg-transparent placeholder:text-gray-300"
                  value={search}
                  onChange={(e) => setSearch(e.target.value)}
                />
              </div>
              <button type="submit" className="bg-primary text-white p-5 rounded-[2rem] hover:bg-primary-dark transition-all shadow-lg shadow-primary/20">
                <ArrowRight size={24} strokeWidth={3} />
              </button>
            </form>

            <div className="flex gap-12 mt-16">
              {[
                { val: '50+', label: 'Thực đơn' },
                { val: '15p', label: 'Tốc độ' },
                { val: '4.9', label: 'Đánh giá' }
              ].map((stat, i) => (
                <div key={i} className="flex flex-col">
                  <span className="text-4xl font-black text-secondary tracking-tighter">{stat.val}</span>
                  <span className="text-[11px] text-gray-400 uppercase tracking-widest font-black mt-2">{stat.label}</span>
                </div>
              ))}
            </div>
          </motion.div>

          {/* Visual Element */}
          <motion.div 
            className="relative"
            initial={{ opacity: 0, scale: 0.9 }}
            animate={{ opacity: 1, scale: 1 }}
            transition={{ duration: 1, delay: 0.2 }}
          >
            <div className="relative z-10 animate-float-slow">
              <div className="absolute -inset-4 bg-primary/10 rounded-[3rem] blur-2xl -z-10"></div>
              <img 
                src="https://images.unsplash.com/photo-1546069901-ba9599a7e63c?auto=format&fit=crop&q=80&w=1000" 
                alt="Main Food" 
                className="rounded-[3rem] shadow-premium border-[16px] border-white w-full object-cover aspect-square"
              />
            </div>
            
            {/* Floating Info Cards */}
            <motion.div 
              className="absolute -bottom-6 -left-12 bg-white p-6 rounded-3xl shadow-premium z-20 flex items-center gap-4 border border-gray-50"
              animate={{ y: [0, -20, 0] }}
              transition={{ duration: 4, repeat: Infinity, ease: "easeInOut" }}
            >
              <div className="w-14 h-14 bg-green-50 rounded-2xl flex items-center justify-center text-green-500">
                <TrendingUp size={28} />
              </div>
              <div>
                <p className="text-sm font-black text-secondary">Phổ biến nhất</p>
                <p className="text-xs font-bold text-gray-400">Phở Bò Tái - 55k</p>
              </div>
            </motion.div>

            <motion.div 
              className="absolute -top-12 -right-6 bg-white p-6 rounded-3xl shadow-premium z-20 flex items-center gap-4 border border-gray-50"
              animate={{ y: [0, 20, 0] }}
              transition={{ duration: 5, repeat: Infinity, ease: "easeInOut", delay: 1 }}
            >
              <div className="w-14 h-14 bg-amber-50 rounded-2xl flex items-center justify-center text-amber-500">
                <Clock size={28} />
              </div>
              <div>
                <p className="text-sm font-black text-secondary">Giao nội bộ</p>
                <p className="text-xs font-bold text-gray-400">Dưới 15 phút</p>
              </div>
            </motion.div>
          </motion.div>
        </div>
      </section>

      {/* Main Content */}
      <section className="bg-background py-32 rounded-t-[5rem] -mt-16 relative z-30">
        <div className="max-w-7xl mx-auto px-6">
          <div className="flex flex-col md:flex-row justify-between items-end mb-20 gap-10">
            <div className="max-w-xl">
              <h2 className="text-5xl font-black mb-6 tracking-tighter text-secondary">Thực đơn <span className="text-primary">tươi ngon</span> mỗi ngày.</h2>
              <p className="text-lg text-gray-500 font-medium">Chúng tôi thay đổi thực đơn hàng ngày để bạn luôn có những trải nghiệm mới mẻ và đầy đủ dinh dưỡng.</p>
            </div>
            
            <div className="flex gap-2 bg-white p-2 rounded-[2rem] shadow-soft border border-gray-100 overflow-x-auto no-scrollbar">
              {categories.map(cat => (
                <button 
                  key={cat}
                  onClick={() => setActiveCategory(cat)}
                  className={`px-8 py-3 rounded-2xl font-black text-xs uppercase tracking-widest transition-all whitespace-nowrap ${activeCategory === cat ? 'bg-primary text-white shadow-lg shadow-primary/20' : 'text-gray-400 hover:text-secondary'}`}
                >
                  {cat}
                </button>
              ))}
            </div>
          </div>

          {loading ? (
            <div className="flex flex-col items-center justify-center py-40">
              <div className="relative">
                <div className="w-20 h-20 border-4 border-primary/10 border-t-primary rounded-full animate-spin"></div>
                <div className="absolute inset-0 flex items-center justify-center">
                  <UtensilsCrossed size={24} className="text-primary animate-pulse" />
                </div>
              </div>
              <p className="text-gray-400 font-black uppercase tracking-[0.3em] mt-10 text-sm">Đang chuẩn bị...</p>
            </div>
          ) : foods.length > 0 ? (
            <motion.div layout className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-10">
              {foods.map((food, index) => (
                <FoodCard key={food.id} food={food} index={index} />
              ))}
            </motion.div>
          ) : (
            <div className="text-center py-40 card-premium bg-white/50 border-dashed border-2 border-gray-200">
              <div className="w-24 h-24 bg-gray-100 rounded-full flex items-center justify-center mx-auto mb-8">
                <Search size={40} className="text-gray-300" />
              </div>
              <h3 className="text-3xl font-black mb-4 tracking-tight">Hết sạch món rồi!</h3>
              <p className="text-gray-400 font-medium max-w-xs mx-auto">Vui lòng quay lại vào ngày mai hoặc thử tìm kiếm từ khóa khác.</p>
              <button onClick={() => fetchFoods()} className="btn-primary mt-10 px-12 rounded-3xl">Xem tất cả</button>
            </div>
          )}
        </div>
      </section>

      {/* Trust Section */}
      <section className="bg-secondary py-32 text-white">
        <div className="max-w-7xl mx-auto px-6 grid grid-cols-1 md:grid-cols-3 gap-20">
          {[
            { icon: <ShieldCheck size={40} />, title: 'Vệ sinh 5 sao', desc: 'Mọi quy trình đều đạt chuẩn an toàn thực phẩm khắt khe nhất.' },
            { icon: <Clock size={40} />, title: 'Giao hàng siêu tốc', desc: 'Hệ thống vận chuyển nội bộ giúp món ăn luôn nóng hổi khi đến tay.' },
            { icon: <Sparkles size={40} />, title: 'Vị ngon khó cưỡng', desc: 'Đội ngũ đầu bếp tâm huyết mang lại hương vị chuẩn nhà hàng.' }
          ].map((item, i) => (
            <div key={i} className="flex flex-col items-center text-center">
              <div className="bg-white/10 p-6 rounded-3xl mb-8 text-primary">
                {item.icon}
              </div>
              <h4 className="text-2xl font-black mb-4 tracking-tight">{item.title}</h4>
              <p className="text-gray-400 font-medium leading-relaxed">{item.desc}</p>
            </div>
          ))}
        </div>
      </section>
    </main>
  );
};

export default Home;
