package com.example.ql_chitieu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ql_chitieu.dao.UserDao;
import com.example.ql_chitieu.database.AppDatabase;
import com.example.ql_chitieu.entity.User;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    private EditText username, passoword, password1;
    Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username=findViewById(R.id.username);
        passoword=findViewById(R.id.password);
        password1=findViewById(R.id.password1);
        btnRegister=findViewById(R.id.btn_register);
        AppDatabase db=AppDatabase.getInstance(getApplicationContext());
        UserDao userDao=db.userDao();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName=username.getText().toString();
                String passWord=passoword.getText().toString();
                String passWord1=password1.getText().toString();
                Intent intent=new Intent();
                int check=0;
                if(!userName.isEmpty() && !passWord.isEmpty() && !passWord1.isEmpty()){
                    if(passWord.equals(passWord1)){
                        User user=new User(userName, passWord);
                        List<User> list=userDao.getList();
                        for(User i: list){
                            if(!i.getUsername().equalsIgnoreCase(userName)){
                               check=1;
                            }
                        }
                        if (check==0){
                            userDao.insert(user);
                            intent.putExtra("user", user);
                            setResult(RESULT_OK, intent);
                            Toast.makeText(RegisterActivity.this, "Them thanh cong", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(RegisterActivity.this, "Ten usrename bi trung", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "Nhap lai mat khau", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(RegisterActivity.this, "Nhap day du thong tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}