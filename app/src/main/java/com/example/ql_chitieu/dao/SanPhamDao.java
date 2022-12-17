package com.example.ql_chitieu.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ql_chitieu.entity.SanPham;

import java.util.List;
@Dao
public interface SanPhamDao {
    @Query("Select * from sanpham where idUser= :id")
    List<SanPham> getAll(int id);
    @Query("Select * from sanpham where ngaymua= :date and idUser= :id")
    List<SanPham> getBydate(String date, int id);
    @Insert
    void insert(SanPham ... sanPhams);
    @Update
    void update(SanPham ... sanPhams);
    @Delete
    void delete(SanPham sanPham);
    @Query("Select * from sanpham where tensp= :tenSP and idUser= :id")
    List<SanPham> getByName(String tenSP, int id);
    @Query("Select * from sanpham where loaisp= :loaiSP and idUser= :id")
    List<SanPham> getByCategory(String loaiSP, int id);
}
