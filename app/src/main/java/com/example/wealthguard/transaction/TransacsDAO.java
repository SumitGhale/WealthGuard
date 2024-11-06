package com.example.wealthguard.transaction;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TransacsDAO{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void Insert(Transacs transacs);
    @Update
    void Update(Transacs transacs);
    @Delete
    void Delete(Transacs transacs);
    @Query("SELECT * FROM TRANSACS")
    LiveData<List<Transacs>> getAllTransacs();
    @Query("SELECT * FROM TRANSACS WHERE ID = :id")
    Transacs findById(int id);
}
