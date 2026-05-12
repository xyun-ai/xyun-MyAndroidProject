package com.example.class_two.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.example.class_two.R;
import com.example.class_two.model.RecordItem;
import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView适配器
 * 负责创建ViewHolder和绑定数据
 * 这是RecyclerView循环复用的核心组件
 */
public class RecordAdapter extends RecyclerView.Adapter<RecordViewHolder> {
    
    private List<RecordItem> records;
    
    public RecordAdapter() {
        this.records = new ArrayList<>();
    }
    
    public RecordAdapter(List<RecordItem> records) {
        this.records = records != null ? new ArrayList<>(records) : new ArrayList<>();
    }
    
    /**
     * 创建ViewHolder
     * 当RecyclerView需要新的ViewHolder时调用
     * 这是循环复用的起点
     */
    @Override
    public RecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 加载列表项布局
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_record, parent, false);
        
        // 创建并返回ViewHolder
        return new RecordViewHolder(view);
    }
    
    /**
     * 绑定数据到ViewHolder
     * 当RecyclerView需要显示数据时调用
     * 这里实现了真正的循环复用
     */
    @Override
    public void onBindViewHolder(RecordViewHolder holder, int position) {
        // 获取当前位置的数据
        RecordItem record = records.get(position);
        
        // 绑定数据到ViewHolder
        holder.bind(record);
        
        // 教学注释：这里演示了ViewHolder的复用
        // 当列表滚动时，离开屏幕的ViewHolder会被复用
        // 而不是创建新的ViewHolder，这提高了性能
    }
    
    /**
     * 获取数据项数量
     */
    @Override
    public int getItemCount() {
        return records.size();
    }
    
    /**
     * 更新数据
     * 当数据变化时调用此方法更新列表
     */
    public void updateData(List<RecordItem> newRecords) {
        // 教学注释：这里可以优化为使用DiffUtil
        // 但对于教学示例，简单替换数据即可
        this.records = newRecords != null ? new ArrayList<>(newRecords) : new ArrayList<>();
        notifyDataSetChanged();
    }
    
    /**
     * 获取指定位置的数据
     */
    public RecordItem getItem(int position) {
        return records.get(position);
    }
    
    /**
     * 添加新记录
     */
    public void addRecord(RecordItem record) {
        List<RecordItem> newList = new ArrayList<>(records);
        newList.add(0, record);  // 添加到开头
        records = newList;
        notifyItemInserted(0);
    }
    
    /**
     * 删除记录
     */
    public void removeRecord(int position) {
        List<RecordItem> newList = new ArrayList<>(records);
        newList.remove(position);
        records = newList;
        notifyItemRemoved(position);
    }
}