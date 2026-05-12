package com.example.class_two.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * 记账数据契约类
 * 定义ContentProvider相关的常量
 * 软件工程思想：集中管理数据契约，提高代码可维护性
 */
public class ExpenseContract {

    // ContentProvider的Authority
    public static final String AUTHORITY = "com.example.class_two.expenseprovider";

    // 基础URI
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // 路径常量
    public static final String PATH_EXPENSES = "expenses";
    public static final String PATH_EXPENSES_ID = "expenses/#";

    /**
     * 账目表定义
     */
    public static final class ExpenseEntry implements BaseColumns {

        // 表名
        public static final String TABLE_NAME = "expenses";

        // 完整URI
        public static final Uri CONTENT_URI =
                Uri.withAppendedPath(BASE_CONTENT_URI, PATH_EXPENSES);

        // MIME类型
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + PATH_EXPENSES;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd." + AUTHORITY + "." + PATH_EXPENSES;

        // 列名
        public static final String COLUMN_ID = BaseColumns._ID;
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMN_CATEGORY = "category";
        public static final String COLUMN_NOTE = "note";
        public static final String COLUMN_TIMESTAMP = "timestamp";
        public static final String COLUMN_CREATED_AT = "created_at";

        // 默认排序
        public static final String DEFAULT_SORT_ORDER = COLUMN_CREATED_AT + " DESC";

        // 创建表的SQL语句
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_AMOUNT + " REAL NOT NULL," +
                        COLUMN_CATEGORY + " TEXT NOT NULL," +
                        COLUMN_NOTE + " TEXT," +
                        COLUMN_TIMESTAMP + " INTEGER," +
                        COLUMN_CREATED_AT + " INTEGER DEFAULT (strftime('%s','now'))" +
                        ");";

        // 删除表的SQL语句
        public static final String SQL_DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    // 私有构造函数，防止实例化
    private ExpenseContract() {
        throw new IllegalStateException("Contract class");
    }
}