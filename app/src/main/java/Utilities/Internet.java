package Utilities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.appelectronicdevicesalessoftware.R;

import Login.ActivityLogin;

public class Internet extends BroadcastReceiver {
    private Intent intent;

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
       if(ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction()))
       {
           if(!isNetWorkAvailable(context))
           {
               TipsInternet(context);
           }

       }
    }
    private static boolean isNetWorkAvailable(Context context)
    {
        ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager==null)
        {
            return  false;
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            Network network=connectivityManager.getActiveNetwork();
            if(network == null)
            {
                return false;
            }
            NetworkCapabilities capabilities=connectivityManager.getNetworkCapabilities(network);
            return capabilities !=null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)|| capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
        }
        else
        {
            NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
            return networkInfo !=null && networkInfo.isConnected();
        }
    }
    private void TipsInternet(Context context)
    {
        final Dialog dialog =new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.notification_nointernet);
        Window window= dialog.getWindow();
        if(window==null)
        {
            return;
        }
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
         Button btn_relogin=dialog.findViewById(R.id.btn_relogin);
         Button btn_retry=dialog.findViewById(R.id.btn_retry);
         btn_relogin.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent=new Intent(context,ActivityLogin.class);
                 context.startActivity(intent);
               dialog.dismiss();
             }
         });
         btn_retry.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if (isNetWorkAvailable(context)) {
                     Handler handler =new Handler();
                     handler.postDelayed(new Runnable() {
                         @Override
                         public void run() {

                         }
                     },3000);


                 }
             }
         });
         dialog.show();

    }
}
