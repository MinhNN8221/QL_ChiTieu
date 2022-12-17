package com.example.ql_chitieu.adapter;

import com.example.ql_chitieu.R;
import com.example.ql_chitieu.entity.SanPham;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<SanPham> list;
    private ItemListener itemListener;

    public ItemListener getItemListener() {
        return itemListener;
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public RecyclerViewAdapter() {
        list=new ArrayList<>();
    }
    public SanPham getSanPham(int position){
        return list.get(position);
    }
    public void setList(List<SanPham> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SanPham sanPham=list.get(position);
        holder.txtName.setText(sanPham.getTensp());
        holder.txtCategory.setText(sanPham.getLoaisp());
        holder.txtPrice.setText(sanPham.getGia()+"");
        holder.txtDate.setText(sanPham.getNgaymua());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtName, txtCategory, txtPrice, txtDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName=itemView.findViewById(R.id.txtName);
            txtCategory=itemView.findViewById(R.id.txtCategory);
            txtPrice=itemView.findViewById(R.id.txtPrice);
            txtDate=itemView.findViewById(R.id.txtDate);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(itemListener!=null){
                itemListener.OnItemClick(v, getAdapterPosition());
            }
        }
    }
}
