package com.example.appnew.view;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnew.R;
import com.example.appnew.controller.LocationProvider;
import com.example.appnew.controller.MessageController;
import com.example.appnew.model.Message;

public class ChatDetailActivity extends AppCompatActivity {

    private static final String TAG = "ChatDetailActivity";

    private RecyclerView chatRecyclerView;
    private EditText messageInput;
    private Button sendButton;
    private Button sendLocationButton;
    private MessageAdapter messageAdapter;
    private MessageController messageController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);

        // Initialisiere Views
        chatRecyclerView = findViewById(R.id.chat_recycler_view);
        messageInput = findViewById(R.id.message_input);
        sendButton = findViewById(R.id.send_button);
        sendLocationButton = findViewById(R.id.send_location_button);

        // Initialisiere Controller und Adapter
        messageController = new MessageController(this);
        messageAdapter = new MessageAdapter();

        // Setze Klick-Listener für Nachrichten
        messageAdapter.setOnMessageClickListener(message -> {
            if (message.getLocation() != null) {
                Intent intent = new Intent(ChatDetailActivity.this, MapActivity.class);
                intent.putExtra("location", message.getLocation());
                startActivity(intent);
            }
        });

        // RecyclerView konfigurieren
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatRecyclerView.setAdapter(messageAdapter);

        // Nachrichten laden
        loadMessages();

        // Klick-Listener für Buttons
        sendButton.setOnClickListener(v -> {
            String messageText = messageInput.getText().toString();
            if (!messageText.isEmpty()) {
                sendMessage(messageText);
            } else {
                Toast.makeText(this, "Bitte eine Nachricht eingeben", Toast.LENGTH_SHORT).show();
            }
        });

        sendLocationButton.setOnClickListener(v -> sendCurrentLocation());
    }

    private void loadMessages() {
        messageController.getAllMessages().observe(this, messages -> {
            if (messages != null) {
                messageAdapter.setMessages(messages);
            }
        });
    }

    private void sendMessage(String messageText) {
        Message message = new Message("User1", messageText, System.currentTimeMillis());
        messageController.addMessage(message);
        messageInput.setText("");
    }

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