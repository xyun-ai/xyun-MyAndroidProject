package com.example.class_two.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.class_two.model.Transaction;
import com.example.class_two.repository.TransactionRepository;
import java.util.List;

public class ExpenseViewModel extends ViewModel {
    
    // Repository单例
    private final TransactionRepository repository;

    // LiveData
    private final MutableLiveData<List<Transaction>> transactionList = new MutableLiveData<>();
    private final MutableLiveData<Double> totalAmount = new MutableLiveData<>();
    private final MutableLiveData<Integer> recordCount = new MutableLiveData<>();
    
    public ExpenseViewModel() {
        // 获取Repository单例
        repository = TransactionRepository.getInstance();

        // 初始化LiveData
        updateLiveData();
    }
    
    // 获取消费记录列表（LiveData）
    public LiveData<List<Transaction>> getTransactionList() {
        return transactionList;
    }
    
    // 获取总消费金额（LiveData）
    public LiveData<Double> getTotalAmount() {
        return totalAmount;
    }

    // 获取记录数量（LiveData）
    public LiveData<Integer> getRecordCount() {
        return recordCount;
    }

    // 添加消费记录
    public void addTransaction(Transaction transaction) {
        if (transaction == null) {
            return;
        }

        // 通过Repository添加
        repository.addTransaction(transaction);

            // 更新LiveData
            updateLiveData();
        }

    // 添加消费记录（重载方法）
    public void addTransaction(double amount, String category, String note) {
        Transaction transaction = new Transaction(amount, category, note);
        addTransaction(transaction);
        }

    // 删除消费记录
    public void removeTransaction(int position) {
        // 通过Repository删除
        repository.deleteTransaction(position);

        // 更新LiveData
        updateLiveData();
    }
    
    // 删除消费记录（通过对象）
    public void removeTransaction(Transaction transaction) {
        // 需要先找到位置
        List<Transaction> transactions = repository.getAllTransactions();
        int position = transactions.indexOf(transaction);
        if (position != -1) {
            removeTransaction(position);
        }
    }
    
    // 清空所有消费记录
    public void clearAllTransactions() {
        repository.clearAll();
            updateLiveData();
        }

    // 更新LiveData
    private void updateLiveData() {
        // 从Repository获取最新数据
        transactionList.setValue(repository.getAllTransactions());
        totalAmount.setValue(repository.getTotalExpense());
        recordCount.setValue(repository.getTransactionCount());
    }
    
    // 获取指定位置的交易
    public Transaction getTransactionAt(int position) {
        return repository.getTransactionAt(position);
            }

    // 更新交易信息
    public void updateTransaction(int position, Transaction newTransaction) {
        // 先删除旧记录
        repository.deleteTransaction(position);
        // 添加新记录
        repository.addTransaction(newTransaction);
        updateLiveData();
        }
    }
