package com.example.appnew.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * Data Access Object (DAO) für den Zugriff auf die Nachrichten-Tabelle in der Room-Datenbank.
 * Diese Schnittstelle definiert die SQL-Abfragen und Datenbankoperationen, die auf der
 * Nachrichten-Tabelle ausgeführt werden können.
 */
@Dao
public interface MessageDao {

    /**
     * Fügt eine neue Nachricht in die Datenbank ein.
     *
     * @param message Die Nachricht, die in die Datenbank eingefügt werden soll.
     *                Die Nachricht muss alle erforderlichen Felder enthalten.
     */
    @Insert
    void insert(Message message);

    /**
     * Ruft alle Nachrichten aus der Datenbank ab und sortiert sie nach ihrem Zeitstempel in
     * absteigender Reihenfolge.
     *
     * @return Ein {@link LiveData}-Objekt, das eine Liste von Nachrichten enthält.
     *         Das LiveData ermöglicht eine automatische Aktualisierung der Daten in der Benutzeroberfläche,
     *         wenn sich der Inhalt der Datenbank ändert.
     */
    @Query("SELECT * FROM messages ORDER BY timestamp DESC")
    LiveData<List<Message>> getAllMessages();
}
