package com.example.appnew.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.appnew.interfaces.ConnectionManagerInterface;

/**
 * Klasse für die Room-Datenbank, die Nachrichten speichert und
 * das {@link ConnectionManagerInterface} implementiert, um die Verfügbarkeit
 * der Datenbank zu prüfen.
 */
@Database(entities = {Message.class}, version = 2, exportSchema = true) // Version erhöht auf 2
public abstract class MessageDatabase extends RoomDatabase implements ConnectionManagerInterface {

    private static volatile MessageDatabase INSTANCE;

    /**
     * Liefert die Instanz des {@link MessageDao}, um auf die Datenbank zuzugreifen.
     *
     * @return Das {@link MessageDao}-Interface.
     */
    public abstract MessageDao messageDao();

    /**
     * Liefert die Instanz der Datenbank. Verwendet das Singleton-Muster,
     * um sicherzustellen, dass nur eine Instanz der Datenbank erstellt wird.
     *
     * @param context Der Anwendungskontext.
     * @return Die Instanz der {@link MessageDatabase}.
     */
    public static MessageDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (MessageDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    MessageDatabase.class, "message_database")
                            .fallbackToDestructiveMigration() // Datenbank wird bei Schemaänderungen neu erstellt
                            .addCallback(new Callback() { // Optional: Logging oder Initialisierung
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    // Code für erste Initialisierung, falls notwendig
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Implementierung der Methode aus {@link ConnectionManagerInterface},
     * um die Verfügbarkeit der Datenbankverbindung zu überprüfen.
     *
     * @return true, wenn die Datenbankinstanz initialisiert wurde, andernfalls false.
     */
    @Override
    public boolean isServiceAvailable() {
        return INSTANCE != null;
    }
}

