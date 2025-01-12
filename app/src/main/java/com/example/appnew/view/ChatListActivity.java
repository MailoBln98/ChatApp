package com.example.appnew.view;

import android.Manifest;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.core.app.ActivityCompat;

import com.example.appnew.R;
import com.example.appnew.controller.MessageController;
import com.example.appnew.model.Message;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.Collections;

public class ChatListActivity extends AppCompatActivity {

    private static final String TAG = "ChatListActivity";

    private MessageController messageController;
    private ChatListAdapter chatListAdapter;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;

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
            Log.d(TAG, "Message clicked: " + message.getContent());
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

        // Standort-Client initialisieren
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Standortanforderungen konfigurieren
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(1000); // Intervall für kontinuierliche Updates

        // Standort-Aktualisierungs-Callback
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

        findViewById(R.id.location_button).setOnClickListener(v -> {
            requestLocationUpdates(locationRequest);
        });
    }

    private void requestLocationUpdates(LocationRequest locationRequest) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, getMainLooper());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (fusedLocationClient != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    public MessageController getMessageController() {
        return messageController;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Beende die aktuelle Aktivität und kehre zur vorherigen zurück
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}