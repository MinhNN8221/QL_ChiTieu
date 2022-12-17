package com.example.ql_chitieu.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ql_chitieu.entity.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("Select * from user where username like:name and password = :pass")
    boolean checkLogin(String name, String pass);
    @Insert
    void insert(User ... users);
    @Query("Select id from user where username= :name")
    int getIdUser(String name);
    @Update
    void update(User ... users);
    @Query("Select * from user")
    List<User> getList();
    @Delete
    void delete(User users);
}
