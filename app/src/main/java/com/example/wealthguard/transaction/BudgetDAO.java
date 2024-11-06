package com.example.wealthguard.transaction;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BudgetDAO {
    @Insert
    void insert(Budget budget);
    @Update
    void update(Budget budget);
    @Delete
    void delete(Budget budget);
    @Query("SELECT * FROM BUDGET")
    LiveData<List<Budget>> getAllBudgets();
    @Query("SELECT * FROM BUDGET WHERE BUDGETID = :budgetId")
    Budget getByBudgetId(Integer budgetId);
}
