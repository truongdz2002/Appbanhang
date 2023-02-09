package com.example.appelectronicdevicesalessoftware;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;

import Object.BillPayment;

public class ActivityDetailBill extends AppCompatActivity {
    private ImageButton btn_Back_Bill_Payment;
private TextView tv_CodeBill_detail,tv_name_user_bill_detail,tv_TelephoneNumber_Bill_Detail,tv_AddressUser_bill_detail,tv_name_product_detail,tv_OrderDate_bill_detail,tv_Total_bill_detail,tv_Payment_Method_bill_detail;
private ImageView img_product_bill_detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bill);
        Mapping();
        SetDataUi();
        OnClick();
    }
    //Ánh xạ
    private void Mapping()
    {
      tv_CodeBill_detail=findViewById(R.id.tv_CodeBill_detail);
      tv_AddressUser_bill_detail=findViewById(R.id.tv_AddressUser_bill_detail);
      tv_name_user_bill_detail=findViewById(R.id.tv_name_user_bill_detail);
      tv_OrderDate_bill_detail=findViewById(R.id.tv_OrderDate_bill_detail);
      tv_Payment_Method_bill_detail=findViewById(R.id.tv_Payment_Method_bill_detail);
      tv_name_product_detail=findViewById(R.id.tv_name_product_detail);
      tv_Total_bill_detail=findViewById(R.id.tv_Total_bill_detail);
      tv_TelephoneNumber_Bill_Detail=findViewById(R.id.tv_TelephoneNumber_Bill_Detail);
      img_product_bill_detail=findViewById(R.id.img_product_bill_detail);
        btn_Back_Bill_Payment=findViewById(R.id.btn_Back_Bill_Payment);
    }
    //Set dữ liệu cho giao diện
    private void SetDataUi()
    {
        String Patern="###,###,###";
        DecimalFormat decimalFormat=new DecimalFormat(Patern);
        BillPayment billPayment=(BillPayment) getIntent().getSerializableExtra("BillPayment");
        tv_Total_bill_detail.setText(String.valueOf(decimalFormat.format(billPayment.getTotalMoney()))+"VND");
        tv_TelephoneNumber_Bill_Detail.setText(billPayment.getTelephoneNumber());
        tv_OrderDate_bill_detail.setText(billPayment.getOrderDate());
        tv_Payment_Method_bill_detail.setText(billPayment.getPaymentMethod());
        tv_name_user_bill_detail.setText(billPayment.getUserName());
        tv_AddressUser_bill_detail.setText(billPayment.getAddressUser());
        tv_CodeBill_detail.setText(String.valueOf(billPayment.getId()));
        tv_name_product_detail.setText(billPayment.getProductName());
        Glide.with(this).load(billPayment.getImg_Product()).error(R.drawable.logo).into(img_product_bill_detail);
    }
    //Bắt sự kiện cho button
    private void OnClick()

    {
        btn_Back_Bill_Payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}