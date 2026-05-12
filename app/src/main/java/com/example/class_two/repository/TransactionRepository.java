package com.example.class_two.repository;

import com.example.class_two.model.Transaction;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {
    
    // 单例实例
    private static TransactionRepository instance;
    
    // 内存中的数据存储
    private List<Transaction> transactionList;
    
    // 私有构造方法
    private TransactionRepository() {
        transactionList = new ArrayList<>();
        initializeSampleData();
    }
    
    /**
     * 获取单例实例
     */
    public static synchronized TransactionRepository getInstance() {
        if (instance == null) {
            instance = new TransactionRepository();
        }
        return instance;
    }
    
    /**
     * 初始化示例数据
     */
    private void initializeSampleData() {
        transactionList.add(new Transaction(50.00, "餐饮", "午餐"));
        transactionList.add(new Transaction(30.00, "交通", "地铁卡充值"));
        transactionList.add(new Transaction(200.00, "购物", "购买日用品"));
    }
    
    /**
     * 获取所有消费记录
     */
    public List<Transaction> getAllTransactions() {
        // 返回副本，防止外部修改内部数据
        return new ArrayList<>(transactionList);
    }
    
    /**
     * 添加消费记录
     */
    public void addTransaction(Transaction transaction) {
        if (transaction != null) {
            transactionList.add(transaction);
        }
    }
    
    /**
     * 删除指定位置的消费记录
     */
    public void deleteTransaction(int position) {
        if (position >= 0 && position < transactionList.size()) {
            transactionList.remove(position);
        }
    }
    
    /**
     * 获取总消费金额
     */
    public double getTotalExpense() {
        double total = 0.0;
        for (Transaction transaction : transactionList) {
            total += transaction.getAmount();
        }
        return total;
    }
    
    /**
     * 获取记录数量
     */
    public int getTransactionCount() {
        return transactionList.size();
    }
    
    /**
     * 清空所有记录
     */
    public void clearAll() {
        transactionList.clear();
    }
    
    /**
     * 获取指定位置的记录
     */
    public Transaction getTransactionAt(int position) {
        if (position >= 0 && position < transactionList.size()) {
            return transactionList.get(position);
        }
        return null;
    }
}