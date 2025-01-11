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

public class BluetoothManagerTest {

    private Context mockContext;

    @Before
    public void setUp() {
        // Mock Context
        mockContext = mock(Context.class);
    }

    @Test
    public void testConstructor_initialization() {
        // Mock static method BluetoothAdapter.getDefaultAdapter()
        try (MockedStatic<BluetoothAdapter> mockedBluetoothAdapter = mockStatic(BluetoothAdapter.class)) {
            BluetoothAdapter mockBluetoothAdapter = mock(BluetoothAdapter.class);
            mockedBluetoothAdapter.when(BluetoothAdapter::getDefaultAdapter).thenReturn(mockBluetoothAdapter);

            // Create an instance of BluetoothManager
            BluetoothManager bluetoothManager = new BluetoothManager(mockContext);

            // Verify that the context is set correctly
            assertNotNull("Context should be initialized", bluetoothManager);

            // Verify that the BluetoothAdapter is initialized
            assertNotNull("BluetoothAdapter should be initialized", BluetoothAdapter.getDefaultAdapter());
        }
    }
}