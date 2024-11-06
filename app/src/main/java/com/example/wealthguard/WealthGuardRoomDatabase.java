package com.example.wealthguard;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Transaction;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.wealthguard.transaction.Budget;
import com.example.wealthguard.transaction.BudgetDAO;
import com.example.wealthguard.transaction.TotalIncomeAndExpenses;
import com.example.wealthguard.transaction.TotalIncomeAndExpensesDAO;
import com.example.wealthguard.transaction.Transacs;
import com.example.wealthguard.transaction.TransacsDAO;
import com.example.wealthguard.transaction.User;
import com.example.wealthguard.transaction.UserDAO;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Transacs.class, TotalIncomeAndExpenses.class, Budget.class, User.class}, version = 4, exportSchema = false)
public abstract class WealthGuardRoomDatabase extends RoomDatabase {

    // declare all your DAOs
    public abstract TransacsDAO transacsDao();
    public abstract TotalIncomeAndExpensesDAO totalIncomeAndExpensesDAO();
    public abstract BudgetDAO budgetDAO();
    public abstract UserDAO userDAO();

    private static volatile WealthGuardRoomDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public static WealthGuardRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (WealthGuardRoomDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room
                            .databaseBuilder(context.getApplicationContext(), WealthGuardRoomDatabase.class, "transaction_db")
                            .addCallback(roomCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    };
    public static RoomDatabase.Callback roomCallback = new  RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            populateInitialData(INSTANCE);
        }
    };

    private static void populateInitialData(WealthGuardRoomDatabase instance) {
        WealthGuardRoomDatabase.databaseWriteExecutor.execute(() ->{
            //Deafault Transactions
            TransacsDAO transactionDAO = INSTANCE.transacsDao();
            transactionDAO.Insert(new com.example.wealthguard.transaction.Transacs(1200F, "April", "Expense", "Grocery", "12 April"));
            transactionDAO.Insert(new com.example.wealthguard.transaction.Transacs(1000F, "May", "Expense", "Grocery", "12 May"));
            transactionDAO.Insert(new com.example.wealthguard.transaction.Transacs(3500F, "April", "Income", "Pay", "12 April"));
            transactionDAO.Insert(new com.example.wealthguard.transaction.Transacs(3200F, "May", "Income", "Pay", "12 May"));

            //Deafault Budget
            BudgetDAO budgetDAO = INSTANCE.budgetDAO();
            budgetDAO.insert(new Budget("Food", 1000, 0));
            budgetDAO.insert(new Budget("Grocery", 1000, 0));
            budgetDAO.insert(new Budget("Transportation", 1000, 0));
            budgetDAO.insert(new Budget("Education", 1000, 0));
            budgetDAO.insert(new Budget("Others", 1000, 0));
        });
    }

}
