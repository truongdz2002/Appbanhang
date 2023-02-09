package Fragment;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import Object.Const;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appelectronicdevicesalessoftware.ActivityBillPayment;
import com.example.appelectronicdevicesalessoftware.MainActivity;
import com.example.appelectronicdevicesalessoftware.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import Object.Const;

import Adapter.Adapter_Notification;
import Retrofit.ApiNotification;
import Utilities.PushNotification;
import Object.Notification_object;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Notification extends Fragment {

    private View view;
    private int Sent;
    private RecyclerView rcv_notification;
    private MainActivity mainActivity;
    private PushNotification pushNotification;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment__notification, container, false);
        Mapping();
        Init();
        SetdataUi();
        SetControlClick();

        return view;
    }
    //Ánh xạ
    private void Mapping()
    {

        rcv_notification=view.findViewById(R.id.rcv_notification);
    }


    //hiển thị dữ liệu lên ui
    private void SetdataUi()
    {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mainActivity);
        rcv_notification.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(mainActivity,DividerItemDecoration.VERTICAL);
        rcv_notification.addItemDecoration(dividerItemDecoration);
        rcv_notification.setAdapter(mainActivity.adapter_notification);

    }
    //Khởi tạo biến
    private void Init()
    {
        mainActivity=(MainActivity) getActivity();
    }
    //Bắt sự kiện button
    private void SetControlClick()
    {
        mainActivity.adapter_notification.SetItemClick(new Adapter_Notification.ItemClick() {
            @Override
            public void itemClickview(Notification_object notification_object) {
                mainActivity.getOrCreateNotificationNotSeenList().remove(notification_object);
                mainActivity.updateViewAndSendNotificatiom.UpdatedataNotification(notification_object.getId(),Const.SEEN,Const.SENT);
                mainActivity.SetdataNotificationBadge();
                Intent intent=new Intent(mainActivity, ActivityBillPayment.class);
                startActivity(intent);

            }
            @Override
            public void itemClickDeleteNotification(Notification_object notification_object) {
                mainActivity.id_notification=notification_object.getId();
                mainActivity.notification_object=notification_object;
                mainActivity.ShowBottomSheetDeleteNotification();
            }
        });
    }

}