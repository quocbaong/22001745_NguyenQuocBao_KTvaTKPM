const express = require('express');
const axios = require('axios');
const cors = require('cors');
const morgan = require('morgan');
require('dotenv').config();

const app = express();
const PORT = process.env.PORT || 8080;

// Service URLs from environment variables
const USER_SERVICE = process.env.USER_SERVICE_URL;
const TOUR_SERVICE = process.env.TOUR_SERVICE_URL;
const BOOKING_SERVICE = process.env.BOOKING_SERVICE_URL;
const PAYMENT_SERVICE = process.env.PAYMENT_SERVICE_URL;

app.use(cors());
app.use(express.json());
app.use(morgan('dev')); // Logging each request to the orchestrator

/**
 * API: POST /login
 * Chuyển tiếp yêu cầu đăng nhập sang User Service
 */
app.post('/login', async (req, res) => {
    try {
        console.log('--- Đang chuyển tiếp yêu cầu đăng nhập sang User Service ---');
        const response = await axios.post(`${USER_SERVICE}/login`, req.body);

        // Trả kết quả từ User Service về cho Frontend
        res.status(200).json(response.data);
    } catch (error) {
        console.error('Lỗi đăng nhập tại Orchestrator:', error.message);
        res.status(error.response?.status || 500).json({
            message: error.response?.data?.message || 'Lỗi kết nối User Service'
        });
    }
});

/**
 * API: GET /tours
 * Lấy danh sách tour từ Tour Service
 */
app.get('/tours', async (req, res) => {
    try {
        console.log('--- Đang lấy danh sách tour từ Tour Service ---');
        const response = await axios.get(`${TOUR_SERVICE}/tours`);
        res.status(200).json(response.data);
    } catch (error) {
        console.error('Lỗi lấy danh sách tour tại Orchestrator:', error.message);
        res.status(error.response?.status || 500).json({
            message: error.response?.data?.message || 'Lỗi kết nối Tour Service'
        });
    }
});

/**
 * API: POST /book-tour
 * Input: { userId, tourId }
 */
app.post('/book-tour', async (req, res) => {
    const { userId, tourId } = req.body;

    console.log(`\n--- Starting Booking Flow for User: ${userId}, Tour: ${tourId} ---`);

    try {
        // Step 1: Validate User
        console.log('Step 1: Validating user via User Service...');
        const userRes = await axios.get(`${USER_SERVICE}/users/${userId}`);
        const user = userRes.data;
        console.log(`> User found: ${user.name || userId}`);

        // Step 2: Get Tour Details
        console.log('Step 2: Getting tour details via Tour Service...');
        const tourRes = await axios.get(`${TOUR_SERVICE}/tours/${tourId}`);
        const tour = tourRes.data;
        console.log(`> Tour details retrieved: ${tour.name || tourId} - Price: ${tour.price}`);

        // Step 3: Create Booking
        console.log('Step 3: Creating booking via Booking Service...');
        const bookingRes = await axios.post(`${BOOKING_SERVICE}/bookings`, {
            userId,
            tourId,
            price: tour.price,
            date: new Date().toISOString()
        });
        const bookingId = bookingRes.data.bookingId;
        console.log(`> Booking created successfully. ID: ${bookingId}`);

        // Step 4: Process Payment
        console.log('Step 4: Processing payment via Payment Service...');
        const paymentRes = await axios.post(`${PAYMENT_SERVICE}/payments`, {
            bookingId,
            amount: tour.price,
            userId
        });

        if (paymentRes.data.status === 'success') {
            console.log('> Payment successful!');
            console.log('--- Booking Flow Completed Successfully ---\n');
            return res.status(200).json({
                status: 'success',
                bookingId: bookingId
            });
        } else {
            console.error('> Payment failed: ' + (paymentRes.data.message || 'Unknown error'));
            throw new Error(paymentRes.data.message || 'Payment failed');
        }

    } catch (error) {
        const errorMessage = error.response?.data?.message || error.message;
        console.error(`\n!!! Flow Failed at some point: ${errorMessage}`);
        console.log('--- Booking Flow Terminated ---\n');

        return res.status(error.response?.status || 500).json({
            status: 'fail',
            message: errorMessage
        });
    }
});

app.listen(PORT, "0.0.0.0", () => {
    console.log(`Orchestrator Service is running on http://localhost:${PORT}`);
    console.log(`Connected Services:`);
    console.log(`- User: ${USER_SERVICE}`);
    console.log(`- Tour: ${TOUR_SERVICE}`);
    console.log(`- Booking: ${BOOKING_SERVICE}`);
    console.log(`- Payment: ${PAYMENT_SERVICE}`);
});
