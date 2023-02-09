package Fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import Object.Const;

import com.example.appelectronicdevicesalessoftware.MainActivity;
import com.example.appelectronicdevicesalessoftware.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import ConnectAPI.DeleteNotification;
import Retrofit.ApiNotification;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import Object.Notification_object;


public class Fragment_Bottom_Sheet_Delete_Notification extends BottomSheetDialogFragment {
private View viewdialog;
private ImageButton btnDelete;
private ImageButton btnCancel;
private MainActivity mainActivity;
private DeleteNotification deleteNotification;
private BottomSheetDialog bottomSheetDialog;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        bottomSheetDialog=(BottomSheetDialog)super.onCreateDialog(savedInstanceState);
        viewdialog=LayoutInflater.from(getContext()).inflate(R.layout.fragment_bottom_sheet_delete_notification,null);
        Mapping();
        Init();
        SetControlClick();
        bottomSheetDialog.setContentView(viewdialog);
        return bottomSheetDialog;
    }
    //Ánh xạ
    private void Mapping()
    {
        btnDelete=viewdialog.findViewById(R.id.btn_delete_notification);
        btnCancel=viewdialog.findViewById(R.id.btn_cancel_delete_notification);
    }
    //Khởi tạo biến
    private void Init()
    {
        mainActivity=(MainActivity) getActivity();
        deleteNotification=new DeleteNotification(mainActivity);
    }
    //Bắt sự kiẹn buttom
    private void  SetControlClick()
    {
        //Đóng bottom sheet
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.DismissBottomSheetDeleteNotification();
            }
        });
        //Buttom xóa notification
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.getOrCreateNotificationList().remove(mainActivity.notification_object);
                mainActivity.getOrCreateNotificationNotSeenList().remove(mainActivity.notification_object);
                deleteNotification.deleteNotification(mainActivity.id_notification);
                mainActivity.adapter_notification.SetData(mainActivity.getOrCreateNotificationList());
                mainActivity.SetdataNotificationBadge();
                mainActivity.DismissBottomSheetDeleteNotification();

            }
        });
    }

}