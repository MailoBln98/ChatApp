package com.example.appnew.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appnew.R;

public class PresentationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Button für aktuelle Chats
        findViewById(R.id.btn_chats).setOnClickListener(v ->
                startActivity(new Intent(PresentationActivity.this, ChatListActivity.class))
        );

        // Button für Gerät suchen
        findViewById(R.id.btn_device_search).setOnClickListener(v ->
                startActivity(new Intent(PresentationActivity.this, DeviceListActivity.class))
        );

        // Button für Chat starten (eingehende Anfrage simulieren)
        findViewById(R.id.btn_chat_start).setOnClickListener(v ->
                startActivity(new Intent(PresentationActivity.this, IncomingRequestActivity.class))
        );
    }
}
