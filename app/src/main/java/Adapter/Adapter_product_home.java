package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appelectronicdevicesalessoftware.R;

import java.text.DecimalFormat;
import java.util.List;
import Object.Product;

public class Adapter_product_home extends  RecyclerView.Adapter<Adapter_product_home.Adapter_product_homeViewHoder>{
    private List<Product> listProduct;
    private Context context;
    private ItemClickProduct itemClickproduct;
    public interface ItemClickProduct
    {
        void ItemClickproduct(Product product);
    }

    public Adapter_product_home(Context context,ItemClickProduct itemClickproduct) {
        this.context = context;
        this.itemClickproduct=itemClickproduct;
    }

    public void setdata(List<Product> productList)
    {
        this.listProduct=productList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public Adapter_product_homeViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        return new Adapter_product_homeViewHoder(view);
    }

    @Override
    public int getItemCount() {
        if(listProduct!=null)
        {
            return listProduct.size();
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_product_homeViewHoder holder, int position) {
     String pattern="###,###,###";
     DecimalFormat decimalFormat=new DecimalFormat(pattern);
     Product product=listProduct.get(position);
     if(product==null)
     {
         return;
     }
        Glide.with(holder.img_product.getContext()).load(product.getImg_Product()).into(holder.img_product);
       holder.img_product.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               itemClickproduct.ItemClickproduct(product);
           }
       });
        holder.tv_name_product.setText(product.getName_Product());
        holder.tv_name_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickproduct.ItemClickproduct(product);
            }
        });
        holder.tv_spcial_price_product.setText(String.valueOf(product.getDiscount()));
        holder.tv_spcial_price_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickproduct.ItemClickproduct(product);
            }
        });
        holder.tv_price_product.setText(String.valueOf(decimalFormat.format(product.getPrice_Product())));
        holder.tv_price_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickproduct.ItemClickproduct(product);
            }
        });
    }

    public class Adapter_product_homeViewHoder extends RecyclerView.ViewHolder
 {
     private ImageView img_product;
     private TextView tv_name_product,tv_spcial_price_product,tv_price_product;
     public Adapter_product_homeViewHoder(@NonNull View itemView) {
         super(itemView);
         img_product=itemView.findViewById(R.id.img_product);
         tv_name_product=itemView.findViewById(R.id.name_product);
         tv_spcial_price_product=itemView.findViewById(R.id.special_price_product);
         tv_price_product=itemView.findViewById(R.id.price_product);
     }
 }
}
