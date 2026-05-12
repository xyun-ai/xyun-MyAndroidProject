package com.example.class_two;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.class_two.adapter.TransactionAdapter;
import com.example.class_two.model.Transaction;
import com.example.class_two.repository.TransactionRepository;
import com.example.class_two.utils.NotificationHelper;
import com.example.class_two.receiver.ExpenseUpdateReceiver;
import com.example.class_two.utils.BroadcastConstants;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ExpenseTracker";
    private static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 100;

    private TextView tvTotalAmount;
    private TextView tvRecordCount;
    private TextView tvEmptyHint;
    private Button btnAddExpense;
    private Button btnViewRecords;
    private Button btnSendNotification;
    private Button btnTestNotifications;
    private ListView lvExpenseRecords;

    private List<Transaction> transactionList;
    private TransactionAdapter transactionAdapter;
    private double totalAmount = 0.0;

    private NotificationHelper notificationHelper;
    private TransactionRepository transactionRepository;
    private ExpenseUpdateReceiver expenseUpdateReceiver;

    private Button testFeaturesBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            Log.e(TAG, "应用崩溃:", throwable);
            throwable.printStackTrace();
        });

        setContentView(R.layout.activity_main);
        transactionList = new ArrayList<>();
        transactionRepository = TransactionRepository.getInstance();

        initViews();

        transactionAdapter = new TransactionAdapter(this, R.layout.transaction_item, transactionList);
        lvExpenseRecords.setAdapter(transactionAdapter);

        notificationHelper = new NotificationHelper(this);
        handleNotificationIntent(getIntent());

        setupButtonListeners();
        registerBroadcastReceiver();
        loadExpenseData();
    }

    private void registerBroadcastReceiver() {
        expenseUpdateReceiver = new ExpenseUpdateReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadcastConstants.ACTION_EXPENSE_UPDATED);
        filter.addAction(BroadcastConstants.ACTION_BUDGET_CHANGED);
        filter.addAction(BroadcastConstants.ACTION_CATEGORY_UPDATED);
        registerReceiver(expenseUpdateReceiver, filter, Context.RECEIVER_EXPORTED);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleNotificationIntent(intent);
    }

    private void handleNotificationIntent(Intent intent) {
        if (intent != null && intent.hasExtra("source")) {
            String type = intent.getStringExtra("notification_type");
            if (type != null) {
                switch (type) {
                    case "expense_reminder":
                        Toast.makeText(this, "来自记账提醒", Toast.LENGTH_SHORT).show();
                        break;
                    case "daily_summary":
                        Toast.makeText(this, "来自每日总结", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }
    }

    private boolean checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                    == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                    NOTIFICATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void setupButtonListeners() {
        btnAddExpense.setOnClickListener(v -> showAddTransactionDialog());
        btnViewRecords.setOnClickListener(v -> startActivity(new Intent(this, RecordListActivity.class)));
        btnSendNotification.setOnClickListener(v -> {
            if (checkNotificationPermission()) {
                notificationHelper.sendExpenseReminderNotification();
                Toast.makeText(this, "通知已发送", Toast.LENGTH_SHORT).show();
            } else {
                requestNotificationPermission();
            }
        });
        btnTestNotifications.setOnClickListener(v -> startActivity(new Intent(this, NotificationTestActivity.class)));

        testFeaturesBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FeaturesTestActivity.class);
            startActivity(intent);
        });
        if (testFeaturesBtn != null) {
            testFeaturesBtn.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, FeaturesTestActivity.class);
                startActivity(intent);
            });
        }
    }

    private void initViews() {
        tvTotalAmount = findViewById(R.id.tv_total_amount);
        tvRecordCount = findViewById(R.id.tv_record_count);
        tvEmptyHint = findViewById(R.id.tv_empty_hint);
        btnAddExpense = findViewById(R.id.btn_add_expense);
        btnViewRecords = findViewById(R.id.btn_view_records);
        btnSendNotification = findViewById(R.id.btn_send_notification);
        btnTestNotifications = findViewById(R.id.btn_test_notifications);
        lvExpenseRecords = findViewById(R.id.lv_expense_records);
        testFeaturesBtn = findViewById(R.id.test_features_btn);
        testFeaturesBtn = findViewById(R.id.test_features_btn);
    }

    private void loadExpenseData() {
        transactionList.clear();
        transactionList.addAll(transactionRepository.getAllTransactions());
        updateUI();
    }

    private void updateUI() {
        totalAmount = transactionRepository.getTotalExpense();
        tvTotalAmount.setText(String.format("¥%.2f", totalAmount));
        int count = transactionList.size();
        tvRecordCount.setText("共 " + count + " 条");

        if (count == 0) {
            tvEmptyHint.setVisibility(View.VISIBLE);
            lvExpenseRecords.setVisibility(View.GONE);
        } else {
            tvEmptyHint.setVisibility(View.GONE);
            lvExpenseRecords.setVisibility(View.VISIBLE);
        }
        transactionAdapter.notifyDataSetChanged();
    }

    private void showAddTransactionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_add_transaction, null);
        builder.setView(view);

        EditText etAmount = view.findViewById(R.id.et_amount);
        Spinner spCategory = view.findViewById(R.id.sp_category);
        EditText etNote = view.findViewById(R.id.et_note);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.expense_categories, android.R.layout.simple_spinner_item);
        spCategory.setAdapter(adapter);

        builder.setTitle("添加记录");
        builder.setPositiveButton("保存", (dialog, which) -> {
            try {
                double amount = Double.parseDouble(etAmount.getText().toString());
                String category = spCategory.getSelectedItem().toString();
                String note = etNote.getText().toString();
                transactionRepository.addTransaction(new Transaction(amount, category, note));
                loadExpenseData();
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "输入无效", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (expenseUpdateReceiver != null) {
            unregisterReceiver(expenseUpdateReceiver);
            expenseUpdateReceiver = null;
        }
    }
}