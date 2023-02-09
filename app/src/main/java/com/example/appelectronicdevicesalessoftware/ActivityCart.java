package com.example.appelectronicdevicesalessoftware;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.telephony.mbms.MbmsErrors;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import Object.Product_add_in_cart_of_user;
import Object.Money_user;
import Object.Personal_Information;


import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import Adapter.Adapter_load_tablayout_in_cart;
import Retrofit.ApiInformation;
import Retrofit.ApiMoney_User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityCart extends AppCompatActivity {
private Adapter_load_tablayout_in_cart adapter_load_tablayout_in_cart;
private TabLayout tabLayout_cart;
private ViewPager2 vp_cart;
private ImageView btn_back_cart;
private CheckBox ck_all_product_in_cart;
private int Total_price;
private int Uid;
private int Monney_User;
private String Username;
private String NumberPhone;
private String Address;
private boolean Delivery=false;
private boolean Choose;
private TextView  tv_TotalMoney,tv_Buy_Product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Mapping();
        Initialization();
        getdataIntent();
        GetMoneyAccountUser();
        GetDataInformationUser();
        LoadTabLayout();
        SetOnClick();


    }
    //Ánh xạ
    private void Mapping()
    {
        tv_Buy_Product=findViewById(R.id.tv_Buy_product);
        tabLayout_cart=findViewById(R.id.tablaout_cart);
        vp_cart=findViewById(R.id.vp_cart);
        btn_back_cart=findViewById(R.id.btn_back_cart);
        ck_all_product_in_cart=findViewById(R.id.ck_Purchase_all_product_in_cart);
        tv_TotalMoney=findViewById(R.id.tv_Total_monney);
    }
    //Nhân dữ liệu số tiền trong tài khoản người dùng
   private void GetMoneyAccountUser()
   {
       ApiMoney_User.api_money_user.Get_Money_Account_User().enqueue(new Callback<List<Money_user>>() {
           @Override
           public void onResponse(Call<List<Money_user>> call, Response<List<Money_user>> response) {
               List<Money_user> l=new ArrayList<>();
               l=response.body();
               for(Money_user money_user:l)
               {
                   if(money_user.getUid()==getUid())
                   {
                       setMonney_User(money_user.getMoney());
                   }
               }
           }

           @Override
           public void onFailure(Call<List<Money_user>> call, Throwable t) {

           }
       });
   }
     //Nhận dữ liệu từ intent
    private void getdataIntent()
    {
        int uid=getIntent().getIntExtra("Uid",1);
        setUid(uid);


    }
    //Khởi tạo cho các biến
    private void Initialization()
    {
        adapter_load_tablayout_in_cart=new Adapter_load_tablayout_in_cart(this);
        vp_cart.setAdapter(adapter_load_tablayout_in_cart);
    }
    //Set dữ liệu cho tablayout
    private void LoadTabLayout()
    {
        new TabLayoutMediator(tabLayout_cart, vp_cart, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position)
                {
                    case 0:
                        tab.setText("Tất cả");
                        break;
                    case 1:
                        tab.setText("Giảm giá");
                        break;
                }
            }
        }).attach();
    }
    //Bắt sự kiện cho các button
    private void SetOnClick()
    {

        btn_back_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_Buy_Product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetMoneyAccountUser();
            }
        });


    }
    //Lấy thông tin cá nhân của người dung từ api
    private void GetDataInformationUser()
    {
        ApiInformation.apiInfo.GetPersonalInformation().enqueue(new Callback<List<Personal_Information>>() {
            @Override
            public void onResponse(Call<List<Personal_Information>> call, Response<List<Personal_Information>> response) {
                List<Personal_Information> l=new ArrayList<>();
                l=response.body();
                if(l==null)
                {
                    return;
                }
                for(Personal_Information personal_information : l)
                {
                    if(personal_information.getUid_Login()==getUid())
                    {
                        setAddress(personal_information.getAddress());
                        setUsername(personal_information.getFullName());
                        setNumberPhone(personal_information.getTelephone_Number());
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Personal_Information>> call, Throwable t) {

            }
        });
    }


            public int getUid() {
        return Uid;
    }

    public void setUid(int uid) {
        Uid = uid;
    }

    public boolean isChoose() {
        return Choose;
    }

    public void setChoose(boolean choose) {
        Choose = choose;
    }

    public int getTotal_price() {
        return Total_price;
    }

    public void setTotal_price(int total_price) {
        Total_price = total_price;
    }

    public TextView getTv_TotalMoney() {
        return tv_TotalMoney;
    }

    public CheckBox getCk_all_product_in_cart() {
        return ck_all_product_in_cart;
    }

    public void setCk_all_product_in_cart(CheckBox ck_all_product_in_cart) {
        this.ck_all_product_in_cart = ck_all_product_in_cart;
    }

    public void setTv_TotalMoney(TextView tv_TotalMoney) {
        this.tv_TotalMoney = tv_TotalMoney;
    }

    public TextView getTv_Buy_Product() {
        return tv_Buy_Product;
    }

    public int getMonney_User() {
        return Monney_User;
    }

    public void setMonney_User(int monney_User) {
        Monney_User = monney_User;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getNumberPhone() {
        return NumberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        NumberPhone = numberPhone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public boolean isDelivery() {
        return Delivery;
    }

    public void setDelivery(boolean delivery) {
        Delivery = delivery;
    }


}