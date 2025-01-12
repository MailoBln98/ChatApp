package com.example.appnew.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appnew.controller.BluetoothManager;
import com.example.appnew.controller.MessageController;
import com.example.appnew.model.Message;

/**
 * Die MainActivity simuliert die Verbindung und den Datenaustausch zwischen zwei Geräten
 * über Bluetooth. Außerdem startet sie die Chat-Ansicht.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Debugging-Tag für Logging-Zwecke.
     */
    private static final String TAG = "MainActivity";

    /**
     * Instanz des BluetoothManagers für die Verwaltung der Bluetooth-Funktionalität.
     */
    private BluetoothManager bluetoothManager;

    /**
     * Instanz des MessageControllers für die Verwaltung der Nachrichten.
     */
    private MessageController messageController;

    /**
     * Name des ersten simulierten Geräts.
     */
    private static final String DEVICE1_NAME = "SimuliertesGerät1";

    /**
     * Name des zweiten simulierten Geräts.
     */
    private static final String DEVICE2_NAME = "SimuliertesGerät2";

    /**
     * Initialisiert die Aktivität, den BluetoothManager und den MessageController.
     * Startet die Simulation von Bluetooth-Verbindung und Datenaustausch.
     *
     * @param savedInstanceState Bundle mit gespeicherten Zuständen (falls vorhanden).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialisiere BluetoothManager und MessageController
        bluetoothManager = new BluetoothManager(this);
        messageController = new MessageController(this);

        // Starte die Bluetooth-Simulation
        startBluetoothSimulation();
    }

    /**
     * Startet die Simulation der Bluetooth-Verbindung und des Datenaustauschs.
     * Öffnet nach erfolgreicher Simulation die Chat-Ansicht.
     */
    private void startBluetoothSimulation() {
        Log.d(TAG, "Starte Bluetooth-Simulation...");

        // Simuliere die Verbindung zwischen zwei Geräten
        boolean isConnected = simulateBluetoothConnection();
        if (!isConnected) {
            Toast.makeText(this, "Bluetooth-Verbindung fehlgeschlagen.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Simuliere den Datenaustausch zwischen den Geräten
        simulateDataExchange();

        // Öffne die Chat-Ansicht
        openChatDetailActivity();
    }

    /**
     * Simuliert die Verbindung zwischen zwei Geräten.
     *
     * @return true, wenn die Verbindung erfolgreich simuliert wurde, andernfalls false.
     */
    private boolean simulateBluetoothConnection() {
        Log.d(TAG, "Verbinde " + DEVICE1_NAME + " mit " + DEVICE2_NAME + "...");

        // Hier wird die Verbindung simuliert
        try {
            Log.d(TAG, DEVICE1_NAME + " verbindet sich mit " + DEVICE2_NAME);
            Log.d(TAG, DEVICE2_NAME + " verbindet sich mit " + DEVICE1_NAME);
            Log.d(TAG, "Verbindung zwischen den simulierten Geräten erfolgreich hergestellt!");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Fehler bei der Verbindungssimulation", e);
            return false;
        }
    }

    /**
     * Simuliert den Datenaustausch zwischen zwei Geräten.
     * Fügt die ausgetauschten Nachrichten dem MessageController hinzu.
     */
    private void simulateDataExchange() {
        Log.d(TAG, "Starte Datenaustausch zwischen " + DEVICE1_NAME + " und " + DEVICE2_NAME);

        // Nachricht von DEVICE1 an DEVICE2
        String messageFromDevice1 = "Hallo Grüß dich";
        Log.d(TAG, DEVICE1_NAME + " sendet Daten: " + messageFromDevice1);
        messageController.addMessage(new Message(DEVICE1_NAME, messageFromDevice1, System.currentTimeMillis()));

        // Simulierte Antwort von DEVICE2 an DEVICE1
        String messageFromDevice2 = "Hallo zurück";
        Log.d(TAG, DEVICE2_NAME + " empfängt Daten: " + messageFromDevice1);
        Log.d(TAG, DEVICE2_NAME + " sendet Daten: " + messageFromDevice2);
        messageController.addMessage(new Message(DEVICE2_NAME, messageFromDevice2, System.currentTimeMillis()));

        // DEVICE1 empfängt die Antwort
        Log.d(TAG, DEVICE1_NAME + " empfängt Daten: " + messageFromDevice2);

        Log.d(TAG, "Datenaustausch abgeschlossen.");
    }

    /**
     * Öffnet die ChatDetailActivity, um die Nachrichten anzuzeigen.
     */
    private void openChatDetailActivity() {
        Log.d(TAG, "Öffne Chat-Ansicht...");
        Intent intent = new Intent(this, ChatDetailActivity.class);
        startActivity(intent);
    }
}
