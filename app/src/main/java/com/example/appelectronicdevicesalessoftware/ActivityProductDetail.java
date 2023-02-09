package com.example.appelectronicdevicesalessoftware;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import ConnectAPI.UpdateProduct;
import ConnectAPI.UpdateViewAndSendNotification;
import Object.Money_user;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import Fragment.Fragment_Bottom_Sheet_Dialog_Bill_To_Payment;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import Fragment.Fragment_botom_sheet_dialog_delivery_information;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import Object.Product_add_in_cart_of_user;

import Object.Product;

import Fragment.Fragment_Product_Detail;
import Retrofit.ApiCart;
import Retrofit.ApiInformation;
import Object.Personal_Information;
import Retrofit.ApiMoney_User;
import Utilities.PushNotification;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityProductDetail extends AppCompatActivity {
    private String Name_Product;
    private int Price_Product;
    public UpdateViewAndSendNotification updateViewAndSendNotification;
    private int Discount;
    private MaterialToolbar tb_product_detail;
    private int iCodeProduct;
    public PushNotification pushNotification;
    public int Amount_Product_buy;
    public String Oderdate;
    public int Price_Product_real;
    public UpdateProduct updateProduct;
    private int Uid;
    public int quantity_in_stock;
    private int Money_User;
    int exist;
    private String Color_Product;
    private String Img_Product;
    private List<Product_add_in_cart_of_user> product_add_in_cart_of_userList;
    private ImageButton btn_Add_Product_in_cart;
    private TextView tv_Buy_now;
    private BadgeDrawable bd_NotificationCart;
    private ImageButton btn_Back_Product_detail;
    private String FullName,TelephoneNumber,Address;
    private Fragment_Bottom_Sheet_Dialog_Bill_To_Payment bottom_sheet_dialog_bill_to_payment;
    private Fragment_botom_sheet_dialog_delivery_information bottomSheetDialogFragment_Delivery_Information;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        replaceFragment(new Fragment_Product_Detail());
        GetDataFragmentHome();
        Mapping();
        Initialization();
        GetInformationUser();
        GetMoneyUser();
        SetOnclick();



    }
    //Nhận dữ liệu số tiền trong tài khoản người dùng
    public void GetMoneyUser()
    {
        ApiMoney_User.api_money_user.Get_Money_Account_User().enqueue(new Callback<List<Money_user>>() {
            @Override
            public void onResponse(Call<List<Money_user>> call, Response<List<Money_user>> response) {
                List<Money_user> l=new ArrayList<Money_user>();
                l=response.body();
                for (Money_user money_user:l)
                {
                    if(money_user.getUid()==getUid())
                    {
                        setMoney_User(money_user.getMoney());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Money_user>> call, Throwable t) {

            }
        });
    }
    //Khởi tạo list
    public List<Product_add_in_cart_of_user> GetOrCreateListProductInCart()
    {
        if(product_add_in_cart_of_userList==null) product_add_in_cart_of_userList=new ArrayList<>();
        return product_add_in_cart_of_userList;
    }
    //Cập nhật số tiền user sau khi sử dụng
    public void UpdateMoney_User(int money_User)
    {
        ApiMoney_User.api_money_user.Update_Money_Account_User(getUid(),money_User).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    //Ghép fragment vào activity
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.Content_Product_Detail, fragment);
        transaction.commit();
    }
    //Hiện thị bottomsheet thông tin giao hàng người dùng
    public void ShowBottom_Sheet_delivery_information()
    {
        bottomSheetDialogFragment_Delivery_Information.show(getSupportFragmentManager(),"Show");
    }
    //Tắt bottomsheet
    public void DismissBottom_Sheet_delivery_information()
    {
        bottomSheetDialogFragment_Delivery_Information.dismiss();
    }
    //Hiện thi hóa đơn mua hàng để xác nhận thanh toán
    public void Show_Bottom_Sheet_Bill_To_Payment()
    {
        bottom_sheet_dialog_bill_to_payment.show(getSupportFragmentManager(),"Show");
    }
    //Tắt bottomsheet
    public void Dismiss_Bottom_Sheet_Bill_To_Payment()
    {
        bottom_sheet_dialog_bill_to_payment.dismiss();
    }
    //Khởi tạo dữ liệu biến
    private void Initialization()
    {
        updateProduct=new UpdateProduct();
        pushNotification=new PushNotification(this);
        updateViewAndSendNotification=new UpdateViewAndSendNotification(this);
        bd_NotificationCart=BadgeDrawable.create(this);
        bd_NotificationCart.setVisible(false);
        bd_NotificationCart.setMaxCharacterCount(3);
        bd_NotificationCart.setBackgroundColor(Color.RED);
        bd_NotificationCart.setBadgeTextColor(Color.WHITE);
        BadgeUtils.attachBadgeDrawable(bd_NotificationCart,tb_product_detail,R.id.btn_Cart);
        bottom_sheet_dialog_bill_to_payment=new Fragment_Bottom_Sheet_Dialog_Bill_To_Payment();
        bottomSheetDialogFragment_Delivery_Information=new Fragment_botom_sheet_dialog_delivery_information();
    }
    //Update dữ liệu số sản phẩm trên icon giỏ hàng
    private void setNumberIconCart()
    {
        if(  GetOrCreateListProductInCart().size()<=0)
        {
            bd_NotificationCart.setVisible(false);
        }
        else
        {
            bd_NotificationCart.setVisible(true);
            bd_NotificationCart.setNumber(GetOrCreateListProductInCart().size());
        }
    }
    //Update số lượng sản phẩm trong giỏ hàng
    private void UpdateNumberProductCartCount()
    {
        ApiCart.apicart.GetProductInCart().enqueue(new Callback<List<Product_add_in_cart_of_user>>() {

            @Override
            public void onResponse(Call<List<Product_add_in_cart_of_user>> call, Response<List<Product_add_in_cart_of_user>> response) {
                List<Product_add_in_cart_of_user> list=new ArrayList<>();
                GetOrCreateListProductInCart().clear();
                list=response.body();
                for(Product_add_in_cart_of_user product_add_in_cart_of_user:list)
                {
                    if(product_add_in_cart_of_user.getUid()==getUid())
                    {
                        GetOrCreateListProductInCart().add(product_add_in_cart_of_user);
                    }
                }
                setNumberIconCart();
            }


            @Override
            public void onFailure(Call<List<Product_add_in_cart_of_user>> call, Throwable t) {

            }
        });

    }

 //Nhận data từ intent
    private void GetDataFragmentHome()
    {
        Product product=(Product) getIntent().getSerializableExtra("Product");
        setName_Product(product.getName_Product());
        setImg_Product(product.getImg_Product());
        setPrice_Product(product.getPrice_Product());
        setDiscount(product.getDiscount());
        int uid=getIntent().getIntExtra("Uid",1);
        setUid(uid);
        setiCodeProduct(product.getId());

    }
    //Ánh xạ
    private void Mapping()
    {
        tb_product_detail=findViewById(R.id.toolbar_product_detail);
        btn_Add_Product_in_cart=findViewById(R.id.btn_add_product_in_cart);
        btn_Back_Product_detail=findViewById(R.id.btn_back_product_detail);
        tv_Buy_now=findViewById(R.id.tv_Buy_now);
    }

    public int getMoney_User() {
        return Money_User;
    }

    public void setMoney_User(int money_User) {
        Money_User = money_User;
    }

    //Bắt sự kiện Button
    private void SetOnclick()
    {
        btn_Add_Product_in_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int s=0,p=0;
                s=getDiscount() * (getPrice_Product()/100);
                p=getPrice_Product()-s;
                for(int i=0;i<getProduct_add_in_cart_of_userList().size();i++)
                {
                    if(getProduct_add_in_cart_of_userList().get(i).getName_product_add_in_cart().equals(getName_Product())&& getProduct_add_in_cart_of_userList().get(i).getImg_product_add_in_cart().equals(getImg_Product()))
                    {
                        setExist(1);
                    }
                }
                if(getExist()==1)
                {
                    Toast.makeText(ActivityProductDetail.this,"Sản phẩm này đã có trong giỏi hàng của bạn",Toast.LENGTH_SHORT).show();
                }
                else {
                    AddProductInCart(p);
                }

            }
        });
        tb_product_detail.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id=item.getItemId();
                if(id==R.id.btn_Cart)
                {
                    Intent intent=new Intent(ActivityProductDetail.this,ActivityCart.class);
                    intent.putExtra("Uid",getUid());
                    startActivity(intent);
                }
                return true;
            }
        });
        btn_Back_Product_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    //Nhận thông tin cá nhân của người dùng từ api
    private void GetInformationUser()
    {
        ApiInformation.apiInfo.GetPersonalInformation().enqueue(new Callback<List<Personal_Information>>() {
            @Override
            public void onResponse(Call<List<Personal_Information>> call, Response<List<Personal_Information>> response) {
                List<Personal_Information> l=new ArrayList<Personal_Information>();
                l=response.body();
                for (Personal_Information personal_information:l)
                {
                    if(personal_information.getUid_Login()==getUid())
                    {
                        setAddress(personal_information.getAddress());
                        setFullName(personal_information.getFullName());
                        setTelephoneNumber(personal_information.getTelephone_Number());
                    }
                    else
                    {
                        setTelephoneNumber("");
                        setAddress("");
                        setFullName("");
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Personal_Information>> call, Throwable t) {

            }
        });
    }
    //Thêm sản phẩm muốn mja vào giỏ hàng
    private void AddProductInCart(int p)
    {

        ApiCart.apicart.AddproductInCartofUser(getUid(),getName_Product(),p,getImg_Product()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                UpdateNumberProductCartCount();
                Toast.makeText(ActivityProductDetail.this,"Đã thêm thành công",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ActivityProductDetail.this,"Failed",Toast.LENGTH_SHORT).show();

            }
        });
    }

    public String getName_Product() {
        return Name_Product;
    }

    public void setName_Product(String name_Product) {
        Name_Product = name_Product;
    }

    public int getPrice_Product() {
        return Price_Product;
    }

    public void setPrice_Product(int price_Product) {
        Price_Product = price_Product;
    }

    public int getDiscount() {
        return Discount;
    }

    public void setDiscount(int discount) {
        Discount = discount;
    }

    public String getImg_Product() {
        return Img_Product;
    }

    public void setImg_Product(String img_Product) {
        Img_Product = img_Product;
    }

    public int getUid() {
        return Uid;
    }

    public void setUid(int uid) {
        Uid = uid;
    }

    public List<Product_add_in_cart_of_user> getProduct_add_in_cart_of_userList() {
        return product_add_in_cart_of_userList;
    }

    public void setProduct_add_in_cart_of_userList(List<Product_add_in_cart_of_user> product_add_in_cart_of_userList) {
        this.product_add_in_cart_of_userList = product_add_in_cart_of_userList;
    }

    public int getExist() {
        return exist;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getTelephoneNumber() {
        return TelephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        TelephoneNumber = telephoneNumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public TextView getTv_Buy_now() {
        return tv_Buy_now;
    }

    public void setTv_Buy_now(TextView tv_Buy_now) {
        this.tv_Buy_now = tv_Buy_now;
    }

    public void setExist(int exist) {
        this.exist = exist;
    }

    public int getiCodeProduct() {
        return iCodeProduct;
    }

    public void setiCodeProduct(int iCodeProduct) {
        this.iCodeProduct = iCodeProduct;
    }

    public int getQuantity_in_stock() {
        return quantity_in_stock;
    }

    public void setQuantity_in_stock(int quantity_in_stock) {
        this.quantity_in_stock = quantity_in_stock;
    }

    public int getAmount_Product_buy() {
        return Amount_Product_buy;
    }

    public void setAmount_Product_buy(int amount_Product_buy) {
        Amount_Product_buy = amount_Product_buy;
    }

    public String getColor_Product() {
        return Color_Product;
    }

    public void setColor_Product(String color_Product) {
        Color_Product = color_Product;
    }

    @Override
    protected void onResume() {
        super.onResume();
        UpdateNumberProductCartCount();
    }
}