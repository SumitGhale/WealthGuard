package com.example.wealthguard.ui.main;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.net.http.InlineExecutionProhibitedException;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wealthguard.R;
import com.example.wealthguard.databinding.ShowTransactionsFragmentBinding;
import com.example.wealthguard.transaction.Transacs;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class showTransactionsFragment extends Fragment {

    private ShowTransactionsViewModel mViewModel;
    private ShowTransactionsFragmentBinding binding;

    public static showTransactionsFragment newInstance() {
        return new showTransactionsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ShowTransactionsFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ShowTransactionsViewModel.class);
        //configure to the recycler view
        binding.transactionsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.transactionsRecyclerView.setHasFixedSize(true);

        //TODO: set the adapter to the recycler view
        TransactionRecyclerViewAdapter adapter = new TransactionRecyclerViewAdapter();
        binding.transactionsRecyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int adapterPosition = viewHolder.getAdapterPosition();
                Transacs transacs = mViewModel.getAllTransactions().getValue().get(adapterPosition);
                mViewModel.delete(transacs);

                Snackbar.make(binding.transactionsRecyclerView, transacs.getCategory().concat(" deleted"), Snackbar.LENGTH_LONG).setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewModel.insert(transacs);
                    }
                }).show();
            }
        });

        itemTouchHelper.attachToRecyclerView(binding.transactionsRecyclerView);

        final Observer<List<Transacs>> allTransactionsObserver = new Observer<List<Transacs>>() {
            @Override
            public void onChanged(List<Transacs> transacs) {
                // TODO: set the list of the monster to the adapter
                adapter.submitList(transacs);
            }
        };
        mViewModel.getAllTransactions().observe(getViewLifecycleOwner(), allTransactionsObserver);

    }
}