package com.example.appelectronicdevicesalessoftware;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import Adapter.Adapter_Notification;
import ConnectAPI.UpdateViewAndSendNotification;
import Fragment.Fragment_Bottom_Sheet_User_Buy_Product;
import Object.Notification_object;

import android.content.Intent;

import Fragment.Fragment_Bottom_Sheet_Delete_Notification;
import Object.Money_user;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import Object.Const;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import Object.fragment_information_option_setup;
import Adapter.Load_fragment_view_pager_Adapter;
import Retrofit.ApiCart;
import Retrofit.ApiMoney_User;
import Retrofit.ApiNotification;
import Utilities.Internet;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import Object.Product_add_in_cart_of_user;

public class MainActivity extends AppCompatActivity {
private ViewPager2 vp_load_fragment;
    private RecyclerView rcv_home;
    private Internet internet;
    public BadgeDrawable badgeDrawableNotification;
    public BadgeDrawable badgeDrawableNotificationCart;
    public Adapter_Notification adapter_notification;
    public int id_notification;
    public Notification_object notification_object;
    private MaterialToolbar toolbar;
    private int Id_Notification;
    private String Title_notification,Message_notification;
    private ImageButton btn_home_move_to_cart;
    private TextView tv_search1;
    private List<Product_add_in_cart_of_user> product_add_in_cart_of_userList;
    private List<Money_user> money_userList;
    private List<fragment_information_option_setup> mlist;
    private LinearLayout Top_toolbar;
    public  UpdateViewAndSendNotification updateViewAndSendNotificatiom;
    public  List<Notification_object> notificationList;
    public  List<Notification_object> notificationnotseenList;
    private Fragment_Bottom_Sheet_User_Buy_Product fragment_bottom_sheet_user_buy_product;
    private Fragment_Bottom_Sheet_Delete_Notification fragment_bottom_sheet_delete_notification;
    private int money_account_user;
    String Username;
    int UID_Login;
private BottomNavigationView Navigation_Bottom;
private Load_fragment_view_pager_Adapter load_fragment_view_pager_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Mapping();
        GetdataIntent();
        Initialization();
        GetMoneyAccountUser();
        PerformFunction();
        SetOnClick();



    }
   //Khởi tạo list
    public List<Notification_object> getOrCreateNotificationList() {
        if(notificationList==null) notificationList=new ArrayList<>();
        return notificationList;
    }
    //Khởi tạo list
    public List<Notification_object> getOrCreateNotificationNotSeenList() {
        if(notificationnotseenList==null) notificationnotseenList=new ArrayList<>();
        return notificationnotseenList;
    }
     //Ánh xạ
    private void Mapping()
    {   tv_search1=findViewById(R.id.tv_search_1);
        toolbar=findViewById(R.id.toolbar);
        vp_load_fragment=findViewById(R.id.view_pager_load_fragment);
        load_fragment_view_pager_adapter=new Load_fragment_view_pager_Adapter(this);
        Navigation_Bottom=findViewById(R.id.bottom_navication);
        internet=new Internet();

    }
   //Nhận dữ liệu từ intent
    private void GetdataIntent()
    {
        setUsername(getIntent().getStringExtra("UserName"));
        setUID_Login(getIntent().getIntExtra("UidLogin",1));
    }
    public void Show_Bottom_Sheet_Dialog_Fragment_User_Buy_Product()
    {
        fragment_bottom_sheet_user_buy_product.show(getSupportFragmentManager(),"Show");

    }
    public void Dismiss_Bottom_Sheet_Dialog_Fragment_User_Buy_Product()
    {
        fragment_bottom_sheet_user_buy_product.dismiss();

    }
    //Khởi tạo list
    private List<Product_add_in_cart_of_user> GetOrCreateProductInCart()
    {
        if(product_add_in_cart_of_userList==null) product_add_in_cart_of_userList=new ArrayList<>();
        return  product_add_in_cart_of_userList;
    }
     //nhận dữ liệu số tiền của ngời dùng từ api
    private void GetMoneyAccountUser()
    {
        ApiMoney_User.api_money_user.Get_Money_Account_User().enqueue(new Callback<List<Money_user>>() {
            @Override
            public void onResponse(Call<List<Money_user>> call, Response<List<Money_user>> response) {

                money_userList=response.body();
                setMoney_userList(money_userList);
                for(Money_user money_user:getMoney_userList())
                {
                    if(money_user.getUid() == getUID_Login())
                    {
                        setMoney_account_user(money_user.getMoney());
                    }
                }



            }

            @Override
            public void onFailure(Call<List<Money_user>> call, Throwable t) {

            }
        });

    }
    //Khởi tạo biến
    private  void Initialization()
    {
        adapter_notification=new Adapter_Notification(this);
        money_userList=new ArrayList<Money_user>();

        badgeDrawableNotification=Navigation_Bottom.getOrCreateBadge(R.id.btn_Notification);
        badgeDrawableNotification.setBackgroundColor(Color.RED);
        badgeDrawableNotification.setBadgeTextColor(Color.WHITE);
        badgeDrawableNotification.setMaxCharacterCount(3);
        badgeDrawableNotification.setVisible(false);

        badgeDrawableNotificationCart=BadgeDrawable.create(this);
        badgeDrawableNotificationCart.setBackgroundColor(Color.RED);
        badgeDrawableNotificationCart.setBadgeTextColor(Color.WHITE);
        badgeDrawableNotificationCart.setMaxCharacterCount(3);
        badgeDrawableNotificationCart.setVisible(false);
        BadgeUtils.attachBadgeDrawable(badgeDrawableNotificationCart,toolbar,R.id.btn_move_to_cart);

        updateViewAndSendNotificatiom=new UpdateViewAndSendNotification(this);

        fragment_bottom_sheet_delete_notification=new Fragment_Bottom_Sheet_Delete_Notification();

        fragment_bottom_sheet_user_buy_product =new Fragment_Bottom_Sheet_User_Buy_Product();
    }
    //Hiện thị bottom sheet delete notification
    public void ShowBottomSheetDeleteNotification()
    {
        fragment_bottom_sheet_delete_notification.show(getSupportFragmentManager(),"Show");
    }
    //Cập nhận số lượng trên icon notification
    public void setBadgeDrawableNotificationCart()
    {
        if(GetOrCreateProductInCart().size()==0)
        {
            badgeDrawableNotificationCart.setVisible(false);
        }
        else
        {
            badgeDrawableNotificationCart.setVisible(true);
            badgeDrawableNotificationCart.setNumber(GetOrCreateProductInCart().size());
        }
    }
    //Tắt bottomsheet
    public void DismissBottomSheetDeleteNotification()
    {
       fragment_bottom_sheet_delete_notification.dismiss();

    }
    //Update số lượng sản phẩm trong giỏ hàng
    private void UpdateProductCartCount()
    {
        ApiCart.apicart.GetProductInCart().enqueue(new Callback<List<Product_add_in_cart_of_user>>() {
            @Override
            public void onResponse(Call<List<Product_add_in_cart_of_user>> call, Response<List<Product_add_in_cart_of_user>> response) {
                List<Product_add_in_cart_of_user> list=new ArrayList<>();
                GetOrCreateProductInCart().clear();
                list=response.body();
                for(Product_add_in_cart_of_user product_add_in_cart_of_user:list)
                {
                    if(product_add_in_cart_of_user.getUid()==getUID_Login())
                    {
                        GetOrCreateProductInCart().add(product_add_in_cart_of_user);
                    }
                }
                setBadgeDrawableNotificationCart();

            }
            @Override
            public void onFailure(Call<List<Product_add_in_cart_of_user>> call, Throwable t) {

            }
        });
    }
    //Setdata ui
    private void PerformFunction()
    {
        vp_load_fragment.setAdapter(load_fragment_view_pager_adapter);
    }
    //Nhán list thông báo từ api
    public  void GetdataNotification()
    {
        ApiNotification.apiNotification.GetDataNotification().enqueue(new Callback<List<Notification_object>>() {
            @Override
            public void onResponse(Call<List<Notification_object>> call, Response<List<Notification_object>> response) {
                List<Notification_object>  l = new ArrayList<>();
                getOrCreateNotificationList().clear();
                getOrCreateNotificationNotSeenList().clear();
                l=response.body();
                if(l==null) return;
                for(Notification_object notification_object:l)
                {
                    if(getUID_Login()==notification_object.getUid() )
                    {
                       getOrCreateNotificationList().add(notification_object);
                       adapter_notification.SetData(getOrCreateNotificationList());
                    }
                }
                for(Notification_object notification_object1:getOrCreateNotificationList())
                    if( notification_object1.getView().equals(Const.NOTSEEN))
                    {
                        getOrCreateNotificationNotSeenList().add(notification_object1);
                        SetdataNotificationBadge();
                    }



            }

            @Override
            public void onFailure(Call<List<Notification_object>> call, Throwable t) {

            }
        });
    }
    //Bắt sựu kiện kiện button
    private void SetOnClick()
    {
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id=item.getItemId();
                if(id==R.id.btn_move_to_cart)
                {
                    Intent intent=new Intent(MainActivity.this,ActivityCart.class);
                    intent.putExtra("Uid",getUID_Login());
                    startActivity(intent);
                }
                return true;
            }
        });

        Navigation_Bottom.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if(id ==R.id.btn_home)
                {
                    vp_load_fragment.setCurrentItem(0);
                }
                else if(id==R.id.btn_Endow)
                {
                    vp_load_fragment.setCurrentItem(1);
                }
                else if(id==R.id.btn_history_transaction)
                {
                    vp_load_fragment.setCurrentItem(2);
                }
                else if(id ==R.id.btn_Notification)
                {
                    vp_load_fragment.setCurrentItem(3);
                }
                else if(id ==R.id.btn_Information)
                {
                    vp_load_fragment.setCurrentItem(4);

                }
                return true;
            }
        });
        vp_load_fragment.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position)
                {
                    case 0:
                        Navigation_Bottom.getMenu().findItem(R.id.btn_home).setChecked(true);
                        break;
                    case 1:
                        Navigation_Bottom.getMenu().findItem(R.id.btn_Endow).setChecked(true);
                        break;
                    case 2:
                        Navigation_Bottom.getMenu().findItem(R.id.btn_history_transaction).setChecked(true);
                        break;
                    case 3:
                        Navigation_Bottom.getMenu().findItem(R.id.btn_Notification).setChecked(true);
                        break;
                    case 4:
                        Navigation_Bottom.getMenu().findItem(R.id.btn_Information).setChecked(true);
                        break;

                }
            }


        });

        tv_search1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ActivityHistorySearch.class);
                intent.putExtra("Uid",getUID_Login());
                startActivity(intent);

            }
        });

    }
   //set dữ liệu số thông báo để giển thị lên icon notification
    public void SetdataNotificationBadge ()
    {
        if(getOrCreateNotificationNotSeenList().size()<=0)
        {
            badgeDrawableNotification.setVisible(false);
        }
        else {
            badgeDrawableNotification.setVisible(true);
            badgeDrawableNotification.setNumber(getOrCreateNotificationNotSeenList().size());
        }
    }

    public int getMoney_account_user() {
        return money_account_user;
    }

    public void setMoney_account_user(int money_account_user) {
        this.money_account_user = money_account_user;
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(internet,intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(internet);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public int getUID_Login() {
        return UID_Login;
    }

    public void setUID_Login(int UID_Login) {
        this.UID_Login = UID_Login;
    }

    public List<Money_user> getMoney_userList() {
        return money_userList;
    }

    public void setMoney_userList(List<Money_user> money_userList) {
        this.money_userList = money_userList;
    }

    public String getTitle_notification() {
        return Title_notification;
    }

    public void setTitle_notification(String title_notification) {
        Title_notification = title_notification;
    }

    public String getMessage_notification() {
        return Message_notification;
    }

    public void setMessage_notification(String message_notification) {
        Message_notification = message_notification;
    }

    @Override
    protected void onResume() {
        super.onResume();
        UpdateProductCartCount();
        GetMoneyAccountUser();
        GetdataNotification();

    }

    public int getId_Notification() {
        return Id_Notification;
    }

    public void setId_Notification(int id_Notification) {
        Id_Notification = id_Notification;
    }

}
