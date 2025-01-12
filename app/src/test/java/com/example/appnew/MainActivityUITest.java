package com.example.appnew;



import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.appnew.view.MainActivity;
import com.example.appnew.view.PresentationActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityUITest {

    @Rule
    public ActivityTestRule<PresentationActivity> activityRule = new ActivityTestRule<>(PresentationActivity.class);

    @Before
    public void setUp() {
        // Initialisiert Intents für die Validierung
        Intents.init();
    }

    @After
    public void tearDown() {
        // Beendet Intents nach jedem Test
        Intents.release();
    }

    @Test
    public void testChatStartButton() {
        // Simuliert Klick auf den Button "Chat starten"
        onView(withId(R.id.btn_chat_start)).perform(click());

        // Überprüft, ob der Intent die "IncomingRequestActivity" startet
        intended(hasComponent("com.example.appnew.view.IncomingRequestActivity"));
    }

    @Test
    public void testDeviceSearchButton() {
        // Simuliert Klick auf den Button "Gerät suchen"
        onView(withId(R.id.btn_device_search)).perform(click());

        // Überprüft, ob der Intent die "DeviceListActivity" startet
        intended(hasComponent("com.example.appnew.view.DeviceListActivity"));
    }

    @Test
    public void testCurrentChatsButton() {
        // Simuliert Klick auf den Button "Aktuelle Chats"
        onView(withId(R.id.btn_chats)).perform(click());

        // Überprüft, ob der Intent die "ChatListActivity" startet
        intended(hasComponent("com.example.appnew.view.ChatListActivity"));
    }
}