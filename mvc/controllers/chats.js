const chatsService = require('../services/chats.js');
const tokensController = require('./tokens.js');
const firebase = require('../services/firebase.js');
const User = require('../services/users.js');
const userSockets = require('../services/socket.js');



const admin = require('firebase-admin');
const serviceAccount = require('../config/advanced-programming-2-android-firebase-adminsdk-u82e9-e8d7f4a76c.json');

const io = require('socket.io-client')

const socket = io.connect('http://localhost:7000');


// Initialize the Firebase Admin SDK with the JSON key object
admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
});

const getAllChats = async (req, res) => {
    const username = await tokensController.getUsernameFromToken(req, res);
    const chats = await chatsService.getAllChats(username);
    if (!chats) {
        return res.status(404).json({ error: 'chats not found' });
    }
    res.status(200).json(chats);
}

const addNewChat = async (req, res) => {
    const username = await tokensController.getUsernameFromToken(req, res);
    const user = await User.getUserByUsername(username);
    const chat = await chatsService.addNewChat(username, req.body.username);
    if (!chat) {
        let otherUser = await User.getUserByUsername(req.body.username);
        if (!otherUser) {
            return res.status(400).json({ error: 'the inputted user doesnt exists' });
        }
        return res.status(404).json({ error: 'chat not found' });
    }

    //how sent the message
    const givenUsername = username;

    //how sent the message to
    const sentToUsername = req.body.username;

    // Define the notification payload
    const tokensFirebase = firebase.getTokens();
    const tokenFirebase = tokensFirebase.find(token => token.username === sentToUsername)?.token;

    if (tokenFirebase) {
        const message = {
            notification: {
                title: "New chat had been added",
                body: user.displayName + " has started a chat with you",
            },
            // Specify the target device token(s) or topic
            token: tokenFirebase,
        };
        // Send the notification
        try {
            admin.messaging().send(message);
        }
        catch (err) {
            console.log(err);
        }
    }


    if (!userSockets.has(givenUsername) && userSockets.has(sentToUsername)) {
        socket.emit('addChat', sentToUsername);
    }

    res.status(200).json(chat);
}

const getFullChatById = async (req, res) => {
    const username = await tokensController.getUsernameFromToken(req, res);
    const chat = await chatsService.getFullChatById(username, req.params.id);
    if (!chat) {
        return res.status(404).json({ error: 'chat not found' });
    }
    res.status(200).json(chat);
}

const deleteFullChatById = async (req, res) => {
    const chat = await chatsService.deleteFullChatById(req.params.id);
    if (!chat) {
        return res.status(404).json({ error: 'chat not found' });
    }
    res.status(200).json(chat);
}

const addNewMessageByChatId = async (req, res) => {
    const username = await tokensController.getUsernameFromToken(req, res);
    const user = await User.getUserByUsername(username);
    const chat = await chatsService.addNewMessageByChatId(username, req.params.id, req.body.msg);
    if (!chat) {
        return res.status(404).json({ error: 'chat not found' });
    }

    //how sent the message
    const givenUsername = username;

    const chatUsers = chat[0].users;

    const sentToUsernameArray = chatUsers.map(user => user.username).filter(username => username !== givenUsername);
    //how sent the message to
    const sentToUsername = sentToUsernameArray[0]


    // Define the notification payload
    const tokensFirebase = firebase.getTokens();
    const tokenFirebase = tokensFirebase.find(token => token.username === sentToUsername)?.token;



    if (tokenFirebase) {
        const message = {
            notification: {
                title: user.displayName + " sent you a message",
                body: req.body.msg,
            },
            // Specify the target device token(s) or topic
            token: tokenFirebase,
        };
        // Send the notification
        try {
            admin.messaging().send(message);
        }
        catch (err) {
            console.log(err);
        }
    }


    if (!userSockets.has(givenUsername) && userSockets.has(sentToUsername)) {
        socket.emit('addMessage', sentToUsername);
    }



    res.status(200).json(chat);

}


const getAllMessagesById = async (req, res) => {
    const username = await tokensController.getUsernameFromToken(req, res);
    const chat = await chatsService.getAllMessagesById(username, req.params.id);
    if (!chat) {
        return res.status(404).json({ error: 'chat not found' });
    }
    res.status(200).json(chat);
}

module.exports = { getAllChats, addNewChat, getFullChatById, deleteFullChatById, addNewMessageByChatId, getAllMessagesById }

