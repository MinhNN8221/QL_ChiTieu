package com.example.ql_chitieu.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ql_chitieu.R;
import com.example.ql_chitieu.adapter.RecyclerViewAdapter;
import com.example.ql_chitieu.dao.SanPhamDao;
import com.example.ql_chitieu.database.AppDatabase;
import com.example.ql_chitieu.entity.SanPham;
import com.example.ql_chitieu.entity.Userr;

import java.util.List;

public class FragmentHistory extends Fragment {
    private TextView txtTong, txtTongThang;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private Spinner spinner;
    private RecyclerViewAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtTong=view.findViewById(R.id.txtTong);
        txtTongThang=view.findViewById(R.id.txtTongThang);
        searchView=view.findViewById(R.id.search);
        spinner=view.findViewById(R.id.spinner);
        recyclerView=view.findViewById(R.id.reclerView);
        adapter=new RecyclerViewAdapter();

        String array[]=spinner.getResources().getStringArray(R.array.category);
        String arrays[]=new String[array.length+1];
        arrays[0]="Tất cả";
        for(int i=0; i<array.length; i++){
            arrays[i+1]=array[i];
        }
        spinner.setAdapter(new ArrayAdapter<>(getContext(), R.layout.item_spinner, arrays));

        AppDatabase db=AppDatabase.getInstance(getContext());
        SanPhamDao sanPhamDao=db.sanPhamDao();
        List<SanPham> list=sanPhamDao.getAll(Userr.id);
        adapter.setList(list);

        LinearLayoutManager manager=new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);

        txtTong.setText("Tong so tien da chi "+tong(list));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<SanPham> sanPhams=sanPhamDao.getByName(newText, Userr.id);
                adapter.setList(sanPhams);
                return true;
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<SanPham> sanPhams;
                String loaiSP=spinner.getItemAtPosition(position).toString();
                if(loaiSP.equalsIgnoreCase("tất cả")){
                    sanPhams=sanPhamDao.getAll(Userr.id);
                }else{
                    sanPhams=sanPhamDao.getByCategory(loaiSP, Userr.id);
                }
                adapter.setList(sanPhams);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private double tong(List<SanPham> list){
        double sum=0;
        for(SanPham i:list){
            sum+=i.getGia();
        }
        return sum;
    }

    @Override
    public void onResume() {
        super.onResume();
        AppDatabase db=AppDatabase.getInstance(getContext());
        SanPhamDao sanPhamDao=db.sanPhamDao();
        List<SanPham> list=sanPhamDao.getAll(Userr.id);
        adapter.setList(list);
    }
}
