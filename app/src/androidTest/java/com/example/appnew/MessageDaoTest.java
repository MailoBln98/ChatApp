package com.example.appnew;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.appnew.model.Message;
import com.example.appnew.model.MessageDao;
import com.example.appnew.model.MessageDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class MessageDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private MessageDatabase database;
    private MessageDao messageDao;

    @Before
    public void setup() {
        Context context = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(context, MessageDatabase.class)
                .allowMainThreadQueries() // Für Tests erlaubt
                .build();
        messageDao = database.messageDao();
    }

    @After
    public void tearDown() {
        database.close();
    }

    /**
     * Hilfsmethode zum Abrufen von LiveData-Werten.
     */
    private <T> T getLiveDataValue(LiveData<T> liveData) throws InterruptedException {
        final Object[] data = new Object[1];
        CountDownLatch latch = new CountDownLatch(1);

        liveData.observeForever(value -> {
            data[0] = value;
            latch.countDown();
        });

        latch.await(2, TimeUnit.SECONDS); // Timeout für den Test
        //noinspection unchecked
        return (T) data[0];
    }

    /**
     * Test: Einfügen und Abrufen einer Nachricht
     */
    @Test
    public void testInsertAndRetrieveMessage() throws InterruptedException {
        Message message = new Message("User1", "Testnachricht", System.currentTimeMillis());
        messageDao.insert(message);

        List<Message> messages = getLiveDataValue(messageDao.getAllMessages());

        assertNotNull(messages);
        assertEquals(1, messages.size());
        assertEquals("Testnachricht", messages.get(0).getContent());
        assertEquals("User1", messages.get(0).getSender());
    }

    /**
     * Test: Abrufen von Nachrichten aus einer leeren Datenbank
     */
    @Test
    public void testEmptyDatabase() throws InterruptedException {
        List<Message> messages = getLiveDataValue(messageDao.getAllMessages());

        assertNotNull(messages);
        assertTrue(messages.isEmpty());
    }

    /**
     * Test: Nachrichten werden in der richtigen Reihenfolge zurückgegeben
     */
    @Test
    public void testMessagesOrderByTimestamp() throws InterruptedException {
        Message message1 = new Message("User1", "Erste Nachricht", System.currentTimeMillis() - 1000);
        Message message2 = new Message("User2", "Zweite Nachricht", System.currentTimeMillis());
        messageDao.insert(message1);
        messageDao.insert(message2);

        List<Message> messages = getLiveDataValue(messageDao.getAllMessages());

        assertNotNull(messages);
        assertEquals(2, messages.size());
        assertEquals("Zweite Nachricht", messages.get(0).getContent()); // Neueste Nachricht zuerst
        assertEquals("Erste Nachricht", messages.get(1).getContent());
    }

    /**
     * Test: Mehrere Nachrichten einfügen und abrufen
     */
    @Test
    public void testInsertMultipleMessages() throws InterruptedException {
        Message message1 = new Message("User1", "Nachricht 1", System.currentTimeMillis());
        Message message2 = new Message("User2", "Nachricht 2", System.currentTimeMillis());
        messageDao.insert(message1);
        messageDao.insert(message2);

        List<Message> messages = getLiveDataValue(messageDao.getAllMessages());

        assertNotNull(messages);
        assertEquals(2, messages.size());
    }
}