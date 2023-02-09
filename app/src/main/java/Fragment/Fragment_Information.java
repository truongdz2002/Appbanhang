package Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appelectronicdevicesalessoftware.MainActivity;
import com.example.appelectronicdevicesalessoftware.R;

import Adapter.Adapter_fragment_information_option_setup;
import Object.fragment_information_option_setup;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import Object.fragment_information_option_setup;
import Retrofit.ApiInformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import Object.Personal_Information;

public class Fragment_Information extends Fragment {
private View view;
private static final int MY_REQUEST_CODE=10;
private RecyclerView rcv_option_setup;
private MainActivity mainActivity;
private TextView tv_UserName,tv_list_User_buy_product;
private List<Personal_Information> personal_informationList;
private Uri muri;
private ImageView avatar;
private Adapter_fragment_information_option_setup adapter_fragment_information_option_setup;
private List<fragment_information_option_setup> fragment_information_option_setupList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.fragment__information, container, false);
            Mapping();
            Initialization();
            SetDataUi();
            OnClick();
            GetImgAvatar();
            return view;
        }
        //Hiển thị dữ liệu lên ui
        private void SetDataUi()
        {
            rcv_option_setup.setAdapter(adapter_fragment_information_option_setup);
            tv_UserName.setText(mainActivity.getUsername());
            if(mainActivity.getUID_Login()==1)
            {
                tv_list_User_buy_product.setVisibility(View.VISIBLE);
            }
        }


  //Ánh xạ
    private void Mapping()
        {
            tv_list_User_buy_product=view.findViewById(R.id.tv_list_User_buy_product);
            tv_UserName=view.findViewById(R.id.tv_UserName);
            rcv_option_setup=view.findViewById(R.id.rcv_option_setup);
            avatar=view.findViewById(R.id.avatar);
        }
        //Bắt sự kiện button
        private  void OnClick()
        {
            tv_list_User_buy_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainActivity.Show_Bottom_Sheet_Dialog_Fragment_User_Buy_Product();
                }
            });
        }
        //Nhận dữ liệu hình ảnh avatar
        private void GetImgAvatar()
        {
            ApiInformation.apiInfo.GetPersonalInformation().enqueue(new Callback<List<Personal_Information>>() {
                @Override
                public void onResponse(Call<List<Personal_Information>> call, Response<List<Personal_Information>> response) {
                    personal_informationList=response.body();
                    if(personal_informationList==null)
                    {
                        return;
                    }
                    for (Personal_Information personal_information : personal_informationList)
                    {
                        if(personal_information!=null)
                        {
                            if(personal_information.getUid_Login()== mainActivity.getUID_Login())
                            {
                                Glide.with(mainActivity).load(personal_information.getImg_Avatar()).error(R.drawable.avatarnew).into(avatar);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Personal_Information>> call, Throwable t) {

                }
            });
        }
        // Khởi tạo biến
    private  void Initialization()
        {

            mainActivity=(MainActivity) getActivity();

            personal_informationList=new ArrayList<>();
            fragment_information_option_setupList=getdatalist();

            LinearLayoutManager linearLayoutManager =new LinearLayoutManager(mainActivity);
            rcv_option_setup.setLayoutManager(linearLayoutManager);

            DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(mainActivity,DividerItemDecoration.VERTICAL);
            rcv_option_setup.addItemDecoration(dividerItemDecoration);

            adapter_fragment_information_option_setup=new Adapter_fragment_information_option_setup(mainActivity, fragment_information_option_setupList,mainActivity.getUID_Login());


        }
        //Khỏi tạo list
        private  List<fragment_information_option_setup> getdatalist()
        {
            List<fragment_information_option_setup> fragment_information_option_setupList=new ArrayList<>();
            fragment_information_option_setupList.add(new fragment_information_option_setup("Thông tin cá nhân"));
            fragment_information_option_setupList.add(new fragment_information_option_setup("Hóa đơn mua hàng"));
            fragment_information_option_setupList.add(new fragment_information_option_setup("Cài đặt"));
            fragment_information_option_setupList.add(new fragment_information_option_setup("Đã thích "));
            fragment_information_option_setupList.add(new fragment_information_option_setup("Đã xem gần đây"));
            fragment_information_option_setupList.add(new fragment_information_option_setup("Số dư tài khoản"));
            fragment_information_option_setupList.add(new fragment_information_option_setup("Đánh giá của tôi"));
            fragment_information_option_setupList.add(new fragment_information_option_setup("Gói ưu đãi "));
            fragment_information_option_setupList.add(new fragment_information_option_setup("Thiệt lập tài khoản"));
            fragment_information_option_setupList.add(new fragment_information_option_setup("Hỗ trợ khách hàng"));
            fragment_information_option_setupList.add(new fragment_information_option_setup("Đăng xuất"));
            return fragment_information_option_setupList;

        }

    @Override
    public void onResume() {
        super.onResume();
        GetImgAvatar();

    }
}
    
    




