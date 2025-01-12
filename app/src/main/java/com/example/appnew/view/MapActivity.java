package com.example.appnew.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import com.example.appnew.R;
import com.example.appnew.interfaces.ActionHandlerInterface;

/**
 * Die MapActivity zeigt eine Karte an, zentriert auf einen bestimmten Standort,
 * und erlaubt die Interaktion mit Markern.
 */
public class MapActivity extends AppCompatActivity implements ActionHandlerInterface {

    private static final double DEFAULT_ZOOM_LEVEL = 15.0; // Standard-Zoomstufe für die Karte
    private MapView mapView;

    /**
     * Wird beim Erstellen der Aktivität aufgerufen.
     * Initialisiert die Karte und lädt den Standort aus dem Intent.
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
        setContentView(R.layout.activity_map);

        // Konfiguration für OSMdroid laden
        Configuration.getInstance().load(this, getPreferences(MODE_PRIVATE));

        setupViews();
        setupListeners();
        loadLocationFromIntent();
    }

    /**
     * Initialisiert die Views der Karte und aktiviert Multi-Touch-Steuerung.
     */
    private void setupViews() {
        mapView = findViewById(R.id.map);
        mapView.setMultiTouchControls(true); // Multi-Touch-Steuerung aktivieren
    }

    /**
     * Initialisiert die Event-Listener, wie z. B. für den Zurück-Button.
     */
    private void setupListeners() {
        findViewById(R.id.back_button).setOnClickListener(v -> {
            finish(); // Schließt die aktuelle Aktivität
        });
    }

    /**
     * Lädt den Standort aus dem Intent und zentriert die Karte darauf.
     * Fügt einen Marker hinzu, falls ein gültiger Standort vorhanden ist.
     */
    private void loadLocationFromIntent() {
        String location = getIntent().getStringExtra("location");
        if (location != null && !location.isEmpty()) {
            try {
                String[] latLng = location.split(",");
                double latitude = Double.parseDouble(latLng[0]);
                double longitude = Double.parseDouble(latLng[1]);

                GeoPoint geoPoint = new GeoPoint(latitude, longitude);

                // Karte zentrieren
                mapView.getController().setZoom(DEFAULT_ZOOM_LEVEL);
                mapView.getController().setCenter(geoPoint);

                // Marker hinzufügen
                Marker marker = new Marker(mapView);
                marker.setPosition(geoPoint);
                marker.setTitle("Dein Standort");
                mapView.getOverlays().add(marker);

            } catch (Exception e) {
                Toast.makeText(this, "Ungültige Standortdaten", Toast.LENGTH_SHORT).show();
                finish(); // Schließt die Aktivität
            }
        } else {
            Toast.makeText(this, "Kein Standort verfügbar", Toast.LENGTH_SHORT).show();
            finish(); // Schließt die Aktivität
        }
    }

    /**
     * Ruft den Resume-Zustand der Karte auf, wenn die Aktivität fortgesetzt wird.
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * Pausiert die Karte, wenn die Aktivität pausiert wird.
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * Handhabung des Zurück-Buttons. Zeigt eine Toast-Nachricht an und
     * ruft die Standard-Zurück-Logik auf.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this, "Zurück zur vorherigen Ansicht", Toast.LENGTH_SHORT).show();
    }
}