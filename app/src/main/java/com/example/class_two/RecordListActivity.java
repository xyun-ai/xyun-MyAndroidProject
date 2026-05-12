package com.example.class_two;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.class_two.adapter.RecordAdapter;
import com.example.class_two.model.RecordDataProvider;
import com.example.class_two.model.RecordItem;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 记账记录列表页面
 * 教学重点：RecyclerView的循环复用机制
 * 
 * 本页面演示：
 * 1. RecyclerView的基本使用
 * 2. ViewHolder的创建和复用
 * 3. Adapter的数据绑定
 * 4. LinearLayoutManager的布局管理
 */
public class RecordListActivity extends AppCompatActivity {
    
    private RecyclerView recyclerView;
    private RecordAdapter adapter;
    private TextView tvTotalIncome;
    private TextView tvTotalExpense;
    private TextView tvNetIncome;
    private TextView tvRecordCount;
    
    private final String tag = "RecordListActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);
        
        Log.d(tag, "RecordListActivity创建");
        
        // 初始化视图
        initViews();
        
        // 设置RecyclerView
        setupRecyclerView();
        
        // 加载数据
        loadData();
        
        // 更新统计信息
        updateStatistics();
        
        Log.d(tag, "页面初始化完成");
    }
    
    /**
     * 初始化视图
     */
    private void initViews() {
        recyclerView = findViewById(R.id.rv_records);
        tvTotalIncome = findViewById(R.id.tv_total_income);
        tvTotalExpense = findViewById(R.id.tv_total_expense);
        tvNetIncome = findViewById(R.id.tv_net_income);
        tvRecordCount = findViewById(R.id.tv_record_count);
        
        Log.d(tag, "视图初始化完成");
    }
    
    /**
     * 设置RecyclerView
     * 这是教学的核心部分
     */
    private void setupRecyclerView() {
        // 1. 创建适配器
        adapter = new RecordAdapter();
        
        // 2. 设置布局管理器（LinearLayoutManager是最简单的）
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        
        // 3. 设置适配器
        recyclerView.setAdapter(adapter);
        
        // 4. 添加滚动监听器（用于教学演示）
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                
                // 获取第一个和最后一个可见项的位置
                int firstVisible = layoutManager.findFirstVisibleItemPosition();
                int lastVisible = layoutManager.findLastVisibleItemPosition();
                
                Log.d(tag, "滚动中... 可见项: " + firstVisible + " - " + lastVisible);
                Log.d(tag, "ViewHolder复用情况: RecyclerView会复用离开屏幕的ViewHolder");
            }
        });
        
        Log.d(tag, "RecyclerView设置完成");
        Log.d(tag, "教学提示：RecyclerView会创建有限数量的ViewHolder并复用它们");
        Log.d(tag, "当列表项滚动离开屏幕时，其ViewHolder会被回收并用于新的列表项");
    }
    
    /**
     * 加载数据
     */
    private void loadData() {
        // 从数据提供者获取示例数据
        List<RecordItem> records = RecordDataProvider.getSampleData();
        
        // 更新适配器数据
        adapter.updateData(records);
        
        // 更新记录数量显示
        tvRecordCount.setText(records.size() + "条记录");
        
        Log.d(tag, "数据加载完成，共" + records.size() + "条记录");
        
        // 教学演示：打印ViewHolder复用信息
        printRecyclerViewInfo();
    }
    
    /**
     * 更新统计信息
     */
    private void updateStatistics() {
        List<RecordItem> records = RecordDataProvider.getSampleData();
        
        double totalIncome = RecordDataProvider.getTotalIncome(records);
        double totalExpense = RecordDataProvider.getTotalExpense(records);
        double netIncome = RecordDataProvider.getNetIncome(records);
        
        tvTotalIncome.setText(String.format("¥%.2f", totalIncome));
        tvTotalExpense.setText(String.format("¥%.2f", totalExpense));
        tvNetIncome.setText(String.format("¥%.2f", netIncome));
        
        Log.d(tag, "统计信息更新: 收入=¥" + totalIncome + ", 支出=¥" + totalExpense + ", 净收入=¥" + netIncome);
    }
    
    /**
     * 打印RecyclerView信息（用于教学）
     */
    private void printRecyclerViewInfo() {
        Log.d(tag, "=== RecyclerView教学信息 ===");
        Log.d(tag, "1. RecyclerView创建时会创建有限数量的ViewHolder");
        Log.d(tag, "2. 当列表滚动时，离开屏幕的ViewHolder会被回收");
        Log.d(tag, "3. 新进入屏幕的列表项会复用回收的ViewHolder");
        Log.d(tag, "4. 这避免了频繁创建和销毁View，提高了性能");
        Log.d(tag, "5. onBindViewHolder()方法用于将数据绑定到复用的ViewHolder");
        Log.d(tag, "=========================");
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(tag, "页面恢复，可以观察ViewHolder的复用情况");
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(tag, "页面暂停");
    }
}