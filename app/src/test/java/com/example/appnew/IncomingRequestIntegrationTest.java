package com.example.appnew;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.appnew.view.IncomingRequestActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class IncomingRequestIntegrationTest {

    @Before
    public void setUp() {
        // Starte die IncomingRequestActivity vor jedem Test
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setClassName("com.example.appnew", "com.example.appnew.view.IncomingRequestActivity");
        ActivityScenario.launch(intent);
    }

    @Test
    public void testAcceptRequest() {
        // Simuliere das Klicken auf die "Annehmen"-Schaltfläche
        onView(withId(R.id.btn_accept_request)).perform(click());

        // Überprüfe, ob die ChatDetailActivity gestartet wurde
        onView(withId(R.id.chat_detail_view))
                .check(matches(withText("ChatDetailActivity")));
    }
}