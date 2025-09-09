const mongoose = require('mongoose');

var messageSchema = new mongoose.Schema({
    id: {
        type: Number,
        required: true
    },
    created: {
        type: String,
        required: true,
        default: () => new Date().toISOString()
    },
    sender: {
        username: {
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
    },
    content: {
        type: String,
        required: true
    }
});

module.exports = mongoose.model('messages', messageSchema);
