package Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import Object.Imageadvertisement;
import Object.Const;

public interface ApiAdvertisement {
    Gson gson=new GsonBuilder().setDateFormat("dd-MM-yy").create();
    ApiAdvertisement apiAdvertisement=new  Retrofit.Builder()
            .baseUrl(Const.Key_BaseUrl + "/advertisement/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiAdvertisement.class);

    @GET("getdataadvertisement.php")
    Call<List<Imageadvertisement>> Getdata() ;
}
