package com.example.appnew;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.appnew.view.DeviceListActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class NoBluetoothIntegrationTest {

    @Before
    public void setUp() {
        // Starte die DeviceListActivity vor jedem Test
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setClassName("com.example.appnew", "com.example.appnew.view.DeviceListActivity");
        ActivityScenario.launch(intent);
    }

    @Test
    public void testNoBluetoothDevices() {
        // Überprüfe die Anzeige, wenn keine Geräte verfügbar sind
        onView(withId(R.id.paired_devices_list))
                .check(matches(withText("No paired devices found")));
    }
}
