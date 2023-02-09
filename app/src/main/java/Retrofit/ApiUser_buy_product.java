package Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import Object.Const;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import Object.user_buy_product;

public interface ApiUser_buy_product {
    Gson gson=new GsonBuilder().setDateFormat("dd-MM_yy").create();
    ApiUser_buy_product api_user_buy_product=new Retrofit.Builder()
            .baseUrl(Const.Key_BaseUrl+"/list_user_buy_product/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiUser_buy_product.class);
    @FormUrlEncoded
    @POST("Post_user_buy_product_in_list_user_buy_product.php")
    Call<ResponseBody> AddUserBuyProduct(@Field(Const.Key_Uid) int Uid,
                                         @Field(Const.Key_UserName) String UserName,
                                         @Field(Const.Key_Address) String Address,
                                         @Field(Const.Key_Telephone_Number) String PhoneNumber,
                                         @Field(Const.Key_Name_Product) String NameProduct,
                                         @Field(Const.Key_Price_Product) int Price_Product,
                                         @Field(Const.Key_Delivery) String Delivery);
    @GET("Get_list_User_buy_product.php")
    Call<List<user_buy_product>> GetdataUserBuyProduct();
}
