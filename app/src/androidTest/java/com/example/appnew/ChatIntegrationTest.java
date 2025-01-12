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

import java.util.Collections;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.appnew.view.ChatListAdapter.withMessageContent;

@RunWith(AndroidJUnit4.class)
public class ChatIntegrationTest {

    @Before
    public void setUp() {
        // Initialisiere die `ChatListActivity` vor jedem Test
        ActivityScenario.launch(ChatListActivity.class);
    }

    @Test
    public void testMessageDisplayedInChatList() {
        // Erstelle eine neue Nachricht
        Message testMessage = new Message("TestUser", "Integration Test Message", System.currentTimeMillis());

        // Lade die Aktivität und füge die Nachricht zur Datenbank hinzu
        ActivityScenario<ChatListActivity> scenario = ActivityScenario.launch(ChatListActivity.class);
        scenario.onActivity(activity -> {
            activity.getMessageController().addMessage(testMessage);
        });

        // Überprüfe, ob die Nachricht im RecyclerView angezeigt wird
        onView(withId(R.id.recycler_view_chats))
                .perform(RecyclerViewActions.scrollToHolder(withMessageContent("Integration Test Message")));
    }


}