package com.example.appnew.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appnew.R;
import com.example.appnew.model.Message;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatViewHolder> {

    private List<Message> chatMessages;
    private final OnItemClickListener clickListener;

    public interface OnItemClickListener {
        void onItemClick(Message message);
    }

    public ChatListAdapter(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setMessages(List<Message> messages) {
        this.chatMessages = messages;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Message message = chatMessages.get(position);
        holder.bind(message, clickListener);
    }

    @Override
    public int getItemCount() {
        return chatMessages != null ? chatMessages.size() : 0;
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {

        private final TextView messageTextView;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.message_text);
        }

        public void bind(final Message message, final OnItemClickListener listener) {
            messageTextView.setText(message.getContent());
            itemView.setOnClickListener(v -> listener.onItemClick(message));
        }
    }

    public static Matcher<RecyclerView.ViewHolder> withMessageContent(String expectedContent) {
        return new TypeSafeMatcher<RecyclerView.ViewHolder>() {
            @Override
            protected boolean matchesSafely(RecyclerView.ViewHolder viewHolder) {
                if (!(viewHolder instanceof ChatViewHolder)) {
                    return false;
                }
                ChatViewHolder chatViewHolder = (ChatViewHolder) viewHolder;
                String actualContent = chatViewHolder.messageTextView.getText().toString().trim();
                return actualContent.equals(expectedContent.trim());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("ViewHolder with message content: " + expectedContent);
            }
        };
    }



}