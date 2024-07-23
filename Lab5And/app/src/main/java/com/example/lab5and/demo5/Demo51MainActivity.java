package com.example.lab5and.demo5;

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
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Demo51MainActivity extends AppCompatActivity {

    EditText txt1, txt2, txt3;
    TextView tvKQ;
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo51_main);
        txt1=findViewById(R.id.demo51Txt1);
        txt2=findViewById(R.id.demo51Txt2);
        txt3=findViewById(R.id.demo51Txt3);
        tvKQ=findViewById(R.id.demo51TvKQ);
        btn1=findViewById(R.id.demo51Btn1);
        btn1.setOnClickListener(v->{
            insertData(txt1, txt2, txt3, tvKQ);
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
}