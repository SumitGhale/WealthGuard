package com.example.wealthguard.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wealthguard.R;
import com.example.wealthguard.databinding.SignUpFragmentBinding;
import com.example.wealthguard.transaction.User;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SignUpFragment extends Fragment {
    private SignUpFragmentBinding binding;
    private LoginViewModel mViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = SignUpFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        Picasso.get().load("https://img.freepik.com/free-vector/sign-page-abstract-concept-illustration_335657-3875.jpg?t=st=1713882043~exp=1713885643~hmac=a8925c8110076fdfa8a0952ab7aaaf431b36dd4b434795d19e5d94a0ac14dad7&w=740").into(binding.signUpImageView);

        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Observer<List<User>> allUsersObserver = new Observer<List<User>>() {
                    @Override
                    public void onChanged(List<User> users) {
                        if (binding.usernameEditText.getText().toString().isEmpty()){
                            Toast.makeText(getContext(), "Empty textfield", Toast.LENGTH_SHORT).show();
                            binding.usernameEditText.requestFocus();
                            return;
                        }else{
                            for(User user : users){
                                if(user.getUserName().equals(binding.usernameEditText.getText().toString())){
                                    Toast.makeText(getContext(), "Username already exists", Toast.LENGTH_SHORT).show();
                                    binding.usernameEditText.requestFocus();
                                    return;
                                }
                            }
                        }
                        String userName = binding.usernameEditText.getText().toString();
                        String password;
                        if(binding.passwordEditText.getText().toString().equals(binding.confirmPasswordEditText.getText().toString())){
                            password = binding.passwordEditText.getText().toString();
                        }else{
                            Toast.makeText(getContext(), "Password not matched", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        User user = new User(userName, password);
                        mViewModel.insert(user);
                        NavController navController = Navigation.findNavController(view);
                        NavDirections navDirections = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment();
                        navController.navigate(navDirections);
                    }
                };
                mViewModel.getAllUsers().observe(getViewLifecycleOwner(), allUsersObserver);
            }
        });
    }
}