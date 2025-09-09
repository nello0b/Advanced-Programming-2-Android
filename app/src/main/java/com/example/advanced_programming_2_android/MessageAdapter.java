package com.example.advanced_programming_2_android;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.advanced_programming_2_android.database.Message;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Message> messages;
    private String currentUser; // Assuming you have a variable to store the current user ID

    private static final int VIEW_TYPE_RECEIVED = 1;
    private static final int VIEW_TYPE_SENT = 2;


    public MessageAdapter(List<Message> messages, String currentUser) {
        this.messages = messages;
        this.currentUser = currentUser;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;

        if (viewType == VIEW_TYPE_RECEIVED) {
            view = inflater.inflate(R.layout.item_container_recieved_message, parent, false);
            return new ReceivedMessageViewHolder(view);
        } else {
            view = inflater.inflate(R.layout.item_container_send_message, parent, false);
            return new SentMessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);

        if (holder instanceof ReceivedMessageViewHolder) {
            ReceivedMessageViewHolder receivedHolder = (ReceivedMessageViewHolder) holder;
            receivedHolder.tvContent.setText(message.getContact());
            String iso8601String = message.getCreatedDate(); // ISO 8601 string
            String formattedTime = formatISO8601ToTime(iso8601String); // Format the time
            receivedHolder.tvTime.setText(formattedTime);
        } else if (holder instanceof SentMessageViewHolder) {
            SentMessageViewHolder sentHolder = (SentMessageViewHolder) holder;
            sentHolder.tvContent.setText(message.getContact());
            String iso8601String = message.getCreatedDate(); // ISO 8601 string
            String formattedTime = formatISO8601ToTime(iso8601String); // Format the time
            sentHolder.tvTime.setText(formattedTime);
        }
    }

    private String formatISO8601ToTime(String iso8601String) {
        LocalDateTime dateTime = LocalDateTime.parse(iso8601String);
        DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return dateTime.format(targetFormatter);
    }

    @Override
    public int getItemCount() {
        if (messages == null) {
            return 0; // Return 0 when the messages list is null
        }
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);

        if (message.getSender().getUsername().equals(currentUser)) {
            return VIEW_TYPE_SENT;
        } else {
            return VIEW_TYPE_RECEIVED;
        }
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    private static class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {
        TextView tvContent;
        TextView tvTime;

        ReceivedMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }

    private static class SentMessageViewHolder extends RecyclerView.ViewHolder {
        TextView tvContent;
        TextView tvTime;

        SentMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    public List<Message> getMessages() {
        return this.messages;
    }
}
