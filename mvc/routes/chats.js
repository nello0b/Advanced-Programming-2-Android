const chatsController = require('../controllers/chats');

const express = require('express');
const router = express.Router();

router.route('/')
    .get(chatsController.getAllChats)
    .post(chatsController.addNewChat);

router.route('/:id')
    .get(chatsController.getFullChatById)
    .delete(chatsController.deleteFullChatById);

router.route('/:id/Messages')
    .post(chatsController.addNewMessageByChatId)
    .get(chatsController.getAllMessagesById);

module.exports = router;