package com.example.wealthguard.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.wealthguard.R;
import com.example.wealthguard.databinding.AddTransactionFragmentBinding;
import com.example.wealthguard.transaction.Transacs;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormatSymbols;
import java.util.Calendar;

public class AddTransactionFragment extends Fragment {

    AddTransactionFragmentBinding binding;
    String amountString;
    Float amount;
    Transacs transacs;
    String selectedText;
    public static AddTransactionFragment newInstance() {
        return new AddTransactionFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = AddTransactionFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] transaction_types = getResources().getStringArray(R.array.Transaction_types);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(requireContext(), R.layout.dropdown_transaction_type, transaction_types);
        binding.transactionTypeAutoCompleteTextView.setAdapter(arrayAdapter);

        binding.transactionTypeAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedText =(String) parent.getItemAtPosition(position);
            }
        });

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        binding.datePicker.init(year, month, day, null);

        binding.addTransactionFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amountString = binding.amountEditText.getText().toString();
                String category = binding.CategoryEditText.getText().toString();
                String notes = binding.notesMultiLineEditText.getText().toString();
                if(amountString.trim().isEmpty()){
                    Snackbar.make(view,"Amount is required", Snackbar.LENGTH_SHORT).show();
                    binding.amountEditText.getText().clear();
                    binding.amountEditText.requestFocus();
                    return;
                } else if (category.trim().isEmpty()) {
                    Snackbar.make(view,"Category is required", Snackbar.LENGTH_SHORT).show();
                    binding.CategoryEditText.getText().clear();
                    binding.CategoryEditText.requestFocus();
                    return;
                } else if (notes.trim().isEmpty()) {
                    Snackbar.make(view,"Notes is required", Snackbar.LENGTH_SHORT).show();
                    binding.notesMultiLineEditText.getText().clear();
                    binding.notesMultiLineEditText.requestFocus();
                    return;
                }

                transacs = new Transacs();
                amount = Float.parseFloat(amountString);
                transacs.setAmount(amount);

                int month = binding.datePicker.getMonth();
                DateFormatSymbols dfs = new DateFormatSymbols();
                String[] months = dfs.getMonths();
                String monthName = months[month];
                transacs.setMonthName(monthName);

                transacs.setCategory(binding.CategoryEditText.getText().toString());
                transacs.setTransaction_Type(selectedText);
                transacs.setNote(binding.notesMultiLineEditText.getText().toString());

                if(selectedText.equals("Expense")){
                    NavController navController = Navigation.findNavController(view);
                    AddTransactionFragmentDirections.ActionAddTransactionFragmentToExpenseFragment action = AddTransactionFragmentDirections.actionAddTransactionFragmentToExpenseFragment(transacs);
                    navController.navigate(action);
                    // After navigating to IncomeFragment or ExpenseFragment
                    setArguments(null);

                }
                else{
                    NavController navController = Navigation.findNavController(view);
                    AddTransactionFragmentDirections.ActionAddTransactionFragmentToIncomeFragment action = AddTransactionFragmentDirections.actionAddTransactionFragmentToIncomeFragment(transacs);
                    navController.navigate(action);
                }
            }
        });
    }
}