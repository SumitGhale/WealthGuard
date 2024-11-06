package com.example.wealthguard.transaction;

import android.nfc.tech.NfcA;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.util.TableInfo;

import java.io.Serializable;

@Entity (tableName = "BUDGET")
public class Budget implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "BUDGETID")
    private Integer budgetId;
    @ColumnInfo(name = "CATEGORYNAME")
    private String categoryName;
    @ColumnInfo(name = "TARGETBUDGET")
    private Integer targetBudget;
    @ColumnInfo(name = "AMOUNTSPENT")
    private Integer amountSpent;

    @Ignore
    public Budget() {
    }

    public Budget(String categoryName, Integer targetBudget, Integer amountSpent) {
        this.categoryName = categoryName;
        this.targetBudget = targetBudget;
        this.amountSpent = amountSpent;
    }

    @NonNull
    public Integer getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(@NonNull Integer budgetId) {
        this.budgetId = budgetId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getTargetBudget() {
        return targetBudget;
    }

    public void setTargetBudget(Integer targetBudget) {
        this.targetBudget = targetBudget;
    }

    public Integer getAmountSpent() {
        return amountSpent;
    }

    public void setAmountSpent(Integer amountSpent) {
        this.amountSpent = amountSpent;
    }

    @Override
    public String toString() {
        return "Budget{" +
                "budgetId=" + budgetId +
                ", categoryName='" + categoryName + '\'' +
                ", targetBudget=" + targetBudget +
                ", amountSpent=" + amountSpent +
                '}';
    }
}
