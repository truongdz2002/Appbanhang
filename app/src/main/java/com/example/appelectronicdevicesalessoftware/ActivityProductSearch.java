package com.example.appelectronicdevicesalessoftware;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.mbms.MbmsErrors;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.TextView;
import Adapter.Adapter_load_fragment_product_search;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ActivityProductSearch extends AppCompatActivity {
private TextView tv_search_product;
private String TextSearch;
private int Uid;
private int MoneyUser;
private Adapter_load_fragment_product_search adapter_load_fragment_product_search;
private TabLayout tabLayout_product_search;
private ViewPager2 vp_product_search;
private ImageButton btn_back_product_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search);
        Mapping();
        Initialization();
        GetDataIntent();
        SetOnClick();
        LoadTablayout();

    }
    //Ánh xạ
    private void Mapping()
    {
        tv_search_product=findViewById(R.id.tv_search_product);
        btn_back_product_search=findViewById(R.id.btn_back_product_search);
        tabLayout_product_search=findViewById(R.id.tablayout_product_search);
        vp_product_search=findViewById(R.id.vp_product_search);
    }
    //Khởi tạo biến
    private void Initialization()
    {
        adapter_load_fragment_product_search=new Adapter_load_fragment_product_search(this);
        vp_product_search.setAdapter(adapter_load_fragment_product_search);
    }

    public int getMoneyUser() {
        return MoneyUser;
    }

    public void setMoneyUser(int moneyUser) {
        MoneyUser = moneyUser;
    }
   //Bắt sự hiện button
    private void SetOnClick()
    {
        btn_back_product_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              finish();
            }
        });
        tv_search_product.setText(getTextSearch());
        tv_search_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str=tv_search_product.getText().toString().trim();
                Intent intent=new Intent(ActivityProductSearch.this,ActivityHistorySearch.class);
                intent.putExtra("Uid",getUid());
                intent.putExtra("KeySearch",getTextSearch());
                startActivity(intent);
                finish();
            }
        });
    }
    //Nhận dữ liệu từ intent
    private void GetDataIntent()
    {
        String str_textsearch=getIntent().getStringExtra("KeySearch").toString().trim();
        setTextSearch(str_textsearch);
        int uid=getIntent().getIntExtra("Uid",1);
        setUid(uid);

    }
    //Set dữ liệu ui
    private void LoadTablayout()
    {
        new TabLayoutMediator(tabLayout_product_search, vp_product_search, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
              switch (position)
              {
                  case 0:
                      tab.setText("Relate to");
                      break;
                  case 1:
                      tab.setText("Latest");
                      break;
                  case 2:
                      tab.setText("Celling");
                      break;
                  case 3:
                      tab.setText("Price");
                      break;

              }
            };
        }).attach();

    }

    public String getTextSearch() {
        return TextSearch;
    }

    public void setTextSearch(String textSearch) {
        TextSearch = textSearch;
    }

    public int getUid() {
        return Uid;
    }

    public void setUid(int uid) {
        Uid = uid;
    }


}