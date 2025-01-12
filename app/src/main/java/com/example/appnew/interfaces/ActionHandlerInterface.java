package com.example.appnew.interfaces;

import android.os.Bundle;

public interface ActionHandlerInterface {



    /**
     * Wird aufgerufen, um die Activity oder den Bildschirm zu initialisieren.
     *
     * @param savedInstanceState Bundle mit gespeicherten Zuständen (falls vorhanden).
     */
    void initialize(Bundle savedInstanceState);
}