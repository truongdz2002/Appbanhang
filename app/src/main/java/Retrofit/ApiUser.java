package Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import Object.User;
import retrofit2.http.Part;
import Object.Const;

public interface ApiUser {
    Gson gson=new GsonBuilder().setDateFormat("dd-MM-yy").create();
    ApiUser apiUser=new Retrofit.Builder()
            .baseUrl(Const.Key_BaseUrl + "/Account/")
             .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiUser.class);
    @FormUrlEncoded
    @POST("postdataaccount.php")
    Call<ResponseBody> RegisterAccount(@Field(Const.Key_UserName) String  UserName,
                                       @Field(Const.Key_Password)String  Password);
    @GET("getdataaccount.php")
    Call<List<User>> GetAccountUsers();
    

}
