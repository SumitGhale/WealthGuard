package com.example.wealthguard.transaction;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "TOTALINCOMEANDEXPENSES")
public class TotalIncomeAndExpenses implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "TOTALSID")
    private Integer totalsId;
    @ColumnInfo(name = "MONTHNAME")
    private String monthName;
    @ColumnInfo(name = "TOTALINCOME")
    private Float totalIncome;
    @ColumnInfo(name = "TOTALEXPENSES")
    private Float totalExpenses;

    @Ignore
    public TotalIncomeAndExpenses() {
    }

    public TotalIncomeAndExpenses(String monthName, Float totalIncome, Float totalExpenses) {
        this.monthName = monthName;
        this.totalIncome = totalIncome;
        this.totalExpenses = totalExpenses;
    }

    @NonNull
    public Integer getTotalsId() {
        return totalsId;
    }

    public void setTotalsId(@NonNull Integer totalsId) {
        this.totalsId = totalsId;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public Float getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Float totalIncome) {
        this.totalIncome = totalIncome;
    }

    public Float getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(Float totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    @Override
    public String toString() {
        return "TotalIncomeAndExpenses{" +
                "totalsId=" + totalsId +
                ", monthName='" + monthName + '\'' +
                ", totalIncome=" + totalIncome +
                ", totalExpenses=" + totalExpenses +
                '}';
    }
}
