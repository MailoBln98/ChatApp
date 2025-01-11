package com.example.appnew.interfaces;

/**
 * Interface für das Empfangen einer eingehenden Verbindungsanfrage.
 * Definiert Methoden zur Behandlung von eingehenden Verbindungsanfragen.
 */
public interface OnIncomingRequestListener {

    /**
     * Wird aufgerufen, wenn eine eingehende Anfrage empfangen wird.
     *
     * @param deviceName Der Name des Geräts, das die Anfrage sendet.
     */
    void onRequestReceived(String deviceName);

    /**
     * Wird aufgerufen, wenn eine Anfrage akzeptiert wird.
     */
    void onRequestAccepted();

    /**
     * Wird aufgerufen, wenn eine Anfrage abgelehnt wird.
     */
    void onRequestDeclined();
}

