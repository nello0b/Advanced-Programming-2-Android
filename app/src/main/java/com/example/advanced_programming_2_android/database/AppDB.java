package com.example.advanced_programming_2_android.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {User.class, Message.class, Chat.class,Conversation.class, Settings.class}, version = 15, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDB extends RoomDatabase {


    private static AppDB instance; // Add the instance variable

    public static synchronized AppDB getInstance() {
        return instance;
    }
    public static synchronized AppDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDB.class, "database-name")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract UserDao getUserDao();
    public abstract MessageDao getMessageDao();
    public abstract ChatDao getChatDao();
    public abstract ConversationDao getConversationDao();
    public abstract SettingsDao getSettingsDao();
}
// how to use:
// AppDB db = AppDB.getInstance(this);
// UserDao userDao = db.getUserDao();
// userDao.insert(user);
// userDao.delete(user);
// userDao.update(user);
// List<User> users = userDao.getAllUsers();
// User user = userDao.getUserByUsername("username");
// User user = userDao.getUserById(1);
// ChatDao chatDao = db.getChatDao();
// chatDao.insert(chat);
// chatDao.delete(chat);
// chatDao.update(chat);
// List<Chat> chats = chatDao.getAllChats();
// Chat chat = chatDao.getChat(1);
// MessageDao messageDao = db.getMessageDao();
// messageDao.insert(message);
// messageDao.delete(message);
// messageDao.update(message);
// List<Message> messages = messageDao.getAllMessages();
// List<Message> messages = messageDao.getAllMessagesByUser("username");
// Message message = messageDao.getMessageById(1);

