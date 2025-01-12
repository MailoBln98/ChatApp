package com.example.appnew.view;

import android.Manifest;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnew.R;
import com.example.appnew.controller.MessageController;
import com.example.appnew.interfaces.ActionHandlerInterface;
import com.example.appnew.model.Message;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.Collections;

/**
 * Die ChatListActivity zeigt eine Liste von Chats an und verwaltet Standort-Updates.
 * Sie ermöglicht es, Nachrichten anzuzeigen, Standortinformationen zu teilen
 * und Benutzerinteraktionen zu verarbeiten.
 */
public class ChatListActivity extends AppCompatActivity implements ActionHandlerInterface {

    /**
     * Debugging-Tag für Log-Ausgaben.
     */
    private static final String TAG = "ChatListActivity";

    /**
     * Controller zur Verwaltung der Nachrichtenoperationen.
     */
    private MessageController messageController;

    /**
     * Adapter für die RecyclerView, die die Chatliste verwaltet.
     */
    private ChatListAdapter chatListAdapter;

    /**
     * Standortanbieter für die Standortaktualisierungen.
     */
    private FusedLocationProviderClient fusedLocationClient;

    /**
     * Callback für Standortaktualisierungen.
     */
    private LocationCallback locationCallback;

    /**
     * Initialisiert die Aktivität und ruft notwendige Setup-Methoden auf.
     *
     * @param savedInstanceState Bundle mit gespeicherten Zuständen (falls vorhanden).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize(savedInstanceState);
    }

    /**
     * Initialisiert die Activity, indem Views, Listener und Standortaktualisierungen eingerichtet werden.
     *
     * @param savedInstanceState Das Bundle mit dem gespeicherten Zustand der Activity.
     */
    @Override
    public void initialize(Bundle savedInstanceState) {
        setContentView(R.layout.activity_chat_list);

        // Setup der Views
        setupViews();

        // Setup der Listener
        setupListeners();

        // Setup der Standortaktualisierungen
        setupLocationUpdates();
    }

    /**
     * Richtet alle Views in der Aktivität ein.
     * Dazu gehören die RecyclerView und der Nachrichtenadapter.
     */
    private void setupViews() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Initialisierung des MessageControllers
        messageController = new MessageController(this);

        // Einrichtung der RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_view_chats);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatListAdapter = new ChatListAdapter(message -> Log.d(TAG, "Message clicked: " + message.getContent()));
        recyclerView.setAdapter(chatListAdapter);

        // Nachrichten aus der Datenbank abrufen und anzeigen
        messageController.getAllMessages().observe(this, messages -> {
            if (messages != null && !messages.isEmpty()) {
                Log.d(TAG, "Messages loaded: " + messages.size());
                chatListAdapter.setMessages(messages);
            } else {
                Log.d(TAG, "No messages found");
                chatListAdapter.setMessages(Collections.singletonList(new Message("System", "No messages available", System.currentTimeMillis())));
            }
        });
    }

    /**
     * Richtet die Event-Listener für Benutzerinteraktionen ein.
     * Dazu gehört das Verarbeiten von Klicks auf Buttons und RecyclerView-Elemente.
     */
    private void setupListeners() {
        findViewById(R.id.location_button).setOnClickListener(v -> {
            // Konfiguriere Standortanforderungen
            LocationRequest locationRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(1000); // Intervall für kontinuierliche Updates

            // Fordere Standortaktualisierungen an
            requestLocationUpdates(locationRequest);
        });
    }

    /**
     * Initialisiert die Standortaktualisierungen und definiert einen Callback.
     */
    private void setupLocationUpdates() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if (locationResult != null) {
                    for (Location location : locationResult.getLocations()) {
                        if (location != null) {
                            String locationMessage = "Latitude: " + location.getLatitude() + ", Longitude: " + location.getLongitude();
                            Toast.makeText(ChatListActivity.this, locationMessage, Toast.LENGTH_LONG).show();
                            Log.d(TAG, "Current location: " + locationMessage);
                        }
                    }
                }
            }
        };
    }

    /**
     * Fordert Standortaktualisierungen basierend auf den angegebenen Anforderungen an.
     *
     * @param locationRequest Die Konfiguration der Standortanforderungen.
     */
    private void requestLocationUpdates(LocationRequest locationRequest) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, getMainLooper());
    }

    /**
     * Entfernt Standortaktualisierungen und bereinigt Ressourcen.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (fusedLocationClient != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    /**
     * Verarbeitet Menü-Optionen und ermöglicht das Zurückkehren zur vorherigen Ansicht.
     *
     * @param item Das ausgewählte Menü-Item.
     * @return true, wenn das Menü-Item verarbeitet wurde, andernfalls false.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Beendet die aktuelle Aktivität und kehrt zurück
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Gibt den MessageController der Aktivität zurück.
     * Dieser kann von anderen Komponenten (z. B. Tests) verwendet werden.
     *
     * @return Der MessageController, der die Nachrichtenoperationen verwaltet.
     */
    public MessageController getMessageController() {
        return messageController;
    }
}