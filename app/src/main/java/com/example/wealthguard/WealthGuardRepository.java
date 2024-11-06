package com.example.wealthguard;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.PrimaryKey;

import com.example.wealthguard.transaction.Budget;
import com.example.wealthguard.transaction.BudgetDAO;
import com.example.wealthguard.transaction.TotalIncomeAndExpenses;
import com.example.wealthguard.transaction.TotalIncomeAndExpensesDAO;
import com.example.wealthguard.transaction.Transacs;
import com.example.wealthguard.transaction.TransacsDAO;
import com.example.wealthguard.transaction.User;
import com.example.wealthguard.transaction.UserDAO;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class WealthGuardRepository {
    private WealthGuardRoomDatabase db;

    // Transactions:
    private TransacsDAO transacsDAO;
    private Transacs transacs;
    private LiveData<List<Transacs>> allTransacs;

    // Total income and expenses:
    private TotalIncomeAndExpensesDAO totalIncomeAndExpensesDAO;
    private TotalIncomeAndExpenses totalIncomeAndExpenses;
    private LiveData<List<TotalIncomeAndExpenses>> allTotalIncomeAndExpenses;

    // Budget
    private BudgetDAO budgetDAO;
    private Budget budget;
    private LiveData<List<Budget>> allBudgets;

    //Users
    private UserDAO userDAO;
    private User user;
    private LiveData<List<User>> allUsers;

    public WealthGuardRepository(Application application) {
        db = WealthGuardRoomDatabase.getDatabase(application);

        transacsDAO = db.transacsDao();
        allTransacs = transacsDAO.getAllTransacs();

        totalIncomeAndExpensesDAO = db.totalIncomeAndExpensesDAO();
        allTotalIncomeAndExpenses = totalIncomeAndExpensesDAO.getAllTotalIncomeAndExpenses();

        budgetDAO = db.budgetDAO();
        allBudgets = budgetDAO.getAllBudgets();

        userDAO = db.userDAO();
        allUsers = userDAO.getAllUsers();
    }

    // Transactions:
    public void insert(Transacs transacs){
        WealthGuardRoomDatabase.databaseWriteExecutor.execute(() ->{
            transacsDAO.Insert(transacs);
        });
    }

    public void update(Transacs transacs){
        WealthGuardRoomDatabase.databaseWriteExecutor.execute(() ->{
            transacsDAO.Update(transacs);
        });
    }

    public void delete(Transacs transacs){
        WealthGuardRoomDatabase.databaseWriteExecutor.execute(() ->{
            transacsDAO.Delete(transacs);
        });
    }

    public LiveData<List<Transacs>> getAllTransactions(){
        return allTransacs;
    }

    public Transacs findById(int id){
        Callable c = () ->{
            Transacs transacs = transacsDAO.findById(id);
            return transacs;
        };
        Future<Transacs> future = WealthGuardRoomDatabase.databaseWriteExecutor.submit(c);
        try{
            transacs = future.get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return transacs;
    }

    // Total income and expenses:
    public void insert(TotalIncomeAndExpenses totalIncomeAndExpenses){
        WealthGuardRoomDatabase.databaseWriteExecutor.execute(() ->{
            totalIncomeAndExpensesDAO.Insert(totalIncomeAndExpenses);
        });
    }

    public void update(TotalIncomeAndExpenses totalIncomeAndExpenses){
        WealthGuardRoomDatabase.databaseWriteExecutor.execute(() ->{
            totalIncomeAndExpensesDAO.Update(totalIncomeAndExpenses);
        });
    }

    public void delete(TotalIncomeAndExpenses totalIncomeAndExpenses){
        WealthGuardRoomDatabase.databaseWriteExecutor.execute(() ->{
            totalIncomeAndExpensesDAO.Delete(totalIncomeAndExpenses);
        });
    }

    public LiveData<List<TotalIncomeAndExpenses>> getAllTotalIncomeAndExpenses(){
        return allTotalIncomeAndExpenses;
    }

    public TotalIncomeAndExpenses getById(Integer totalsId){
        Callable c = () ->{
            TotalIncomeAndExpenses totalIncomeAndExpenses = totalIncomeAndExpensesDAO.getById(totalsId);
            return totalIncomeAndExpenses;
        };
        Future<TotalIncomeAndExpenses> future = WealthGuardRoomDatabase.databaseWriteExecutor.submit(c);
        try{
            totalIncomeAndExpenses = future.get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return totalIncomeAndExpenses;
    }

    public TotalIncomeAndExpenses getBymonthName(String monthName){
        Callable c = () ->{
            TotalIncomeAndExpenses totalIncomeAndExpenses = totalIncomeAndExpensesDAO.getBymonthName(monthName);
            return totalIncomeAndExpenses;
        };
        Future<TotalIncomeAndExpenses> future = WealthGuardRoomDatabase.databaseWriteExecutor.submit(c);
        try{
            totalIncomeAndExpenses = future.get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return totalIncomeAndExpenses;
    }

    // Budget
    public void insert(Budget budget){
        WealthGuardRoomDatabase.databaseWriteExecutor.execute(() ->{
            budgetDAO.insert(budget);
        });
    }

    public void update(Budget budget){
        WealthGuardRoomDatabase.databaseWriteExecutor.execute(() ->{
            budgetDAO.update(budget);
        });
    }

    public void delete(Budget budget){
        WealthGuardRoomDatabase.databaseWriteExecutor.execute(() ->{
            budgetDAO.delete(budget);
        });
    }

    public LiveData<List<Budget>> getAllBudgets(){
        return allBudgets;
    }

    public Budget getByBudgetId(Integer BudgetId){
        Callable c = () ->{
            Budget budget = budgetDAO.getByBudgetId(BudgetId);
            return budget;
        };
        Future<Budget> future = WealthGuardRoomDatabase.databaseWriteExecutor.submit(c);
        try{
            budget = future.get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return budget;
    }

    //Users
    public void insert(User user){
        WealthGuardRoomDatabase.databaseWriteExecutor.execute(() ->{
            userDAO.insert(user);
        });
    }

    public void update(User user){
        WealthGuardRoomDatabase.databaseWriteExecutor.execute(() ->{
            userDAO.update(user);
        });
    }

    public void delete(User user){
        WealthGuardRoomDatabase.databaseWriteExecutor.execute(() ->{
            userDAO.delete(user);
        });
    }

    public LiveData<List<User>> getAllUsers(){
        return allUsers;
    }

    public User getByUserID(Integer userId){
        Callable c = () ->{
            User user = userDAO.getByUserId(userId);
            return user;
        };
        Future<User> future = WealthGuardRoomDatabase.databaseWriteExecutor.submit(c);
        try{
            user = future.get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}
