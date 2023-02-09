package Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import Object.Const;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import Object.Notification_object;

public interface ApiNotification {
    Gson gson= new GsonBuilder().setDateFormat("dd:MM:ss").create();
    ApiNotification apiNotification=new  Retrofit.Builder()
            .baseUrl(Const.Key_BaseUrl+"/Notification/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiNotification.class);
    @FormUrlEncoded
    @POST("PostdataNotification.php")
    Call<ResponseBody> PostdataNotification(@Field(Const.Key_Uid) int Uid,
                                            @Field(Const.Key_Message_Notification) String  message,
                                            @Field(Const.Key_View) String  view,
                                            @Field(Const.Key_Title_Notification) String title,
                                            @Field(Const.Key_SEND) String Send,
                                            @Field(Const.Key_Time_Push_notification) String timePush);
    @GET("GetDataNotification.php")
    Call<List<Notification_object>> GetDataNotification();
    @FormUrlEncoded
    @POST("UpdateDataNotification.php")
    Call<ResponseBody> UpdatedataNotification(@Field(Const.Key_Id) int id,
                                              @Field(Const.Key_View) String  view,
                                              @Field(Const.Key_SEND) String Send);
    @FormUrlEncoded
    @POST("DeleteDataNotification.php")
    Call<ResponseBody> DeletedataNotification(@Field(Const.Key_Id) int id);


}
