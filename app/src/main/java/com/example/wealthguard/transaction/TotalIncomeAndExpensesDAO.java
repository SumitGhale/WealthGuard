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
public interface TotalIncomeAndExpensesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void Insert(TotalIncomeAndExpenses totalIncomeAndExpenses);

    @Update
    void Update(TotalIncomeAndExpenses totalIncomeAndExpenses);

    @Delete
    void Delete(TotalIncomeAndExpenses totalIncomeAndExpenses);

    @Query("SELECT * FROM TOTALINCOMEANDEXPENSES")
    LiveData<List<TotalIncomeAndExpenses>> getAllTotalIncomeAndExpenses();

    @Query("SELECT * FROM TOTALINCOMEANDEXPENSES WHERE TOTALSID = :totals_id")
    TotalIncomeAndExpenses getById(Integer totals_id);

    @Query("SELECT * FROM TOTALINCOMEANDEXPENSES WHERE MONTHNAME = :monthName")
    TotalIncomeAndExpenses getBymonthName(String monthName);
}
