package com.example.wealthguard.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.wealthguard.WealthGuardRepository;
import com.example.wealthguard.transaction.Transacs;

import java.util.List;

public class ShowTransactionsViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private WealthGuardRepository wealthGuardRepository;
    private LiveData<List<Transacs>> allTransacs;

    public ShowTransactionsViewModel(@NonNull Application application) {
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
}