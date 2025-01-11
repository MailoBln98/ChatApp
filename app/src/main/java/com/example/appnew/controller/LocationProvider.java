package com.example.appnew.controller;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class LocationProvider {

    private final Context context;
    private final FusedLocationProviderClient fusedLocationClient;

    public interface CustomLocationCallback {
        void onLocationRetrieved(Location location);
        void onError(String errorMessage);
    }

    public LocationProvider(Context context) {
        this.context = context;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    @SuppressLint("MissingPermission")
    public void getCurrentLocation(CustomLocationCallback callback) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

    @SuppressLint("MissingPermission")
    private void requestNewLocation(CustomLocationCallback callback) {
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10000)
                .setFastestInterval(5000);

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