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
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.example.appnew.view.ChatListAdapter.withMessageContent;

@RunWith(AndroidJUnit4.class)
public class ChatListUpdateTest {

    @Before
    public void setUp() {
        // Starte die ChatListActivity vor jedem Test
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setClassName("com.example.appnew", "com.example.appnew.view.ChatListActivity");
        ActivityScenario.launch(intent);
    }

    @Test
    public void testRecyclerViewUpdateOnNewMessage() {
        // Erstelle eine neue Nachricht
        Message newMessage = new Message("TestSender", "NewContent", System.currentTimeMillis());

        // Füge die Nachricht über den Controller hinzu
        ActivityScenario<ChatListActivity> scenario = ActivityScenario.launch(ChatListActivity.class);
        scenario.onActivity(activity -> {
            activity.getMessageController().addMessage(newMessage);
        });

        // Überprüfe, ob die Nachricht angezeigt wird
        onView(withId(R.id.recycler_view_chats))
                .perform(RecyclerViewActions.scrollToHolder(withMessageContent("NewContent")));
    }
}