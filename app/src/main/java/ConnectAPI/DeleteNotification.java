package ConnectAPI;

import android.content.Context;

import Retrofit.ApiNotification;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteNotification {
    private Context context;

    public DeleteNotification(Context context) {
        this.context = context;
    }
    public void deleteNotification(int id)
    {
        ApiNotification.apiNotification.DeletedataNotification(id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
