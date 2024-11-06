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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.db.williamchart.view.BarChartView;
import com.example.wealthguard.R;
import com.example.wealthguard.databinding.IncomeFragmentBinding;
import com.example.wealthguard.transaction.Transacs;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlin.Pair;

public class IncomeFragment extends Fragment {
    private IncomeFragmentBinding binding;
    private IncomeViewModel mViewModel;
    List<Pair<String, Float>> barSet = new ArrayList<>();
     float totalIncome;

    public static IncomeFragment newInstance() {
        return new IncomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = IncomeFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(IncomeViewModel.class);

        final Observer<List<Transacs>> allTransactionObserver = new Observer<List<Transacs>>() {
            @Override
            public void onChanged(List<Transacs> transacs) {
                totalIncome = 0f;
                // code to retrive and display from database
                for (Transacs transactions : transacs) {
                    Float amount = transactions.getAmount();
                    String month = transactions.getMonthName();
                    if(transactions.getTransaction_Type() != null){
                        if(transactions.getTransaction_Type().equals("Income")){
                            totalIncome = totalIncome + transactions.getAmount();
                            binding.totalAmountTextView.setText("$ " + Float.toString(totalIncome));
                            boolean monthExists = false;
                            for (int i = 0; i < barSet.size(); i++) {
                                Pair<String, Float> pair = barSet.get(i);
                                if (pair.getFirst().equals(month)) {
                                    // Update the existing entry with the new expense amount
                                    barSet.set(i, new Pair<>(month, pair.getSecond() + amount));
                                    monthExists = true;
                                    break;
                                }
                            }

                            if (!monthExists){
                                barSet.add(new Pair<>(transactions.getMonthName(), amount));
                            };
                        }
                    }

                    Integer size = transacs.size();

                    if (size >= 3) {
                        Transacs transacs1 = transacs.get(size - 1);
                        Transacs transacs2 = transacs.get(size - 2);
                        Transacs transacs3 = transacs.get(size - 3);
                        Transacs transacs4 = transacs.get(size - 4);

                        binding.sourceOneTextView.setText(transacs1.getCategory());
                        binding.sourceOneAmountTextView.setText(transacs1.getAmount().toString());

                        binding.sourceTwoTextView.setText(transacs2.getCategory());
                        binding.sourceTwoAmountTextView.setText(transacs2.getAmount().toString());

                        binding.sourceThreeTextView.setText(transacs3.getCategory());
                        binding.sourceThreeAmountTextView.setText(transacs3.getAmount().toString());

                        binding.sourceFourTextView.setText(transacs4.getCategory());
                        binding.sourceFourAmountTextView.setText(transacs4.getAmount().toString());
                    }
                }
                // feed data to the bar chart
                BarChartView barChart = binding.barChartIncome;
                barChart.animate((List<Pair<String, Float>>) barSet);
            }
        };
        mViewModel.getAllTransactions().observe(getViewLifecycleOwner(), allTransactionObserver);
        binding.viewMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(view);
                NavDirections navDirections = IncomeFragmentDirections.actionIncomeFragmentToShowTransactionsFragment();
                navController.navigate(navDirections);
            }
        });
        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(view);
                NavDirections navDirections = IncomeFragmentDirections.actionIncomeFragmentToAddTransactionFragment();
                navController.navigate(navDirections);
            }
        });

        // code to insert the transaction data we got from the add transaction to the database
        if(getArguments() != null){
            if(!getArguments().isEmpty()){
                IncomeFragmentArgs args = IncomeFragmentArgs.fromBundle(getArguments());
                Transacs transacs = args.getTransacs();
                if(transacs!= null){
                    mViewModel.insert(transacs);
                }
                setArguments(null);
            };
        }
    }
}