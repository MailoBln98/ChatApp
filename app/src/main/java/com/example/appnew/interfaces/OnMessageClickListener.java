package com.example.appnew.interfaces;

import com.example.appnew.model.Message;

/**
 * Interface für den Klick-Listener im Chat-Adapter.
 * Definiert die Methode, die aufgerufen wird, wenn eine Nachricht angeklickt wird.
 */
public interface OnMessageClickListener {

    /**
     * Wird aufgerufen, wenn eine Nachricht in der Liste angeklickt wird.
     *
     * @param message Die angeklickte Nachricht.
     */
    void onMessageClick(Message message);
}
