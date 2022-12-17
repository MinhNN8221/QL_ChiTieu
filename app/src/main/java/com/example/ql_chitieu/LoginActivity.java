package com.example.ql_chitieu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ql_chitieu.dao.UserDao;
import com.example.ql_chitieu.database.AppDatabase;
import com.example.ql_chitieu.entity.User;
import com.example.ql_chitieu.entity.Userr;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtUser, edtPass;
    private CheckBox checkBox;
    private Button btnLogin;
    private TextView txt;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtUser=findViewById(R.id.username);
        edtPass=findViewById(R.id.password);
        checkBox=findViewById(R.id.checkbox);
        txt=findViewById(R.id.txtRegister);
        btnLogin=findViewById(R.id.btn_login);
        txt.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        sharedPreferences=getSharedPreferences("data", MODE_PRIVATE);
        edtUser.setText(sharedPreferences.getString("user", ""));
        edtPass.setText(sharedPreferences.getString("pass", ""));
        checkBox.setChecked(sharedPreferences.getBoolean("checked", false));
        AppDatabase db=AppDatabase.getInstance(getApplicationContext());
        UserDao userDao=db.userDao();
        if(sharedPreferences.getString("user", "")!=null){
            if(userDao.checkLogin(edtUser.getText().toString(), edtPass.getText().toString())){
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                User user = new User(edtUser.getText().toString(), edtPass.getText().toString());
                intent.putExtra("user", user);
                Userr.username=edtUser.getText().toString();
                Userr.password=edtPass.getText().toString();
                startActivity(intent);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v==txt){
            Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
            startActivityForResult(intent, 1);
        }
        if(v==btnLogin){
            String username=edtUser.getText().toString();
            String password=edtPass.getText().toString();
            AppDatabase db=AppDatabase.getInstance(getApplicationContext());
            UserDao userDao=db.userDao();
            if(userDao.checkLogin(username, password)) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                User user = new User(username, password);
                Userr.username=username;
                Userr.password=password;
                Userr.id=userDao.getIdUser(username);
                intent.putExtra("user", user);
                startActivity(intent);
                if(checkBox.isChecked()){
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("user", username);
                    editor.putString("pass", password);
                    editor.putBoolean("checked", true);
                    editor.commit();
                }
            }else{
                Toast.makeText(this, "Dang nhap that bai", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==1){
            User user=(User) data.getSerializableExtra("user");
            edtUser.setText(user.getUsername());
            edtPass.setText(user.getPassword());
        }
    }
}