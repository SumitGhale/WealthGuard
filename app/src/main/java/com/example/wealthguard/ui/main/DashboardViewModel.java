package com.example.wealthguard.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.wealthguard.WealthGuardRepository;
import com.example.wealthguard.transaction.Transacs;

import java.util.List;

public class DashboardViewModel extends AndroidViewModel {

    private WealthGuardRepository wealthGuardRepository;
    private LiveData<List<Transacs>> allTransacs;
    public DashboardViewModel(@NonNull Application application) {
        super(application);

        wealthGuardRepository = new WealthGuardRepository(application);
        allTransacs = wealthGuardRepository.getAllTransactions();
    }

    public LiveData<List<Transacs>> getAllTransactions(){
        return allTransacs;
    };
    public Transacs findById(Integer id){
        return wealthGuardRepository.findById(id);
    }

}