package com.example.wealthguard.transaction;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void insert(User user);
    @Update
    void update(User user);
    @Delete
    void delete(User user);
    @Query("SELECT * FROM USER")
    LiveData<List<User>> getAllUsers();
    @Query("SELECT * FROM USER WHERE USERID = :userId")
    User getByUserId(Integer userId);
}
