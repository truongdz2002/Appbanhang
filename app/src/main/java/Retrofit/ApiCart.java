package Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import Object.Const;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import Object.Product_add_in_cart_of_user;

public interface ApiCart {
    Gson gson=new GsonBuilder().setDateFormat("dd-MM-yy").create();
    ApiCart apicart=new  Retrofit.Builder()
            .baseUrl(Const.Key_BaseUrl+"/product_in_cart_of_user/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiCart.class);
    @FormUrlEncoded
    @POST("post_data_user_add_cart.php")
    Call<ResponseBody> AddproductInCartofUser(@Field(Const.Key_Uid) int Uid,
                                              @Field(Const.Key_Name_Product) String NameProduct,
                                              @Field(Const.Key_Price_Product) int Price_Product,
                                              @Field(Const.Key_ImgProduct) String ImgProduct);
    @GET("get_data_product_in_cart_of_user.php")
    Call<List<Product_add_in_cart_of_user>> GetProductInCart();

    @FormUrlEncoded
    @POST("Delete_product_buy_in_cart.php")
    Call<ResponseBody> DeleteProduct_buy_in_cart(@Field(Const.Key_Id) int Id);

}
