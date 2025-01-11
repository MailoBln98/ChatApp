package com.example.appnew.interfaces;

import android.bluetooth.BluetoothDevice;

/**
 * Interface für die Bluetooth-Geräteverbindung.
 * Definiert Methoden zur Verwaltung der Verbindung mit einem Bluetooth-Gerät.
 */
public interface BluetoothDeviceConnectionInterface {

    /**
     * Verbindet sich mit einem angegebenen Bluetooth-Gerät.
     *
     * @param device Das Bluetooth-Gerät, mit dem eine Verbindung hergestellt werden soll.
     */
    void connectToDevice(BluetoothDevice device);

    /**
     * Trennt die Verbindung zu einem Bluetooth-Gerät.
     */
    void disconnectDevice();

    /**
     * Überprüft den Verbindungsstatus eines Bluetooth-Geräts.
     *
     * @return true, wenn das Gerät verbunden ist, andernfalls false.
     */
    boolean isConnected();
}
