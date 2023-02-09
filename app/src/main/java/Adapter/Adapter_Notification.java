package Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import Object.Const;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appelectronicdevicesalessoftware.R;

import java.util.List;
import Object.Notification_object;

public class Adapter_Notification extends RecyclerView.Adapter<Adapter_Notification.NotificationViewHolder> {
  private View view;
  private List<Notification_object> listNotifications;
  private Context context;
  private ItemClick itemClick;
  public interface ItemClick{
      void itemClickview(Notification_object notification_object);
      void itemClickDeleteNotification(Notification_object notification_object);
  }
  public void SetData(List<Notification_object> listNotifications)
  {
      this.listNotifications=listNotifications;
      notifyDataSetChanged();
  }
  public void SetItemClick(ItemClick itemClick)
  {
      this.itemClick=itemClick;
      notifyDataSetChanged();

  }

    public Adapter_Notification(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custem_notification,parent,false);
      return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
       Notification_object notification=listNotifications.get(position);
        holder.tv_message.setText(notification.getMessage_Notification());
        holder.tv_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.itemClickview(notification);
            }
        });
        holder.tv_Title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.itemClickview(notification);
            }
        });
       holder.tv_Title.setText(notification.getTitle_Notification());
       if(notification.getView().equals(Const.SEEN))
       {
           holder.form_item_notification.setBackgroundColor(Color.GRAY);
       }
       else if(notification.getView().equals(Const.NOTSEEN))
       {
           holder.form_item_notification.setBackgroundColor(Color.WHITE);
       }
       holder.btn_option_with_item_notification.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               itemClick.itemClickDeleteNotification(notification);
           }
       });
       holder.tv_time_send_notification.setText(notification.getTime());

    }
    @Override
    public int getItemCount() {
       if(listNotifications!=null)
       {
           return listNotifications.size();
       }
        return 0;
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tv_Title,tv_message,tv_time_send_notification;
        private RelativeLayout form_item_notification;
        private ImageButton btn_option_with_item_notification;
        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_message=itemView.findViewById(R.id.tv_Message_Notification);
            tv_Title=itemView.findViewById(R.id.tv_Title_Notification);
            tv_time_send_notification=itemView.findViewById(R.id.tv_time_send_notification);
            form_item_notification=itemView.findViewById(R.id.form_item_notification);
            btn_option_with_item_notification=itemView.findViewById(R.id.btn_option_with_item_notification);
        }
    }
}
