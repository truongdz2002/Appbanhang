package Fragment;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import Adapter.Adapter_characteristic_product;
import Object.Characteristic_product;

import com.bumptech.glide.Glide;
import com.example.appelectronicdevicesalessoftware.ActivityInformation;
import com.example.appelectronicdevicesalessoftware.ActivityProductDetail;
import com.example.appelectronicdevicesalessoftware.R;
import com.google.android.gms.common.api.Api;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import Adapter.Adapter_List_Product_Horizontal;
import Adapter.Adapter_product_home;
import Object.Product;
import Retrofit.ApiBanHang;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import Object.Product_Model;


public class Fragment_Product_Detail extends Fragment {
private View view;
private List<Characteristic_product> characteristic_productList;
private ActivityProductDetail mainActivityProductDetail;
private Adapter_characteristic_product adapter_characteristic_product;
private ImageView imgProduct,img_product_buy;
private List<Product> productList;
private Button btn_buy_now;
private ImageButton btn_add, btn_reduction;
private ImageButton btn_Close_bottom_sheet_buy_now;
private Adapter_List_Product_Horizontal adapter_list_product_horizontal;
private List<Product_Model> product_modelList;
private Adapter_product_home adapter_product_home;
private TextView tv_name_product,tv_Price_product,tv_Discount,tv_Cost_product,tv_amount, tv_price, tv_price_orgin,tv_getdata_update_option;
private RecyclerView rcv_list_product_same_price,rcv_list_product_same_company,rcv_product_color;
int iAmountOption;
String StrAmountOption;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       view=inflater.inflate(R.layout.fragment__product__detail, container, false);
       Mapping();
       Initialization();
       GetDataFromActivityDeferent();
       GetDataApi();
       OnClick();

       return view;
    }
    //Ánh xạ
    private void Mapping()
    {
        rcv_list_product_same_company=view.findViewById(R.id.rcv_list_product_same_company);
        rcv_list_product_same_price=view.findViewById(R.id.rcv_list_product_same_price);
        tv_Cost_product=view.findViewById(R.id.tv_Cost_product);
        tv_name_product=view.findViewById(R.id.tv_name_product_detail);
        tv_Price_product=view.findViewById(R.id.tv_Price_product_detail);
        tv_Discount=view.findViewById(R.id.tv_Discount);
        imgProduct=view.findViewById(R.id.img_product_detail);

    }
    //Bắt sự kiện Buttom
    private void OnClick()
    {
        //text view buy now
        mainActivityProductDetail.getTv_Buy_now().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view=getLayoutInflater().inflate(R.layout.layout_bottomsheet_click_buy_now,null);
                BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(mainActivityProductDetail);
                getCharacteristic_productList().clear();
                UpdateAmountProductWantToBuy(view);
                bottomSheetDialog.setContentView(view);
                bottomSheetDialog.show();
            }
        });
    }
    //Cập nhật dữ liệu muốn mua
    private void UpdateAmountProductWantToBuy(View view)
    {

        Mapping_BottomSheet(view);
        btn_reduction.setEnabled(false);
        btn_add.setEnabled(false);
        //btn_buy_now.setEnabled(false);
        InitBottomSheet(view);
        Getdatacharacteristic_product();
        SetDataUiBottomSheet();
        SetControlClick();

    }
    //Bắt sự kiện button
    private void SetControlClick()
    {
        //button buy now
        btn_buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if(mainActivityProductDetail.getAddress().length()== 0 &&mainActivityProductDetail.getFullName().length()==0 && mainActivityProductDetail.getTelephoneNumber().length()==0)
  //              {
 //                   Intent intent=new Intent(mainActivityProductDetail, ActivityInformation.class);
    //                intent.putExtra("Uid",mainActivityProductDetail.getUid());
     //               startActivity(intent);
         //       }
        //        else
          //     {
                    mainActivityProductDetail.setAmount_Product_buy(Integer.parseInt(tv_getdata_update_option.getText().toString().trim()));
                    mainActivityProductDetail.Amount_Product_buy=Integer.parseInt(tv_getdata_update_option.getText().toString().trim());
                    mainActivityProductDetail.setQuantity_in_stock(Integer.parseInt(tv_amount.getText().toString().trim()));
                    mainActivityProductDetail.setPrice_Product(mainActivityProductDetail.Price_Product_real);
                    mainActivityProductDetail.ShowBottom_Sheet_delivery_information();


             //   }

            }
        });
        btn_Close_bottom_sheet_buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    //Bắt sự kiện button
    private void SetControlButton()
    {
        setStrAmountOption(tv_getdata_update_option.getText().toString().trim());
        setiAmountOption(Integer.parseInt(getStrAmountOption()));
        btn_add.setEnabled(true);
        //btn_buy_now.setEnabled(true);
        //btn_buy_now.setBackgroundColor(R.color.Orange);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setiAmountOption(getiAmountOption()+1);
                tv_getdata_update_option.setText(String.valueOf(getiAmountOption()));
                SetEnableButton(btn_reduction,btn_add);
            }
        });
        btn_reduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setiAmountOption(getiAmountOption() - 1);
                tv_getdata_update_option.setText(String.valueOf(getiAmountOption()));
                SetEnableButton(btn_reduction,btn_add);
            }
        });
    }
    //Hiển thị dữ liệu lên bottom sheet
    private void SetDataUiBottomSheet() {
        String pattern="###,###,###";
        DecimalFormat decimalFormat=new DecimalFormat(pattern);
        Glide.with(mainActivityProductDetail).load(mainActivityProductDetail.getImg_Product()).error(R.drawable.logo).into(img_product_buy);
        tv_price_orgin.setText(String.valueOf(decimalFormat.format(mainActivityProductDetail.getPrice_Product()))+"VND");
        tv_price_orgin.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tv_price.setText(tv_Price_product.getText().toString()+"VND");

    }
    //Khởi tạo dữ liệu cho bottom sheet
    private void InitBottomSheet(View view) {
        adapter_characteristic_product=new Adapter_characteristic_product(mainActivityProductDetail, new Adapter_characteristic_product.ItemClick() {
            @Override
            public void ItemClick(Characteristic_product characteristic_product,int position) {
                tv_amount.setText(String.valueOf(characteristic_product.getAmount()));
                SetControlButton();
                tv_getdata_update_option.setText("1");
                mainActivityProductDetail.setColor_Product(characteristic_product.getColor_product());
                adapter_characteristic_product.setChoose(position);
                mainActivityProductDetail.setImg_Product(characteristic_product.getImg_product());
                Glide.with(mainActivityProductDetail).load(characteristic_product.getImg_product()).error(R.drawable.logo).into(img_product_buy);



            }
        });
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mainActivityProductDetail,RecyclerView.HORIZONTAL,false);
        rcv_product_color.setLayoutManager(linearLayoutManager);
        rcv_product_color.setAdapter(adapter_characteristic_product);
    }
  //Ánh xạ dữ liệu bottom sheet
    private void Mapping_BottomSheet(View view) {
         btn_add=view.findViewById(R.id.btn_add_amount);
         btn_buy_now=view.findViewById(R.id.btn_buy_now);
         tv_amount=view.findViewById(R.id.tv_amount_product);
         tv_price=view.findViewById(R.id.tv_price);
         tv_price_orgin=view.findViewById(R.id.tv_price_orgin);
         img_product_buy=view.findViewById(R.id.img_product_buy);
         rcv_product_color=view.findViewById(R.id.rcv_color_product_buy);
         btn_reduction=view.findViewById(R.id.btn_add_reduction);
         btn_Close_bottom_sheet_buy_now=view.findViewById(R.id.btn_Close_bottom_sheet_buy_now);
         tv_getdata_update_option= view.findViewById(R.id.tv_getdata_update_option);
    }
    //Nhận các sản phẩm có thuộc tính cùng 1 tên sản phẩm
    private void Getdatacharacteristic_product()
    {
        ApiBanHang.apiHang.GetListCharacteristicProduct().enqueue(new Callback<List<Characteristic_product>>() {
            @Override
            public void onResponse(Call<List<Characteristic_product>> call, Response<List<Characteristic_product>> response) {
                List<Characteristic_product> l=new ArrayList<Characteristic_product>();
                l=response.body();
                for(Characteristic_product characteristic_product :l)
                {
                    if(characteristic_product.getCode_product()==mainActivityProductDetail.getiCodeProduct())
                    {
                        characteristic_productList.add(characteristic_product);
                        setCharacteristic_productList(characteristic_productList);
                        tv_amount.setText(String.valueOf(characteristic_product.getAmount()));
                        adapter_characteristic_product.setdata(getCharacteristic_productList());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Characteristic_product>> call, Throwable t) {

            }
        });
    }
    //Cái đặt điều khiển cho 2 nút button
    private void SetEnableButton(ImageButton btn_reduction,ImageButton btn_add)
    {
        if(getiAmountOption()>1)
        {
            btn_reduction.setEnabled(true);
        }
        else
        {
            btn_reduction.setEnabled(false);
        }
        if(getiAmountOption()>=Integer.parseInt(tv_amount.getText().toString().trim()))
        {
            btn_add.setEnabled(false);
        }
        else
        {
            btn_add.setEnabled(true);
        }
    }
    //Khởi tạo biến
    private void Initialization()
    {
        mainActivityProductDetail=(ActivityProductDetail) getActivity();

        product_modelList=new ArrayList<>();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mainActivityProductDetail);
        rcv_list_product_same_price.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(mainActivityProductDetail,DividerItemDecoration.VERTICAL);
        rcv_list_product_same_price.addItemDecoration(dividerItemDecoration);
        DividerItemDecoration dividerItemDecoration2=new DividerItemDecoration(mainActivityProductDetail,DividerItemDecoration.HORIZONTAL);
        rcv_list_product_same_price.addItemDecoration(dividerItemDecoration2);
        adapter_list_product_horizontal=new Adapter_List_Product_Horizontal(mainActivityProductDetail);
        rcv_list_product_same_price.setAdapter(adapter_list_product_horizontal);
        adapter_list_product_horizontal.SetUid(mainActivityProductDetail.getUid());
        adapter_list_product_horizontal.setPrice_Product(mainActivityProductDetail.getPrice_Product());

        productList=new ArrayList<>();
        adapter_product_home=new Adapter_product_home(mainActivityProductDetail, new Adapter_product_home.ItemClickProduct() {
            @Override
            public void ItemClickproduct(Product product) {
                Intent intent=new Intent(mainActivityProductDetail, ActivityProductDetail.class);
                intent.putExtra("Product",product);
                intent.putExtra("Uid",mainActivityProductDetail.getUid());
                startActivity(intent);
            }
        });
        GridLayoutManager gridLayoutManager=new GridLayoutManager(mainActivityProductDetail,2);
        rcv_list_product_same_company.setLayoutManager(gridLayoutManager);
        DividerItemDecoration dividerItemDecoration1=new DividerItemDecoration(mainActivityProductDetail,DividerItemDecoration.VERTICAL);
        rcv_list_product_same_company.addItemDecoration(dividerItemDecoration1);
        DividerItemDecoration dividerItemDecoration3=new DividerItemDecoration(mainActivityProductDetail,DividerItemDecoration.HORIZONTAL);
        rcv_list_product_same_company.addItemDecoration(dividerItemDecoration3);
        rcv_list_product_same_company.setAdapter(adapter_product_home);

        characteristic_productList=new ArrayList<>();
    }
    //Set dữ liệu lên ui
    private void GetDataFromActivityDeferent()
    {
        String pattern="###,###,###";
        DecimalFormat decimalFormat=new DecimalFormat(pattern);
        int s=0,p=0;
        s=mainActivityProductDetail.getDiscount() * (mainActivityProductDetail.getPrice_Product()/100);
        p=mainActivityProductDetail.getPrice_Product()-s;
        mainActivityProductDetail.Price_Product_real=p;
        Glide.with(mainActivityProductDetail).load(mainActivityProductDetail.getImg_Product()).into(imgProduct);
        tv_name_product.setText(mainActivityProductDetail.getName_Product());
        tv_Cost_product.setText(String.valueOf(decimalFormat.format(mainActivityProductDetail.getPrice_Product())));
        tv_Cost_product.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tv_Discount.setText(String.valueOf(mainActivityProductDetail.getDiscount()));
        tv_Price_product.setText(String.valueOf(decimalFormat.format(p)));


    }
    private void GetDataApi()
    {
        ApiBanHang.apiHang.getListProduct().enqueue(new Callback<Product_Model>() {
            @Override
            public void onResponse(Call<Product_Model> call, Response<Product_Model> response) {
                List<Product> list=new ArrayList<>();
                Product_Model product_model=response.body();
                product_modelList.add(product_model);
                adapter_list_product_horizontal.setdata(product_modelList);
                list=response.body().getResult();
                for(Product product:list)
                {
                    if(product.getPrice_Product()< mainActivityProductDetail.getPrice_Product())
                    {
                        productList.add(product);
                        adapter_product_home.setdata(productList);
                    }
                }

                }


            @Override
            public void onFailure(Call<Product_Model> call, Throwable t) {

            }
        });
    }

    public int getiAmountOption() {
        return iAmountOption;
    }

    public void setiAmountOption(int iAmountOption) {
        this.iAmountOption = iAmountOption;
    }

    public String getStrAmountOption() {
        return StrAmountOption;
    }

    public List<Characteristic_product> getCharacteristic_productList() {
        return characteristic_productList;
    }

    public void setCharacteristic_productList(List<Characteristic_product> characteristic_productList) {
        this.characteristic_productList = characteristic_productList;
    }

    public void setStrAmountOption(String strAmountOption) {
        StrAmountOption = strAmountOption;
    }
}