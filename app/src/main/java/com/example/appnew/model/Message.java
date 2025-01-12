package com.example.appnew.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Repräsentiert eine Nachricht in der Anwendung.
 * Diese Klasse dient als Entity für die Room-Datenbank und speichert Details einer Nachricht
 * wie Absender, Inhalt, Zeitstempel und Standort.
 */
@Entity(tableName = "messages")
public class Message {

    /**
     * Eindeutige ID der Nachricht.
     * Diese ID wird automatisch von der Room-Datenbank generiert.
     */
    @PrimaryKey(autoGenerate = true)
    private int id;

    /**
     * Der Absender der Nachricht.
     */
    private String sender;

    /**
     * Der Inhalt der Nachricht.
     */
    private String content;

    /**
     * Der Zeitstempel, wann die Nachricht gesendet wurde.
     * Dieser wird in Millisekunden seit der Epoche (UNIX-Zeit) gespeichert.
     */
    private long timestamp;

    /**
     * Der Standort, der mit der Nachricht verbunden ist (optional).
     * Format: Latitude und Longitude als String, z. B. "52.5200,13.4050".
     */
    private String location;

    /**
     * Konstruktor zur Erstellung einer neuen Nachricht.
     *
     * @param sender    Der Absender der Nachricht.
     * @param content   Der Inhalt der Nachricht.
     * @param timestamp Der Zeitstempel der Nachricht.
     */
    public Message(String sender, String content, long timestamp) {
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
    }

    /**
     * Gibt die eindeutige ID der Nachricht zurück.
     *
     * @return Die ID der Nachricht.
     */
    public int getId() {
        return id;
    }

    /**
     * Setzt die eindeutige ID der Nachricht.
     *
     * @param id Die neue ID der Nachricht.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gibt den Absender der Nachricht zurück.
     *
     * @return Der Absender der Nachricht.
     */
    public String getSender() {
        return sender;
    }

    /**
     * Setzt den Absender der Nachricht.
     *
     * @param sender Der neue Absender der Nachricht.
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * Gibt den Inhalt der Nachricht zurück.
     *
     * @return Der Inhalt der Nachricht.
     */
    public String getContent() {
        return content;
    }

    /**
     * Setzt den Inhalt der Nachricht.
     *
     * @param content Der neue Inhalt der Nachricht.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Gibt den Zeitstempel der Nachricht zurück.
     *
     * @return Der Zeitstempel der Nachricht in Millisekunden seit der Epoche.
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Setzt den Zeitstempel der Nachricht.
     *
     * @param timestamp Der neue Zeitstempel der Nachricht.
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Gibt den Standort zurück, der mit der Nachricht verbunden ist.
     *
     * @return Der Standort der Nachricht als String (z. B. "52.5200,13.4050"), oder null, wenn kein Standort gesetzt ist.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Setzt den Standort, der mit der Nachricht verbunden ist.
     *
     * @param location Der Standort der Nachricht als String (z. B. "52.5200,13.4050").
     */
    public void setLocation(String location) {
        this.location = location;
    }
}

