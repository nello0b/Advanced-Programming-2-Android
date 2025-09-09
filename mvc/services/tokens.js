const jwt = require("jsonwebtoken");
const userPassName = require('../models/userPassName.js');
const customEnv = require('custom-env').env(process.env.NODE_ENV, './config');
customEnv.env(process.env.NODE_ENV, './config');
const secretKey =  process.env.KEY;
const expiration = process.env.TOKEN_EXPIRATION;


const createToken = async (username, password) => {    
    const user = await userPassName.findOne({ username: username, password: password });   
    if (!user) {
        return null;
    }

    const payload = { username: user.username }; 
    const token = jwt.sign(payload, secretKey, { expiresIn: expiration });
  
    return token;
}

const getUsernameFromToken = (token) => {
    try {
        const decoded = jwt.verify(token, secretKey);
        return decoded.username;
    } catch (error) {
        return null;
    }
};

module.exports = { createToken, getUsernameFromToken };