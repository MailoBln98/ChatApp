package com.example.appnew.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appnew.R;
import com.example.appnew.model.Message;
import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> messages = new ArrayList<>();
    private OnMessageClickListener onMessageClickListener;

    // Interface für Klickaktionen
    public interface OnMessageClickListener {
        void onMessageClick(Message message);
    }

    // Methode, um den Listener zu setzen
    public void setOnMessageClickListener(OnMessageClickListener listener) {
        this.onMessageClickListener = listener;
    }

    // Methode, um die Nachrichten in den Adapter zu setzen
    public void setMessages(List<Message> messages) {
        if (messages != null) {
            this.messages = messages;
        } else {
            this.messages = new ArrayList<>(); // Fallback auf leere Liste
        }
        notifyDataSetChanged(); // Aktualisiert den RecyclerView
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_item, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messages.get(position);

        // Nachricht setzen
        holder.messageText.setText(message.getSender() + ": " + message.getContent());

        // Klick-Listener für die Nachricht setzen
        holder.itemView.setOnClickListener(v -> {
            if (onMessageClickListener != null) {
                onMessageClickListener.onMessageClick(message);
            }
        });
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    // ViewHolder Klasse für Nachrichten
    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.message_text);
        }
    }
}