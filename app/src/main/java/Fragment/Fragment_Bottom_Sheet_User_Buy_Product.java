package Fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import Object.user_buy_product;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


import com.example.appelectronicdevicesalessoftware.MainActivity;
import com.example.appelectronicdevicesalessoftware.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.time.chrono.MinguoChronology;
import java.util.ArrayList;
import java.util.List;

import Adapter.Adapter_user_buy_product;
import Retrofit.ApiUser_buy_product;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment_Bottom_Sheet_User_Buy_Product extends BottomSheetDialogFragment {
private View view;
private MainActivity mainActivity;
private BottomSheetDialog bottomSheetDialog;
private RecyclerView rcv_user_buy_product;
private ImageButton btn_Close_bottom_sheet_user_buy_product;
private Adapter_user_buy_product adapter_user_buy_product;
private List<user_buy_product> list_user_buy;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        bottomSheetDialog= (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        view=LayoutInflater.from(getContext()).inflate(R.layout.fragment__bottom__sheet__user__buy__product,null);
        bottomSheetDialog.setContentView(view);
        Mapping();
        Init();
        SetdataUi();
        GetdataUserBuyProduct();
        SetControlClick();
        BottomSheetBehavior bottomSheetBehavior=BottomSheetBehavior.from((View) view.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
       return bottomSheetDialog;
    }

    //Ánh xạ
    private  void Mapping() {
        rcv_user_buy_product = view.findViewById(R.id.rcv_user_buy_product);
        btn_Close_bottom_sheet_user_buy_product=view.findViewById(R.id.btn_Close_bottom_sheet_user_buy_product);
    }
    //Bắt sự kiện Button
    private void SetControlClick()
    {
        btn_Close_bottom_sheet_user_buy_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.Dismiss_Bottom_Sheet_Dialog_Fragment_User_Buy_Product();
            }
        });
    }
    //Khởi tạo biến
    private void Init()
    {
         list_user_buy=new ArrayList<user_buy_product>();

        mainActivity=(MainActivity) getActivity();

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mainActivity);
        rcv_user_buy_product.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(mainActivity,DividerItemDecoration.VERTICAL);
        rcv_user_buy_product.addItemDecoration(dividerItemDecoration);

        adapter_user_buy_product=new Adapter_user_buy_product(mainActivity);
    }
    //Hiển thị dữ liệu lên ui
    private  void SetdataUi()
    {
        rcv_user_buy_product.setAdapter(adapter_user_buy_product);
    }
    //Nhận dữ liệu danh sách đã mua hàng
    private void GetdataUserBuyProduct()
    {
        ApiUser_buy_product.api_user_buy_product.GetdataUserBuyProduct().enqueue(new Callback<List<user_buy_product>>() {
            @Override
            public void onResponse(Call<List<user_buy_product>> call, Response<List<user_buy_product>> response) {
                list_user_buy=response.body();
                adapter_user_buy_product.Setdata(list_user_buy);
            }
            @Override
            public void onFailure(Call<List<user_buy_product>> call, Throwable t) {

            }
        });
    }
}