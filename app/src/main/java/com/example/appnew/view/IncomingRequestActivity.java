package com.example.appnew.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appnew.R;
import com.example.appnew.interfaces.ActionHandlerInterface;

/**
 * Die IncomingRequestActivity verwaltet eingehende Anfragen und erlaubt es dem Benutzer,
 * diese anzunehmen oder abzulehnen.
 */
public class IncomingRequestActivity extends AppCompatActivity implements ActionHandlerInterface {

    /**
     * Wird aufgerufen, wenn die Aktivität erstellt wird.
     * Initialisiert die Benutzeroberfläche und Event-Listener.
     *
     * @param savedInstanceState Das Bundle mit dem vorherigen Zustand der Aktivität, falls vorhanden.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize(savedInstanceState); // Verwende die initialize-Methode
    }

    /**
     * Initialisiert die Aktivität. Diese Methode wird in onCreate aufgerufen.
     *
     * @param savedInstanceState Das Bundle mit dem vorherigen Zustand der Aktivität, falls vorhanden.
     */
    @Override
    public void initialize(Bundle savedInstanceState) {
        setContentView(R.layout.activity_incoming_request);

        setupViews();
        setupListeners();
    }

    /**
     * Initialisiert die Views der Aktivität, wie Buttons.
     */
    private void setupViews() {
        // Alle notwendigen View-Initialisierungen (falls später erweitert)
    }

    /**
     * Initialisiert die Event-Listener für Benutzerinteraktionen.
     */
    private void setupListeners() {
        // Button für Anfrage annehmen
        findViewById(R.id.btn_accept_request).setOnClickListener(v -> handleAcceptRequest());

        // Button für Anfrage ablehnen
        findViewById(R.id.btn_decline_request).setOnClickListener(v -> handleDeclineRequest());
    }

    /**
     * Handhabung der Aktion, wenn die Anfrage angenommen wird.
     * Öffnet die ChatDetailActivity und schließt die aktuelle Aktivität.
     */
    private void handleAcceptRequest() {
        Intent intent = new Intent(IncomingRequestActivity.this, ChatDetailActivity.class);
        startActivity(intent);
        finish(); // Schließt die aktuelle Aktivität
    }

    /**
     * Handhabung der Aktion, wenn die Anfrage abgelehnt wird.
     * Schließt die aktuelle Aktivität.
     */
    private void handleDeclineRequest() {
        finish(); // Schließt die aktuelle Aktivität
    }
}