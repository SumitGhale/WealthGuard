package com.example.wealthguard.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.wealthguard.WealthGuardRepository;
import com.example.wealthguard.transaction.Transacs;
import com.example.wealthguard.transaction.User;

import java.util.List;

public class LoginViewModel extends AndroidViewModel {

    private WealthGuardRepository wealthGuardRepository;
    private LiveData<List<User>> allUsers;
    public LoginViewModel(@NonNull Application application) {
        super(application);
        wealthGuardRepository = new WealthGuardRepository(application);
        allUsers = wealthGuardRepository.getAllUsers();
    }

    public void insert(User user){
        wealthGuardRepository.insert(user);
    }

    public void update(User user){
        wealthGuardRepository.update(user);
    }

    public void delete(User user){
        wealthGuardRepository.delete(user);
    }

    public User getByUserID(Integer id){
        User user = wealthGuardRepository.getByUserID(id);
        return user;
    }

    public LiveData<List<User>> getAllUsers(){
        return allUsers;
    };
}