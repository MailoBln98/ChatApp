package com.example.appnew.interfaces;

/**
 * Schnittstelle für Klassen, die mit externen Diensten interagieren (z. B. Bluetooth oder Standortdienste).
 */
public interface ConnectionManagerInterface {

    /**
     * Überprüft, ob der entsprechende Service verfügbar ist.
     *
     * @return true, wenn der Service verfügbar ist, andernfalls false.
     */
    boolean isServiceAvailable();
}