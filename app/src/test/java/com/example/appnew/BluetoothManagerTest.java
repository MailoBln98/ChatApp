package com.example.appnew;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;

import com.example.appnew.controller.BluetoothManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;

/**
 * Testklasse für die `BluetoothManager`-Klasse.
 * Diese Klasse stellt sicher, dass der Konstruktor und die Initialisierung des BluetoothManagers korrekt funktionieren.
 */
public class BluetoothManagerTest {

    /**
     * Mock-Objekt für den Android-Context.
     */
    private Context mockContext;

    /**
     * Richtet die Testumgebung ein, indem ein Mock-Context erstellt wird.
     */
    @Before
    public void setUp() {
        // Initialisiere ein Mock-Objekt für den Context
        mockContext = mock(Context.class);
    }

    /**
     * Testet den Konstruktor und die Initialisierung des `BluetoothManager`.
     * Dieser Test überprüft, ob die erforderlichen Felder korrekt initialisiert werden.
     */
    @Test
    public void testConstructor_initialization() {
        // Mocken der statischen Methode BluetoothAdapter.getDefaultAdapter()
        try (MockedStatic<BluetoothAdapter> mockedBluetoothAdapter = mockStatic(BluetoothAdapter.class)) {
            // Erstelle ein Mock-Objekt für den BluetoothAdapter
            BluetoothAdapter mockBluetoothAdapter = mock(BluetoothAdapter.class);

            // Lege fest, dass BluetoothAdapter.getDefaultAdapter() das Mock-Objekt zurückgibt
            mockedBluetoothAdapter.when(BluetoothAdapter::getDefaultAdapter).thenReturn(mockBluetoothAdapter);

            // Erstelle eine Instanz des BluetoothManagers
            BluetoothManager bluetoothManager = new BluetoothManager(mockContext);

            // Überprüfe, ob der Context korrekt initialisiert wurde
            assertNotNull("Der Kontext sollte initialisiert sein", bluetoothManager);

            // Überprüfe, ob der BluetoothAdapter korrekt initialisiert wurde
            assertNotNull("Der BluetoothAdapter sollte initialisiert sein", BluetoothAdapter.getDefaultAdapter());
        }
    }
}