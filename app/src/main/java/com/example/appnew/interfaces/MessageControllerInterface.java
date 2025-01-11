package com.example.appnew.interfaces;

import androidx.lifecycle.LiveData;
import com.example.appnew.model.Message;
import java.util.List;

/**
 * Interface für den Nachrichtencontroller.
 * Definiert Methoden für das Hinzufügen und Abrufen von Nachrichten in der Datenbank.
 */
public interface MessageControllerInterface {

    /**
     * Fügt eine neue Nachricht zur Datenbank hinzu.
     *
     * @param message Die Nachricht, die hinzugefügt werden soll.
     */
    void addMessage(Message message);

    /**
     * Ruft alle Nachrichten aus der Datenbank ab.
     *
     * @return Ein LiveData-Objekt, das eine Liste aller Nachrichten enthält.
     */
    LiveData<List<Message>> getAllMessages();
}
