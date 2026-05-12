package com.example.class_two.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 记账ContentProvider
 * 教学重点：ContentProvider的实现，URI匹配，CRUD操作
 * 软件工程思想：数据访问接口层，抽象数据访问细节
 */
public class ExpenseProvider extends ContentProvider {

    private static final String TAG = "ExpenseProvider";

    // URI匹配器
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // URI匹配码
    private static final int EXPENSES = 100;          // 查询所有账目
    private static final int EXPENSES_ID = 101;       // 查询单个账目

    // 初始化URI匹配器
    static {
        // content://com.example.class_two.expenseprovider/expenses
        sUriMatcher.addURI(ExpenseContract.AUTHORITY,
                ExpenseContract.PATH_EXPENSES,
                EXPENSES);

        // content://com.example.class_two.expenseprovider/expenses/#
        sUriMatcher.addURI(ExpenseContract.AUTHORITY,
                ExpenseContract.PATH_EXPENSES_ID,
                EXPENSES_ID);
    }

    // 数据库辅助类
    private ExpenseDatabaseHelper mDbHelper;

    @Override
    public boolean onCreate() {
        Log.d(TAG, "ContentProvider onCreate");

        mDbHelper = new ExpenseDatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        Log.d(TAG, "query() called with URI: " + uri);

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case EXPENSES:
                // 查询所有账目
                cursor = db.query(
                        ExpenseContract.ExpenseEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder != null ? sortOrder : ExpenseContract.ExpenseEntry.DEFAULT_SORT_ORDER
                );
                break;

            case EXPENSES_ID:
                // 查询单个账目
                String id = uri.getLastPathSegment();
                String whereClause = ExpenseContract.ExpenseEntry.COLUMN_ID + " = ?";
                String[] whereArgs = new String[]{id};

                cursor = db.query(
                        ExpenseContract.ExpenseEntry.TABLE_NAME,
                        projection,
                        whereClause,
                        whereArgs,
                        null,
                        null,
                        sortOrder
                );
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        // 设置通知URI，当数据变化时通知观察者
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        Log.d(TAG, "查询返回 " + cursor.getCount() + " 条记录");
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.d(TAG, "getType() called with URI: " + uri);

        int match = sUriMatcher.match(uri);
        switch (match) {
            case EXPENSES:
                return ExpenseContract.ExpenseEntry.CONTENT_TYPE;
            case EXPENSES_ID:
                return ExpenseContract.ExpenseEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.d(TAG, "insert() called with URI: " + uri);

        int match = sUriMatcher.match(uri);
        if (match != EXPENSES) {
            throw new IllegalArgumentException("Insertion not supported for URI: " + uri);
        }

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // 添加创建时间
        if (values != null && !values.containsKey(ExpenseContract.ExpenseEntry.COLUMN_CREATED_AT)) {
            values.put(ExpenseContract.ExpenseEntry.COLUMN_CREATED_AT,
                    System.currentTimeMillis() / 1000);
        }

        long id = db.insert(ExpenseContract.ExpenseEntry.TABLE_NAME, null, values);

        if (id == -1) {
            Log.e(TAG, "插入失败");
            throw new SQLException("Failed to insert row into " + uri);
        }

        Log.d(TAG, "插入成功，ID: " + id);

        // 通知数据变化
        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        Log.d(TAG, "delete() called with URI: " + uri);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int rowsDeleted;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case EXPENSES:
                // 删除多个账目
                rowsDeleted = db.delete(
                        ExpenseContract.ExpenseEntry.TABLE_NAME,
                        selection,
                        selectionArgs
                );
                break;

            case EXPENSES_ID:
                // 删除单个账目
                String id = uri.getLastPathSegment();
                String whereClause = ExpenseContract.ExpenseEntry.COLUMN_ID + " = ?";
                String[] whereArgs = new String[]{id};

                rowsDeleted = db.delete(
                        ExpenseContract.ExpenseEntry.TABLE_NAME,
                        whereClause,
                        whereArgs
                );
                break;

            default:
                throw new IllegalArgumentException("Deletion not supported for URI: " + uri);
        }

        if (rowsDeleted > 0) {
            // 通知数据变化
            getContext().getContentResolver().notifyChange(uri, null);
        }

        Log.d(TAG, "删除 " + rowsDeleted + " 条记录");
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values,
                      @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "update() called with URI: " + uri);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int rowsUpdated;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case EXPENSES:
                // 更新多个账目
                rowsUpdated = db.update(
                        ExpenseContract.ExpenseEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs
                );
                break;

            case EXPENSES_ID:
                // 更新单个账目
                String id = uri.getLastPathSegment();
                String whereClause = ExpenseContract.ExpenseEntry.COLUMN_ID + " = ?";
                String[] whereArgs = new String[]{id};

                rowsUpdated = db.update(
                        ExpenseContract.ExpenseEntry.TABLE_NAME,
                        values,
                        whereClause,
                        whereArgs
                );
                break;

            default:
                throw new IllegalArgumentException("Update not supported for URI: " + uri);
        }

        if (rowsUpdated > 0) {
            // 通知数据变化
            getContext().getContentResolver().notifyChange(uri, null);
        }

        Log.d(TAG, "更新 " + rowsUpdated + " 条记录");
        return rowsUpdated;
    }
}