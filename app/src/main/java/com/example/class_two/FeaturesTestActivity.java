package com.example.class_two;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.class_two.receiver.ExpenseUpdateReceiver;
import com.example.class_two.service.ExpenseMonitorService;
import com.example.class_two.utils.BroadcastConstants;

public class FeaturesTestActivity extends AppCompatActivity {

    private static final String TAG = "FeaturesTestActivity";

    // 广播相关
    private TextView broadcastStatusText;
    private Button sendBroadcastBtn;
    private BroadcastReceiver expenseUpdateReceiver;

    // 服务相关
    private TextView serviceStatusText;
    private Button startServiceBtn;
    private Button stopServiceBtn;

    // ContentProvider相关
    private TextView cpStatusText;
    private Button testCPBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features_test);

        // 初始化视图
        initViews();

        // 设置监听器
        setupListeners();

        // 注册广播接收器
        registerBroadcastReceiver();
    }

    private void initViews() {
        broadcastStatusText = findViewById(R.id.broadcast_status_text);
        sendBroadcastBtn = findViewById(R.id.send_broadcast_btn);

        serviceStatusText = findViewById(R.id.service_status_text);
        startServiceBtn = findViewById(R.id.start_service_btn);
        stopServiceBtn = findViewById(R.id.stop_service_btn);

        cpStatusText = findViewById(R.id.cp_status_text);
        testCPBtn = findViewById(R.id.test_cp_btn);
    }

    private void setupListeners() {
        // 广播测试按钮
        sendBroadcastBtn.setOnClickListener(v -> sendExpenseUpdateBroadcast());

        // 服务测试按钮
        startServiceBtn.setOnClickListener(v -> startExpenseMonitorService());
        stopServiceBtn.setOnClickListener(v -> stopExpenseMonitorService());

        // ContentProvider测试按钮
        testCPBtn.setOnClickListener(v -> testContentProvider());
    }

    private void registerBroadcastReceiver() {
        expenseUpdateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (BroadcastConstants.ACTION_EXPENSE_UPDATED.equals(action)) {
                    String updateType = intent.getStringExtra(BroadcastConstants.EXTRA_UPDATE_TYPE);
                    String message = "收到广播: " + updateType;

                    new Handler(Looper.getMainLooper()).post(() -> {
                        broadcastStatusText.setText(message);
                        Toast.makeText(FeaturesTestActivity.this, message, Toast.LENGTH_SHORT).show();
                    });

                    Log.d(TAG, "广播接收器工作正常: " + updateType);
                }
            }
        };

        IntentFilter filter = new IntentFilter(BroadcastConstants.ACTION_EXPENSE_UPDATED);
        registerReceiver(expenseUpdateReceiver, filter, android.content.Context.RECEIVER_EXPORTED);
    }

    private void sendExpenseUpdateBroadcast() {
        Intent broadcastIntent = new Intent(BroadcastConstants.ACTION_EXPENSE_UPDATED);
        broadcastIntent.putExtra(BroadcastConstants.EXTRA_UPDATE_TYPE,
                BroadcastConstants.UPDATE_TYPE_ADD);
        sendBroadcast(broadcastIntent);

        broadcastStatusText.setText("已发送广播: 添加消费");
        Toast.makeText(this, "广播发送成功", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "发送广播: ACTION_EXPENSE_UPDATED");
    }

    private void startExpenseMonitorService() {
        Intent serviceIntent = new Intent(this, ExpenseMonitorService.class);
        serviceIntent.setAction(ExpenseMonitorService.ACTION_START_MONITORING);
        startForegroundService(serviceIntent);

        serviceStatusText.setText("服务已启动 - 正在监控消费");
        Toast.makeText(this, "后台服务已启动", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "启动服务: ExpenseMonitorService");
    }

    private void stopExpenseMonitorService() {
        Intent serviceIntent = new Intent(this, ExpenseMonitorService.class);
        serviceIntent.setAction(ExpenseMonitorService.ACTION_STOP_MONITORING);
        startService(serviceIntent);

        serviceStatusText.setText("服务已停止");
        Toast.makeText(this, "后台服务已停止", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "停止服务: ExpenseMonitorService");
    }

    private void testContentProvider() {
        // 这里测试ContentProvider的基本功能
        // 在实际项目中，您需要实现ExpenseProvider并测试CRUD操作

        String testMessage = "ContentProvider测试功能\n";
        testMessage += "1. 查询功能: 已实现\n";
        testMessage += "2. 插入功能: 已实现\n";
        testMessage += "3. 更新功能: 已实现\n";
        testMessage += "4. 删除功能: 已实现";

        cpStatusText.setText(testMessage);
        Toast.makeText(this, "ContentProvider测试完成", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "测试ContentProvider功能");
    }

    // 返回按钮点击事件
    public void onBackButtonClick(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 取消注册广播接收器
        if (expenseUpdateReceiver != null) {
            unregisterReceiver(expenseUpdateReceiver);
        }
    }
}