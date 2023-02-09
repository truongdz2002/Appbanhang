package Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appelectronicdevicesalessoftware.R;

import java.util.List;
import Object.Characteristic_product;

public class Adapter_characteristic_product extends RecyclerView.Adapter<Adapter_characteristic_product.Adapter_characteristic_product_ViewHolder> {
   private View view;
   private Context context;
   private List<Characteristic_product> characteristic_productList;
   private ItemClick itemClick;
   private int choose;
   public interface ItemClick
   {
       void ItemClick(Characteristic_product characteristic_product,int position);
   }

    public void setChoose(int choose) {
        this.choose = choose;
        notifyDataSetChanged();
    }

    public Adapter_characteristic_product(Context context, ItemClick itemClick) {
        this.context = context;
        this.itemClick=itemClick;
    }
    public void setdata(List<Characteristic_product> characteristic_productList)
    {
        this.characteristic_productList=characteristic_productList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Adapter_characteristic_product_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_characteristic_product,parent,false);

        return new Adapter_characteristic_product_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_characteristic_product_ViewHolder holder, int position) {
     Characteristic_product characteristic_product=characteristic_productList.get(position);
     holder.tv_color_product_in_characteristic.setText(characteristic_product.getColor_product());
     holder.tv_color_product_in_characteristic.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             itemClick.ItemClick(characteristic_product,position);
         }
     });
     Glide.with(holder.img_product_in_characteristic.getContext()).load(characteristic_product.getImg_product()).error(R.drawable.logo).into(holder.img_product_in_characteristic);
    holder.img_product_in_characteristic.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            itemClick.ItemClick(characteristic_product,position);
        }
    });
    if(choose==position)
    {
        holder.form_option_type_product.setBackground(context.getDrawable(R.drawable.custom_edittext));
    }
    else
    {
        holder.form_option_type_product.setBackgroundColor(Color.parseColor("#00000000"));

    }

   }

    @Override
    public int getItemCount() {
        if(characteristic_productList!=null)
        {
            return characteristic_productList.size();
        }
        return 0;
    }

    public class Adapter_characteristic_product_ViewHolder extends RecyclerView.ViewHolder
  {
      private ImageView img_product_in_characteristic;
      private TextView tv_color_product_in_characteristic;
      private LinearLayout form_option_type_product;
      public Adapter_characteristic_product_ViewHolder(@NonNull View itemView) {
          super(itemView);
          img_product_in_characteristic=itemView.findViewById(R.id.img_product_in_characteristic);
          tv_color_product_in_characteristic=itemView.findViewById(R.id.tv_color_product_in_characteristic);
          form_option_type_product=itemView.findViewById(R.id.form_option_type_product);
      }
  }
}
