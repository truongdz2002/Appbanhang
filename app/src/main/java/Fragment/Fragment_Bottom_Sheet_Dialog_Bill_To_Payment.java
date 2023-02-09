package Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import Object.Const;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appelectronicdevicesalessoftware.ActivityProductDetail;
import com.example.appelectronicdevicesalessoftware.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.DecimalFormat;

import Retrofit.ApiBillPayment;
import Retrofit.ApiNotification;
import Retrofit.ApiUser_buy_product;
import Utilities.ProcessDialogCustom;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment_Bottom_Sheet_Dialog_Bill_To_Payment extends BottomSheetDialogFragment {
private View viewdialog;
private ProcessDialogCustom processDialogCustom;
private ActivityProductDetail activityProductDetail;
private Button btn_paymentOnline_bill_to_payment,btn_paymentOnffline_bill_to_payment;
private BottomSheetDialog bottomSheetDialog;
private TextView tv_Total_bill_to_payment,tv_OrderDate_bill_to_payment,tv_name_product_bill_to_payment,tv_AddressUser_bill_to_payment,tv_TelephoneNumber_bill_to_payment,tv_name_user_bill_to_payment;
private ImageView img_product_bill_to_payment;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
      bottomSheetDialog=(BottomSheetDialog)super.onCreateDialog(savedInstanceState);
      viewdialog=LayoutInflater.from(getContext()).inflate(R.layout.fragment__bottom__sheet__dialog__bill__to__payment,null);
      bottomSheetDialog.setContentView(viewdialog);
      Mapping();
      Init();
      SetDataUi();
      SetControlClick();
        BottomSheetBehavior bottomSheetBehavior=BottomSheetBehavior.from((View) viewdialog.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
      return bottomSheetDialog;
    }
    //Bắt sự kiện button
    private void SetControlClick()
    {


        btn_paymentOnffline_bill_to_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // PostdataBillPayment();
               // PostDataUserBuyProduct();

            }
        });
        //Thanh toán online
        btn_paymentOnline_bill_to_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processDialogCustom.TurnOnProcessDiaLog();
                if(activityProductDetail.getMoney_User()<activityProductDetail.getPrice_Product())
                {
                    Toast.makeText(activityProductDetail,"Tài khoản của bạn không đủ",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    int totalPrice=activityProductDetail.Amount_Product_buy * activityProductDetail.getPrice_Product();
                    int moneyuser=activityProductDetail.getMoney_User()-totalPrice;
                    activityProductDetail.UpdateMoney_User(moneyuser);
                    PostdataBillPayment(totalPrice);

                }

            }
        });
    }
    //Thêm hoa đơn thanh toán vào database
    private void PostdataBillPayment(int totalPrice)
    {
        ApiBillPayment.apiBillPayment.PostDataBillPayment(activityProductDetail.getUid(),activityProductDetail.getFullName(),activityProductDetail.getAddress(),totalPrice,activityProductDetail.getTelephoneNumber(),activityProductDetail.Oderdate,btn_paymentOnline_bill_to_payment.getText().toString().trim(),activityProductDetail.getImg_Product(),activityProductDetail.getName_Product()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                PostDataNotificationBuyProduct( totalPrice);


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    //Thêm thông báo mua hàng đã thanh toán
    private void PostDataNotificationBuyProduct(int totalPrice)
    {
        String message=Const.Message_postnotification+activityProductDetail.getName_Product()+" với giá "+String.valueOf(totalPrice)+"VND";
        ApiNotification.apiNotification.PostdataNotification(activityProductDetail.getUid(),message,Const.NOTSEEN,Const.Title_postNotification,Const.SENT,activityProductDetail.Oderdate).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                PostDataUserBuyProduct( totalPrice);
                activityProductDetail.pushNotification.SendNotification_1(Const.Title_postNotification,message);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    //Thêm sản phẩm đã mua củ người dùng vào danh sách mua hàng
    private void PostDataUserBuyProduct(int totalPrice)
    {
        ApiUser_buy_product.api_user_buy_product.AddUserBuyProduct(activityProductDetail.getUid(),activityProductDetail.getFullName(),activityProductDetail.getAddress(),activityProductDetail.getTelephoneNumber(),activityProductDetail.getName_Product(),totalPrice,Const.UnDilivery).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                processDialogCustom.TurnOffProgressDialog();
                int amount=activityProductDetail.getQuantity_in_stock()- activityProductDetail.getAmount_Product_buy();
                activityProductDetail.updateProduct.UpdateProduct(activityProductDetail.getiCodeProduct(),amount,activityProductDetail.getColor_Product());
                Toast.makeText(activityProductDetail,"Thanh toán thành công",Toast.LENGTH_SHORT).show();
                activityProductDetail.finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    //Ánh xạ
    private void Mapping()
    {
        btn_paymentOnffline_bill_to_payment=viewdialog.findViewById(R.id.btn_paymentOnffline_bill_to_payment);
        btn_paymentOnline_bill_to_payment=viewdialog.findViewById(R.id.btn_paymentOnline_bill_to_payment);
        tv_AddressUser_bill_to_payment=viewdialog.findViewById(R.id.tv_AddressUser_bill_to_payment);
        tv_Total_bill_to_payment=viewdialog.findViewById(R.id.tv_Total_bill_to_payment);
        tv_name_user_bill_to_payment=viewdialog.findViewById(R.id.tv_name_user_bill_to_payment);
        tv_OrderDate_bill_to_payment=viewdialog.findViewById(R.id.tv_OrderDate_bill_to_payment);
        img_product_bill_to_payment=viewdialog.findViewById(R.id.img_product_bill_to_payment);
        tv_TelephoneNumber_bill_to_payment=viewdialog.findViewById(R.id.tv_TelephoneNumber_bill_to_payment);
        tv_name_product_bill_to_payment=viewdialog.findViewById(R.id.tv_name_product_bill_to_payment);
    }
    //Khởi tạo biến
    private  void Init()
    {
        activityProductDetail=(ActivityProductDetail) getActivity();
        processDialogCustom=new ProcessDialogCustom(activityProductDetail);

    }
    //Hiển thị dữ liệu lên ui
    private void SetDataUi()
    {
        String patern="###,###,###";
        DecimalFormat decimalFormat=new DecimalFormat(patern);
        tv_name_product_bill_to_payment.setText(activityProductDetail.getName_Product()+"("+""+String.valueOf(activityProductDetail.Amount_Product_buy)+""+"máy)");
        tv_name_user_bill_to_payment.setText(activityProductDetail.getFullName());
        tv_OrderDate_bill_to_payment.setText(activityProductDetail.Oderdate);
        tv_Total_bill_to_payment.setText(String.valueOf(decimalFormat.format(activityProductDetail.getPrice_Product()*activityProductDetail.Amount_Product_buy))+"VND");
        tv_TelephoneNumber_bill_to_payment.setText(activityProductDetail.getTelephoneNumber());
        Glide.with(activityProductDetail).load(activityProductDetail.getImg_Product()).error(R.drawable.logo).into(img_product_bill_to_payment);
        tv_AddressUser_bill_to_payment.setText(activityProductDetail.getAddress());
    }
}