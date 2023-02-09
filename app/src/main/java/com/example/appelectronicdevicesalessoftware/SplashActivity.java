package com.example.appelectronicdevicesalessoftware;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import DatabaseUserOffline.DataBaseUserOffline;
import Login.ActivityLogin;
import Object.SaveAccountUserOffline;
import Object.User;
import Retrofit.ApiUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    private List<SaveAccountUserOffline> listUsersOffline;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        //Sau 3s sẽ chuyển activity
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Chuyển activity
                NextActivity();

            }
        }, 3000);
    }

    private void NextActivity() {
        //Khởi tạo một list lấy data trong roomdatabase
        listUsersOffline = new ArrayList<>();
        //Get list roomdatabase
        listUsersOffline = DataBaseUserOffline.getInstance(SplashActivity.this).userOffline().GetListSavePhone();
        //Vì  trong roomdatabase chỉ có 1 tài khoản đăng nhập duy nhất nên tự động login sẽ vào aminActivity
        //Còn nếu database ko có tài khoản nào sẽ chuyển tới màn hình login
        if (listUsersOffline.size() == 0) {
            Intent intent = new Intent(SplashActivity.this, ActivityLogin.class);
            startActivity(intent);
            finish();
        } else {
            //Lấy dữ liệu của tài khoản
            GetDataUserDatabase();

        }


    }
    private void GetDataUserDatabase()
    {
//Sử dụng retrofit call Api để nhận biết sever có hoạt động hay ko
        ApiUser.apiUser.GetAccountUsers().enqueue(new Callback<List<User>>() {
            @Override
            //Hoạt động thì sẽ gửi các dữ liệu cần thiết đi
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                for (SaveAccountUserOffline saveAccountUserOffline : listUsersOffline) {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    intent.putExtra("UidLogin", saveAccountUserOffline.getUid());
                    intent.putExtra("UserName",saveAccountUserOffline.getUserName());
                    Toast.makeText(SplashActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finishAffinity();
                }
            }

            @Override
            //Thất bại sẽ trả lại dòng dữ liệu cho người dùng biết ko đăng nhập được do nguyên nhân gì
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(SplashActivity.this, "Lỗi sever đang sửa chữa", Toast.LENGTH_SHORT).show();

            }
        });
    }


}

