package com.example.appnew.controller;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.example.appnew.interfaces.ConnectionManagerInterface;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Klasse zur Verwaltung von Bluetooth-Verbindungen und -Kommunikation.
 */
public class BluetoothManager implements ConnectionManagerInterface {
    private final BluetoothAdapter bluetoothAdapter;
    private final Context context;
    private BluetoothSocket bluetoothSocket;
    private InputStream inputStream;
    private OutputStream outputStream;

    private static final String TAG = "BluetoothManager";
    private static final UUID APP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    /**
     * Konstruktor zur Initialisierung des BluetoothManagers.
     *
     * @param context Der Kontext der Anwendung, der für Berechtigungsprüfungen verwendet wird.
     */
    public BluetoothManager(Context context) {
        this.context = context;
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    /**
     * Startet die Suche nach Bluetooth-Geräten.
     */
    public void startDiscovery() {
        if (!checkPermission(Manifest.permission.BLUETOOTH_SCAN)) {
            Log.e(TAG, "Fehlende Berechtigung: BLUETOOTH_SCAN");
            return;
        }

        if (bluetoothAdapter != null && bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.startDiscovery();
            Log.d(TAG, "Bluetooth-Gerätesuche gestartet.");
        } else {
            Log.e(TAG, "Bluetooth ist nicht aktiviert oder Adapter ist null.");
        }
    }

    /**
     * Verbindet sich mit einem Bluetooth-Gerät.
     *
     * @param device Das Bluetooth-Gerät, mit dem eine Verbindung hergestellt werden soll.
     */
    public void connectToDevice(BluetoothDevice device) {
        if (!checkPermission(Manifest.permission.BLUETOOTH_CONNECT)) {
            Log.e(TAG, "Fehlende Berechtigung: BLUETOOTH_CONNECT");
            return;
        }

        try {
            bluetoothSocket = device.createRfcommSocketToServiceRecord(APP_UUID);
            bluetoothSocket.connect();
            inputStream = bluetoothSocket.getInputStream();
            outputStream = bluetoothSocket.getOutputStream();
            Log.d(TAG, "Erfolgreich verbunden mit: " + device.getName());
        } catch (IOException e) {
            Log.e(TAG, "Fehler beim Verbinden mit dem Gerät: " + device.getName(), e);
        }
    }

    /**
     * Sendet Daten an das verbundene Gerät.
     *
     * @param data Die zu sendenden Daten als Byte-Array.
     */
    public void sendData(byte[] data) {
        if (outputStream != null) {
            try {
                outputStream.write(data);
                Log.d(TAG, "Daten gesendet: " + new String(data));
            } catch (IOException e) {
                Log.e(TAG, "Fehler beim Senden der Daten.", e);
            }
        } else {
            Log.e(TAG, "Kein verbundenes Gerät zum Senden von Daten.");
        }
    }

    /**
     * Empfängt Daten vom verbundenen Gerät.
     *
     * @return Die empfangenen Daten als Byte-Array.
     */
    public byte[] receiveData() {
        byte[] buffer = new byte[1024];
        int bytesRead;

        if (inputStream != null) {
            try {
                bytesRead = inputStream.read(buffer);
                Log.d(TAG, "Daten empfangen: " + new String(buffer, 0, bytesRead));
                return buffer;
            } catch (IOException e) {
                Log.e(TAG, "Fehler beim Empfangen der Daten.", e);
            }
        } else {
            Log.e(TAG, "Kein verbundenes Gerät zum Empfangen von Daten.");
        }

        return new byte[0];
    }

    /**
     * Trennt die Verbindung zu einem Bluetooth-Gerät.
     */
    public void disconnectDevice() {
        try {
            if (bluetoothSocket != null) {
                bluetoothSocket.close();
                Log.d(TAG, "Bluetooth-Verbindung getrennt.");
            }
        } catch (IOException e) {
            Log.e(TAG, "Fehler beim Trennen der Bluetooth-Verbindung.", e);
        }
    }

    /**
     * Überprüft, ob die erforderlichen Berechtigungen vorliegen.
     *
     * @param permission Die zu überprüfende Berechtigung.
     * @return true, wenn die Berechtigung vorhanden ist, andernfalls false.
     */
    private boolean checkPermission(String permission) {
        return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Überprüft, ob Bluetooth aktiviert ist.
     *
     * @return true, wenn Bluetooth aktiviert ist, andernfalls false.
     */
    public boolean isBluetoothEnabled() {
        return bluetoothAdapter != null && bluetoothAdapter.isEnabled();
    }

    public boolean isConnected() {
        return bluetoothSocket != null && bluetoothSocket.isConnected();
    }

    @Override
    public boolean isServiceAvailable() {
        return isBluetoothEnabled();
    }
}