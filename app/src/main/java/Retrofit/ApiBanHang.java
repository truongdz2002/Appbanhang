package Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import Object.Product_Model;
import Object.Characteristic_product;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import Object.Const;
import retrofit2.http.POST;

public interface ApiBanHang {
    Gson gson=new GsonBuilder().setDateFormat("dd-MM-yy").create();

    ApiBanHang apiHang=new Retrofit.Builder()
            .baseUrl(Const.Key_BaseUrl + "/banhang/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiBanHang.class);

    @GET("getdata.php")
    Call<Product_Model> getListProduct();
    @FormUrlEncoded
    @POST("Updatecharacteristic_product.php")
    Call<ResponseBody> UpdateAmountProduct(@Field(Const.Key_Amount) int amount,
                                           @Field(Const.Key_Code_Product)int id_product,
                                           @Field(Const.Key_Color_Product) String colorproduct);
    @GET("getdatacharacteristic_product.php")
    Call<List<Characteristic_product>> GetListCharacteristicProduct();
}
