package com.example.ql_chitieu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Insert;

import com.example.ql_chitieu.R;
import com.example.ql_chitieu.UpdateActivity;
import com.example.ql_chitieu.adapter.ItemListener;
import com.example.ql_chitieu.adapter.RecyclerViewAdapter;
import com.example.ql_chitieu.dao.SanPhamDao;
import com.example.ql_chitieu.database.AppDatabase;
import com.example.ql_chitieu.entity.SanPham;
import com.example.ql_chitieu.entity.User;
import com.example.ql_chitieu.entity.Userr;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class FragmentHome extends Fragment implements ItemListener {
    private TextView txtTong;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtTong=view.findViewById(R.id.txtTongNgay);
        recyclerView=view.findViewById(R.id.reclerView);
        adapter=new RecyclerViewAdapter();

        Calendar calendar=Calendar.getInstance();
        String date=new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime());
        AppDatabase db=AppDatabase.getInstance(getContext());
        SanPhamDao sanPhamDao=db.sanPhamDao();
        List<SanPham> list=sanPhamDao.getBydate(date, Userr.id);
        adapter.setList(list);
        adapter.setItemListener(this);
        LinearLayoutManager manager=new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        txtTong.setText("Tong so tien da chi trong ngay "+tongTien(list));
    }

    @Override
    public void OnItemClick(View view, int position) {
        SanPham sanPham=adapter.getSanPham(position);
        Intent intent=new Intent(getContext(), UpdateActivity.class);
        intent.putExtra("sanpham", sanPham);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        AppDatabase db=AppDatabase.getInstance(getContext());
        SanPhamDao sanPhamDao=db.sanPhamDao();
        Calendar calendar=Calendar.getInstance();
        String date=new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime());
        List<SanPham> list=sanPhamDao.getBydate(date, Userr.id);
        adapter.setList(list);
    }

    private double tongTien(List<SanPham> list){
        double sum=0;
        for(SanPham i:list){
            sum+=i.getGia();
        }
        return sum;
    }
}
