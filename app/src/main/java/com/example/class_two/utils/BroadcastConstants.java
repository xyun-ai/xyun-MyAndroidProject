package com.example.class_two.utils;

/**
 * 广播常量类
 * 统一管理广播相关的Action字符串
 * 软件工程思想：集中管理常量，避免硬编码，提高可维护性
 */
public class BroadcastConstants {
    
    // 自定义广播Action
    public static final String ACTION_EXPENSE_UPDATED = "com.example.class_two.EXPENSE_UPDATED";
    public static final String ACTION_BUDGET_CHANGED = "com.example.class_two.BUDGET_CHANGED";
    public static final String ACTION_CATEGORY_UPDATED = "com.example.class_two.CATEGORY_UPDATED";
    
    // 广播Extra键名
    public static final String EXTRA_EXPENSE_ID = "expense_id";
    public static final String EXTRA_EXPENSE_AMOUNT = "expense_amount";
    public static final String EXTRA_EXPENSE_CATEGORY = "expense_category";
    public static final String EXTRA_UPDATE_TYPE = "update_type";
    public static final String EXTRA_TIMESTAMP = "timestamp";
    
    // 更新类型
    public static final String UPDATE_TYPE_ADD = "add";
    public static final String UPDATE_TYPE_EDIT = "edit";
    public static final String UPDATE_TYPE_DELETE = "delete";
    
    // 私有构造函数，防止实例化
    private BroadcastConstants() {
        throw new IllegalStateException("Constants class");
    }
}