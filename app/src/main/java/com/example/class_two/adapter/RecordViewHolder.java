package com.example.class_two.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.class_two.R;
import com.example.class_two.model.RecordItem;
import com.example.class_two.model.RecordType;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * RecyclerView的ViewHolder
 * 负责绑定列表项视图和数据
 * 这是RecyclerView循环复用的关键组件
 */
public class RecordViewHolder extends RecyclerView.ViewHolder {
    
    // 视图引用
    private final ImageView ivIcon;
    private final TextView tvTitle;
    private final TextView tvAmount;
    private final TextView tvDate;
    private final TextView tvType;
    
    // 日期格式化器
    private final SimpleDateFormat dateFormatter;
    
    public RecordViewHolder(View itemView) {
        super(itemView);
        
        // 初始化视图引用
        ivIcon = itemView.findViewById(R.id.iv_icon);
        tvTitle = itemView.findViewById(R.id.tv_title);
        tvAmount = itemView.findViewById(R.id.tv_amount);
        tvDate = itemView.findViewById(R.id.tv_date);
        tvType = itemView.findViewById(R.id.tv_type);
        
        // 初始化日期格式化器
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    }
    
    /**
     * 绑定数据到视图
     * 这是ViewHolder的核心方法，用于显示数据
     */
    public void bind(RecordItem record) {
        try {
            // 设置图标
            try {
                ivIcon.setImageResource(record.getIconResId());
            } catch (Exception e) {
                // 如果图标资源不存在，使用默认图标
                ivIcon.setImageResource(android.R.drawable.ic_menu_info_details);
            }

            // 设置标题
        tvTitle.setText(record.getTitle());
        
        // 设置金额（收入为绿色，支出为红色）
        String amountText = String.format("¥%.2f", record.getAmount());
        tvAmount.setText(amountText);
        
        if (record.getType() == RecordType.INCOME) {
            tvAmount.setTextColor(itemView.getContext().getResources().getColor(android.R.color.holo_green_dark));
            tvType.setText("收入");
            tvType.setBackgroundColor(itemView.getContext().getResources().getColor(android.R.color.holo_green_light));
            tvType.setTextColor(itemView.getContext().getResources().getColor(android.R.color.white));
        } else {
            tvAmount.setTextColor(itemView.getContext().getResources().getColor(android.R.color.holo_red_dark));
            tvType.setText("支出");
            tvType.setBackgroundColor(itemView.getContext().getResources().getColor(android.R.color.holo_red_light));
            tvType.setTextColor(itemView.getContext().getResources().getColor(android.R.color.white));
        }
        
        // 设置日期
        tvDate.setText(dateFormatter.format(record.getDate()));
        
        // 设置点击事件（可选，用于教学演示）
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 这里可以添加点击事件处理
                // 例如：显示详细信息或执行其他操作
            }
        });

        } catch (Exception e) {
            // 记录错误但不崩溃
            e.printStackTrace();
    }
}
}
