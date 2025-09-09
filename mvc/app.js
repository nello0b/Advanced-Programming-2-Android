const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const userSockets = require('./services/socket.js');

const mongoose = require('mongoose');

const customEnv = require('custom-env');
customEnv.env(process.env.NODE_ENV, './config');

mongoose.connect(process.env.CONNECTION_STRING, {
    useNewUrlParser: true,
    useUnifiedTopology: true
});

const io = require('socket.io')(7000, { cors: { origin: '*' } });

io.on('connection', socket => {
    socket.on('UserConnect', username => {
        userSockets.set(username, socket);
        socket.join(username);
    });

    socket.on('addMessage', sentTo => {
        socket.broadcast.to(sentTo).emit('newMessage');
    });

    socket.on('addChat', sentTo => {
        socket.broadcast.to(sentTo).emit('newChat');
    });

    socket.on('disconnect', () => {
        for (const [key, value] of userSockets) {
            if (value === socket) {
                userSockets.delete(key);
                break;
            }
        }
    });
});



// routers
const routerUsers = require('./routes/users.js');
const routerTokens = require('./routes/tokens.js');
const routerChats = require('./routes/chats.js');
const routerFirebase = require('./routes/firebase.js');

const server = express();

const build = express.static('public');
server.use(build);
server.use(cors());
server.use(bodyParser.urlencoded({ extended: true }));
server.use(express.json());

server.use('/', build);
server.use('/chat', build);
server.use('/register', build);

server.use('/api/Users', routerUsers);
server.use('/api/Tokens', routerTokens);
server.use('/api/Chats', routerChats);
server.use('/api/fireBaseTokens', routerFirebase);

server.listen(process.env.PORT);
