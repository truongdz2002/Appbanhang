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
import Object.HistorySearch;
public interface ApiHistorySearch {
    Gson gson=new GsonBuilder().setDateFormat("dd-MM-yy").create();
    ApiHistorySearch apiHistorySearch= new Retrofit.Builder()
            .baseUrl(Const.Key_BaseUrl+"/list_history_search_of_user/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiHistorySearch.class);
    @FormUrlEncoded
    @POST("post_data_list_search_of_user.php")
    Call<ResponseBody> AddTextHistorySearchOfUser(@Field(Const.Key_Uid) int Uid,
                                                  @Field(Const.Key_TextHistory) String TextSearch);

    @GET("get_data_list_history_search_of_user.php")
    Call<List<HistorySearch>>  GetlistHistorySearch();
    @FormUrlEncoded
    @POST("delete_list_history_search_user.php")
    Call<ResponseBody> DeleteProduct_list_history_searched(@Field(Const.Key_TextHistory) String  TextSearch);
}
