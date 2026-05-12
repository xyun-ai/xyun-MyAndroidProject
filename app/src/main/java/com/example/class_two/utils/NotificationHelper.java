package com.example.class_two.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import com.example.class_two.MainActivity;
import com.example.class_two.R;

/**
 * 通知工具类
 * 封装通知创建和发送逻辑
 * 教学重点：NotificationChannel、PendingIntent
 */
public class NotificationHelper {

    private static final String CHANNEL_ID = "expense_reminder_channel";
    private static final String CHANNEL_NAME = "记账提醒";
    private static final String CHANNEL_DESCRIPTION = "记账应用的通知频道";
    private static final int NOTIFICATION_ID = 1001;
    private static final int NOTIFICATION_ID_DAILY = 1002;
    private static final int NOTIFICATION_ID_WEEKLY = 1003;

    private Context context;
    private NotificationManager notificationManager;

    public NotificationHelper(Context context) {
        this.context = context;
        this.notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        // 创建通知渠道（Android 8.0+ 需要）
        createNotificationChannel();
    }

    /**
     * 创建通知渠道
     * Android 8.0 (API 26) 及以上版本需要
     */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription(CHANNEL_DESCRIPTION);
            channel.enableLights(true);
            channel.setLightColor(android.R.color.holo_green_light);
            channel.enableVibration(true);

            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * 发送记账提醒通知
     * 教学重点：PendingIntent的使用
     */
    public void sendExpenseReminderNotification() {
        // 创建PendingIntent，点击通知时跳转到MainActivity
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // 添加额外数据，用于在MainActivity中识别通知来源
        intent.putExtra("source", "notification");
        intent.putExtra("notification_type", "expense_reminder");
        intent.putExtra("timestamp", System.currentTimeMillis());

        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pendingIntent = PendingIntent.getActivity(
                    context,
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );
        } else {
            pendingIntent = PendingIntent.getActivity(
                    context,
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );
        }

        // 构建通知
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification) // 使用现有图标或默认图标
                .setContentTitle("记账提醒")
                .setContentText("今天还没有记录支出，点击立即记账")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent) // 设置点击意图
                .setAutoCancel(true) // 点击后自动取消
                .setWhen(System.currentTimeMillis())
                .addAction(android.R.drawable.ic_menu_view, "立即记账", pendingIntent) // 添加操作按钮
                .build();

        // 发送通知
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    /**
     * 发送每日消费总结通知
     * 教学示例：不同的PendingIntent配置
     */
    public void sendDailySummaryNotification(double totalAmount, int transactionCount) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("source", "notification");
        intent.putExtra("notification_type", "daily_summary");
        intent.putExtra("total_amount", totalAmount);
        intent.putExtra("transaction_count", transactionCount);
        intent.putExtra("show_summary", true); // 标记显示总结

        PendingIntent pendingIntent = createPendingIntent(intent, 1);

        // 构建通知
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("今日消费总结")
                .setContentText(String.format("今日消费 ¥%.2f，共 %d 笔", totalAmount, transactionCount))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(String.format("今日消费详情：\n• 总金额：¥%.2f\n• 交易笔数：%d笔\n• 平均每笔：¥%.2f\n\n点击查看详细记录",
                                totalAmount, transactionCount,
                                transactionCount > 0 ? totalAmount / transactionCount : 0)))
                .build();

        notificationManager.notify(NOTIFICATION_ID_DAILY, notification);
    }

    /**
     * 发送每周预算提醒通知
     * 教学示例：带多个操作按钮的通知
     */
    public void sendWeeklyBudgetNotification(double spent, double budget) {
        Intent mainIntent = new Intent(context, MainActivity.class);
        mainIntent.putExtra("source", "notification");
        mainIntent.putExtra("notification_type", "budget_alert");
        mainIntent.putExtra("show_budget", true);

        // 创建主要PendingIntent
        PendingIntent mainPendingIntent = createPendingIntent(mainIntent, 2);

        // 创建"添加消费"的PendingIntent（带特殊标记）
        Intent addExpenseIntent = new Intent(context, MainActivity.class);
        addExpenseIntent.putExtra("source", "notification");
        addExpenseIntent.putExtra("notification_type", "budget_alert");
        addExpenseIntent.putExtra("action", "add_expense");
        PendingIntent addExpensePendingIntent = createPendingIntent(addExpenseIntent, 3);

        double remaining = budget - spent;
        double percentage = (spent / budget) * 100;

        String budgetStatus;
        if (percentage >= 90) {
            budgetStatus = "⚠️ 预算即将用完！";
        } else if (percentage >= 70) {
            budgetStatus = "⚠️ 预算使用较多";
        } else {
            budgetStatus = "✅ 预算使用正常";
        }

        // 构建通知
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("每周预算提醒")
                .setContentText(String.format("已消费 ¥%.2f / ¥%.2f", spent, budget))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(mainPendingIntent)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .addAction(R.drawable.ic_add, "添加消费", addExpensePendingIntent)
                .addAction(android.R.drawable.ic_menu_view, "查看详情", mainPendingIntent)
                .setStyle(new NotificationCompat.InboxStyle()
                        .addLine(budgetStatus)
                        .addLine(String.format("已消费: ¥%.2f", spent))
                        .addLine(String.format("预算: ¥%.2f", budget))
                        .addLine(String.format("剩余: ¥%.2f", remaining))
                        .addLine(String.format("使用率: %.1f%%", percentage))
                        .setBigContentTitle("每周预算状态")
                        .setSummaryText("点击查看详情"))
                .build();

        notificationManager.notify(NOTIFICATION_ID_WEEKLY, notification);
    }

    /**
     * 创建PendingIntent的辅助方法
     */
    private PendingIntent createPendingIntent(Intent intent, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return PendingIntent.getActivity(
                    context,
                    requestCode,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );
        } else {
            return PendingIntent.getActivity(
                    context,
                    requestCode,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );
        }
    }

    /**
     * 取消所有通知
     */
    public void cancelAllNotifications() {
        notificationManager.cancel(NOTIFICATION_ID);
        notificationManager.cancel(NOTIFICATION_ID_DAILY);
        notificationManager.cancel(NOTIFICATION_ID_WEEKLY);
    }

    /**
     * 取消通知
     */
    public void cancelNotification() {
        notificationManager.cancel(NOTIFICATION_ID);
    }
    /**
     * 获取NotificationManager实例
     */
    public NotificationManager getNotificationManager() {
        return notificationManager;
    }
}
