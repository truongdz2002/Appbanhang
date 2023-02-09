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
import Object.Money_user;
import retrofit2.http.POST;

public interface ApiMoney_User {
    Gson gson=new GsonBuilder().setDateFormat("dd-MM-yy").create();
    ApiMoney_User api_money_user= new Retrofit.Builder()
            .baseUrl(Const.Key_BaseUrl+"/money_user/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiMoney_User.class);
    @GET("GetMoney_Account_User.php")
    Call<List<Money_user>> Get_Money_Account_User();
    @FormUrlEncoded
    @POST("Update_Monney_User.php")
    Call<ResponseBody> Update_Money_Account_User(@Field(Const.Key_Uid) int Uid,
                                                 @Field(Const.Key_Money_User) int money);
}
