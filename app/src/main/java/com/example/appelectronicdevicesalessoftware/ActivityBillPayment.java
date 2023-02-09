package com.example.appelectronicdevicesalessoftware;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Selection;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import Object.BillPayment;

import Adapter.Adapter_BillPayment;
import Retrofit.ApiBillPayment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityBillPayment extends AppCompatActivity {
private RecyclerView rcv_list_bill_detail;
private ImageButton btn_Back_Bill_Payment;
private Adapter_BillPayment adapter_billPayment;
private List<BillPayment> billPaymentList;
private BillPayment billPayment;
private int Uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_payment);
        Mapping();
        GetDataIntent();
        Init();
        OnClick();
        GetListBillDetail();
        SetdataUi();
    }
    ///Ánh xạ
    private void Mapping()
    {
        rcv_list_bill_detail=findViewById(R.id.rcv_list_billdetail);
        btn_Back_Bill_Payment=findViewById(R.id.btn_Back_Bill_Payment);
    }
    //khởi tạo list
    private List<BillPayment> GetOrCreateListBillPayment()
    {
        if(billPaymentList==null) billPaymentList=new ArrayList<>();
        return billPaymentList;
    }
    //Nhận dữ liệu từ Intent
    private void GetDataIntent()
    {
        setUid(getIntent().getIntExtra("Uid",1));
    }
    //Khởi tạo dữ liệu cho các biến
    private void Init()
    {

        adapter_billPayment=new Adapter_BillPayment(this);
        billPayment=new BillPayment();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration1=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rcv_list_bill_detail.setLayoutManager(linearLayoutManager);
        rcv_list_bill_detail.addItemDecoration(dividerItemDecoration1);

    }
    //Nhận dữ liệu từ api
    private void GetListBillDetail()
    {
        ApiBillPayment.apiBillPayment.GetDataBillPayment().enqueue(new Callback<List<BillPayment>>() {
            @Override
            public void onResponse(Call<List<BillPayment>> call, Response<List<BillPayment>> response) {
               List<BillPayment> l=new ArrayList<BillPayment>();
               l=response.body();
                for(BillPayment billPayment:l)
                {
                    if(billPayment.getUid()==getUid())
                    {
                        GetOrCreateListBillPayment().add(billPayment);
                        Collections.sort(GetOrCreateListBillPayment(),billPayment.billPaymentSortCodeBill);
                        adapter_billPayment.Setdata(GetOrCreateListBillPayment());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<BillPayment>> call, Throwable t) {

            }
        });
    }
    //Bắt sự kiện với các nút
    private void OnClick()
    {
        btn_Back_Bill_Payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    //Set dữ liệu cho ui
    private void SetdataUi()
    {

        rcv_list_bill_detail.setAdapter(adapter_billPayment);


    }

    public int getUid() {
        return Uid;
    }

    public void setUid(int uid) {
        Uid = uid;
    }
}