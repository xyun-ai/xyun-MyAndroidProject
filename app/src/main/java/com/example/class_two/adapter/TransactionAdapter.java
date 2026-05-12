package com.example.class_two.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.class_two.R;
import com.example.class_two.model.Transaction;
import java.util.List;

public class TransactionAdapter extends ArrayAdapter<Transaction> {
    
    private Context context;
    private int resource;
    
    public TransactionAdapter(@NonNull Context context, int resource, @NonNull List<Transaction> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }
    
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }
        
        Transaction transaction = getItem(position);
        
        if (transaction != null) {
            TextView tvAmount = convertView.findViewById(R.id.tv_item_amount);
            TextView tvCategory = convertView.findViewById(R.id.tv_item_category);
            TextView tvNote = convertView.findViewById(R.id.tv_item_note);
            TextView tvDate = convertView.findViewById(R.id.tv_item_date);
            
            tvAmount.setText(String.format("¥%.2f", transaction.getAmount()));
            tvCategory.setText(transaction.getCategory());
            tvNote.setText(transaction.getNote());
            tvDate.setText(transaction.getDate());
            
            // 根据金额设置颜色（红色表示支出）
            if (transaction.getAmount() < 0) {
                tvAmount.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
            } else {
                tvAmount.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));
            }
        }
        
        return convertView;
    }

    // 更新数据的方法
    public void updateData(List<Transaction> newTransactions) {
        clear();
        addAll(newTransactions);
        notifyDataSetChanged();
    }
}
