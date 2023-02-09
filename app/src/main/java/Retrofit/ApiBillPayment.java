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
import Object.BillPayment;
import retrofit2.http.POST;

public interface ApiBillPayment {
    Gson gson=new GsonBuilder().setDateFormat("dd-MM-yy").create();
    ApiBillPayment apiBillPayment=new Retrofit.Builder()
            .baseUrl(Const.Key_BaseUrl+"/BillPayment/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiBillPayment.class);
    @GET("getdatabillpayment.php")
    Call<List<BillPayment>> GetDataBillPayment();
    @FormUrlEncoded
    @POST("postdatabillpayment.php")
    Call<ResponseBody> PostDataBillPayment(@Field(Const.Key_Uid) int Uid,
                                           @Field(Const.Key_FullName) String fullname,
                                           @Field(Const.Key_Address) String Address,
                                           @Field(Const.Key_TotalMoney) int totalMoney,
                                           @Field(Const.Key_Telephone_Number)String telephoneNumber,
                                           @Field(Const.Key_OrderDate ) String OrderDate,
                                           @Field(Const.Key_PaymentMethod) String paymentMethod,
                                           @Field(Const.Key_ImgProduct) String imgProduct,
                                           @Field(Const.Key_Name_Product) String NameProduct);
}
