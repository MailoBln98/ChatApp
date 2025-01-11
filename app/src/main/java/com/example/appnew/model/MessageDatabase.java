package com.example.appnew.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Message.class}, version = 2, exportSchema = true) // Version erhöht auf 2
public abstract class MessageDatabase extends RoomDatabase {

    private static volatile MessageDatabase INSTANCE;

    public abstract MessageDao messageDao();

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
}

