package com.example.appnew;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.appnew.view.DeviceListActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;

@RunWith(AndroidJUnit4.class)
@Config(sdk = {30})
public class BluetoothIntegrationTest {

    @Before
    public void setUp() {
        // Starte die DeviceListActivity vor jedem Test
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setClassName("com.example.appnew", "com.example.appnew.view.DeviceListActivity");
        ActivityScenario.launch(intent);
    }

    @Test
    public void testDisplayPairedDevices() {
        // Überprüfe, ob das simulierte Gerät in der Liste angezeigt wird
        onData(anything())
                .inAdapterView(withId(R.id.paired_devices_list))
                .atPosition(0)
                .check(matches(withText(containsString("TestDevice\n00:11:22:33:44:55"))));
    }

    @Test
    public void testAvailableDevices() {
        // Überprüfe, ob das simulierte verfügbare Gerät angezeigt wird
        onData(anything())
                .inAdapterView(withId(R.id.available_devices_list))
                .atPosition(0)
                .check(matches(withText(containsString("MockDevice\n66:77:88:99:AA:BB"))));
    }
}