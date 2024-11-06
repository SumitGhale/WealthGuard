package com.example.wealthguard.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.wealthguard.WealthGuardRepository;
import com.example.wealthguard.transaction.Budget;
import com.example.wealthguard.transaction.BudgetDAO;
import com.example.wealthguard.transaction.Transacs;

import java.util.List;

public class BudgetViewModel extends AndroidViewModel {
    private WealthGuardRepository wealthGuardRepository;
    private LiveData<List<Budget>> allBudgets;
    public BudgetViewModel(@NonNull Application application) {
        super(application);
        wealthGuardRepository = new WealthGuardRepository(application);
        allBudgets = wealthGuardRepository.getAllBudgets();
    }

    public void insert(Budget budget){ wealthGuardRepository.insert(budget);}
    public void update(Budget budget){wealthGuardRepository.update(budget);}
    public void delete(Budget budget){}
    public Budget getByBudgetId(Integer budgetId){
        Budget budget = wealthGuardRepository.getByBudgetId(budgetId);
        return budget;
    }
    public LiveData<List<Budget>> getAllBudgets (){
        return allBudgets;
    }

}