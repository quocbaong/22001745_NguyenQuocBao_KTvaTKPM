import React, { useState, useEffect } from 'react';
import api from '../api/axios';
import { useAuth } from '../context/AuthContext';
import { Package, Clock, MapPin, ChevronDown, ChevronUp, Bell, Truck, ShoppingBag, Receipt, AlertCircle } from 'lucide-react';
import { motion, AnimatePresence } from 'framer-motion';

const Orders = () => {
  const { user } = useAuth();
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [expandedOrder, setExpandedOrder] = useState(null);
  const [shippingInfo, setShippingInfo] = useState({});

  useEffect(() => {
    if (user) {
      fetchOrders();
    }
  }, [user]);

  const fetchOrders = async () => {
    try {
      const response = await api.get('/orders', { params: { userId: user.id } });
      setOrders(response.data);
      
      response.data.forEach(order => {
        fetchShipping(order.id);
      });
    } catch (error) {
      console.error('Error fetching orders:', error);
    } finally {
      setLoading(false);
    }
  };

  const fetchShipping = async (orderId) => {
    try {
      const response = await api.get(`/shipping/order/${orderId}`);
      setShippingInfo(prev => ({ ...prev, [orderId]: response.data }));
    } catch (error) {
      // Ignore
    }
  };

  const formatPrice = (price) => {
    return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price);
  };

  const getStatusConfig = (status) => {
    switch (status) {
      case 'PAID': return { label: 'Đã thanh toán', bg: 'bg-green-50', text: 'text-green-600' };
      case 'PENDING': return { label: 'Đang chuẩn bị', bg: 'bg-amber-50', text: 'text-amber-600' };
      case 'SHIPPED': return { label: 'Đang giao hàng', bg: 'bg-blue-50', text: 'text-blue-600' };
      case 'DELIVERED': return { label: 'Đã hoàn thành', bg: 'bg-primary/5', text: 'text-primary' };
      case 'CANCELLED': return { label: 'Đã hủy', bg: 'bg-red-50', text: 'text-red-600' };
      default: return { label: status, bg: 'bg-gray-100', text: 'text-gray-600' };
    }
  };

  if (loading) {
    return (
      <div className="min-h-screen bg-background flex flex-col items-center justify-center">
         <div className="w-16 h-16 border-4 border-primary/10 border-t-primary rounded-full animate-spin"></div>
         <p className="mt-8 text-gray-400 font-black uppercase tracking-widest text-xs">Đang tải đơn hàng...</p>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-background pt-32 pb-20 px-6">
      <div className="max-w-7xl mx-auto">
        <div className="flex flex-col md:flex-row justify-between items-start md:items-end mb-12 gap-6">
           <div className="flex items-center gap-4">
              <div className="bg-primary p-3 rounded-2xl text-white shadow-lg shadow-primary/20">
                 <Package size={28} />
              </div>
              <div>
                 <h1 className="text-5xl font-black tracking-tighter text-secondary">Đơn hàng.</h1>
                 <p className="text-gray-400 font-medium mt-1">Theo dõi lịch sử thưởng thức món ngon của bạn.</p>
              </div>
           </div>
           
           <div className="flex gap-4">
              <div className="bg-white px-6 py-3 rounded-2xl shadow-soft border border-gray-50 flex items-center gap-3">
                 <span className="w-2 h-2 bg-green-500 rounded-full animate-pulse"></span>
                 <span className="text-xs font-black text-secondary uppercase tracking-widest">{orders.length} Đơn hàng</span>
              </div>
           </div>
        </div>

        {orders.length === 0 ? (
          <div className="text-center py-40 card-premium bg-white/50 border-dashed border-2 border-gray-200">
             <div className="w-24 h-24 bg-gray-100 rounded-full flex items-center justify-center mx-auto mb-8">
                <Receipt size={40} className="text-gray-300" />
             </div>
             <h3 className="text-3xl font-black mb-4 tracking-tight">Chưa có đơn hàng nào!</h3>
             <p className="text-gray-400 font-medium max-w-xs mx-auto">Hãy đặt món ăn đầu tiên để tận hưởng hương vị tuyệt vời từ MiniFood.</p>
             <button onClick={() => navigate('/')} className="btn-primary mt-10 px-12 rounded-3xl">Đặt món ngay</button>
          </div>
        ) : (
          <div className="space-y-8">
            {orders.map((order, index) => {
              const status = getStatusConfig(order.status);
              const isExpanded = expandedOrder === order.id;

              return (
                <motion.div 
                  key={order.id}
                  initial={{ opacity: 0, y: 20 }}
                  animate={{ opacity: 1, y: 0 }}
                  transition={{ delay: index * 0.1 }}
                  className={`bg-white rounded-[3rem] shadow-soft border border-gray-50 overflow-hidden transition-all duration-500 ${isExpanded ? 'shadow-premium' : ''}`}
                >
                  {/* Order Header */}
                  <div className={`p-8 md:p-10 flex flex-col md:flex-row justify-between items-center gap-10 transition-colors ${isExpanded ? 'bg-gray-50/50' : 'bg-white'}`}>
                    <div className="grid grid-cols-2 md:grid-cols-4 gap-10 md:gap-16 flex-1 w-full">
                      <div>
                        <p className="text-[10px] font-black uppercase tracking-[0.2em] text-gray-400 mb-3">Mã đơn hàng</p>
                        <p className="text-lg font-black text-secondary tracking-tight">#{order.id}</p>
                      </div>
                      <div>
                        <p className="text-[10px] font-black uppercase tracking-[0.2em] text-gray-400 mb-3">Ngày đặt</p>
                        <p className="text-sm font-bold text-secondary flex items-center gap-2">
                           <Clock size={16} className="text-gray-300" />
                           {new Date(order.createdAt).toLocaleDateString('vi-VN')}
                        </p>
                      </div>
                      <div>
                        <p className="text-[10px] font-black uppercase tracking-[0.2em] text-gray-400 mb-3">Tổng cộng</p>
                        <p className="text-xl font-black text-primary tracking-tighter">{formatPrice(order.totalAmount)}</p>
                      </div>
                      <div className="flex flex-col items-start">
                        <p className="text-[10px] font-black uppercase tracking-[0.2em] text-gray-400 mb-3">Trạng thái</p>
                        <span className={`px-4 py-1.5 rounded-xl text-[10px] font-black uppercase tracking-widest ${status.bg} ${status.text}`}>
                          {status.label}
                        </span>
                      </div>
                    </div>

                    <button 
                      onClick={() => setExpandedOrder(isExpanded ? null : order.id)}
                      className={`w-14 h-14 rounded-full flex items-center justify-center transition-all ${isExpanded ? 'bg-primary text-white shadow-lg shadow-primary/20 rotate-180' : 'bg-gray-100 text-gray-400 hover:bg-gray-200'}`}
                    >
                      <ChevronDown size={24} />
                    </button>
                  </div>

                  {/* Expanded Content */}
                  <AnimatePresence>
                    {isExpanded && (
                      <motion.div 
                        initial={{ height: 0, opacity: 0 }}
                        animate={{ height: 'auto', opacity: 1 }}
                        exit={{ height: 0, opacity: 0 }}
                        transition={{ duration: 0.5, ease: "easeInOut" }}
                        className="border-t border-gray-100"
                      >
                        <div className="p-10 lg:p-16 grid grid-cols-1 lg:grid-cols-2 gap-20">
                          {/* Order Details */}
                          <div>
                            <h3 className="text-2xl font-black mb-8 tracking-tighter flex items-center gap-3 text-secondary">
                               <ShoppingBag size={24} className="text-primary" />
                               Chi tiết món ăn
                            </h3>
                            <div className="space-y-6">
                              {order.items.map((item, idx) => (
                                <div key={idx} className="flex justify-between items-center group">
                                  <div className="flex gap-5 items-center">
                                    <div className="w-12 h-12 bg-gray-50 rounded-2xl flex items-center justify-center font-black text-primary border border-gray-100">
                                      {item.quantity}x
                                    </div>
                                    <div>
                                       <p className="font-black text-secondary">{item.foodName}</p>
                                       <p className="text-[10px] text-gray-400 uppercase tracking-widest font-bold">Món chính</p>
                                    </div>
                                  </div>
                                  <span className="font-black text-secondary">{formatPrice(item.subtotal)}</span>
                                </div>
                              ))}
                            </div>
                            
                            <div className="mt-12 p-8 bg-gray-50 rounded-[2.5rem] border border-gray-100">
                               <div className="flex justify-between items-end">
                                  <div>
                                     <p className="text-[10px] font-black uppercase tracking-widest text-gray-400 mb-1">Phương thức thanh toán</p>
                                     <p className="font-black text-secondary flex items-center gap-2">
                                        <Receipt size={16} /> {order.paymentMethod === 'COD' ? 'Tiền mặt' : 'Chuyển khoản'}
                                     </p>
                                  </div>
                                  <div className="text-right">
                                     <p className="text-[10px] font-black uppercase tracking-widest text-gray-400 mb-1">Tổng thanh toán</p>
                                     <p className="text-3xl font-black text-primary tracking-tighter">{formatPrice(order.totalAmount)}</p>
                                  </div>
                               </div>
                            </div>
                          </div>

                          {/* Logistics Info */}
                          <div className="space-y-12">
                            {/* Delivery Location */}
                            <div>
                               <h3 className="text-2xl font-black mb-8 tracking-tighter flex items-center gap-3 text-secondary">
                                  <MapPin size={24} className="text-primary" />
                                  Địa chỉ nhận hàng
                               </h3>
                               <div className="bg-white p-8 rounded-[2.5rem] shadow-soft border border-gray-50">
                                  <p className="font-black text-secondary mb-2">{order.username}</p>
                                  <p className="text-gray-500 font-medium leading-relaxed mb-6">{order.deliveryAddress}</p>
                                  <div className="flex items-start gap-3 bg-amber-50 p-4 rounded-2xl text-amber-700">
                                     <AlertCircle size={18} className="shrink-0 mt-0.5" />
                                     <p className="text-xs font-bold leading-relaxed italic">
                                        Ghi chú: {order.notes || 'Không có ghi chú thêm.'}
                                     </p>
                                  </div>
                               </div>
                            </div>

                            {/* Tracking Timeline */}
                            {shippingInfo[order.id] && (
                              <div>
                                 <h3 className="text-2xl font-black mb-8 tracking-tighter flex items-center gap-3 text-secondary">
                                    <Truck size={24} className="text-primary" />
                                    Hành trình vận chuyển
                                 </h3>
                                 <div className="relative pl-10 space-y-10 before:content-[''] before:absolute before:left-[15px] before:top-2 before:bottom-2 before:w-[3px] before:bg-gray-100">
                                    <div className="relative">
                                       <div className="absolute -left-[35px] bg-white border-4 border-primary w-8 h-8 rounded-full flex items-center justify-center z-10 shadow-lg">
                                          <div className="w-2 h-2 bg-primary rounded-full"></div>
                                       </div>
                                       <p className="text-[10px] font-black uppercase tracking-widest text-gray-400 mb-1">Mã vận đơn</p>
                                       <p className="font-black text-secondary">{shippingInfo[order.id].trackingNumber}</p>
                                    </div>
                                    
                                    <div className="relative">
                                       <div className={`absolute -left-[35px] w-8 h-8 rounded-full flex items-center justify-center z-10 shadow-lg border-4 border-white ${shippingInfo[order.id].status === 'DELIVERED' ? 'bg-primary shadow-primary/30' : 'bg-secondary animate-pulse'}`}>
                                          <Truck size={12} className="text-white" />
                                       </div>
                                       <p className="text-[10px] font-black uppercase tracking-widest text-gray-400 mb-1">Trạng thái hiện tại</p>
                                       <p className="font-black text-primary uppercase tracking-tighter text-xl">{shippingInfo[order.id].status}</p>
                                       <p className="text-xs text-gray-400 font-medium mt-1 italic">
                                          Ước tính giao: {new Date(shippingInfo[order.id].estimatedDelivery).toLocaleTimeString('vi-VN')}
                                       </p>
                                    </div>
                                 </div>
                              </div>
                            )}
                          </div>
                        </div>
                      </motion.div>
                    )}
                  </AnimatePresence>
                </motion.div>
              );
            })}
          </div>
        )}
      </div>
    </div>
  );
};

export default Orders;
