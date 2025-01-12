package com.example.appnew.controller;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.appnew.interfaces.ConnectionManagerInterface;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

/**
 * Klasse zur Bereitstellung von Standortinformationen.
 * Diese Klasse implementiert das Interface {@link ConnectionManagerInterface}, um die Verfügbarkeit
 * von Standortdiensten zu prüfen.
 */
public class LocationProvider implements ConnectionManagerInterface {

    private final Context context;
    private final FusedLocationProviderClient fusedLocationClient;

    /**
     * Konstruktor zur Initialisierung des LocationProviders.
     *
     * @param context Der Kontext der Anwendung, erforderlich für Berechtigungsprüfungen.
     */
    public LocationProvider(Context context) {
        this.context = context;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    /**
     * Überprüft, ob die erforderlichen Standortdienste verfügbar sind.
     *
     * @return true, wenn die Berechtigungen für den Zugriff auf den Standort erteilt wurden,
     * andernfalls false.
     */
    @Override
    public boolean isServiceAvailable() {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Schnittstelle zur Bereitstellung von Standortinformationen über Rückrufe.
     */
    public interface CustomLocationCallback {
        /**
         * Wird aufgerufen, wenn der Standort erfolgreich abgerufen wurde.
         *
         * @param location Das abgerufene Standortobjekt.
         */
        void onLocationRetrieved(Location location);

        /**
         * Wird aufgerufen, wenn ein Fehler beim Abrufen des Standorts aufgetreten ist.
         *
         * @param errorMessage Die Fehlermeldung.
         */
        void onError(String errorMessage);
    }

    /**
     * Ruft den aktuellen Standort ab.
     * Wenn der Standort nicht verfügbar ist, wird ein neuer Standort angefordert.
     *
     * @param callback Der Rückruf, der bei Erfolg oder Fehler ausgeführt wird.
     */
    @SuppressLint("MissingPermission")
    public void getCurrentLocation(CustomLocationCallback callback) {
        if (!isServiceAvailable()) {
            callback.onError("Location permissions not granted.");
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        callback.onLocationRetrieved(location);
                    } else {
                        requestNewLocation(callback);
                    }
                })
                .addOnFailureListener(e -> callback.onError("Failed to retrieve location: " + e.getMessage()));
    }

    /**
     * Fordert einen neuen Standort an, wenn der letzte Standort nicht verfügbar ist.
     *
     * @param callback Der Rückruf, der bei Erfolg oder Fehler ausgeführt wird.
     */
    @SuppressLint("MissingPermission")
    private void requestNewLocation(CustomLocationCallback callback) {
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10000) // Intervall für Standortaktualisierungen
                .setFastestInterval(5000); // Schnellstmögliches Intervall

        fusedLocationClient.requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                fusedLocationClient.removeLocationUpdates(this);
                if (locationResult != null && !locationResult.getLocations().isEmpty()) {
                    callback.onLocationRetrieved(locationResult.getLastLocation());
                } else {
                    callback.onError("Failed to get new location.");
                }
            }
        }, Looper.getMainLooper());
    }
}