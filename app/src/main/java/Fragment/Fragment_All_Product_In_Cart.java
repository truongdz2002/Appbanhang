package Fragment;

import android.content.Intent;
import android.os.Bundle;
import Object.Const;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.telephony.mbms.MbmsErrors;
import android.text.style.UpdateAppearance;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;
import Object.Personal_Information;

import com.example.appelectronicdevicesalessoftware.ActivityCart;
import com.example.appelectronicdevicesalessoftware.ActivityProductDetail;
import com.example.appelectronicdevicesalessoftware.R;
import com.google.android.gms.common.api.Api;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import Adapter.Adapter_product_home;
import Object.Product;

import Adapter.Adapter_product_add_in_cart;
import Object.Product_add_in_cart_of_user;
import Retrofit.ApiBanHang;
import Retrofit.ApiCart;
import Retrofit.ApiInformation;
import Retrofit.ApiMoney_User;
import Retrofit.ApiUser_buy_product;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import Object.Product_Model;


public class Fragment_All_Product_In_Cart extends Fragment {
private RecyclerView rcv_list_product_in_cart,rcv_list_product_cart;
private View view;
private ActivityCart activityCart;
private List<Product>  productListcart;
private Adapter_product_home adapter_product_home;
private Adapter_product_add_in_cart adapter_product_add_in_cart;
private List<Product_add_in_cart_of_user> list_delete_product_choose_buy_in_cart;
private String nameproduct;
private int totalPriceProduct=0;
private int Price_Product_In_Cart;
private int choose;
private List<Product_add_in_cart_of_user> product_add_in_cart_of_userList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment__all__product__in__cart, container, false);
        Mapping();
        Initialization();
        GetdataProductCompatibleProductInCart();
        SetCkeckBuyAllProductInCart();
        SetOnClick();
     return view;
     }
     //Ánh xạ
     private void Mapping()
     {
         rcv_list_product_in_cart=view.findViewById(R.id.rcv_product_add_cart);
         rcv_list_product_cart=view.findViewById(R.id.rcv_list_product_cart);
     }
     // Khởi tạo biến
     private void Initialization()
     {
         activityCart=(ActivityCart) getActivity();
         product_add_in_cart_of_userList=new ArrayList<>();
         list_delete_product_choose_buy_in_cart=new ArrayList<>();
         adapter_product_add_in_cart=new Adapter_product_add_in_cart(activityCart,new Adapter_product_add_in_cart.ItemCick() {
             @Override
             public void checked(Product_add_in_cart_of_user product_add_in_cart_of_user, int position) {
                 setTotalPriceProduct(getTotalPriceProduct()+product_add_in_cart_of_user.getPrice_product_add_in_cart());
                 SetTotalProductInCart();
                 list_delete_product_choose_buy_in_cart.add(product_add_in_cart_of_user);
                 setList_delete_product_choose_buy_in_cart(list_delete_product_choose_buy_in_cart);
                 //setNameproduct(product_add_in_cart_of_user.getName_product_add_in_cart());
                 //setPrice_Product_In_Cart(product_add_in_cart_of_user.getPrice_product_add_in_cart());
             }
             @Override
             public void Unchecked(Product_add_in_cart_of_user product_add_in_cart_of_user, int position) {
                 setTotalPriceProduct(getTotalPriceProduct()-product_add_in_cart_of_user.getPrice_product_add_in_cart());
                 SetTotalProductInCart();
                 if(getList_delete_product_choose_buy_in_cart()==null)
                 {
                     return;
                 }

                     for(int i=0;i<getList_delete_product_choose_buy_in_cart().size();i++)
                     {
                         if(getList_delete_product_choose_buy_in_cart().get(i).getName_product_add_in_cart().equals(product_add_in_cart_of_user.getName_product_add_in_cart()))
                         {
                             getList_delete_product_choose_buy_in_cart().remove(i);
                         }

                  }
             }
         });



         DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(activityCart, DividerItemDecoration.VERTICAL);
         rcv_list_product_in_cart.addItemDecoration(dividerItemDecoration);
         LinearLayoutManager linearLayoutManager=new LinearLayoutManager(activityCart);
         rcv_list_product_in_cart.setLayoutManager(linearLayoutManager);
         rcv_list_product_in_cart.setAdapter(adapter_product_add_in_cart);
         productListcart=new ArrayList<>();
         adapter_product_home=new Adapter_product_home(activityCart, new Adapter_product_home.ItemClickProduct() {
             @Override
             public void ItemClickproduct(Product product) {
                 Intent intent=new Intent(activityCart,ActivityProductDetail.class);
                 intent.putExtra("Product",product);
                 intent.putExtra("Uid",activityCart.getUid());
                 startActivity(intent);
             }
         });
         GridLayoutManager gridLayoutManager=new GridLayoutManager(activityCart,2);
         rcv_list_product_cart.setLayoutManager(gridLayoutManager);
         DividerItemDecoration dividerItemDecoration1=new DividerItemDecoration(activityCart,DividerItemDecoration.HORIZONTAL);
         rcv_list_product_cart.addItemDecoration(dividerItemDecoration1);
         DividerItemDecoration dividerItemDecoration2=new DividerItemDecoration(activityCart,DividerItemDecoration.VERTICAL);
         rcv_list_product_cart.addItemDecoration(dividerItemDecoration2);
         rcv_list_product_cart.setAdapter(adapter_product_home);

     }
     //Bắt sự kiện button
     private void SetOnClick()
     {
         activityCart.getTv_Buy_Product().setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if(getList_delete_product_choose_buy_in_cart().size()>0)
                 {
                     Update_Money_Acoount_User_And_Update_List_Product_In_Cart();
                 }
                 else
                 {
                     Toast.makeText(activityCart,"Bạn chưa chọn sản phẩm mua",Toast.LENGTH_SHORT).show();
                 }

             }
         });
     }

     //Xoa producr trong cart
     private void DeleteProductInCartBounght()
     {
         for(Product_add_in_cart_of_user product_add_in_cart_of_user:getList_delete_product_choose_buy_in_cart())
         {
             ApiCart.apicart.DeleteProduct_buy_in_cart(product_add_in_cart_of_user.getId()).enqueue(new Callback<ResponseBody>() {
                 @Override
                 public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                     getProduct_add_in_cart_of_userList().clear();
                     adapter_product_add_in_cart.setdata(getProduct_add_in_cart_of_userList());
                     GetdataInCartOfUser();
                 }

                 @Override
                 public void onFailure(Call<ResponseBody> call, Throwable t) {

                 }
             });
         }
     }
     //cập nhật số tiền trong tài khoản và cập nhât số dánhachs sản phẩm trong giỏ hàng
     private void Update_Money_Acoount_User_And_Update_List_Product_In_Cart()
     {
         if(activityCart.getMonney_User() < getTotalPriceProduct())
         {
             Toast.makeText(activityCart,"Tài khoản của bạn không đủ để thanh toán",Toast.LENGTH_SHORT).show();
         }
         else
         {
             DeleteProductInCartBounght();
             int excess_money=activityCart.getMonney_User()-getTotalPriceProduct();
             Update_Money_Acoount_User(excess_money);
             for(int i=0;i<getList_delete_product_choose_buy_in_cart().size();i++)
             {
                 AddUserBuyProduct(getList_delete_product_choose_buy_in_cart().get(i).getName_product_add_in_cart(),getList_delete_product_choose_buy_in_cart().get(i).getPrice_product_add_in_cart());
             }

             activityCart.getTv_TotalMoney().setText("");
         }

     }
     //Thêm số lượng sản phẩm người dùng đã mua
     private void AddUserBuyProduct(String NameProduct,int PriceProduct)
     {
         ApiUser_buy_product.api_user_buy_product.AddUserBuyProduct(activityCart.getUid(), activityCart.getUsername(), activityCart.getAddress(), activityCart.getNumberPhone(),NameProduct,PriceProduct,Const.UnDilivery ).enqueue(new Callback<ResponseBody>() {
             @Override
             public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

             }

             @Override
             public void onFailure(Call<ResponseBody> call, Throwable t) {

             }
         });

     }

     // Cập nhật các tiền trong tài khoản của người dùng
     private void Update_Money_Acoount_User(int excess_money)
     {
         if(activityCart.getUid()==0 )
         {
             return;
         }
         ApiMoney_User.api_money_user.Update_Money_Account_User(activityCart.getUid(),excess_money).enqueue(new Callback<ResponseBody>() {
             @Override
             public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

             }

             @Override
             public void onFailure(Call<ResponseBody> call, Throwable t) {

             }
         });
     }
     //Kiểm tra xem có chọn mua tất cả hay ko
     private  void SetCkeckBuyAllProductInCart()
     {
         activityCart.getCk_all_product_in_cart().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                 if(b)
                 {
                      adapter_product_add_in_cart.setChoose(1);
                 }
                 else
                     adapter_product_add_in_cart.setChoose(0);

             }
         });
     }
     //Nhận dữ liệu số sản phẩm trong giỏ hàng của người dùng
     private void GetdataInCartOfUser()
     {
         ApiCart.apicart.GetProductInCart().enqueue(new Callback<List<Product_add_in_cart_of_user>>() {
             @Override
             public void onResponse(Call<List<Product_add_in_cart_of_user>> call, Response<List<Product_add_in_cart_of_user>> response) {
                 List<Product_add_in_cart_of_user> list=new ArrayList<>();
                 list=response.body();
                 for (Product_add_in_cart_of_user product_add_in_cart_of_user:list)
                 {
                     if(product_add_in_cart_of_user.getUid()==activityCart.getUid())
                     {
                         String nameproduct=product_add_in_cart_of_user.getName_product_add_in_cart();
                         setNameproduct(nameproduct);
                         product_add_in_cart_of_userList.add(product_add_in_cart_of_user);
                         setProduct_add_in_cart_of_userList(product_add_in_cart_of_userList);
                         adapter_product_add_in_cart.setdata(getProduct_add_in_cart_of_userList());
                     }
                 }
             }

             @Override
             public void onFailure(Call<List<Product_add_in_cart_of_user>> call, Throwable t) {
                 Toast.makeText(activityCart,"Đã thêm failed",Toast.LENGTH_SHORT).show();

             }
         });
     }
     //Hiện thị danh sách sản phẩm theo giá tiền tương ứng với các sản phẩm trong giỏ hàng
     private void GetdataProductCompatibleProductInCart()
     {
         ApiBanHang.apiHang.getListProduct().enqueue(new Callback<Product_Model>() {
             @Override
             public void onResponse(Call<Product_Model> call, Response<Product_Model> response) {
                 List<Product> list=new ArrayList<>();
                 list=response.body().getResult();
                 for(Product product:list)
                 {

                         productListcart.add(product);
                         adapter_product_home.setdata(productListcart);
                 }
             }

             @Override
             public void onFailure(Call<Product_Model> call, Throwable t) {

             }
         });
     }
     //Định dạng cho giá sản phẩm
     private void SetTotalProductInCart()
     {
         String pattern="###,###,###";
         DecimalFormat decimalFormat=new DecimalFormat(pattern);
         activityCart.getTv_TotalMoney().setText(String.valueOf(decimalFormat.format(getTotalPriceProduct())));

     }

    public String getNameproduct() {
        return nameproduct;
    }

    public void setNameproduct(String nameproduct) {
        this.nameproduct = nameproduct;
    }

    public int getTotalPriceProduct() {
        return totalPriceProduct;
    }

    public void setTotalPriceProduct(int totalPriceProduct) {
        this.totalPriceProduct = totalPriceProduct;
    }

    public List<Product_add_in_cart_of_user> getProduct_add_in_cart_of_userList() {
        return product_add_in_cart_of_userList;
    }

    public void setProduct_add_in_cart_of_userList(List<Product_add_in_cart_of_user> product_add_in_cart_of_userList) {
        this.product_add_in_cart_of_userList = product_add_in_cart_of_userList;
    }

    public int getChoose() {
        return choose;
    }

    public void setChoose(int choose) {
        this.choose = choose;
    }

    public void setList_delete_product_choose_buy_in_cart(List<Product_add_in_cart_of_user> list_delete_product_choose_buy_in_cart) {
        this.list_delete_product_choose_buy_in_cart = list_delete_product_choose_buy_in_cart;
    }

    public List<Product_add_in_cart_of_user> getList_delete_product_choose_buy_in_cart() {
        return list_delete_product_choose_buy_in_cart;

    }

    public int getPrice_Product_In_Cart() {
        return Price_Product_In_Cart;
    }

    public void setPrice_Product_In_Cart(int price_Product_In_Cart) {
        Price_Product_In_Cart = price_Product_In_Cart;
    }

    @Override
    public void onResume() {
        super.onResume();
        getProduct_add_in_cart_of_userList().clear();
        GetdataInCartOfUser();
    }
}