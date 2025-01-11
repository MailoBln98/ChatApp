package com.example.appnew.interfaces;

/**
 * OnLocationClickListener ist ein Interface zur Behandlung von Klickereignissen,
 * wenn ein Standort innerhalb des Chats angezeigt oder angeklickt wird.
 */
public interface OnLocationClickListener {

    /**
     * Methode, die ausgeführt wird, wenn ein Standort angeklickt wird.
     *
     * @param latitude Die Breitengradkoordinate des Standorts.
     * @param longitude Die Längengradkoordinate des Standorts.
     */
    void onLocationClick(double latitude, double longitude);
}