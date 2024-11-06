package com.example.wealthguard.ui.main;

import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wealthguard.databinding.TransactionRecyclerItemViewBinding;
import com.example.wealthguard.transaction.Transacs;

public class TransactionViewHolder extends RecyclerView.ViewHolder {
    private TransactionRecyclerItemViewBinding binding;
    public TransactionViewHolder(@NonNull TransactionRecyclerItemViewBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
    public void updateTransaction(Transacs transac){
        binding.monthNameTextView.setText(transac.getMonthName());
        binding.categoryTextView.setText(transac.getCategory());
        binding.amountTextView.setText(transac.getAmount().toString());
        if(transac.getTransaction_Type().equals("Income")){
            binding.amountTextView.setTextColor(Color.parseColor("#009688"));
        }else {
            binding.amountTextView.setTextColor(Color.parseColor("#F44336"));
        }
    }
}
