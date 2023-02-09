package Fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appelectronicdevicesalessoftware.ActivityProductDetail;
import com.example.appelectronicdevicesalessoftware.ActivityProductSearch;
import com.example.appelectronicdevicesalessoftware.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Adapter.Adapter_product_home;
import Retrofit.ApiBanHang;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import Object.Product;
import Object.Product_Model;

public class Fragment_Selling extends Fragment {
private List<Product> listProductSearchCelling;
private Adapter_product_home adapter_product_home;
private View view ;
private ActivityProductSearch activityProductSearch;
private RecyclerView rcv_list_product_search_celling;

    public Fragment_Selling() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view =inflater.inflate(R.layout.fragment__selling, container, false);
        Mapping();
        Initialization();
        GetdataApi();
         return view;
    }
    private void Mapping()

    {
        rcv_list_product_search_celling=view.findViewById(R.id.rcv_list_product_search_selling);
    }
    private void Initialization()
    {
        activityProductSearch=(ActivityProductSearch) getActivity();
        listProductSearchCelling=new ArrayList<>();
        adapter_product_home=new Adapter_product_home(activityProductSearch, new Adapter_product_home.ItemClickProduct() {
            @Override
            public void ItemClickproduct(Product product) {
                Intent intent=new Intent(activityProductSearch, ActivityProductDetail.class);
                intent.putExtra("Product",product);
                startActivity(intent);
            }
        });
        GridLayoutManager gridLayoutManager=new GridLayoutManager(activityProductSearch,2);
        rcv_list_product_search_celling.setLayoutManager(gridLayoutManager);
        DividerItemDecoration dividerItemDecoration1=new DividerItemDecoration(activityProductSearch,DividerItemDecoration.HORIZONTAL);
        rcv_list_product_search_celling.addItemDecoration(dividerItemDecoration1);
        DividerItemDecoration dividerItemDecoration2=new DividerItemDecoration(activityProductSearch,DividerItemDecoration.VERTICAL);
        rcv_list_product_search_celling.addItemDecoration(dividerItemDecoration2);
        rcv_list_product_search_celling.setAdapter(adapter_product_home);
    }
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
                        listProductSearchCelling.add(product);
                        adapter_product_home.setdata(listProductSearchCelling);
                    }
                }
            }

            @Override
            public void onFailure(Call<Product_Model> call, Throwable t) {

            }
        });
    }
}