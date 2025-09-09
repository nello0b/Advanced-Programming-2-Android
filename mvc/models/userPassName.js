const mongoose = require('mongoose');

var userPassNameSchema = new mongoose.Schema({
    username: {
        type: String,
        required: true
    }, 
    password: {
        type: String,
        required: true
    }, 
    displayName: {
        type: String,
        required: true
    },
    profilePic: {
        type: String,
        required: true
    }
})

module.exports = mongoose.model('userpassnames', userPassNameSchema)
