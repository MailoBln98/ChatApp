package com.example.appnew.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import com.example.appnew.R;

public class MapActivity extends AppCompatActivity {

    private static final double DEFAULT_ZOOM_LEVEL = 15.0;
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // OSMdroid-Konfiguration laden
        Configuration.getInstance().load(this, getPreferences(MODE_PRIVATE));

        mapView = findViewById(R.id.map);
        mapView.setMultiTouchControls(true);

        // Standort aus Intent abrufen
        String location = getIntent().getStringExtra("location");
        if (location != null && !location.isEmpty()) {
            try {
                String[] latLng = location.split(",");
                double latitude = Double.parseDouble(latLng[0]);
                double longitude = Double.parseDouble(latLng[1]);

                // Karte zentrieren
                GeoPoint geoPoint = new GeoPoint(latitude, longitude);
                mapView.getController().setZoom(DEFAULT_ZOOM_LEVEL);
                mapView.getController().setCenter(geoPoint);

                // Marker hinzufügen
                Marker marker = new Marker(mapView);
                marker.setPosition(geoPoint);
                marker.setTitle("Dein Standort");
                mapView.getOverlays().add(marker);

            } catch (Exception e) {
                Toast.makeText(this, "Ungültige Standortdaten", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Kein Standort verfügbar", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Back-Button Funktionalität
        findViewById(R.id.back_button).setOnClickListener(v -> {
            finish(); // Schließt die aktuelle Aktivität
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this, "Zurück zur vorherigen Ansicht", Toast.LENGTH_SHORT).show();
    }
}