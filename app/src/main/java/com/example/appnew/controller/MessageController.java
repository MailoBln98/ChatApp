package com.example.appnew.controller;

import android.content.Context;
import androidx.lifecycle.LiveData;

import com.example.appnew.model.Message;
import com.example.appnew.model.MessageDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageController {

    private final MessageDatabase messageDatabase;
    private final ExecutorService executorService;

    public MessageController(Context context) {
        // Initialisiere die Room-Datenbank
        messageDatabase = MessageDatabase.getInstance(context);

        // Erstelle einen ExecutorService für Hintergrundthreads
        executorService = Executors.newSingleThreadExecutor();
    }

    // Neue Nachricht in die Datenbank einfügen
    public void addMessage(Message message) {
        executorService.execute(() -> {
            try {
                messageDatabase.messageDao().insert(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // LiveData mit allen Nachrichten abrufen
    public LiveData<List<Message>> getAllMessages() {
        try {
            return messageDatabase.messageDao().getAllMessages();
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Falls ein Fehler auftritt, wird null zurückgegeben
        }
    }


}


