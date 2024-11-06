package com.example.wealthguard.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

//import com.example.wealthguard.WealthGuardRepository;

import com.example.wealthguard.WealthGuardRepository;
import com.example.wealthguard.transaction.Budget;
import com.example.wealthguard.transaction.Transacs;

import java.util.List;

public class ExpenseViewModel extends AndroidViewModel{
    private WealthGuardRepository wealthGuardRepository;
    private LiveData<List<Transacs>> allTransacs;

    public ExpenseViewModel(@NonNull Application application) {
        super(application);
        wealthGuardRepository = new WealthGuardRepository(application);
        allTransacs = wealthGuardRepository.getAllTransactions();
    }

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
    public Budget getByBudgetId(Integer budgetId){
        Budget budget = wealthGuardRepository.getByBudgetId(budgetId);
        return budget;
    }
}
