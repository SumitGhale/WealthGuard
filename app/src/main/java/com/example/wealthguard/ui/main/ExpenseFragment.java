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
import androidx.room.Transaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.db.williamchart.view.BarChartView;
import com.example.wealthguard.R;
import com.example.wealthguard.databinding.ExpenseFragmentBinding;
import com.example.wealthguard.transaction.Budget;
import com.example.wealthguard.transaction.Transacs;

import java.util.ArrayList;
import java.util.List;

import kotlin.Pair;

public class ExpenseFragment extends Fragment {

    private ExpenseViewModel mViewModel;
    private ExpenseFragmentBinding binding;
    List<Pair<String, Float>> barSet = new ArrayList<>();
    float totalExpense;

    public static ExpenseFragment newInstance() {
        return new ExpenseFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ExpenseFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);

        final Observer<List<Transacs>> allTransactionObserver = new Observer<List<Transacs>>() {
            @Override
            public void onChanged(List<Transacs> transacs) {
                totalExpense = 0f;
                // code to retrive and display from database
                for (Transacs transactions : transacs) {
                    Float amount = transactions.getAmount();
                    String month = transactions.getMonthName();
                    if(transactions.getTransaction_Type() != null){
                        if(transactions.getTransaction_Type().equals("Expense")){
                            totalExpense = totalExpense + transactions.getAmount();
                            binding.totalAmountTextView.setText("$ " + Float.toString(totalExpense));
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
                }
                // feed data to the bar chart
                BarChartView barChart = binding.barChartExpense;
                barChart.animate((List<Pair<String, Float>>) barSet);

                Budget budget = mViewModel.getByBudgetId(1);
                binding.categoryOneTextView.setText(budget.getCategoryName());
                binding.targetExpenseOneTextView.setText("( $"  + budget.getTargetBudget().toString() + ')');
                binding.expenseOneProgressBar.setMax(budget.getTargetBudget());
                binding.expenseOneProgressBar.setProgress(budget.getAmountSpent());
                Integer remainingAmount = budget.getTargetBudget() - budget.getAmountSpent();
                binding.budgetOneRemainingTextView.setText(remainingAmount.toString() + "$ Remaining");
            }
        };
        mViewModel.getAllTransactions().observe(getViewLifecycleOwner(), allTransactionObserver);
        binding.addExpensesFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavController navController = Navigation.findNavController(view);
                NavDirections navDirections = ExpenseFragmentDirections.actionExpenseFragmentToAddTransactionFragment();
                navController.navigate(navDirections);
            }
        });

        // code to insert the transaction data we got from the add transaction to the database
        if (getArguments() != null){
            if( !getArguments().isEmpty() ){
                ExpenseFragmentArgs args = ExpenseFragmentArgs.fromBundle(getArguments());
                Transacs transacs = args.getTransacs();
                if(transacs!= null){
                    mViewModel.insert(transacs);
                }
                setArguments(null);
            };
        }


        binding.viewBudgetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(view);
                NavDirections navDirections = ExpenseFragmentDirections.actionExpenseFragmentToBudgetFragment2();
                navController.navigate(navDirections);
            }
        });
    }
}