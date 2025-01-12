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

/**
 * Adapter für die RecyclerView in der ChatListActivity.
 * Verwendet das ViewHolder-Pattern zur effizienten Anzeige von Nachrichten in einer Liste.
 */
public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatViewHolder> {

    /**
     * Liste der Nachrichten, die in der RecyclerView angezeigt werden sollen.
     */
    private List<Message> chatMessages;

    /**
     * Listener, der auf Klicks auf Listenelemente reagiert.
     */
    private final OnItemClickListener clickListener;

    /**
     * Schnittstelle für Klick-Ereignisse auf Nachrichten.
     */
    public interface OnItemClickListener {
        /**
         * Wird aufgerufen, wenn auf eine Nachricht geklickt wird.
         *
         * @param message Die geklickte Nachricht.
         */
        void onItemClick(Message message);
    }

    /**
     * Konstruktor für den ChatListAdapter.
     *
     * @param clickListener Listener für Klick-Ereignisse.
     */
    public ChatListAdapter(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    /**
     * Setzt die Liste der Nachrichten und aktualisiert die RecyclerView.
     *
     * @param messages Die neuen Nachrichten, die angezeigt werden sollen.
     */
    public void setMessages(List<Message> messages) {
        this.chatMessages = messages;
        notifyDataSetChanged();
    }

    /**
     * Erstellt eine neue ViewHolder-Instanz.
     *
     * @param parent   Das übergeordnete ViewGroup-Element.
     * @param viewType Der Typ der View (in diesem Fall immer gleich).
     * @return Eine neue Instanz von {@link ChatViewHolder}.
     */
    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        return new ChatViewHolder(view);
    }

    /**
     * Bindet eine Nachricht an den ViewHolder.
     *
     * @param holder   Der ViewHolder, der gebunden wird.
     * @param position Die Position des Elements in der Liste.
     */
    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Message message = chatMessages.get(position);
        holder.bind(message, clickListener);
    }

    /**
     * Gibt die Anzahl der Nachrichten in der Liste zurück.
     *
     * @return Die Anzahl der Nachrichten oder 0, wenn keine Nachrichten vorhanden sind.
     */
    @Override
    public int getItemCount() {
        return chatMessages != null ? chatMessages.size() : 0;
    }

    /**
     * ViewHolder für die Nachrichtenliste.
     * Verwaltet die Anzeige eines einzelnen Listenelements.
     */
    public static class ChatViewHolder extends RecyclerView.ViewHolder {

        /**
         * TextView zur Anzeige des Nachrichtentexts.
         */
        private final TextView messageTextView;

        /**
         * Konstruktor für den ViewHolder.
         *
         * @param itemView Die View des Listenelements.
         */
        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.message_text);
        }

        /**
         * Bindet eine Nachricht an die View und setzt den Klick-Listener.
         *
         * @param message  Die Nachricht, die angezeigt werden soll.
         * @param listener Der Klick-Listener.
         */
        public void bind(final Message message, final OnItemClickListener listener) {
            messageTextView.setText(message.getContent());
            itemView.setOnClickListener(v -> listener.onItemClick(message));
        }
    }

    /**
     * Matcher für Espresso-Tests, um nach einem bestimmten Nachrichtentext zu suchen.
     *
     * @param expectedContent Der erwartete Inhalt der Nachricht.
     * @return Ein Matcher, der überprüft, ob eine View den erwarteten Inhalt hat.
     */
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