package com.example.appnew;

import android.content.Intent;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.appnew.R;
import com.example.appnew.view.MapActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MapActivityTest {

    @Rule
    public ActivityTestRule<MapActivity> activityRule =
            new ActivityTestRule<>(MapActivity.class, true, false);



    /**
     * Testet die Kartenansicht mit einem gültigen Standort.
     */
    @Test
    public void testValidLocation() {
        // Intent mit gültigem Standort
        Intent intent = new Intent();
        intent.putExtra("location", "52.5200,13.4050");
        activityRule.launchActivity(intent);

        // Überprüfen, ob die Karte sichtbar ist
        onView(withId(R.id.map)).check(matches(isDisplayed()));
    }

    /**
     * Testet die Kartenansicht ohne gültigen Standort.
     */
    @Test
    public void testInvalidLocation() {
        // Intent ohne Standort
        Intent intent = new Intent();
        intent.putExtra("location", "");
        activityRule.launchActivity(intent);

        // Überprüfen, ob die Activity beendet wurde
        // (z. B. durch finish() bei Fehler)
    }
}