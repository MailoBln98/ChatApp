package com.example.appnew.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appnew.R;

public class IncomingRequestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_request);

        // Button für Anfrage annehmen
        findViewById(R.id.btn_accept_request).setOnClickListener(v -> {
            // Logik zum Annehmen der Anfrage
            // Hier könntest du eine neue Chat-Aktivität starten oder die Anfrage speichern
            startActivity(new Intent(IncomingRequestActivity.this, ChatDetailActivity.class));
            finish(); // Schließe das aktuelle Fenster
        });

        // Button für Anfrage ablehnen
        findViewById(R.id.btn_decline_request).setOnClickListener(v -> {
            // Logik zum Ablehnen der Anfrage
            finish(); // Schließe das aktuelle Fenster
        });
    }
}
