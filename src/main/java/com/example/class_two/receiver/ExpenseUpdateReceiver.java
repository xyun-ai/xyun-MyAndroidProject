package com.example.class_two.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import android.util.Log;

import com.example.class_two.utils.BroadcastConstants;

/**
 * 账目更新广播接收器
 * 教学重点：BroadcastReceiver的使用
 * 软件工程思想：观察者模式，实现组件间的松耦合通信
 */
public class ExpenseUpdateReceiver extends BroadcastReceiver {

    private static final String TAG = "ExpenseUpdateReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (action != null) {
            switch (action) {
                case BroadcastConstants.ACTION_EXPENSE_UPDATED:
                    handleExpenseUpdate(context, intent);
                    break;

                case BroadcastConstants.ACTION_BUDGET_CHANGED:
                    handleBudgetChange(context, intent);
                    break;

                case BroadcastConstants.ACTION_CATEGORY_UPDATED:
                    handleCategoryUpdate(context, intent);
                    break;

                default:
                    Log.d(TAG, "收到未知广播: " + action);
            }
        }
    }

    /**
     * 处理账目更新广播
     */
    private void handleExpenseUpdate(Context context, Intent intent) {
        String updateType = intent.getStringExtra(BroadcastConstants.EXTRA_UPDATE_TYPE);
        double amount = intent.getDoubleExtra(BroadcastConstants.EXTRA_EXPENSE_AMOUNT, 0.0);
        String category = intent.getStringExtra(BroadcastConstants.EXTRA_EXPENSE_CATEGORY);
        long timestamp = intent.getLongExtra(BroadcastConstants.EXTRA_TIMESTAMP, System.currentTimeMillis());

        String message;
        switch (updateType) {
            case BroadcastConstants.UPDATE_TYPE_ADD:
                message = String.format("新增账目：¥%.2f (%s)", amount, category);
                break;
            case BroadcastConstants.UPDATE_TYPE_EDIT:
                message = String.format("修改账目：¥%.2f (%s)", amount, category);
                break;
            case BroadcastConstants.UPDATE_TYPE_DELETE:
                message = "删除账目";
                break;
            default:
                message = "账目已更新";
        }

        // 显示Toast通知
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();

        // 记录日志
        Log.i(TAG, "处理账目更新广播: " + message);

        // 这里可以添加更多逻辑，例如：
        // 1. 更新UI界面
        // 2. 刷新数据
        // 3. 发送其他广播
        // 4. 启动服务等
    }

    /**
     * 处理预算变更广播
     */
    private void handleBudgetChange(Context context, Intent intent) {
        String message = "预算设置已更新";
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        Log.i(TAG, message);
    }

    /**
     * 处理分类更新广播
     */
    private void handleCategoryUpdate(Context context, Intent intent) {
        String message = "分类信息已更新";
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        Log.i(TAG, message);
    }
}