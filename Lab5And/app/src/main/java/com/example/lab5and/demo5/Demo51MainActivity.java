package com.example.lab5and.demo5;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lab5and.R;
import com.example.lab5and.demo5.Delete.InterfaceDelete;
import com.example.lab5and.demo5.select.InterfaceSelect;
import com.example.lab5and.demo5.select.SvrResponseSelect;
import com.example.lab5and.demo5.update.InterfaceUpdate;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Demo51MainActivity extends AppCompatActivity {

    EditText txt1, txt2, txt3;
    TextView tvKQ;
    Button btn1, btnSelect, btnDelete, btnUpdate;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo51_main);
        txt1=findViewById(R.id.demo51Txt1);
        txt2=findViewById(R.id.demo51Txt2);
        txt3=findViewById(R.id.demo51Txt3);
        tvKQ=findViewById(R.id.demo51TvKQ);
        btn1=findViewById(R.id.Demo51btn1);
        btn1.setOnClickListener(v->{
            insertData(txt1, txt2, txt3, tvKQ);
        });
        btnSelect=findViewById(R.id.Demo51btnSelect);
        btnSelect.setOnClickListener(v->{
            selectData();
        });
        btnDelete=findViewById(R.id.Demo51btnDelete);
        btnDelete.setOnClickListener(v->{
            deletetData(txt1);
        });
        btnUpdate=findViewById(R.id.Demo51btnUpdate);
        btnUpdate.setOnClickListener(v -> {
            updateData(txt1, txt2, txt3, tvKQ);
        });
    }

    private void insertData(EditText txt1, EditText txt2, EditText txt3, TextView tvKQ){
        // tao doi tuong chua dl
        SanPham s= new SanPham(
                txt1.getText().toString(),
                txt2.getText().toString(),
                txt3.getText().toString());
        //tao doi tuong retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.1/000/2024071/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //goi ham insert
        InterfaceInsertSanPham insertSanPham = retrofit.create(InterfaceInsertSanPham.class);

        //chuan bi ham
        Call<SvrResponseSanPham> call = insertSanPham.insertSanPham(s.getMaSP(), s.getTenSP(), s.getMoTa());

        //thuc thi ham
        call.enqueue(new Callback<SvrResponseSanPham>() {
            @Override
            public void onResponse(Call<SvrResponseSanPham> call, Response<SvrResponseSanPham> response) {
                SvrResponseSanPham res = response.body();
                tvKQ.setText(res.getMessage());
            }

            @Override
            public void onFailure(Call<SvrResponseSanPham> call, Throwable t) {
                tvKQ.setText(t.getMessage());
            }
        });
    }
    private void updateData(EditText txt1, EditText txt2, EditText txt3, TextView tvKQ){
        // tao doi tuong chua dl
        SanPham s= new SanPham(
                txt1.getText().toString(),
                txt2.getText().toString(),
                txt3.getText().toString());
        //tao doi tuong retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.1/000/2024071/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //goi ham insert
        InterfaceUpdate  updateSanPham= retrofit.create(InterfaceUpdate.class);

        //chuan bi ham
        Call<SvrResponseSanPham> call = updateSanPham.updateSanPham(s.getMaSP(), s.getTenSP(), s.getMoTa());

        //thuc thi ham
        call.enqueue(new Callback<SvrResponseSanPham>() {
            @Override
            public void onResponse(Call<SvrResponseSanPham> call, Response<SvrResponseSanPham> response) {
                SvrResponseSanPham res = response.body();
                tvKQ.setText(res.getMessage());
            }

            @Override
            public void onFailure(Call<SvrResponseSanPham> call, Throwable t) {
                tvKQ.setText(t.getMessage());
            }
        });
    }
    private void deletetData(EditText txt1){

        //tao doi tuong retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.1/000/2024071/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //goi ham insert
        InterfaceDelete interfaceDelete = retrofit.create(InterfaceDelete.class);

        //chuan bi ham
        Call<SvrResponseSanPham> call = interfaceDelete.deleteSanPham(txt1.getText().toString());

        //thuc thi ham
        call.enqueue(new Callback<SvrResponseSanPham>() {
            @Override
            public void onResponse(Call<SvrResponseSanPham> call, Response<SvrResponseSanPham> response) {
                SvrResponseSanPham res = response.body();
                tvKQ.setText(res.getMessage());
            }

            @Override
            public void onFailure(Call<SvrResponseSanPham> call, Throwable t) {
                tvKQ.setText(t.getMessage());
            }
        });
    }
    String strKQ="";
    List<SanPham> ls;
    private void selectData(){
        strKQ="";
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("http://192.168.1.1/000/2024071/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        InterfaceSelect interfaceSelect = retrofit.create(InterfaceSelect.class);
        Call<SvrResponseSelect> call = interfaceSelect.selectSanPham();
        call.enqueue(new Callback<SvrResponseSelect>() {
            @Override
            public void onResponse(Call<SvrResponseSelect> call, Response<SvrResponseSelect> response) {
                SvrResponseSelect res = response.body();
                ls=new ArrayList<>(Arrays.asList(res.getProducts()));
                for (SanPham p: ls){
                    strKQ+="MaSP: "+p.getMaSP()+"; TenSP: "+p.getTenSP()+"; MoTa: "+p.getMoTa()+"\n";

                }
                tvKQ.setText(strKQ);
            }

            @Override
            public void onFailure(Call<SvrResponseSelect> call, Throwable throwable) {
                tvKQ.setText(throwable.getMessage());
            }
        });
    }
}