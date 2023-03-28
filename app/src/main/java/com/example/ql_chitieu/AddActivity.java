package com.example.ql_chitieu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ql_chitieu.dao.SanPhamDao;
import com.example.ql_chitieu.database.AppDatabase;
import com.example.ql_chitieu.entity.SanPham;
import com.example.ql_chitieu.entity.Userr;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {
    private EditText edtTenSp, edtGiaSp, edtNgaymua;
    private Spinner spinner;
    private ImageView clearButton;
    private Button btnAdd, btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        edtTenSp=findViewById(R.id.txtTenSP);
        edtGiaSp=findViewById(R.id.txtGia);
        edtNgaymua=findViewById(R.id.txtNgaymua);
        btnAdd=findViewById(R.id.btnAdd);
        btnCancel=findViewById(R.id.btnCancel);
        spinner=findViewById(R.id.spinner);
        clearButton=findViewById(R.id.clearButton);

        //nút xóa bên phải
        edtTenSp.addTextChangedListener(this);
        edtTenSp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // Nếu EditText được chọn, hiển thị nút xóa
                if (!hasFocus || edtTenSp.getText().toString().isEmpty()) {
                    clearButton.setVisibility(View.INVISIBLE);
                } else {
                    clearButton.setVisibility(View.VISIBLE);
                }
            }
        });
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtTenSp.setText("");
            }
        });

        spinner.setAdapter(new ArrayAdapter<>(this, R.layout.item_spinner, getResources().getStringArray(R.array.category)));
        btnAdd.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        edtNgaymua.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v==edtNgaymua){
            Calendar calendar=Calendar.getInstance();
            int ngay=calendar.get(Calendar.DAY_OF_MONTH);
            int thang=calendar.get(Calendar.MONTH);
            int nam=calendar.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog=new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
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
        if(v==btnAdd){
            String tenSp="", loaiSp="", ngayMua="";
            Double giaSp=0.0;
            AppDatabase db=AppDatabase.getInstance(getApplicationContext());
            SanPhamDao sanPhamDao=db.sanPhamDao();
            try {
                tenSp=edtTenSp.getText().toString();
                loaiSp=spinner.getSelectedItem().toString();
                giaSp=Double.parseDouble(edtGiaSp.getText().toString());
                ngayMua=edtNgaymua.getText().toString();
            }catch (NumberFormatException e){
                Toast.makeText(this, "Gia san pham la 1 so", Toast.LENGTH_SHORT).show();
            }
            if(!tenSp.isEmpty() && !ngayMua.isEmpty() && giaSp!=0.0){
                SanPham sanPham=new SanPham(Userr.id, tenSp, loaiSp, giaSp, ngayMua);
                sanPhamDao.insert(sanPham);
                Toast.makeText(this, "Them thanh cong", Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(this, "Nhap day du thong tin", Toast.LENGTH_SHORT).show();
            }
        }
        if(v==btnCancel){
            finish();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.toString().equals("")) {
            clearButton.setVisibility(View.INVISIBLE);
        } else {
            clearButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}