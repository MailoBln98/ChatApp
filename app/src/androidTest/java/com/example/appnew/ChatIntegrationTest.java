package com.example.appnew;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.appnew.model.Message;
import com.example.appnew.view.ChatListActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.example.appnew.view.ChatListAdapter.withMessageContent;

/**
 * Integrationstest für die `ChatListActivity`.
 * Dieser Test überprüft die Funktionalität der Nachrichtenanzeige im RecyclerView.
 */
@RunWith(AndroidJUnit4.class)
public class ChatIntegrationTest {

    /**
     * Setup-Methode, die vor jedem Testfall ausgeführt wird.
     * Startet die `ChatListActivity`, um die Testumgebung vorzubereiten.
     */
    @Before
    public void setUp() {
        // Initialisiert die `ChatListActivity` vor jedem Test
        ActivityScenario.launch(ChatListActivity.class);
    }

    /**
     * Testfall, der überprüft, ob eine Nachricht korrekt in der Chat-Liste angezeigt wird.
     */
    @Test
    public void testMessageDisplayedInChatList() {
        // Erstelle eine Testnachricht
        Message testMessage = new Message("TestUser", "Integration Test Message", System.currentTimeMillis());

        // Lade die `ChatListActivity` und füge die Testnachricht hinzu
        ActivityScenario<ChatListActivity> scenario = ActivityScenario.launch(ChatListActivity.class);
        scenario.onActivity(activity -> {
            // Füge die Testnachricht über den MessageController zur Datenbank hinzu
            activity.getMessageController().addMessage(testMessage);
        });

        // Überprüfe, ob die Testnachricht im RecyclerView angezeigt wird
        onView(withId(R.id.recycler_view_chats))
                .perform(RecyclerViewActions.scrollToHolder(withMessageContent("Integration Test Message")));
    }
}