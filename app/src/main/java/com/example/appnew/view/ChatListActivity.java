package com.example.appnew.view;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnew.R;
import com.example.appnew.controller.LocationProvider;
import com.example.appnew.controller.MessageController;
import com.example.appnew.model.Message;

import org.jetbrains.annotations.VisibleForTesting;

import java.util.Collections;

public class ChatListActivity extends AppCompatActivity {

    private static final String TAG = "ChatListActivity";

    private MessageController messageController;
    private ChatListAdapter chatListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        // Aktiviert die Zurück-Navigation
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        messageController = new MessageController(this);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_chats);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatListAdapter = new ChatListAdapter(message -> {
            // Intent für den Start der Chat-Detailansicht
            Log.d(TAG, "Message clicked: " + message.getContent());
            Intent intent = new Intent(ChatListActivity.this, ChatDetailActivity.class);
            intent.putExtra("chat_id", message.getId());
            startActivity(intent);
        });

        recyclerView.setAdapter(chatListAdapter);

        messageController.getAllMessages().observe(this, messages -> {
            if (messages != null && !messages.isEmpty()) {
                Log.d(TAG, "Messages loaded: " + messages.size());
                chatListAdapter.setMessages(messages);
            } else {
                Log.d(TAG, "No messages found");
                chatListAdapter.setMessages(Collections.singletonList(new Message("System", "No messages available", System.currentTimeMillis())));
            }
        });

        // Hinzufügen der Funktionalität zum Abrufen des Standorts
        findViewById(R.id.location_button).setOnClickListener(v -> {
            LocationProvider locationProvider = new LocationProvider(this);

            locationProvider.getCurrentLocation(new LocationProvider.CustomLocationCallback() {
                @Override
                public void onLocationRetrieved(Location location) {
                    String locationMessage = "Latitude: " + location.getLatitude() + ", Longitude: " + location.getLongitude();
                    Toast.makeText(ChatListActivity.this, locationMessage, Toast.LENGTH_LONG).show();

                    // Optional: Weiterleiten oder Senden des Standorts
                    Log.d(TAG, "Location retrieved: " + locationMessage);
                }

                @Override
                public void onError(String errorMessage) {
                    Toast.makeText(ChatListActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                    Log.e(TAG, "Error retrieving location: " + errorMessage);
                }
            });
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Schließt die aktuelle Aktivität und kehrt zur vorherigen zurück
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @VisibleForTesting
    public MessageController getMessageController() {
        return messageController;
    }
}