const jwt = require('jsonwebtoken');

const checkAuthStatus = (req, res, next) => {
    const authHeader = req.headers['authorization'];
    const token = authHeader && authHeader.split(' ')[1];

    if (!token) {
        req.isLoggedIn = false; 
        return next(); 
    }

    jwt.verify(token, process.env.JWT_KEY, (err, user) => {
        if (err) {
            req.isLoggedIn = false; 
        } else {
            req.isLoggedIn = true; 
            req.user = user; 
        }
        next(); 
    });
};

module.exports = checkAuthStatus;