package com.example.appnew.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MessageDao {

    @Insert
    void insert(Message message);

    @Query("SELECT * FROM messages ORDER BY timestamp DESC")
    LiveData<List<Message>> getAllMessages();
}
