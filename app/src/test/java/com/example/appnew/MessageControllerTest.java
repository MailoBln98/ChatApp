package com.example.appnew;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.appnew.controller.MessageController;
import com.example.appnew.model.Message;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class MessageControllerTest {

    private MessageController messageController;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        messageController = new MessageController(context);
    }

    @Test
    public void testAddMessage() {
        Message message = new Message("TestSender", "TestContent", System.currentTimeMillis());
        messageController.addMessage(message);
        // Hier könntest du prüfen, ob die Nachricht in der Datenbank ist, falls möglich
    }

    @Test
    public void testGetAllMessages() {
        assertNotNull(messageController.getAllMessages());
    }
}