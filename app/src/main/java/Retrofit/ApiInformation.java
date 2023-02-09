package Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import Object.Personal_Information;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import Object.Const;
import retrofit2.http.Part;
import Object.GetApiSuccess;

public interface ApiInformation {
    Gson gson=new GsonBuilder().setDateFormat("dd-MM-yy").create();
    ApiInformation apiInfo=new Retrofit.Builder()
            .baseUrl(Const.Key_BaseUrl + "/Information/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiInformation.class);
    @Multipart
    @POST("postimgavatar.php")
    Call<ResponseBody> UploadAvatar(@Part MultipartBody.Part ImageAvatar);

    @FormUrlEncoded
    @POST("postdatainformation.php")
    Call<GetApiSuccess> ConfirmInformation(@Field(Const.Key_Uid) int Uid,
                                          @Field(Const.Key_Img_Avatar) String ImgAvatar,
                                          @Field(Const.Key_FullName) String FullName,
                                          @Field(Const.Key_Date_Of_Birth) String DateOfBirth,
                                          @Field(Const.Key_Telephone_Number) String TelephoneNumber,
                                          @Field(Const.Key_Address) String Address);
    @GET("getdatainformation.php")
    Call<List<Personal_Information>> GetPersonalInformation();
    @FormUrlEncoded
    @POST("Update_Information.php")
    Call<GetApiSuccess> UpdateInformation (@Field(Const.Key_Uid) int Uid,
                                           @Field(Const.Key_Img_Avatar) String ImgAvatar,
                                           @Field(Const.Key_FullName) String FullName,
                                           @Field(Const.Key_Date_Of_Birth) String DateOfBirth,
                                           @Field(Const.Key_Telephone_Number) String TelephoneNumber,
                                           @Field(Const.Key_Address) String Address);
}
//bg

