package Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appelectronicdevicesalessoftware.ActivityProductDetail;
import com.example.appelectronicdevicesalessoftware.MainActivity;
import com.example.appelectronicdevicesalessoftware.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import Adapter.Adapter_product_home;
import Adapter.AdapterImageAdvetisement;
import Retrofit.ApiAdvertisement;
import Retrofit.ApiBanHang;
import Object.Imageadvertisement;
import   Object.Product_Model;
import  Object.Product;
import me.relex.circleindicator.CircleIndicator3;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Home extends Fragment {
private View view;
private RecyclerView rcv_list_product;
private MainActivity mainActivity;
private List<Product> lproduct;
private TextView tv_Total_money_account_user;
private List<Imageadvertisement> imageadvertisementList;
private Adapter_product_home adapter_product_home;
private ViewPager2 vp_advertisement;
private CircleIndicator3 ci_advertisement;
private AdapterImageAdvetisement adapterImageAdvetisement;
private Handler handler=new Handler();
private  Runnable  mrRunnable=new Runnable() {
    @Override
    public void run() {
        if(vp_advertisement.getCurrentItem()==imageadvertisementList.size()-1)
        {
            vp_advertisement.setCurrentItem(0);
        }
        else
        {
            vp_advertisement.setCurrentItem(vp_advertisement.getCurrentItem()+1);

        }

    }
};


    public Fragment_Home() {
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment__home, container, false);
        Mapping();
        Initialization();
        GetDataApi();

        return view;
    }
   //Nhận dữ liệu danh sách sản phẩm
    private void GetDataApi() {
     ApiBanHang.apiHang.getListProduct().enqueue(new Callback<Product_Model>() {
         @Override
         public void onResponse(Call<Product_Model> call, Response<Product_Model> response) {
           lproduct=response.body().getResult();
           adapter_product_home.setdata(lproduct);
         }
         @Override
         public void onFailure(Call<Product_Model> call, Throwable t) {
             Toast.makeText(mainActivity,"failed", Toast.LENGTH_SHORT).show();
         }
     });
     //Nhận hình ảnh quảng cáo
        ApiAdvertisement.apiAdvertisement.Getdata().enqueue(new Callback<List<Imageadvertisement>>() {
            @Override
            public void onResponse(Call<List<Imageadvertisement>> call, Response<List<Imageadvertisement>> response) {
                imageadvertisementList=response.body();
                adapterImageAdvetisement.setdata(imageadvertisementList);
                ci_advertisement.setViewPager(vp_advertisement);
            }

            @Override
            public void onFailure(Call<List<Imageadvertisement>> call, Throwable t) {
                Toast.makeText(mainActivity,"failed", Toast.LENGTH_SHORT).show();

            }
        });


    }

//Ánh xạ
    private void Mapping() {
        vp_advertisement=view.findViewById(R.id.vp_advertisement);
        ci_advertisement=view.findViewById(R.id.ci_indicator);
        rcv_list_product=view.findViewById(R.id.rcv_list_product);
        mainActivity=(MainActivity) getActivity();
        tv_Total_money_account_user=view.findViewById(R.id.tv_Total_monney_in_wallet);


    }
//Khởi tạo
    private void Initialization()
    {
        //Khởi tạo cho rcv product
        lproduct=new ArrayList<>();
        adapter_product_home= new Adapter_product_home(mainActivity, new Adapter_product_home.ItemClickProduct() {
            @Override
            public void ItemClickproduct(Product product) {
                Intent intent=new Intent(mainActivity, ActivityProductDetail.class);
                intent.putExtra("Product",product);
                intent.putExtra("Uid",mainActivity.getUID_Login());
                intent.putExtra("MoneyUser",mainActivity.getMoney_account_user());
                startActivity(intent);
            }
        });
        GridLayoutManager gridLayoutManager=new GridLayoutManager(mainActivity,2);
        rcv_list_product.setLayoutManager(gridLayoutManager);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(mainActivity,DividerItemDecoration.VERTICAL);
        rcv_list_product.addItemDecoration(dividerItemDecoration);
        DividerItemDecoration di=new DividerItemDecoration(mainActivity,DividerItemDecoration.HORIZONTAL);
        rcv_list_product.addItemDecoration(di);
        ConcatAdapter concatAdapter=new ConcatAdapter(adapter_product_home);
        rcv_list_product.setAdapter(concatAdapter);
        //Khởi tạo Advertisement viewpager2
        imageadvertisementList=new ArrayList<>();
        adapterImageAdvetisement =new AdapterImageAdvetisement(mainActivity);
        vp_advertisement.setAdapter(adapterImageAdvetisement);
        vp_advertisement.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(mrRunnable);
                handler.postDelayed(mrRunnable,2500);
            }
        });
        String pattern="###,###,###";
        DecimalFormat decimalFormat=new DecimalFormat(pattern);
        tv_Total_money_account_user.setText(String.valueOf(decimalFormat.format(mainActivity.getMoney_account_user())));

    }

    @Override
    public void onResume() {
        super.onResume();
        String pattern="###,###,###";
        DecimalFormat decimalFormat=new DecimalFormat(pattern);
        tv_Total_money_account_user.setText(String.valueOf(decimalFormat.format(mainActivity.getMoney_account_user())));
    }
}