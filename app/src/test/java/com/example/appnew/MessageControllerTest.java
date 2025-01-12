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

/**
 * Testklasse für den {@link MessageController}.
 * Diese Klasse überprüft die grundlegenden Funktionen des Controllers,
 * wie das Hinzufügen und Abrufen von Nachrichten.
 */
@RunWith(AndroidJUnit4.class)
public class MessageControllerTest {

    /**
     * Instanz des {@link MessageController}, die für die Tests verwendet wird.
     */
    private MessageController messageController;

    /**
     * Bereitet die Testumgebung vor, indem eine Instanz des {@link MessageController}
     * mit dem Anwendungs-Kontext initialisiert wird.
     */
    @Before
    public void setUp() {
        // Holen des Anwendungs-Kontexts für den Controller
        Context context = ApplicationProvider.getApplicationContext();
        messageController = new MessageController(context);
    }

    /**
     * Testet die Methode {@link MessageController#addMessage(Message)},
     * indem eine Testnachricht hinzugefügt wird.
     * Es wird überprüft, ob kein Fehler bei der Ausführung auftritt.
     */
    @Test
    public void testAddMessage() {
        // Erstellen einer Testnachricht
        Message message = new Message("TestSender", "TestContent", System.currentTimeMillis());

        // Hinzufügen der Nachricht über den Controller
        messageController.addMessage(message);

        // Hinweis: Hier könnte eine weitere Prüfung erfolgen, ob die Nachricht
        // tatsächlich in der Datenbank gespeichert wurde, falls möglich.
    }

    /**
     * Testet die Methode {@link MessageController#getAllMessages()},
     * indem überprüft wird, dass die Rückgabe nicht null ist.
     */
    @Test
    public void testGetAllMessages() {
        // Überprüfen, ob die Methode eine gültige LiveData-Instanz zurückgibt
        assertNotNull("Die Liste der Nachrichten sollte nicht null sein", messageController.getAllMessages());
    }
}