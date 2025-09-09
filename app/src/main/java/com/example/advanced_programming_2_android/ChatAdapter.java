package com.example.advanced_programming_2_android;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.advanced_programming_2_android.database.Chat;
import com.example.advanced_programming_2_android.database.Message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatAdapter extends BaseAdapter {

    List<Chat> chats;

    private class ViewHolder {
        ImageView ivProfilePic;
        TextView tvDisplayName;
        TextView tvLastMessage;
        TextView tvTimestamp;
    }

    public ChatAdapter(List<Chat> chats) {
        this.chats = chats;
    }

    @Override
    public int getCount() {
        return chats.size();
    }

    @Override
    public Object getItem(int position) {
        return chats.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_container_chat, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.ivProfilePic = convertView.findViewById(R.id.ChatProfilePic);
            viewHolder.tvDisplayName = convertView.findViewById(R.id.tvChatDisplayName);
            viewHolder.tvLastMessage = convertView.findViewById(R.id.tvChatLastMessage);
            viewHolder.tvTimestamp = convertView.findViewById(R.id.tvChatTimestamp);

            convertView.setTag(viewHolder);
        }

        Chat c = chats.get(position);
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        Glide.with(parent.getContext())
                .load(c.getUser().getProfilePic())
                .into(viewHolder.ivProfilePic);
        viewHolder.tvDisplayName.setText(c.getUser().getDisplayName());
        if (c.getLastMessage() != null) {
            viewHolder.tvLastMessage.setText(c.getLastMessage().getContent());
            viewHolder.tvLastMessage.setTextColor(Color.BLACK);
            viewHolder.tvTimestamp.setText(changeStringToDate(c.getLastMessage().getCreated()));
        } else {
            viewHolder.tvLastMessage.setText("No massages");
            viewHolder.tvLastMessage.setTextColor(Color.GRAY);
            viewHolder.tvTimestamp.setText("");
        }

        return convertView;
    }

    public void updateChats(List<Chat> filteredChats) {
        chats.clear();
        chats.addAll(filteredChats);
        notifyDataSetChanged();
    }

    public String changeStringToDate(String timestamp) {
        String outputDateString = "";

        @SuppressLint("SimpleDateFormat") SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat outputFormat = new SimpleDateFormat("dd.MM.yyyy, hh:mm");

        try {
            Date date = inputFormat.parse(timestamp);
            outputDateString = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return outputDateString;
    }

    public void addChat(Chat chat) {
        this.chats.add(chat);
    }

    public List<Chat> getChats() {
        return chats;
    }
}
