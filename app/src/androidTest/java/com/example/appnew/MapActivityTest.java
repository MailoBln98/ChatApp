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

/**
 * Testklasse für die `MapActivity`.
 * Diese Klasse überprüft, ob die `MapActivity` korrekt mit gültigen und ungültigen Standorten funktioniert.
 */
@RunWith(AndroidJUnit4.class)
public class MapActivityTest {

    /**
     * Regel für die `MapActivity`, um sie mit einem Intent zu starten.
     * Die Aktivität wird nicht automatisch gestartet (dritter Parameter ist `false`).
     */
    @Rule
    public ActivityTestRule<MapActivity> activityRule =
            new ActivityTestRule<>(MapActivity.class, true, false);

    /**
     * Testet, ob die Karte korrekt angezeigt wird, wenn ein gültiger Standort übergeben wird.
     */
    @Test
    public void testValidLocation() {
        // Erstelle ein Intent mit einem gültigen Standort
        Intent intent = new Intent();
        intent.putExtra("location", "52.5200,13.4050"); // Beispielkoordinaten für Berlin
        activityRule.launchActivity(intent); // Starte die Aktivität mit dem Intent

        // Überprüfen, ob die Karte sichtbar ist
        onView(withId(R.id.map)).check(matches(isDisplayed()));
    }

    /**
     * Testet, wie die Aktivität auf einen ungültigen Standort reagiert.
     */
    @Test
    public void testInvalidLocation() {
        // Erstelle ein Intent ohne gültigen Standort
        Intent intent = new Intent();
        intent.putExtra("location", ""); // Leerer Standort
        activityRule.launchActivity(intent); // Starte die Aktivität mit dem Intent


    }
}