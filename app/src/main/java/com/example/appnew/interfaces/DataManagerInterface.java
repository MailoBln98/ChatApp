package com.example.appnew.interfaces;

import android.content.Context;

/**
 * Schnittstelle zur Verwaltung von Datenoperationen.
 * Diese Schnittstelle definiert grundlegende Methoden zum Einfügen und Abrufen von Daten.
 */
public interface DataManagerInterface {

    /**
     * Fügt ein neues Datenelement in die Datenquelle ein.
     *
     * @param data Das einzufügende Datenelement.
     */
    void insertData(Object data);

    /**
     * Ruft alle Daten aus der Quelle ab.
     *
     * @return Eine Liste oder ein LiveData-Objekt mit den abgerufenen Daten.
     */
    Object fetchData();

    /**
     * Gibt die Instanz der Datenquelle zurück.
     *
     * @param context Der Anwendungskontext.
     * @return Eine Instanz der Datenquelle.
     */
    Object getInstance(Context context);
}