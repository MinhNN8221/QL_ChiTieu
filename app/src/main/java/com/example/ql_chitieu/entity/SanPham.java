package com.example.ql_chitieu.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity(tableName = "sanpham")
public class SanPham implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo
    private String tensp;
    @ColumnInfo
    private String loaisp;
    @ColumnInfo
    private double gia;
    @ColumnInfo
    private String ngaymua;
    @ColumnInfo
    private int idUser;

    public SanPham() {
    }

    public SanPham(int idUser, String tensp, String loaisp, double gia, String ngaymua) {
        this.idUser=idUser;
        this.tensp = tensp;
        this.loaisp = loaisp;
        this.gia = gia;
        this.ngaymua = ngaymua;
    }

    public SanPham(int id, int idUser, String tensp, String loaisp, double gia, String ngaymua) {
        this.id = id;
        this.idUser=idUser;
        this.tensp = tensp;
        this.loaisp = loaisp;
        this.gia = gia;
        this.ngaymua = ngaymua;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getLoaisp() {
        return loaisp;
    }

    public void setLoaisp(String loaisp) {
        this.loaisp = loaisp;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public String getNgaymua() {
        return ngaymua;
    }

    public void setNgaymua(String ngaymua) {
        this.ngaymua = ngaymua;
    }
}
