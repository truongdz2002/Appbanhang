package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appelectronicdevicesalessoftware.R;

import java.text.DecimalFormat;
import java.util.List;

import Object.Product_add_in_cart_of_user;

public class Adapter_product_add_in_cart extends RecyclerView.Adapter<Adapter_product_add_in_cart.Adapter_product_add_in_cartViewHolder> {
    private  View view;
    private Context context;
    private ItemCick itemCick;
    int choose;

    private List<Product_add_in_cart_of_user> product_add_in_cart_of_userList;
    public interface ItemCick
    {
        void checked(Product_add_in_cart_of_user product_add_in_cart_of_user,int position);
        void Unchecked(Product_add_in_cart_of_user product_add_in_cart_of_user,int position);
    }

    public void setdata(List<Product_add_in_cart_of_user> product_add_in_cart_of_userList)
    {
        this.product_add_in_cart_of_userList=product_add_in_cart_of_userList;
        notifyDataSetChanged();
    }

    public void setChoose(int choose) {
        this.choose = choose;
        notifyDataSetChanged();
    }

    public Adapter_product_add_in_cart(Context context, ItemCick itemCick) {
        this.context = context;
        this.choose=choose;
        this.itemCick=itemCick;
    }

    @NonNull
    @Override
    public Adapter_product_add_in_cartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart,parent,false);
        return new Adapter_product_add_in_cartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_product_add_in_cartViewHolder holder, int position) {
        String pattern="###,###,###";
        DecimalFormat decimalFormat=new DecimalFormat(pattern);
      Product_add_in_cart_of_user product_add_in_cart_of_user=product_add_in_cart_of_userList.get(position);
      if(product_add_in_cart_of_user==null)
      {
          return;
      }
        Glide.with(holder.img_product_add_in_cart.getContext()).load(product_add_in_cart_of_user.getImg_product_add_in_cart()).into(holder.img_product_add_in_cart);
        holder.tv_name_product_add_in_cart.setText(product_add_in_cart_of_user.getName_product_add_in_cart());
        holder.tv_price_product_add_in_cart.setText(String.valueOf(decimalFormat.format(product_add_in_cart_of_user.getPrice_product_add_in_cart())));
        if(choose==1)
        {
            holder.ck_option_product_in_cart.setChecked(true);

        }
        else
        {
            holder.ck_option_product_in_cart.setChecked(false);
        }
        holder.ck_option_product_in_cart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               if(b)
               {
                   itemCick.checked(product_add_in_cart_of_user,position);
               }
               else
               {
                   itemCick.Unchecked(product_add_in_cart_of_user,position);
               }


            }
        });




    }

    @Override
    public int getItemCount() {
        if(product_add_in_cart_of_userList != null)
        {
            return product_add_in_cart_of_userList.size();
        }
        return 0;
    }

    public class Adapter_product_add_in_cartViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView img_product_add_in_cart;
        private TextView tv_name_product_add_in_cart,tv_price_product_add_in_cart;
        private CheckBox ck_option_product_in_cart;
        public Adapter_product_add_in_cartViewHolder(@NonNull View itemView) {
            super(itemView);
            img_product_add_in_cart=itemView.findViewById(R.id.img_product_catr);
            tv_name_product_add_in_cart=itemView.findViewById(R.id.tv_name_product_cart);
            tv_price_product_add_in_cart=itemView.findViewById(R.id.tv_price_product_cart);
            ck_option_product_in_cart=itemView.findViewById(R.id.ck_option_product_buy);
        }
    }
}
