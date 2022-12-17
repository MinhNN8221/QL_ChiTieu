package com.example.ql_chitieu.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.ql_chitieu.dao.SanPhamDao;
import com.example.ql_chitieu.dao.UserDao;
import com.example.ql_chitieu.entity.SanPham;
import com.example.ql_chitieu.entity.User;

@Database(entities = {User.class, SanPham.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract SanPhamDao sanPhamDao();
    private static AppDatabase INSTANCE;
    public static AppDatabase getInstance(Context context){
        if(INSTANCE==null){
            INSTANCE= Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "ql_chitieu").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}
