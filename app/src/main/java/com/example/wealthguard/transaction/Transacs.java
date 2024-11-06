package com.example.wealthguard.transaction;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "TRANSACS")
public class Transacs implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "ID")
    private Integer id;
    @ColumnInfo(name = "AMOUNT")
    private Float amount;
    @ColumnInfo(name = "MONTHNAME")
    private String monthName;
    @ColumnInfo(name = "TRANSACTION_TYPE")
    private String transaction_Type;
    @ColumnInfo(name = "CATEGORY")
    private String category;
    @ColumnInfo(name = "NOTE")
    private String note;

    @Ignore
    public Transacs() {
    }

    public Transacs(Float amount, String monthName, String transaction_Type, String category, String note) {
        this.amount = amount;
        this.monthName = monthName;
        this.transaction_Type = transaction_Type;
        this.category = category;
        this.note = note;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public String getTransaction_Type() {
        return transaction_Type;
    }

    public void setTransaction_Type(String transaction_Type) {
        this.transaction_Type = transaction_Type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Transacs{" +
                "id=" + id +
                ", amount=" + amount +
                ", MonthName='" + monthName + '\'' +
                ", Transaction_Type='" + transaction_Type + '\'' +
                ", Category='" + category + '\'' +
                ", Note='" + note + '\'' +
                '}';
    }
}
