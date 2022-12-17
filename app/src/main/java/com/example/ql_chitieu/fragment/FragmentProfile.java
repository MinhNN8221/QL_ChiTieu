package com.example.ql_chitieu.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ql_chitieu.LoginActivity;
import com.example.ql_chitieu.R;
import com.example.ql_chitieu.dao.UserDao;
import com.example.ql_chitieu.database.AppDatabase;
import com.example.ql_chitieu.entity.User;
import com.example.ql_chitieu.entity.Userr;

public class FragmentProfile extends Fragment implements View.OnClickListener{
    private Button btn_update, btn_logout, btn_delete;
    private EditText edtUser, edtpass, edtpassnew;
    private SharedPreferences sharedPreferences;
    private AppDatabase db;
    private UserDao userDao;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        btn_update=view.findViewById(R.id.btnUpdate);
        btn_logout=view.findViewById(R.id.btnLogout);
        edtUser=view.findViewById(R.id.username);
        edtpass=view.findViewById(R.id.password);
        edtpassnew=view.findViewById(R.id.passwordnew);
        btn_delete=view.findViewById(R.id.btnDelete);
        edtUser.setText(Userr.username);

        db=AppDatabase.getInstance(getContext());
        userDao=db.userDao();
        btn_logout.setOnClickListener(this);
        btn_update.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        sharedPreferences= getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        if(v==btn_logout){
            editor.clear();
//            editor.remove("checked");
            editor.commit();
            Intent intent=new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
        if(v==btn_delete){
            User user=new User(Userr.id, Userr.username, Userr.password);
            userDao.delete(user);
            editor.clear();
            editor.commit();
            Toast.makeText(getContext(), "Xoa tai khoan thanh cong", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
        if(v==btn_update){
            User user=new User();
            String pass=edtpass.getText().toString();
            String passnew=edtpassnew.getText().toString();
            String username=edtUser.getText().toString();
            if(!username.isEmpty() && !pass.isEmpty()){
                if(pass.equalsIgnoreCase(Userr.password)){
                    if(!passnew.equalsIgnoreCase(Userr.password)){
                        user.setId(Userr.id);
                        user.setUsername(username);
                        user.setPassword(passnew);
                        userDao.update(user);
                        Userr.password=passnew;
                        Toast.makeText(getContext(), "Cap nhat mat khau thanh cong", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(), "Mật khẩu mới không được trùng với mật khẩu cũ", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getContext(), "Nhập sai mật khẩu cũ", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getContext(), "Dien day du thong tin", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
