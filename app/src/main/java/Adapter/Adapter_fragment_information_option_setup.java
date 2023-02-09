package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appelectronicdevicesalessoftware.ActivityBillPayment;
import com.example.appelectronicdevicesalessoftware.ActivityInformation;
import com.example.appelectronicdevicesalessoftware.R;

import java.util.List;

import DatabaseUserOffline.DataBaseUserOffline;
import Login.ActivityLogin;
import Object.fragment_information_option_setup;

public class Adapter_fragment_information_option_setup extends RecyclerView.Adapter<Adapter_fragment_information_option_setup.Adapter_fragment_information_option_setupViewHolder>{
   private View view;
   private Context context;
   int Uid;

    public int getUid() {
        return Uid;
    }

    public void setUid(int uid) {
        Uid = uid;
    }

    private List<fragment_information_option_setup> list;

    public Adapter_fragment_information_option_setup(Context context,List<fragment_information_option_setup> list,int Uid) {
        this.context = context;
        this.list=list;
        this.Uid=Uid;
    }

    @NonNull
    @Override
    public Adapter_fragment_information_option_setupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_frament_information_option_setup,parent,false);
        return new Adapter_fragment_information_option_setupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_fragment_information_option_setupViewHolder holder, int position) {
     fragment_information_option_setup f_information_option_setup=list.get(position);
     if (f_information_option_setup==null)
     {
         return;

     }
     holder.tv_option_setup.setText(f_information_option_setup.getOption_setup());
      switch (position)
      {
          case 0:
              holder.tv_option_setup.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      Intent intent=new Intent(context, ActivityInformation.class);
                      intent.putExtra("Uid",getUid());
                      context.startActivity(intent);
                  }
              });
              break;
          case 1:
              holder.tv_option_setup.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      Intent intent=new Intent(context, ActivityBillPayment.class);
                      intent.putExtra("Uid",getUid());
                      context.startActivity(intent);
                  }
              });
              break;
          case 2:break;
          case 3:break;
          case 4:break;
          case 5:break;
          case 6:break;
          case 7:break;
          case 8:break;
          case 9:break;
          case 10:
              holder.tv_option_setup.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      DataBaseUserOffline.getInstance(context).userOffline().DeleteUserSaveOffline();
                      Intent intent=new Intent(context, ActivityLogin.class);
                      context.startActivity(intent);
                  }
              });


              break;



      }
    }

    @Override
    public int getItemCount() {
       if(list!=null)
       {
           return list.size();
       }
        return 0;
    }

    public class Adapter_fragment_information_option_setupViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tv_option_setup;
        public Adapter_fragment_information_option_setupViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_option_setup=itemView.findViewById(R.id.tv_option_setup);
        }
    }
}
