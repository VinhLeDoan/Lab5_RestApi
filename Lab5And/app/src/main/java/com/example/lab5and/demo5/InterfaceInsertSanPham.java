package com.example.lab5and.demo5;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface InterfaceInsertSanPham {
    @FormUrlEncoded
    @POST("insert2.php")
    Call<SvrResponseSanPham> insertSanPham(
            @Field("MaSP") String MaSP,
            @Field("TenSP") String TenSP,
            @Field("MoTa") String MoTa
    );

}
