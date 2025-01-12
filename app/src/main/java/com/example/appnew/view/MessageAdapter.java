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

/**
 * Der MessageAdapter verwaltet die Darstellung von Nachrichten in einem RecyclerView.
 * Er sorgt dafür, dass jede Nachricht in einem eigenen ViewHolder angezeigt wird und
 * Benutzerinteraktionen auf Nachrichten behandelt werden können.
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    /**
     * Liste der anzuzeigenden Nachrichten.
     */
    private List<Message> messages = new ArrayList<>();

    /**
     * Listener für Klickaktionen auf Nachrichten.
     */
    private OnMessageClickListener onMessageClickListener;

    /**
     * Interface zur Definition von Klickaktionen auf Nachrichten.
     */
    public interface OnMessageClickListener {
        /**
         * Wird aufgerufen, wenn eine Nachricht angeklickt wird.
         *
         * @param message Die angeklickte Nachricht.
         */
        void onMessageClick(Message message);
    }

    /**
     * Setzt den Listener für Klickaktionen auf Nachrichten.
     *
     * @param listener Der Listener für Klickaktionen.
     */
    public void setOnMessageClickListener(OnMessageClickListener listener) {
        this.onMessageClickListener = listener;
    }

    /**
     * Setzt die Liste der Nachrichten im Adapter und aktualisiert den RecyclerView.
     *
     * @param messages Die Liste der anzuzeigenden Nachrichten.
     */
    public void setMessages(List<Message> messages) {
        if (messages != null) {
            this.messages = messages;
        } else {
            this.messages = new ArrayList<>(); // Fallback auf eine leere Liste
        }
        notifyDataSetChanged(); // Aktualisiert den RecyclerView
    }

    /**
     * Erstellt einen neuen ViewHolder für Nachrichten.
     *
     * @param parent   Das übergeordnete ViewGroup-Element.
     * @param viewType Der Typ der View (nicht genutzt, da einheitliche Layouts verwendet werden).
     * @return Ein neuer MessageViewHolder.
     */
    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_item, parent, false);
        return new MessageViewHolder(view);
    }

    /**
     * Verknüpft eine Nachricht mit einem ViewHolder.
     *
     * @param holder   Der ViewHolder, der die Nachricht anzeigen soll.
     * @param position Die Position der Nachricht in der Liste.
     */
    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messages.get(position);

        // Nachrichtentext setzen
        holder.messageText.setText(message.getSender() + ": " + message.getContent());

        // Klick-Listener für die Nachricht setzen
        holder.itemView.setOnClickListener(v -> {
            if (onMessageClickListener != null) {
                onMessageClickListener.onMessageClick(message);
            }
        });
    }

    /**
     * Gibt die Anzahl der Nachrichten in der Liste zurück.
     *
     * @return Die Anzahl der Nachrichten.
     */
    @Override
    public int getItemCount() {
        return messages.size();
    }

    /**
     * ViewHolder-Klasse zur Darstellung einzelner Nachrichten.
     */
    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        /**
         * TextView zur Anzeige des Nachrichtentextes.
         */
        TextView messageText;

        /**
         * Konstruktor für den ViewHolder.
         *
         * @param itemView Die View, die in diesem ViewHolder verwaltet wird.
         */
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.message_text);
        }
    }
}