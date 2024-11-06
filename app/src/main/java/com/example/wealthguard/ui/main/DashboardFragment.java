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

import com.example.wealthguard.R;
import com.example.wealthguard.databinding.DashboardFragmentBinding;
import com.example.wealthguard.transaction.Transacs;

import java.util.List;

public class DashboardFragment extends Fragment {

    private DashboardViewModel mViewModel;
    DashboardFragmentBinding binding;
    float totalIncome;
    float totalExpense;

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DashboardFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        final Observer<List<Transacs>> allTransactionObserver = new Observer<List<Transacs>>() {
            @Override
            public void onChanged(List<Transacs> transacs) {
                totalIncome = 0;
                totalExpense = 0;
                for (Transacs transactions : transacs) {
                    if (transactions.getTransaction_Type().equals("Income")) {
                        totalIncome = totalIncome + transactions.getAmount();
                    } else if (transactions.getTransaction_Type().equals("Expense")) {
                        totalExpense = totalExpense + transactions.getAmount();
                    }
                }
                binding.amount.setText("$ "+ Float.toString(totalIncome - totalExpense));
                binding.incomeAmountDash.setText("$ " + Float.toString(totalIncome));
                binding.expenseAmountDash.setText("$ " + Float.toString(totalExpense));
                Integer size = transacs.size();
//                Transacs transacs1 = mViewModel.findById(numberOfTransactions);
//                Transacs transacs2 = mViewModel.findById(numberOfTransactions - 1);
//                Transacs transacs3 = mViewModel.findById(numberOfTransactions - 2);


                if (size >= 3) {
                    Transacs transacs1 = transacs.get(size - 1);
                    Transacs transacs2 = transacs.get(size - 2);
                    Transacs transacs3 = transacs.get(size - 3);

                    binding.latestTransaction1Amount.setText(transacs1.getAmount().toString());
                    binding.latestTransaaction1.setText(transacs1.getCategory());

                    binding.latestTransaction2Amount.setText(transacs2.getAmount().toString());
                    binding.latestTransaction2.setText(transacs2.getCategory());

                    binding.latestTransaction3Amount.setText(transacs3.getAmount().toString());
                    binding.latestTransaction3.setText(transacs3.getCategory());
                }
            }
        };
        mViewModel.getAllTransactions().observe(getViewLifecycleOwner(), allTransactionObserver);

        binding.addTransactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(view);
                NavDirections navDirections = DashboardFragmentDirections.actionDashboardFragmentToAddTransactionFragment();
                navController.navigate(navDirections);
            }
        });
        binding.seeAllTransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(view);
                NavDirections navDirections = DashboardFragmentDirections.actionDashboardFragmentToShowTransactionsFragment();
                navController.navigate(navDirections);
            }
        });
    }
}