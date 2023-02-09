package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appelectronicdevicesalessoftware.R;
import Object.user_buy_product;
import Utilities.Format_money;

import java.util.List;

public class Adapter_user_buy_product extends RecyclerView.Adapter<Adapter_user_buy_product.Adapter_user_buy_product_ViewHolder> {
   private View view;
   private List<user_buy_product> list;
   private Context context;

    public Adapter_user_buy_product(Context context) {
        this.context = context;
    }

    public void Setdata(List<user_buy_product> list)
    {
        this.list=list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Adapter_user_buy_product_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_buy_product,parent,false);
        return new Adapter_user_buy_product_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_user_buy_product_ViewHolder holder, int position) {
      user_buy_product current=list.get(position);
        Format_money format_money=new Format_money();
      if(current==null)
      {
          return;
      }
      holder.tv_money.setText(format_money.FormatMoney(current.getMoney_buy_product())+"VND");
      holder.tv_name_product.setText(current.getProduct_name_buy());
      holder.tv_name_user.setText(current.getUser_Name());
      holder.tv_delivery.setText(current.getDelivery());

    }

    @Override
    public int getItemCount() {
        if(list!=null)
        {
            return  list.size();
        }
        return 0;
    }

    public class Adapter_user_buy_product_ViewHolder extends RecyclerView.ViewHolder
  {
      private  TextView tv_name_user ,tv_name_product ,tv_money,tv_delivery;

      public Adapter_user_buy_product_ViewHolder(@NonNull View itemView) {
          super(itemView);
           tv_name_user=itemView.findViewById(R.id.tv_Name_user);
           tv_name_product=itemView.findViewById(R.id.tv_product_buy);
           tv_money=itemView.findViewById(R.id.tv_money_payment_product);
           tv_delivery=itemView.findViewById(R.id.tv_delivery);


      }
  }
}
