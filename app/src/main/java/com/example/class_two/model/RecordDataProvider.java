package com.example.class_two.model;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据提供者
 * 提供本地假数据用于教学演示
 */
public class RecordDataProvider {
    
    // 图标资源ID（使用更通用的图标）
    private static final int[] iconResIds = {
        android.R.drawable.ic_input_add,          // 添加
        android.R.drawable.ic_delete,             // 删除
        android.R.drawable.ic_menu_edit,          // 编辑
        android.R.drawable.ic_menu_save,          // 保存
        android.R.drawable.ic_menu_share,         // 分享
        android.R.drawable.ic_menu_camera,        // 相机
        android.R.drawable.ic_menu_gallery,       // 画廊
        android.R.drawable.ic_menu_manage,        // 管理
        android.R.drawable.ic_menu_preferences,   // 设置
        android.R.drawable.ic_menu_help           // 帮助
    };
    
    // 标题列表
    private static final String[] titles = {
        "工资收入",
        "餐饮消费",
        "交通费用",
        "购物支出",
        "娱乐消费",
        "医疗费用",
        "教育投资",
        "房租水电",
        "投资收入",
        "其他支出"
    };
    
    // 生成假数据
    public static List<RecordItem> getSampleData() {
        List<RecordItem> records = new ArrayList<>();
        Date now = new Date();
        
        for (int i = 1; i <= 10; i++) {
            boolean isIncome = i % 3 == 0;  // 每3条记录中有一条是收入
            double amount = isIncome ? 
                (500 + i * 100) :  // 收入金额较大
                (10 + i * 5);       // 支出金额较小
            
            // 使用更安全的图标索引
            int iconIndex = (i - 1) % iconResIds.length;

            records.add(new RecordItem(
                i,
                titles[i - 1],
                amount,
                new Date(now.getTime() - (i * 24L * 60 * 60 * 1000)), // 每天一条记录
                isIncome ? RecordType.INCOME : RecordType.EXPENSE,
                iconResIds[iconIndex]
            ));
        }
        
        return records;
    }
    
    // 获取收入总额
    public static double getTotalIncome(List<RecordItem> records) {
        double total = 0;
        for (RecordItem record : records) {
            if (record.getType() == RecordType.INCOME) {
                total += record.getAmount();
            }
        }
        return total;
    }
    
    // 获取支出总额
    public static double getTotalExpense(List<RecordItem> records) {
        double total = 0;
        for (RecordItem record : records) {
            if (record.getType() == RecordType.EXPENSE) {
                total += record.getAmount();
            }
        }
        return total;
    }
    
    // 获取净收入（收入-支出）
    public static double getNetIncome(List<RecordItem> records) {
        return getTotalIncome(records) - getTotalExpense(records);
    }
}