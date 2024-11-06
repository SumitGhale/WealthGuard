package com.example.wealthguard.ui.main;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.wealthguard.databinding.TransactionRecyclerItemViewBinding;
import com.example.wealthguard.transaction.Transacs;

public class TransactionRecyclerViewAdapter extends ListAdapter<Transacs, TransactionViewHolder> {
    private TransactionRecyclerItemViewBinding binding;

    public TransactionRecyclerViewAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Transacs> DIFF_CALLBACK = new DiffUtil.ItemCallback<Transacs>() {
        @Override
        public boolean areItemsTheSame(@NonNull Transacs oldItem, @NonNull Transacs newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Transacs oldItem, @NonNull Transacs newItem) {
            return oldItem.getAmount() .equals(newItem.getAmount()) &&
                    oldItem.getTransaction_Type().equals(newItem.getTransaction_Type()) &&
                    oldItem.getCategory().equals(newItem.getCategory()) &&
                    oldItem.getNote().equals(newItem.getNote())&&
                    oldItem.getMonthName().equals(newItem.getMonthName());
        }
    };
    protected TransactionRecyclerViewAdapter(@NonNull DiffUtil.ItemCallback<Transacs> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = TransactionRecyclerItemViewBinding.inflate(inflater, parent, false);

        TransactionViewHolder transactionViewHolder = new TransactionViewHolder(binding);
        return transactionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        // get data from te list based on the position
        Transacs transacs = getTransacAt(position);
        holder.updateTransaction(transacs);
    }

    public Transacs getTransacAt(int position){
        return getItem(position);
    }
}
