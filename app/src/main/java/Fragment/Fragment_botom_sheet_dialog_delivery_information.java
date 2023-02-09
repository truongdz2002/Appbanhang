package Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appelectronicdevicesalessoftware.ActivityDetailBill;
import com.example.appelectronicdevicesalessoftware.ActivityProductDetail;
import com.example.appelectronicdevicesalessoftware.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

import Retrofit.ApiBillPayment;
import Utilities.ProcessDialogCustom;

public class Fragment_botom_sheet_dialog_delivery_information extends BottomSheetDialogFragment {
private BottomSheetDialog bottom_Sheet_Dialog_Fragment_Delivery_Information;
private View viewdialog;
private ProcessDialogCustom processDialogCustom;
private ActivityProductDetail activityProductDetail;
private String Address,FullName,Telephone;
private String  DateOrder;
private EditText edt_Address_delivery_information,edt_TelephoneNumber_delivery_information,edt_FullName_delivery_information;
private Button btn_Confirm_delivery_information;
private ImageButton btn_Close;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
       bottom_Sheet_Dialog_Fragment_Delivery_Information=(BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        viewdialog= LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_fragment_dialog_delivery_information,null);
         bottom_Sheet_Dialog_Fragment_Delivery_Information.setContentView(viewdialog);
         Mapping();
         Init();
         SetdataUi();
         SetOnClick();
        BottomSheetBehavior behavior=BottomSheetBehavior.from((View) viewdialog.getParent());
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
       return bottom_Sheet_Dialog_Fragment_Delivery_Information;
    }
    //Ánh xạ
    private void Mapping()
    {
        btn_Close=viewdialog.findViewById(R.id.btn_Closs_delivery_information);
        edt_Address_delivery_information=viewdialog.findViewById(R.id.edt_Address_delivery_information);
        edt_FullName_delivery_information=viewdialog.findViewById(R.id.edt_FullName_delivery_information);
        edt_TelephoneNumber_delivery_information=viewdialog.findViewById(R.id.edt_TelephoneNumber_delivery_information);
        btn_Confirm_delivery_information=viewdialog.findViewById(R.id.btn_Confirm_delivery_information);
    }
    //Hiển thị dữ liệu ui
    private void SetdataUi()
    {
        edt_Address_delivery_information.setText(activityProductDetail.getAddress());
        edt_FullName_delivery_information.setText(activityProductDetail.getFullName());
        edt_TelephoneNumber_delivery_information.setText(activityProductDetail.getTelephoneNumber());
    }
    //Lấy dữ liệu từ ui
    private void GetdataUi()
    {
        Address=edt_Address_delivery_information.getText().toString().trim();
        FullName=edt_FullName_delivery_information.getText().toString().trim();
        Date date =new Date();
        DateOrder=date.toString();
        Telephone=edt_TelephoneNumber_delivery_information.getText().toString().trim();

    }
    //Khỏi tạo biến
    private void Init()
    {
        activityProductDetail=(ActivityProductDetail) getActivity();
        processDialogCustom=new ProcessDialogCustom(activityProductDetail);


    }
    //Bắt sự kiện button
    private void SetOnClick()
    {
        //Các nhận thông tin giao hàng
        btn_Confirm_delivery_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String patern="yyyy-MM-dd HH:mm:ss";
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat(patern);
                String date=simpleDateFormat.format(new Date());
                activityProductDetail.Oderdate= date;
                activityProductDetail.setAddress(edt_Address_delivery_information.getText().toString().trim());
                activityProductDetail.setFullName(edt_FullName_delivery_information.getText().toString().trim());
                activityProductDetail.setTelephoneNumber(edt_TelephoneNumber_delivery_information.getText().toString().trim());
                activityProductDetail.Show_Bottom_Sheet_Bill_To_Payment();

            }
        });
        //Đóng bottom sheet
        btn_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityProductDetail.DismissBottom_Sheet_delivery_information();
            }
        });
    }

}
