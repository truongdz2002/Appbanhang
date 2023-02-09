package Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appelectronicdevicesalessoftware.ActivityProductDetail;
import com.example.appelectronicdevicesalessoftware.ActivityProductSearch;
import com.example.appelectronicdevicesalessoftware.R;

import java.util.Collections;
import java.util.List;

import Adapter.Adapter_optoin_sort_price;
import Object.Option_sort_price;

import Adapter.Adapter_product_home;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.appelectronicdevicesalessoftware.ActivityProductSearch;
import com.example.appelectronicdevicesalessoftware.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.Adapter_product_home;
import Retrofit.ApiBanHang;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import Object.Product;
import Object.Product_Model;
public class Fragment_Price extends Fragment {

    private List<Product> listProductSearchPrice;
    private Adapter_product_home adapter_product_home;
    private View view ;
    private List<Option_sort_price> option_sort_priceList;
    private Spinner spinner_click_price;
    private ActivityProductSearch activityProductSearch;
    private RecyclerView rcv_list_product_search_price;
    private Adapter_optoin_sort_price adapter_option_sort_price;

    public Fragment_Price() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment__price, container, false);
        Mapping();
        Initialization();
        GetdataApi();
        ClickSpiner();
        return view;
    }
      //Bắt sự kiện button
    private void ClickSpiner() {
      spinner_click_price.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
           Product product=new Product();
            switch (i)
            {
                case 0:
                    Collections.sort(getListProductSearchPrice(),product.Sort_Product_Price_Increase);
                    adapter_product_home.setdata(getListProductSearchPrice());
                    break;
                case 1:
                    Collections.sort(getListProductSearchPrice(),product.Sort_Product_Price_Recrease);
                    adapter_product_home.setdata(getListProductSearchPrice());
                    break;


            }






          }

          @Override
          public void onNothingSelected(AdapterView<?> adapterView) {

          }
      });
    }
    //Ánh xạ
    private void Mapping()

    {
        spinner_click_price=view.findViewById(R.id.Spiner_Click_price);
        rcv_list_product_search_price=view.findViewById(R.id.rcv_list_product_search_price);
    }
    //Khởi tạo biến
    private void Initialization()
    {
        activityProductSearch=(ActivityProductSearch) getActivity();
        //Spriner  option sort price
        option_sort_priceList=new ArrayList<>();
        option_sort_priceList.add(new Option_sort_price("Tăng "));
        option_sort_priceList.add(new Option_sort_price("Giảm "));
        adapter_option_sort_price=new Adapter_optoin_sort_price(activityProductSearch,R.layout.item_printer_sort_price, option_sort_priceList);
         spinner_click_price.setAdapter(adapter_option_sort_price);
         //list product search
        listProductSearchPrice=new ArrayList<>();
        adapter_product_home=new Adapter_product_home(activityProductSearch, new Adapter_product_home.ItemClickProduct() {
            @Override
            public void ItemClickproduct(Product product) {
                Intent intent=new Intent(activityProductSearch, ActivityProductDetail.class);
                intent.putExtra("Product",product);
                startActivity(intent);
            }
        });
        GridLayoutManager gridLayoutManager=new GridLayoutManager(activityProductSearch,2);
        rcv_list_product_search_price.setLayoutManager(gridLayoutManager);
        DividerItemDecoration dividerItemDecoration1=new DividerItemDecoration(activityProductSearch,DividerItemDecoration.HORIZONTAL);
        rcv_list_product_search_price.addItemDecoration(dividerItemDecoration1);
        DividerItemDecoration dividerItemDecoration2=new DividerItemDecoration(activityProductSearch,DividerItemDecoration.VERTICAL);
        rcv_list_product_search_price.addItemDecoration(dividerItemDecoration2);
        rcv_list_product_search_price.setAdapter(adapter_product_home);
    }
    //Nhận data sản phẩm tử Api
    private void GetdataApi()
    {
        ApiBanHang.apiHang.getListProduct().enqueue(new Callback<Product_Model>() {
            @Override
            public void onResponse(Call<Product_Model> call, Response<Product_Model> response) {
                List<Product> list=new ArrayList<>();
                list=response.body().getResult();
                for(Product product :list)
                {
                    if(product.getName_Product().toUpperCase().contains(activityProductSearch.getTextSearch().toUpperCase()))
                    {
                        listProductSearchPrice.add(product);
                        setListProductSearchPrice(listProductSearchPrice);
                        Collections.sort(listProductSearchPrice,product.Sort_Product_Price_Recrease);
                        adapter_product_home.setdata(listProductSearchPrice);
                    }
                }
            }

            @Override
            public void onFailure(Call<Product_Model> call, Throwable t) {

            }
        });
    }

    public List<Product> getListProductSearchPrice() {
        return listProductSearchPrice;
    }

    public void setListProductSearchPrice(List<Product> listProductSearchPrice) {
        this.listProductSearchPrice = listProductSearchPrice;
    }
}