package com.example.class_two.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 记账数据库辅助类
 * 管理数据库的创建和版本升级
 * 软件工程思想：数据库访问封装，单一职责原则
 */
public class ExpenseDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "ExpenseDatabaseHelper";

    // 数据库信息
    private static final String DATABASE_NAME = "expense_provider.db";
    private static final int DATABASE_VERSION = 1;

    public ExpenseDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "创建数据库表");

        // 创建账目表
        db.execSQL(ExpenseContract.ExpenseEntry.SQL_CREATE_TABLE);

        // 可以在这里插入一些测试数据
        insertSampleData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "升级数据库，从版本 " + oldVersion + " 到 " + newVersion);

        // 简单实现：删除旧表，创建新表
        db.execSQL(ExpenseContract.ExpenseEntry.SQL_DROP_TABLE);
        onCreate(db);
    }

    /**
     * 插入示例数据（用于测试）
     */
    private void insertSampleData(SQLiteDatabase db) {
        Log.d(TAG, "插入示例数据");

        // 示例数据1
        db.execSQL("INSERT INTO " + ExpenseContract.ExpenseEntry.TABLE_NAME +
                " (" + ExpenseContract.ExpenseEntry.COLUMN_AMOUNT + ", " +
                ExpenseContract.ExpenseEntry.COLUMN_CATEGORY + ", " +
                ExpenseContract.ExpenseEntry.COLUMN_NOTE + ", " +
                ExpenseContract.ExpenseEntry.COLUMN_TIMESTAMP + ") " +
                "VALUES (125.50, '餐饮', '午餐', " + System.currentTimeMillis() + ")");

        // 示例数据2
        db.execSQL("INSERT INTO " + ExpenseContract.ExpenseEntry.TABLE_NAME +
                " (" + ExpenseContract.ExpenseEntry.COLUMN_AMOUNT + ", " +
                ExpenseContract.ExpenseEntry.COLUMN_CATEGORY + ", " +
                ExpenseContract.ExpenseEntry.COLUMN_NOTE + ", " +
                ExpenseContract.ExpenseEntry.COLUMN_TIMESTAMP + ") " +
                "VALUES (89.99, '购物', '衣服', " + (System.currentTimeMillis() - 86400000) + ")");

        // 示例数据3
        db.execSQL("INSERT INTO " + ExpenseContract.ExpenseEntry.TABLE_NAME +
                " (" + ExpenseContract.ExpenseEntry.COLUMN_AMOUNT + ", " +
                ExpenseContract.ExpenseEntry.COLUMN_CATEGORY + ", " +
                ExpenseContract.ExpenseEntry.COLUMN_NOTE + ", " +
                ExpenseContract.ExpenseEntry.COLUMN_TIMESTAMP + ") " +
                "VALUES (45.00, '交通', '地铁卡充值', " + (System.currentTimeMillis() - 172800000) + ")");

        Log.d(TAG, "示例数据插入完成");
    }
}