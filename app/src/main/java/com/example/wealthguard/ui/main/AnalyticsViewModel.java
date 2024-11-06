package com.example.wealthguard.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.wealthguard.WealthGuardRepository;
import com.example.wealthguard.transaction.TotalIncomeAndExpenses;
import com.example.wealthguard.transaction.Transacs;

import java.util.List;

public class AnalyticsViewModel extends AndroidViewModel {
    private WealthGuardRepository wealthGuardRepository;
    private LiveData<List<Transacs>> allTransacs;
    private LiveData<List<TotalIncomeAndExpenses>> allTotalIncomeAndExpenses;
    public AnalyticsViewModel(@NonNull Application application) {
        super(application);
        wealthGuardRepository = new WealthGuardRepository(application);
        allTransacs = wealthGuardRepository.getAllTransactions();
        allTotalIncomeAndExpenses = wealthGuardRepository.getAllTotalIncomeAndExpenses();
    }
    // Transactions
    public void insert(Transacs transacs){
        wealthGuardRepository.insert(transacs);
    }

    public void update(Transacs transacs){
        wealthGuardRepository.update(transacs);
    }

    public void delete(Transacs transacs){
        wealthGuardRepository.delete(transacs);
    }

    public Transacs findById(Integer id){
        Transacs transacs = wealthGuardRepository.findById(id);
        return transacs;
    }

    public LiveData<List<Transacs>> getAllTransactions(){
        return allTransacs;
    };

    // Total income and expense:
    public void insert(TotalIncomeAndExpenses totalIncomeAndExpenses){
        wealthGuardRepository.insert(totalIncomeAndExpenses);
    }

    public void update(TotalIncomeAndExpenses totalIncomeAndExpenses){
        wealthGuardRepository.update(totalIncomeAndExpenses);
    }

    public void delete(TotalIncomeAndExpenses totalIncomeAndExpenses){
        wealthGuardRepository.delete(totalIncomeAndExpenses);
    }

    public TotalIncomeAndExpenses getById(Integer totalsId){
        TotalIncomeAndExpenses totalIncomeAndExpenses = wealthGuardRepository.getById(totalsId);
        return totalIncomeAndExpenses;
    }

    public TotalIncomeAndExpenses getBymonthName(String monthName){
        TotalIncomeAndExpenses totalIncomeAndExpenses = wealthGuardRepository.getBymonthName(monthName);
        return totalIncomeAndExpenses;
    }

    public LiveData<List<TotalIncomeAndExpenses>> getAllTotalIncomeAndExpenses(){
        return allTotalIncomeAndExpenses;
    };
}