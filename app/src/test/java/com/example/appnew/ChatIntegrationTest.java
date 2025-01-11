package com.example.appnew;

import android.content.Intent;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.appnew.model.Message;
import com.example.appnew.view.ChatListActivity;
import com.example.appnew.view.ChatListAdapter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ChatIntegrationTest {

    @Before
    public void setUp() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setClassName("com.example.appnew", "com.example.appnew.view.ChatListActivity");
        ActivityScenario.launch(intent);
    }

    @Test
    public void testAddAndRetrieveMessage() {
        Message message = new Message("TestSender", "TestContent", System.currentTimeMillis());

        ActivityScenario.launch(ChatListActivity.class).onActivity(activity -> {
            activity.getMessageController().addMessage(message);
        });

        onView(withId(R.id.recycler_view_chats))
                .perform(RecyclerViewActions.scrollToHolder(
                        ChatListAdapter.withMessageContent("TestContent")
                ));

        onView(withText("TestContent")).check(matches(withText("TestContent")));
    }
}