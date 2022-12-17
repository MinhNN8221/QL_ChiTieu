package com.example.ql_chitieu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ql_chitieu.dao.SanPhamDao;
import com.example.ql_chitieu.database.AppDatabase;
import com.example.ql_chitieu.entity.SanPham;
import com.example.ql_chitieu.entity.Userr;

import java.util.Calendar;

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText edtTenSp, edtGiaSp, edtNgaymua;
    private Spinner spinner;
    private Button btnUpdate, btnDelete;
    private SanPham sanPham;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        edtTenSp=findViewById(R.id.txtTenSP);
        edtGiaSp=findViewById(R.id.txtGia);
        edtNgaymua=findViewById(R.id.txtNgaymua);
        btnUpdate=findViewById(R.id.btnUpdate);
        btnDelete=findViewById(R.id.btnDelete);
        spinner=findViewById(R.id.spinner);

        Intent intent=getIntent();
        sanPham=(SanPham) intent.getSerializableExtra("sanpham");
        edtTenSp.setText(sanPham.getTensp());
        edtGiaSp.setText(sanPham.getGia()+"");
        edtNgaymua.setText(sanPham.getNgaymua());
        int position=0;
        for(int i=0; i<spinner.getCount(); i++){
            if(spinner.getItemAtPosition(i).equals(sanPham.getLoaisp())){
                position=i;
                break;
            }
        }
        spinner.setSelection(position);

        spinner.setAdapter(new ArrayAdapter<>(this, R.layout.item_spinner, getResources().getStringArray(R.array.category)));
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        edtNgaymua.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        AppDatabase db=AppDatabase.getInstance(getApplicationContext());
        SanPhamDao sanPhamDao=db.sanPhamDao();
        if(v==edtNgaymua){
            Calendar calendar=Calendar.getInstance();
            int ngay=calendar.get(Calendar.DAY_OF_MONTH);
            int thang=calendar.get(Calendar.MONTH);
            int nam=calendar.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog=new DatePickerDialog(UpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String s="";
                    if(dayOfMonth<10){
                        s+="0"+dayOfMonth;
                    }else{
                        s+=dayOfMonth;
                    }
                    s+="/";
                    if(month<10){
                        s+="0"+(1+month);
                    }else{
                        s+=(1+month);
                    }
                    s+="/"+year;
                    edtNgaymua.setText(s);
                }
            }, nam, thang, ngay);
            datePickerDialog.show();
        }
        if(v==btnUpdate){
            String tenSp="", loaiSp="", ngayMua="";
            Double giaSp=0.0;
            try {
                tenSp=edtTenSp.getText().toString();
                loaiSp=spinner.getSelectedItem().toString();
                giaSp=Double.parseDouble(edtGiaSp.getText().toString());
                ngayMua=edtNgaymua.getText().toString();
            }catch (NumberFormatException e){
                Toast.makeText(this, "Giá sản phẩm là 1 số", Toast.LENGTH_SHORT).show();
            }
            if(!tenSp.isEmpty() && !ngayMua.isEmpty() && giaSp!=0.0){
                sanPham.setTensp(tenSp);
                sanPham.setIdUser(Userr.id);
                sanPham.setLoaisp(loaiSp);
                sanPham.setNgaymua(ngayMua);
                sanPham.setGia(giaSp);
                sanPhamDao.update(sanPham);
                Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }
        }
        if(v==btnDelete){
            AlertDialog.Builder alert=new AlertDialog.Builder(v.getContext());
            alert.setTitle("Thông báo xóa!");
            alert.setMessage("Bạn chắc chắn muốn xóa sản phẩm "+sanPham.getTensp().toUpperCase()+" !");
            alert.setIcon(R.drawable.ic_baseline_delete_24);
            alert.setPositiveButton("Đúng", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sanPhamDao.delete(sanPham);
                    finish();
                }
            });
            alert.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alert.show();
        }
    }
}