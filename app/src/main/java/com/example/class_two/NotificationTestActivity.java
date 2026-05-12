package com.example.class_two;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.class_two.utils.NotificationHelper;

/**
 * 通知测试页面
 * 用于测试不同类型的通知
 */
public class NotificationTestActivity extends AppCompatActivity {

    private NotificationHelper notificationHelper;
    private Button btnTestBasic;
    private Button btnTestDaily;
    private Button btnTestBudget;
    private Button btnClearAll;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_test);

        // 初始化通知助手
        notificationHelper = new NotificationHelper(this);

        // 初始化视图
        initViews();

        // 设置按钮点击事件
        setupButtonListeners();
    }

    private void initViews() {
        btnTestBasic = findViewById(R.id.btn_test_basic);
        btnTestDaily = findViewById(R.id.btn_test_daily);
        btnTestBudget = findViewById(R.id.btn_test_budget);
        btnClearAll = findViewById(R.id.btn_clear_all);
        btnBack = findViewById(R.id.btn_back);
    }

    private void setupButtonListeners() {
        // 测试基础通知
        btnTestBasic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationHelper.sendExpenseReminderNotification();
                Toast.makeText(NotificationTestActivity.this,
                        "基础通知已发送", Toast.LENGTH_SHORT).show();
            }
        });

        // 测试每日总结通知
        btnTestDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationHelper.sendDailySummaryNotification(365.50, 5);
                Toast.makeText(NotificationTestActivity.this,
                        "每日总结通知已发送", Toast.LENGTH_SHORT).show();
            }
        });

        // 测试预算提醒通知
        btnTestBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationHelper.sendWeeklyBudgetNotification(800, 1000);
                Toast.makeText(NotificationTestActivity.this,
                        "预算提醒通知已发送", Toast.LENGTH_SHORT).show();
            }
        });

        // 清除所有通知
        btnClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationHelper.cancelAllNotifications();
                Toast.makeText(NotificationTestActivity.this,
                        "所有通知已清除", Toast.LENGTH_SHORT).show();
            }
        });

        // 返回主页面
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}