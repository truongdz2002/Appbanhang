package ConnectAPI;

import Retrofit.ApiBanHang;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProduct {
    private int amount;
    private int idProduct;
    private String colorproduct;

    public UpdateProduct() {
    }

    public void UpdateProduct(int idProduct,int amount,String colorproduct)
    {
        ApiBanHang.apiHang.UpdateAmountProduct(amount,idProduct,colorproduct).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
