package com.example.wealthguard.ui.main;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wealthguard.R;
import com.example.wealthguard.databinding.BudgetFragmentBinding;
import com.example.wealthguard.transaction.Budget;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BudgetFragment extends Fragment {

    private BudgetViewModel mViewModel;
    BudgetFragmentBinding binding;
    Dialog dialog;
    int totalBudget;

    public static BudgetFragment newInstance() {
        return new BudgetFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = BudgetFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(BudgetViewModel.class);

        Picasso.get().load("https://static.vecteezy.com/system/resources/previews/000/964/198/non_2x/fast-food-meal-set-vector.jpg").into(binding.budgetFoodimageView);
        Picasso.get().load("https://static.vecteezy.com/system/resources/previews/000/251/695/non_2x/grocery-shopping-bag-vector-illustration.jpg").into(binding.budgetGroceryimageView);
        Picasso.get().load("https://c8.alamy.com/comp/2ATRB9D/train-vector-icon-railway-transportation-concept-flat-design-illustration-2ATRB9D.jpg").into(binding.budgetTransportationimageView);
        Picasso.get().load("https://www.the-english-teacher.co.uk/wp-content/uploads/2021/03/Book-Vector-scaled.jpg").into(binding.budgetEducationimageView);
        Picasso.get().load("https://cdn-icons-png.flaticon.com/256/10830/10830604.png").into(binding.budgetOtherimageView);



        final Observer<List<Budget>> allBudgetObserver = new Observer<List<Budget>>() {
            @Override
            public void onChanged(List<Budget> budgets) {
//                Log.d("myTag", "onChanged: budgets");
                totalBudget = 0;
                for(Budget budget : budgets){
                    totalBudget = totalBudget + budget.getTargetBudget();

                    switch (budget.getBudgetId()){
                        case 1:
                            binding.foodProgressBar.setMax(budget.getTargetBudget());
                            binding.foodProgressBar.setProgress(budget.getAmountSpent());
                            break;

                        case 2:
                            binding.groceryProgressBar.setMax(budget.getTargetBudget());
                            binding.groceryProgressBar.setProgress(budget.getAmountSpent());
                            break;

                        case 3:
                            binding.transportationProgressBar.setMax(budget.getTargetBudget());
                            binding.transportationProgressBar.setProgress(budget.getAmountSpent());

                        case 4:
                            binding.educationProgressBar.setMax(budget.getTargetBudget());
                            binding.educationProgressBar.setProgress(budget.getAmountSpent());
                        case 5:
                            binding.otherProgressBar.setMax(budget.getTargetBudget());
                            binding.otherProgressBar.setProgress(budget.getAmountSpent());

                        default:
                            Log.d("progress_bar_tag", "onChanged: no related progress bar found");
                    }
                }
                binding.budgetAmountTextView.setText(Integer.toString(totalBudget) + "$");
            }
        };

        mViewModel.getAllBudgets().observe(getViewLifecycleOwner(), allBudgetObserver);

        dialog = new BottomSheetDialog(requireContext());

        binding.budgetFoodimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //inflate view
                //1 represents the card number or the id of the expense category in the database
                createDialog(1);
                dialog.show();
            }
        });

        binding.budgetGroceryimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //inflate view
                //1 represents the card number or the id of the expense category in the database
                createDialog(2);
                dialog.show();
            }
        });

        binding.budgetTransportationimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //inflate view
                //1 represents the card number or the id of the expense category in the database
                createDialog(3);
                dialog.show();
            }
        });

        binding.budgetEducationimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //inflate view
                //1 represents the card number or the id of the expense category in the database
                createDialog(4);
                dialog.show();
            }
        });
        binding.budgetOtherimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //inflate view
                //1 represents the card number or the id of the expense category in the database
                createDialog(5);
                dialog.show();
            }
        });

        binding.budgetFoodTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialogTarget(1);
                dialog.show();
            }
        });
        binding.budgetGroceryTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialogTarget(2);
                dialog.show();
            }
        });
        binding.budgetTransportationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialogTarget(3);
                dialog.show();
            }
        });
        binding.budgetEducationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialogTarget(4);
                dialog.show();
            }
        });
        binding.budgetOtherTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialogTarget(5);
                dialog.show();
            }
        });
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    private void createDialogTarget(int cardNumber) {
        View view = requireActivity().getLayoutInflater().inflate(R.layout.add_expense_budget_bottom_dialog, null, false);
        Button submit = view.findViewById(R.id.submitExpenseAmountButton);
        EditText addExpenseEditText = view.findViewById(R.id.addExpenseEditTextText);
        TextView title = view.findViewById(R.id.titleTextView);
        title.setText("Target Budget");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Budget newAmount = mViewModel.getByBudgetId(cardNumber);
                int newTargetAmount = Integer.parseInt(addExpenseEditText.getText().toString());
                newAmount.setTargetBudget(newTargetAmount);
                mViewModel.update(newAmount);
            }
        });
        dialog.setContentView(view);
    }

    private void createDialog(int cardNumber) {
        View view = requireActivity().getLayoutInflater().inflate(R.layout.add_expense_budget_bottom_dialog, null, false);
        Button submit = view.findViewById(R.id.submitExpenseAmountButton);
        EditText addExpenseEditText = view.findViewById(R.id.addExpenseEditTextText);
        TextView title = view.findViewById(R.id.titleTextView);
        title.setText("Amount spent");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Budget newAmount = mViewModel.getByBudgetId(cardNumber);
                int totalSpent = newAmount.getAmountSpent() + Integer.parseInt(addExpenseEditText.getText().toString());
                newAmount.setAmountSpent(totalSpent);
                mViewModel.update(newAmount);
            }
        });
        dialog.setContentView(view);
    }
}