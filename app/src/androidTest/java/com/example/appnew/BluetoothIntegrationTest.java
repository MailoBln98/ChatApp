package com.example.appnew;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;

import android.Manifest;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;

import com.example.appnew.view.DeviceListActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class BluetoothIntegrationTest {

    // Berechtigungen während der Tests automatisch gewähren
    @Rule
    public GrantPermissionRule bluetoothPermissionRule = GrantPermissionRule.grant(
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    );

    @Before
    public void setUp() {
        // Starte die DeviceListActivity
        ActivityScenario<DeviceListActivity> scenario = ActivityScenario.launch(DeviceListActivity.class);

        // Füge Testdaten hinzu
        scenario.onActivity(activity -> {
            activity.getPairedDevicesAdapter().add("TestDevice\n00:11:22:33:44:55");
            activity.getAvailableDevicesAdapter().add("MockDevice\n66:77:88:99:AA:BB");
        });
    }

    @Test
    public void testAvailableDevices() {
        // Überprüfe, ob das MockDevice in der Liste der verfügbaren Geräte angezeigt wird
        onData(anything())
                .inAdapterView(withId(R.id.available_devices_list))
                .atPosition(0)
                .check(matches(isDisplayed()));
    }
}
