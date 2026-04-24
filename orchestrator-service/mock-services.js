const express = require('express');

function createMockService(name, port, responseData, method = 'get', path = '*') {
    const app = express();
    app.use(express.json());
    
    app[method](path, (req, res) => {
        console.log(`[${name}] Received request: ${req.method} ${req.url}`);
        res.json(responseData(req));
    });

    app.listen(port, () => {
        console.log(`${name} is mocking on port ${port}`);
    });
}

// Mock User Service (8081)
const userApp = express();
userApp.use(express.json());
userApp.get('/users/:id', (req, res) => {
    console.log(`[User Service] Received request: GET /users/${req.params.id}`);
    res.json({ id: req.params.id, name: 'Nguyen Van A', email: 'test@example.com' });
});
userApp.post('/login', (req, res) => {
    console.log(`[User Service] Received request: POST /login`, req.body);
    const { username, password } = req.body;
    if (username === 'admin' && password === '123') {
        res.json({ status: 'success', userId: '1', name: 'Nguyen Van A' });
    } else {
        res.status(401).json({ status: 'fail', message: 'Invalid credentials' });
    }
});
userApp.listen(8081, () => console.log('User Service is mocking on port 8081'));

// Mock Tour Service (8082)
const tourApp = express();
tourApp.use(express.json());
tourApp.get('/tours', (req, res) => {
    console.log(`[Tour Service] Received request: GET /tours`);
    res.json([
        { id: '101', name: 'Tour Da Lat 3 ngay 2 dem', price: 1500000 },
        { id: '102', name: 'Tour Phu Quoc 4 ngay 3 dem', price: 3500000 },
        { id: '103', name: 'Tour Ha Giang linh thieng', price: 2200000 }
    ]);
});
tourApp.get('/tours/:id', (req, res) => {
    console.log(`[Tour Service] Received request: GET /tours/${req.params.id}`);
    res.json({ id: req.params.id, name: 'Tour Da Lat 3 ngay 2 dem', price: 1500000 });
});
tourApp.listen(8082, () => console.log('Tour Service is mocking on port 8082'));

// Mock Booking Service (8083)
createMockService('Booking Service', 8083, (req) => ({
    status: 'success',
    bookingId: 'B' + Math.floor(Math.random() * 10000)
}), 'post', '/bookings');

// Mock Payment Service (8084)
createMockService('Payment Service', 8084, (req) => {
    // Gia lap thanh cong 80%, that bai 20%
    const isSuccess = Math.random() > 0.2;
    return isSuccess 
        ? { status: 'success', message: 'Payment successful' }
        : { status: 'fail', message: 'Payment failed due to insufficient balance' };
}, 'post', '/payments');
