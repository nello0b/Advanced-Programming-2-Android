const Chat = require('../models/chat');
const User = require('./users');
const DateParser = require('../DateParser/dateParser');
const UserServices = require('./users');

const getAllChats = async (myUsername) => {
    const allChatsWithMessages = await Chat.find({ "users.0.username": myUsername });
    if (allChatsWithMessages.length !== 0) {
        const allChats = allChatsWithMessages.map((chat) => {
            var lastMessage;
            var lastMessageJson;
            if (chat.messages.length !== 0) {
                lastMessage = chat.messages[chat.messages.length - 1];
                lastMessageJson = {
                    id: lastMessage.id,
                    created: lastMessage.created,
                    content: lastMessage.content
                }
            } else {
                lastMessageJson = null;
            }
           
            const chatWithLastMessage = {
                id: chat.id,
                user: chat.users[1],
                lastMessage: lastMessageJson
            }
    
            return chatWithLastMessage;
        });         
   
        return allChats;   
    } else {
        return [];
    }
}

const getLastChat = async () => {
    const allChatsWithMessages = await Chat.find({});
    if (allChatsWithMessages.length !== 0) {
        const lastChat = allChatsWithMessages[allChatsWithMessages.length - 1];
        return lastChat;  
    } else {
        return null;
    }
}

const addNewChat = async (myUsername, username) => {
    const myUser = await User.getUserByUsername(myUsername);
    const otherUser = await User.getUserByUsername(username);
    if (otherUser) { 
        var newChatId;
        const lastChat = await getLastChat();   
        if (lastChat) {
            newChatId = lastChat.id + 1;
        } else {
            newChatId = 1;
        }
        const newChat1 = new Chat({
            id: newChatId,
            users: [myUser, otherUser],
            messages: []
        });
        const newChat2 = new Chat({
            id: newChatId,
            users: [otherUser, myUser],
            messages: []
        });
        await newChat2.save();
        return await newChat1.save();         
    } else {
        return null;
    } 
}

const getFullChatById = async (myUsername, id) => {
    const fullChat = await Chat.find({ id: id, "users.0.username": myUsername });
    if (fullChat) {
        return fullChat[0];
    } else {
        return null;
    }
}

const deleteFullChatById = async (id) => {
    const deletedChat = await Chat.delete({ id: id });
    if (deletedChat) {
        return deletedChat;
    } else {
        return null;
    }
}

const addNewMessageByChatId = async (myUsername, id, newMessageContent) => {
    const fullChat = await Chat.find({ id: id }); 
    if (fullChat) {
        fullChat.map(async (chat) => {
            const senderUser = await UserServices.getUserByUsername(myUsername);
            var newMessageId;
            if (chat.messages.length !== 0) {
                newMessageId = chat.messages[chat.messages.length - 1].id + 1;
            } else {
                newMessageId = 1;
            }    
            const newMessage = {
                id: newMessageId,
                created: DateParser.getCurrentDateTime(),
                sender: senderUser,
                content: newMessageContent
            }        

            chat.messages.push(newMessage);
            return await chat.save();  
        }); 
        return fullChat;     
    } else {
        return null;
    }
}

const getAllMessagesById = async (myUsername, id) => {
    const fullChat = await Chat.findOne({ id: id, "users.0.username": myUsername });
    if (fullChat) {
        const messageList = fullChat.messages.map((message) => {
            var messageJson = {
                id: message.id,
                created: message.created,
                sender: message.sender.username,
                content: message.content
            }
            return messageJson;
        });
        return messageList.reverse();
    } else {
        return null;
    }
}

module.exports = { addNewChat, getAllChats, getFullChatById, deleteFullChatById, addNewMessageByChatId, getAllMessagesById};