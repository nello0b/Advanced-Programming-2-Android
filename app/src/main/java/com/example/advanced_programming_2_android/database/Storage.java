package com.example.advanced_programming_2_android.database;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Storage {
    private ChatStorageDao chatDao;
    private MessageStorageDao messageDao;
    private ConversationStorageDao conversationDao;

    private UserDao userDao;

    private StorageDB db;

    private static Storage storage = null;

    private Storage(Context context){
        db = StorageDB.getInstance(context);
        chatDao = db.getChatDao();
        messageDao = db.getMessageDao();
        conversationDao = db.getConversationDao();
        userDao = db.getUserDao();
    }

    public static Storage getStorage(Context context){
        if(storage == null){
            storage = new Storage(context);
        }
        return storage;
    }

    public void clearStorage(){
        deleteAllChats();
        deleteAllConversations();
        deleteAllUsers();
        deleteAllMessages();
    }

    private void deleteAllMessages() {
        List<MessageStorage> messages = messageDao.getAllMessages();

        MessageStorage[] messageArray = messages.toArray(new MessageStorage[0]);

        messageDao.delete(messageArray);
    }

    private void deleteAllUsers() {
        List<User> users = userDao.getAllUsers();

        User[] userArray = users.toArray(new User[0]);

        userDao.delete(userArray);
    }


    private Chat chatStorageToChat(ChatStorage chatStorage){
        User user = userDao.getUserByUsername(chatStorage.getUser());
        return new Chat(chatStorage.getId(), user, chatStorage.getLastMessage());
    }

    public List<Chat> getAllChats(){
        List<ChatStorage> dbChats = chatDao.getAllChats();
        if(dbChats == null){
            return new ArrayList<>();
        }
        List<Chat> chats = new ArrayList<>();
        for(ChatStorage chat : dbChats){
            chats.add(chatStorageToChat(chat));
        }
        return chats;
    }

    public void insertNewChat(Chat chat) {
        if(chatDao.getChatById(chat.getId()) == null){
            if(userDao.getUserByUsername(chat.getUser().getUsername()) == null){
                userDao.insert(chat.getUser());
            }
            chatDao.insert(new ChatStorage(chat.getId(), chat.getUser().getUsername(), chat.getLastMessage()));
        }
    }

    public void deleteAllChats() {
        // Get all the chats from the database
        List<ChatStorage> chats = chatDao.getAllChats();

        // Convert the list to an array of Chat objects
        ChatStorage[] chatArray = chats.toArray(new ChatStorage[0]);

        // Delete all chats from the database
        chatDao.delete(chatArray);
    }

    public void updateChats(List<Chat> chats){
        List<ChatStorage> dbChats = chatDao.getAllChats();
        HashMap<Integer, ChatStorage> newChatsMap = new HashMap<>();

        for(Chat chat : chats){
            boolean wasEdited = false;
            for(ChatStorage dbChat : dbChats ){
                if(dbChat.getId() == chat.getId()){
                    dbChat.setLastMessage(chat.getLastMessage());
                    wasEdited = true;
                    break;
                }
            }
            // check if a the chat was not edited, so its new
            if (!wasEdited) {
                insertNewChat(chat);
            }
        }

        // Convert the list to an array of Chat objects
        ChatStorage[] chatArray = dbChats.toArray(new ChatStorage[0]);

        // update all chats from the database
        chatDao.update(chatArray);

    }


    private User removeImagesFromUser(User user){

        return new User(user.getUsername(), user.getDisplayName(), "");
    }

    /*  private Conversation removeImagesFromConversation(Conversation conversation){
        List<User> usersWithoutImage = new ArrayList<>();
        for (User user : conversation.getUsers()){
            usersWithoutImage.add(removeImagesFromUser(user));
        }
        List<Message> messagesWithoutImage= new ArrayList<>();
        for(Message message: conversation.getMessages()){
            messagesWithoutImage.add(new Message(message.getId(),message.getCreatedDate(), removeImagesFromUser(message.getSender()), message.getContact()));
        }
        return new Conversation(conversation.getId(), usersWithoutImage, messagesWithoutImage);
    }*/

    public void insertNewConversation(Conversation conversation) {
        if (conversationDao.getConversationById(conversation.getId()) == null) {
            List<String> users = new ArrayList<>();
            List<Integer> messages = new ArrayList<>();
            for(User user : conversation.getUsers()){
                users.add(user.getUsername());
                if(userDao.getUserByUsername(user.getUsername()) == null){
                    userDao.insert(user);
                }
            }
            for(Message message : conversation.getMessages()){
                messageDao.insert(new MessageStorage(message.getId(), message.getCreatedDate(), message.getSender().getUsername(), message.getContact()));
                // Get the ID of the message that was just inserted
                int messageId = messageDao.getLastInsertedMessageId();
                messages.add(messageId);
            }
            ConversationStorage conversationStorage = new ConversationStorage(conversation.getId(),users, messages);
            conversationDao.insert(conversationStorage);
        }
    }

    private Message messageStorageToMessage(MessageStorage messageStorage){
        User user = userDao.getUserByUsername(messageStorage.getSender());
        return new Message(messageStorage.getId2(), messageStorage.getCreatedDate(), user, messageStorage.getContact());
    }

    private Conversation ConversationStorageToConversation(ConversationStorage conversationStorage){
        List<User> users = new ArrayList<>();
        for(String username : conversationStorage.getUsers()){
            User user = userDao.getUserByUsername(username);
            users.add(user);
        }

        List<Message> messages = new ArrayList<>();
        for(Integer id : conversationStorage.getMessages()){
            MessageStorage message = messageDao.getMessageById(id);
            messages.add(messageStorageToMessage(message));
        }

        return new Conversation(conversationStorage.getId(), users, messages);
    }


    public Conversation getConversationByUsernames(List<String> usernames) {
        // Get all conversations from the database
        List<Integer> conversationIds = conversationDao.getAllConversationIds();

        // Iterate through the conversations and find the one with matching usernames
        for (Integer id : conversationIds) {
            ConversationStorage conversation = conversationDao.getConversationById(id);
            List<User> conversationUsers = new ArrayList<>();
            for(String username : conversation.getUsers()){
                User user = userDao.getUserByUsername(username);
                conversationUsers.add(user);
            }

            // Check if the conversation has the same number of users as the input usernames
            if (conversationUsers.size() == usernames.size()) {
                boolean matchFound = true;

                // Check if each username in the conversation matches the input usernames
                for (String username : usernames) {
                    boolean usernameFound = false;

                    // Check if the username exists in the conversation
                    for (User user : conversationUsers) {
                        if (user.getUsername().equals(username)) {
                            usernameFound = true;
                            break;
                        }
                    }

                    // If any username doesn't match, move on to the next conversation
                    if (!usernameFound) {
                        matchFound = false;
                        break;
                    }
                }

                // If all usernames match, return the conversation
                if (matchFound) {
                    return ConversationStorageToConversation(conversation);
                }
            }
        }

        // No conversation found with the specified usernames
        return null;
    }

    public void updateConversation(Conversation conversation) {
        // Check if the conversation ID already exists in the database
        ConversationStorage existingConversation = conversationDao.getConversationById(conversation.getId());

        if (existingConversation != null) {
            // Update the messages of the existing conversation
            List<Integer> messages =  existingConversation.getMessages();

            for(Message message : conversation.getMessages()){
                boolean matchFound = false;

                for(Integer id : messages){
                    if(message.getId() == messageDao.getMessageById(id).getId2()){
                        matchFound = true;
                        break;
                    }
                }

                // If there was no match, that message is new
                if (!matchFound) {
                    messageDao.insert(new MessageStorage(message.getId(), message.getCreatedDate(), message.getSender().getUsername(), message.getContact()));
                    // Get the ID of the message that was just inserted
                    int messageId = messageDao.getLastInsertedMessageId();
                    messages.add(messageId);
                }
            }

            existingConversation.setMessages(messages);
            conversationDao.update(existingConversation);
        } else {
            // Insert the new conversation into the database
            insertNewConversation(conversation);
        }
    }

    public void deleteAllConversations() {
        // Get all conversations from the database
        List<ConversationStorage> conversations = conversationDao.getAllConversations();

        // Convert the list to an array of Conversation objects
        ConversationStorage[] conversationArray = conversations.toArray(new ConversationStorage[0]);

        // Delete all conversations from the database
        conversationDao.delete(conversationArray);
    }
}