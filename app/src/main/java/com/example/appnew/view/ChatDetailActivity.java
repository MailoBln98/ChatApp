package com.example.appnew.view;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnew.R;
import com.example.appnew.controller.LocationProvider;
import com.example.appnew.controller.MessageController;
import com.example.appnew.interfaces.ActionHandlerInterface;
import com.example.appnew.model.Message;

/**
 * Die ChatDetailActivity steuert die Detailansicht eines Chats.
 * Diese Aktivität ermöglicht es dem Benutzer, Nachrichten anzuzeigen, neue Nachrichten zu senden
 * und den aktuellen Standort zu teilen.
 */
public class ChatDetailActivity extends AppCompatActivity implements ActionHandlerInterface {

    /**
     * Debugging-Tag für Log-Ausgaben.
     */
    private static final String TAG = "ChatDetailActivity";

    /**
     * RecyclerView zum Anzeigen von Chat-Nachrichten.
     */
    private RecyclerView chatRecyclerView;

    /**
     * Eingabefeld für das Schreiben von Nachrichten.
     */
    private EditText messageInput;

    /**
     * Button zum Senden von Nachrichten.
     */
    private Button sendButton;

    /**
     * Button zum Teilen des aktuellen Standorts.
     */
    private Button sendLocationButton;

    /**
     * Adapter zur Verwaltung der Nachrichtenanzeige in der RecyclerView.
     */
    private MessageAdapter messageAdapter;

    /**
     * Controller für die Verwaltung von Nachrichtenoperationen.
     */
    private MessageController messageController;

    /**
     * Wird beim Erstellen der Aktivität aufgerufen und initialisiert die notwendigen Komponenten.
     *
     * @param savedInstanceState Das Bundle mit gespeicherten Zuständen (falls vorhanden).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize(savedInstanceState);
    }

    /**
     * Initialisiert die Aktivität, einschließlich der Einrichtung von Views, Listenern
     * und der Datenlogik.
     *
     * @param savedInstanceState Das Bundle mit gespeicherten Zuständen.
     */
    @Override
    public void initialize(Bundle savedInstanceState) {
        setContentView(R.layout.activity_chat_detail);

        // Setup der Views
        setupViews();

        // Setup der Listener
        setupListeners();

        // Nachrichten laden und anzeigen
        loadMessages();
    }

    /**
     * Richtet alle benötigten Views ein, um die Benutzeroberfläche korrekt darzustellen.
     */
    private void setupViews() {
        chatRecyclerView = findViewById(R.id.chat_recycler_view);
        messageInput = findViewById(R.id.message_input);
        sendButton = findViewById(R.id.send_button);
        sendLocationButton = findViewById(R.id.send_location_button);

        // Initialisiere den Nachrichtencontroller und Adapter
        messageController = new MessageController(this);
        messageAdapter = new MessageAdapter();

        // RecyclerView-Konfiguration
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatRecyclerView.setAdapter(messageAdapter);
    }

    /**
     * Richtet die Listener für Benutzerinteraktionen ein.
     */
    private void setupListeners() {
        // Listener für Nachrichtenklicks
        messageAdapter.setOnMessageClickListener(message -> {
            if (message.getLocation() != null) {
                Intent intent = new Intent(ChatDetailActivity.this, MapActivity.class);
                intent.putExtra("location", message.getLocation());
                startActivity(intent);
            }
        });

        // Listener für den Button zum Senden von Nachrichten
        sendButton.setOnClickListener(v -> {
            String messageText = messageInput.getText().toString();
            if (!messageText.isEmpty()) {
                sendMessage(messageText);
            } else {
                Toast.makeText(this, "Bitte eine Nachricht eingeben", Toast.LENGTH_SHORT).show();
            }
        });

        // Listener für den Button zum Teilen des Standorts
        sendLocationButton.setOnClickListener(v -> sendCurrentLocation());
    }

    /**
     * Lädt Nachrichten aus der Datenbank und zeigt sie in der RecyclerView an.
     */
    private void loadMessages() {
        messageController.getAllMessages().observe(this, messages -> {
            if (messages != null) {
                messageAdapter.setMessages(messages);
            }
        });
    }

    /**
     * Erstellt eine neue Nachricht und speichert sie in der Datenbank.
     *
     * @param messageText Der Inhalt der Nachricht, die gesendet werden soll.
     */
    private void sendMessage(String messageText) {
        Message message = new Message("User1", messageText, System.currentTimeMillis());
        messageController.addMessage(message);
        messageInput.setText(""); // Leert das Eingabefeld nach dem Senden
    }

    /**
     * Teilt den aktuellen Standort des Benutzers als Nachricht.
     */
    private void sendCurrentLocation() {
        LocationProvider locationProvider = new LocationProvider(this);

        locationProvider.getCurrentLocation(new LocationProvider.CustomLocationCallback() {
            @Override
            public void onLocationRetrieved(Location location) {
                if (location != null) {
                    String locationMessage = location.getLatitude() + "," + location.getLongitude();

                    Message locationMessageObj = new Message(
                            "System",
                            "Standort geteilt. Tippe hier, um die Karte zu öffnen.",
                            System.currentTimeMillis()
                    );
                    locationMessageObj.setLocation(locationMessage);

                    messageController.addMessage(locationMessageObj);
                    Toast.makeText(ChatDetailActivity.this, "Standort erfolgreich geteilt", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChatDetailActivity.this, "Standort konnte nicht abgerufen werden", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(ChatDetailActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}