package com.example.wealthguard.ui.main;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.nfc.Tag;
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
import com.example.wealthguard.databinding.AnalyticsFragmentBinding;
import com.example.wealthguard.transaction.TotalIncomeAndExpenses;
import com.example.wealthguard.transaction.Transacs;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlin.Pair;

public class AnalyticsFragment extends Fragment {
    private AnalyticsFragmentBinding binding;

    private AnalyticsViewModel mViewModel;

    List<BarEntry> incomeBarSet = new ArrayList<>();
    List<BarEntry> expensesBarSet = new ArrayList<>();
    float totalIncome;
    float totalExpense;

    Map<String, Float> monthlyIncome = new HashMap<>();
    Map<String, Float> monthlyExpenses = new HashMap<>();
    List<String> monthNames = new ArrayList<>();    // array to list month names

    public static AnalyticsFragment newInstance() {
        return new AnalyticsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = AnalyticsFragmentBinding.inflate(inflater, container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AnalyticsViewModel.class);

        final Observer<List<Transacs>> allTransactionObserver = new Observer<List<Transacs>>() {
            @Override
            public void onChanged(List<Transacs> transacs) {
                Log.d("thistag", "i am here");
                String monthName;

                for (Transacs transactions : transacs) {
                    monthName = transactions.getMonthName();
                    if (transactions.getTransaction_Type().equals("Income")) {
                        totalIncome = totalIncome + transactions.getAmount();
                        monthlyIncome.put(monthName, monthlyIncome.getOrDefault(monthName, 0.0f) + transactions.getAmount());
                    } else if (transactions.getTransaction_Type().equals("Expense")) {
                        totalExpense = totalExpense + transactions.getAmount();
                        monthlyExpenses.put(monthName, monthlyExpenses.getOrDefault(monthName, 0.0f) + transactions.getAmount());
                    }
                }
                binding.totalAmountTextView.setText("$ "+ Float.toString(totalIncome - totalExpense));
                binding.moneyInAmountTextView.setText("+ $" + totalIncome);
                binding.moneyOutAmountTextView.setText("- $" + totalExpense);
                totalIncome = 0;
                totalExpense = 0;
                for (Map.Entry<String, Float> entry : monthlyIncome.entrySet()){
                    monthName = entry.getKey();
                    Float totalIncome = entry.getValue();
                    Float totalExpenses = monthlyExpenses.getOrDefault(monthName, 0.0f);

                    TotalIncomeAndExpenses existingData = mViewModel.getBymonthName(monthName);
                    if (existingData != null){
                        existingData.setTotalIncome(totalIncome);
                        existingData.setTotalExpenses(totalExpenses);
                        mViewModel.update(existingData);
                    }else{
                        TotalIncomeAndExpenses totalIncomeAndExpenses = new TotalIncomeAndExpenses(monthName, totalIncome, totalExpenses);
                        mViewModel.insert(totalIncomeAndExpenses);
                    };
                }

                monthlyIncome.clear(); // clear previous data
                monthlyExpenses.clear(); // clear previous data
            }
        };
        mViewModel.getAllTransactions().observe(getViewLifecycleOwner(), allTransactionObserver);


        final Observer<List<TotalIncomeAndExpenses>> allTotalIncomeAndExpensesObserver = new Observer<List<TotalIncomeAndExpenses>>() {
            @Override
            public void onChanged(List<TotalIncomeAndExpenses> totalIncomeAndExpenses) {
                incomeBarSet.clear();
                expensesBarSet.clear();

                int index = 0;

                for (TotalIncomeAndExpenses totals : totalIncomeAndExpenses){
                    incomeBarSet.add(new BarEntry(index, totals.getTotalIncome()));
                    expensesBarSet.add(new BarEntry(index, totals.getTotalExpenses()));
                    monthNames.add(totals.getMonthName());
//                    Log.d("thisTag", monthNames.get(index));
                    index++;
                }
                BarDataSet incomes = new BarDataSet(incomeBarSet, "income");
                incomes.setColor(Color.GREEN);
                BarDataSet expenses = new BarDataSet(expensesBarSet, "expense");
                expenses.setColor(Color.RED);

                float groupSpace = 0.5f;
                float barSpace = 0.02f;
                float barWidth = 0.15f;

                binding.barChartIncomeVsExpense.getAxisLeft().setAxisMinimum(0f); // Set the minimum value for the y-axis
                binding.barChartIncomeVsExpense.getAxisRight().setAxisMinimum(0f);

                BarData data = new BarData(incomes, expenses);
                data.setBarWidth(barWidth);
                binding.barChartIncomeVsExpense.setDragEnabled(true); // Enable dragging
                binding.barChartIncomeVsExpense.setScaleEnabled(false); // Disable scaling to prevent unintentional zooming
                binding.barChartIncomeVsExpense.setPinchZoom(false); // Disable pinch zooming

                XAxis xAxis = binding.barChartIncomeVsExpense.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setGranularity(1f);
                xAxis.setValueFormatter(new IndexAxisValueFormatter(monthNames));

                // Enable touch gestures for the chart
                binding.barChartIncomeVsExpense.setTouchEnabled(true);
                // Set maximum visible range
                binding.barChartIncomeVsExpense.setVisibleXRangeMaximum(5); // Adjust the maximum number of visible entries as needed

                binding.barChartIncomeVsExpense.setData(data);
                // Move the chart view to the end to show the latest entries
                binding.barChartIncomeVsExpense.moveViewToX(data.getEntryCount() - 1);
                binding.barChartIncomeVsExpense.groupBars(0f, groupSpace, barSpace); // perform the "explicit" grouping

                binding.barChartIncomeVsExpense.invalidate(); // refresh
            }
        };
        mViewModel.getAllTotalIncomeAndExpenses().observe(getViewLifecycleOwner(), allTotalIncomeAndExpensesObserver);

        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(view);
                NavDirections navDirections = AnalyticsFragmentDirections.actionAnalyticsFragmentToAddTransactionFragment();
                navController.navigate(navDirections);
            }
        });
    }
}