package com.example.class_two.model;

import java.util.Date;

/**
 * 记录项数据类
 * 用于RecyclerView列表展示的教学示例
 */
public class RecordItem {
    private int id;
    private String title;
    private double amount;
    private Date date;
    private RecordType type;
    private int iconResId;
    
    public RecordItem(int id, String title, double amount, Date date, RecordType type, int iconResId) {
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.date = date;
        this.type = type;
        this.iconResId = iconResId;
    }
    
    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public double getAmount() { return amount; }
    public Date getDate() { return date; }
    public RecordType getType() { return type; }
    public int getIconResId() { return iconResId; }
}