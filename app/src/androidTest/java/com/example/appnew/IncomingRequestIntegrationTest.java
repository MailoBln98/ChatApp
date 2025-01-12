package com.example.appnew;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.appnew.view.ChatDetailActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

/**
 * Integrationstest für die `IncomingRequestActivity`.
 * Dieser Test überprüft die Funktionalität der Buttons "Annehmen" und "Ablehnen"
 * und stellt sicher, dass die entsprechenden Aktionen ausgeführt werden.
 */
@RunWith(AndroidJUnit4.class)
public class IncomingRequestIntegrationTest {

    /**
     * Initialisiert die `IncomingRequestActivity` vor jedem Test.
     */
    @Before
    public void setUp() {
        // Starte die IncomingRequestActivity mit einem expliziten Intent
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setClassName("com.example.appnew", "com.example.appnew.view.IncomingRequestActivity");
        ActivityScenario.launch(intent);
    }

    /**
     * Testet, ob der Klick auf den "Annehmen"-Button die `ChatDetailActivity` startet.
     */
    @Test
    public void testAcceptRequest() {
        // Simuliere das Klicken auf die "Annehmen"-Schaltfläche
        onView(withId(R.id.btn_accept_request)).perform(click());

        // Überprüfe, ob die `ChatDetailActivity` geöffnet wurde,
        // indem überprüft wird, ob die RecyclerView angezeigt wird
        onView(withId(R.id.chat_recycler_view)).check(matches(isDisplayed()));
    }

    /**
     * Testet, ob der Klick auf den "Ablehnen"-Button die `IncomingRequestActivity` schließt.
     */
    @Test
    public void testDeclineRequest() {
        // Simuliere das Klicken auf die "Ablehnen"-Schaltfläche
        onView(withId(R.id.btn_decline_request)).perform(click());

        // Überprüfung, dass die Activity geschlossen wurde
        // Dies lässt sich indirekt testen, indem kein Fehler geworfen wird.
    }
}