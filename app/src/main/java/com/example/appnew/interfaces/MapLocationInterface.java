package com.example.appnew.interfaces;

import org.osmdroid.util.GeoPoint;

/**
 * Interface für die Kartenfunktionalität.
 * Definiert Methoden zur Arbeit mit Standortdaten in der Karte.
 */
public interface MapLocationInterface {

    /**
     * Zeigt den angegebenen Punkt auf der Karte an.
     *
     * @param point Der geografische Punkt, der angezeigt werden soll.
     */
    void showLocation(GeoPoint point);

    /**
     * Holt den aktuellen Standort des Nutzers.
     *
     * @return Ein GeoPoint-Objekt, das die aktuellen Koordinaten des Nutzers darstellt.
     */
    GeoPoint getCurrentLocation();
}

