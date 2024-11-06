package com.example.wealthguard.ui.main;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wealthguard.R;
import com.example.wealthguard.databinding.LoginFragmentBinding;
import com.example.wealthguard.transaction.User;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LoginFragment extends Fragment {

    private LoginViewModel mViewModel;
    private LoginFragmentBinding binding;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = LoginFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        Picasso.get().load("https://cdn-icons-png.flaticon.com/256/4825/4825038.png").into(binding.loginUserImageView);

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.usernameEditText.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Empty textfield", Toast.LENGTH_SHORT).show();
                    binding.usernameEditText.requestFocus();
                    return;
                }
                final Observer<List<User>> allUsersObserver = new Observer<List<User>>() {
                    @Override
                    public void onChanged(List<User> users) {
                        boolean userNameExists = false;
                        for (User user : users){
                            if(user.getUserName().toString().equals(binding.usernameEditText.getText().toString())){
                                userNameExists = true;
                                if (user.getPassword().toString().equals(binding.passwordEditText.getText().toString())){
                                    NavController navController = Navigation.findNavController(view);
                                    NavDirections navDirections = LoginFragmentDirections.actionLoginFragmentToDashboardFragment();
                                    navController.navigate(navDirections);
                                } else{
                                    Toast.makeText(getContext(), "Invalid password", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        }
                        if(!userNameExists){
                            Toast.makeText(getContext(), "Username does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                mViewModel.getAllUsers().observe(getViewLifecycleOwner(), allUsersObserver);
            }
        });
        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(view);
                NavDirections navDirections = LoginFragmentDirections.actionLoginFragmentToSignUpFragment();
                navController.navigate(navDirections);
            }
        });
    }
}