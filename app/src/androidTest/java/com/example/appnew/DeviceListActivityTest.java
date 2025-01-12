package com.example.appnew;

import android.Manifest;
import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.GrantPermissionRule;

import com.example.appnew.view.DeviceListActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.action.ViewActions.click;

import static org.hamcrest.Matchers.containsString;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class DeviceListActivityTest {

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    );

    private ActivityScenario<DeviceListActivity> scenario;

    @Before
    public void setUp() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setClassName("com.example.appnew", "com.example.appnew.view.DeviceListActivity");

        scenario = ActivityScenario.launch(intent);

        // Add mock data
        scenario.onActivity(activity -> {
            activity.getPairedDevicesAdapter().add("TestDevice\n00:11:22:33:44:55");
            activity.getAvailableDevicesAdapter().add("MockDevice\n66:77:88:99:AA:BB");
        });
    }

    @Test
    public void testPairedDevicesDisplayed() {
        onView(withId(R.id.paired_devices_list))
                .check(matches(isDisplayed()));

        onView(withText(containsString("TestDevice")))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testAvailableDevicesDisplayed() {
        onView(withId(R.id.available_devices_list))
                .check(matches(isDisplayed()));

        onView(withText(containsString("MockDevice")))
                .check(matches(isDisplayed()));
    }

}
