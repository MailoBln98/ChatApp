package com.example.appnew.controller;

import android.content.Context;
import androidx.lifecycle.LiveData;

import com.example.appnew.interfaces.DataManagerInterface;
import com.example.appnew.model.Message;
import com.example.appnew.model.MessageDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Der MessageController ist verantwortlich für die Verwaltung von Nachrichten
 * in der Room-Datenbank und implementiert das {@link DataManagerInterface},
 * um eine allgemeine Datenverwaltungsschnittstelle bereitzustellen.
 */
public class MessageController implements DataManagerInterface {

    private final MessageDatabase messageDatabase;
    private final ExecutorService executorService;

    /**
     * Konstruktor zur Initialisierung des MessageControllers.
     *
     * @param context Der Anwendungskontext, der zum Zugriff auf die Datenbank benötigt wird.
     */
    public MessageController(Context context) {
        // Initialisiere die Room-Datenbank
        messageDatabase = MessageDatabase.getInstance(context);

        // Erstelle einen ExecutorService für Hintergrundthreads
        executorService = Executors.newSingleThreadExecutor();
    }

    /**
     * Fügt eine neue Nachricht in die Datenbank ein.
     *
     * @param message Die Nachricht, die in die Datenbank eingefügt werden soll.
     */
    public void addMessage(Message message) {
        executorService.execute(() -> {
            try {
                messageDatabase.messageDao().insert(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Ruft alle Nachrichten aus der Datenbank ab.
     *
     * @return Eine LiveData-Liste aller Nachrichten.
     */
    public LiveData<List<Message>> getAllMessages() {
        try {
            return messageDatabase.messageDao().getAllMessages();
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Falls ein Fehler auftritt, wird null zurückgegeben
        }
    }

    /**
     * Implementierung der Methode aus {@link DataManagerInterface},
     * die eine generische Dateninsertion ermöglicht.
     *
     * @param data Die einzufügenden Daten, erwartet wird ein {@link Message}-Objekt.
     */
    @Override
    public void insertData(Object data) {
        if (data instanceof Message) {
            addMessage((Message) data);
        } else {
            throw new IllegalArgumentException("Ungültiger Datentyp: Erwartet wird eine Instanz von Message.");
        }
    }

    /**
     * Implementierung der Methode aus {@link DataManagerInterface},
     * die alle gespeicherten Daten abruft.
     *
     * @return Eine Liste der gespeicherten Nachrichten als {@link LiveData}.
     */
    @Override
    public Object fetchData() {
        return getAllMessages();
    }

    /**
     * Implementierung der Methode aus {@link DataManagerInterface},
     * die die Instanz der Datenbank zurückgibt.
     *
     * @param context Der Anwendungskontext.
     * @return Die Instanz der {@link MessageDatabase}.
     */
    @Override
    public Object getInstance(Context context) {
        return MessageDatabase.getInstance(context);
    }


}


