package com.example.class_two.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.example.class_two.R;

/**
 * 账目监控服务
 * 教学重点：Service的基础使用，后台任务执行
 * 软件工程思想：前后台职责分离，异步处理
 */
public class ExpenseMonitorService extends Service {

    private static final String TAG = "ExpenseMonitorService";
    private static final String CHANNEL_ID = "expense_monitor_channel";
    private static final String CHANNEL_NAME = "账目监控服务";
    private static final int NOTIFICATION_ID = 1004;

    // 服务状态
    public static final String ACTION_START_MONITORING = "com.example.class_two.START_MONITORING";
    public static final String ACTION_STOP_MONITORING = "com.example.class_two.STOP_MONITORING";
    public static final String ACTION_UPDATE_STATUS = "com.example.class_two.UPDATE_STATUS";

    // 广播Action
    public static final String BROADCAST_SERVICE_STATUS = "com.example.class_two.SERVICE_STATUS";
    public static final String EXTRA_SERVICE_STATUS = "service_status";
    public static final String EXTRA_MESSAGE = "message";

    // 状态常量
    public static final int STATUS_STARTED = 1;
    public static final int STATUS_STOPPED = 0;
    public static final int STATUS_PROCESSING = 2;
    public static final int STATUS_COMPLETED = 3;

    private Handler handler;
    private Runnable monitoringTask;
    private boolean isMonitoring = false;
    private int taskCount = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Service onCreate");

        handler = new Handler();
        createNotificationChannel();

        // 创建前台服务通知
        Notification notification = createForegroundNotification("账目监控服务已启动", "正在监控账目数据...");
        startForeground(NOTIFICATION_ID, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Service onStartCommand, startId: " + startId);

        if (intent != null) {
            String action = intent.getAction();
            if (action != null) {
                switch (action) {
                    case ACTION_START_MONITORING:
                        startMonitoring();
                        break;
                    case ACTION_STOP_MONITORING:
                        stopMonitoring();
                        break;
                    case ACTION_UPDATE_STATUS:
                        // 更新状态
                        String message = intent.getStringExtra("message");
                        updateNotification(message);
                        break;
                }
            }
        }

        // 如果服务被杀死，不自动重启
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // 这是一个Started Service，不需要绑定
        return null;
    }

    /**
     * 开始监控任务
     */
    private void startMonitoring() {
        if (isMonitoring) {
            Log.d(TAG, "监控任务已经在运行");
            return;
        }

        isMonitoring = true;
        taskCount = 0;

        Log.d(TAG, "开始账目监控任务");
        sendStatusBroadcast(STATUS_STARTED, "账目监控服务已启动");

        // 创建监控任务
        monitoringTask = new Runnable() {
            @Override
            public void run() {
                if (!isMonitoring) {
                    return;
                }

                taskCount++;
                Log.d(TAG, "执行第 " + taskCount + " 次监控任务");

                // 模拟不同的后台任务
                switch (taskCount % 4) {
                    case 0:
                        simulateCheckDailyExpenses();
                        break;
                    case 1:
                        simulateOrganizeTransactions();
                        break;
                    case 2:
                        simulateCheckBudget();
                        break;
                    case 3:
                        simulateGenerateReminders();
                        break;
                }

                // 更新通知
                String message = String.format("已执行 %d 次监控任务", taskCount);
                updateNotification(message);

                // 发送状态更新广播
                sendStatusBroadcast(STATUS_PROCESSING, message);

                // 10秒后再次执行
                if (isMonitoring) {
                    handler.postDelayed(this, 10000); // 10秒间隔
                }
            }
        };

        // 立即开始执行
        handler.post(monitoringTask);
    }

    /**
     * 停止监控任务
     */
    private void stopMonitoring() {
        if (!isMonitoring) {
            Log.d(TAG, "监控任务未运行");
            return;
        }

        isMonitoring = false;

        if (monitoringTask != null) {
            handler.removeCallbacks(monitoringTask);
            monitoringTask = null;
        }

        Log.d(TAG, "账目监控任务已停止，共执行 " + taskCount + " 次任务");
        sendStatusBroadcast(STATUS_STOPPED, "账目监控服务已停止");

        // 停止前台服务
        stopForeground(true);
        stopSelf();
    }

    /**
     * 模拟检查当日消费
     */
    private void simulateCheckDailyExpenses() {
        Log.i(TAG, "模拟任务：检查当日消费情况");
        // 这里可以添加实际的业务逻辑
        // 例如：查询数据库，计算当日总消费等
    }

    /**
     * 模拟整理交易记录
     */
    private void simulateOrganizeTransactions() {
        Log.i(TAG, "模拟任务：整理交易记录");
        // 这里可以添加实际的业务逻辑
        // 例如：分类整理交易，生成统计报告等
    }

    /**
     * 模拟检查预算
     */
    private void simulateCheckBudget() {
        Log.i(TAG, "模拟任务：检查预算使用情况");
        // 这里可以添加实际的业务逻辑
        // 例如：计算预算使用率，判断是否需要提醒等
    }

    /**
     * 模拟生成提醒
     */
    private void simulateGenerateReminders() {
        Log.i(TAG, "模拟任务：生成记账提醒");
        // 这里可以添加实际的业务逻辑
        // 例如：根据消费习惯生成个性化提醒
    }

    /**
     * 创建通知渠道（Android 8.0+ 需要）
     */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_LOW
            );
            channel.setDescription("账目监控服务的通知渠道");

            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    /**
     * 创建前台服务通知
     */
    private Notification createForegroundNotification(String title, String content) {
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOngoing(true) // 持续显示
                .build();
    }

    /**
     * 更新通知
     */
    private void updateNotification(String message) {
        Notification notification = createForegroundNotification("账目监控服务运行中", message);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.notify(NOTIFICATION_ID, notification);
        }
    }

    /**
     * 发送状态广播
     */
    private void sendStatusBroadcast(int status, String message) {
        Intent broadcastIntent = new Intent(BROADCAST_SERVICE_STATUS);
        broadcastIntent.putExtra(EXTRA_SERVICE_STATUS, status);
        broadcastIntent.putExtra(EXTRA_MESSAGE, message);
        sendBroadcast(broadcastIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Service onDestroy");

        if (isMonitoring) {
            stopMonitoring();
        }

        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}