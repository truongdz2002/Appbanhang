package ConnectAPI;

import android.content.Context;

import Retrofit.ApiNotification;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateViewAndSendNotification {
    private Context context;

    public UpdateViewAndSendNotification(Context context) {
        this.context = context;
    }
    public void UpdatedataNotification(int Id,String View,String Send)
    {
        ApiNotification.apiNotification.UpdatedataNotification(Id,View,Send).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
