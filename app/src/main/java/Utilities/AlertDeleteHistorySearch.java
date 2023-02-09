package Utilities;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.appelectronicdevicesalessoftware.R;

import Retrofit.ApiHistorySearch;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlertDeleteHistorySearch {
    private Context context;
    private final Dialog dialog;
    private Button btn_Ok;
    private Button btn_Cancel;

    public AlertDeleteHistorySearch(Context context) {
        this.context = context;
        dialog=new Dialog(context);
    }
    public void DeleteHistoryTextSearched(String TextSearched)
    {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alert_dialog_delete_text_searched);
        Window window=dialog.getWindow();
        if(window==null)
        {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        Mapping();
        SetControlClick(TextSearched);
        dialog.show();



    }
    private void Mapping()
    {
        btn_Ok=dialog.findViewById(R.id.btn_Confirm_delete_text_search);
        btn_Cancel=dialog.findViewById(R.id.btn_cancel_delete_text_search);
    }
    private void SetControlClick(String TextSearched)
    {
        btn_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 DeleteTextSearch(TextSearched);
                dialog.dismiss();
            }
        });
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
    private void DeleteTextSearch(String TextSearched)
    {
        ApiHistorySearch.apiHistorySearch.DeleteProduct_list_history_searched(TextSearched).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
