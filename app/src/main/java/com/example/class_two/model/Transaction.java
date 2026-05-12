package com.example.class_two.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Transaction {
    private double amount;
    private String category;
    private String note;
    private String date;
    
    // 默认构造方法
    public Transaction() {
        this.amount = 0.0;
        this.category = "";
        this.note = "";
        this.date = getCurrentDate();
    }
    
    // 带参数的构造方法
    public Transaction(double amount, String category, String note) {
        this.amount = amount;
        this.category = category;
        this.note = note;
        this.date = getCurrentDate();
    }
    
    // 全参数构造方法
    public Transaction(double amount, String category, String note, String date) {
        this.amount = amount;
        this.category = category;
        this.note = note;
        this.date = date;
    }
    
    // Getter 和 Setter 方法
    public double getAmount() {
        return amount;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
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
    
    public String getDate() {
        return date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    // 获取当前日期字符串
    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }
    
    @Override
    public String toString() {
        return "Transaction{" +
                "amount=" + amount +
                ", category='" + category + '\'' +
                ", note='" + note + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}