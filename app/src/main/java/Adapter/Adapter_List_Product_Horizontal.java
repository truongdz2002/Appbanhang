package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appelectronicdevicesalessoftware.ActivityProductDetail;
import com.example.appelectronicdevicesalessoftware.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;
import Object.Product;
import Object.Product_Model;

public class Adapter_List_Product_Horizontal extends RecyclerView.Adapter<Adapter_List_Product_Horizontal.Adapter_List_Product_HorizontalViewHolder>{
private View view;
private Context context;
int Uid;
int Price_Product;
private List<Product_Model> product_modelList;
public void setdata(List<Product_Model> product_modelList)
{
    this.product_modelList=product_modelList;
    notifyDataSetChanged();
}
public void SetUid(int uid)
{
    this.Uid=uid;
}

public int getUid()
{
        return Uid;
}

    public int getPrice_Product() {
        return Price_Product;
    }

    public void setPrice_Product(int price_Product) {
        Price_Product = price_Product;
    }

    public Adapter_List_Product_Horizontal(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter_List_Product_HorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rcv,parent,false);
        return new Adapter_List_Product_HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_List_Product_HorizontalViewHolder holder, int position) {
        Product_Model product_model=product_modelList.get(position);
        if(product_model==null)
        {
            return;
        }
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
        holder.rcv.setLayoutManager(linearLayoutManager);
        Adapter_product_mini adapter_product_mini=new Adapter_product_mini(context, new Adapter_product_mini.ItemClickProduct() {
            @Override
            public void ItemClickproduct(Product product) {
                 Intent intent=new Intent(context, ActivityProductDetail.class);
                 intent.putExtra("Product",product);
                 intent.putExtra("Uid",getUid());
                 context.startActivity(intent);

            }
        });
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL);
        holder.rcv.addItemDecoration(dividerItemDecoration);
        DividerItemDecoration dividerItemDecoration2=new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);
        holder.rcv.addItemDecoration(dividerItemDecoration2);
        List<Product> list=new ArrayList<>();
        List<Product> listproduct=new ArrayList<>();
        list=product_model.getResult();

        for(Product product :list)
        {
            if(product.getPrice_Product()<=getPrice_Product())
            {
                listproduct.add(product);
                adapter_product_mini.setdata(listproduct);
                holder.rcv.setAdapter(adapter_product_mini);
            }
        }


    }

    @Override
    public int getItemCount() {
    if(product_modelList!=null)
    {
        return product_modelList.size();
    }
        return 0;
    }

    public class  Adapter_List_Product_HorizontalViewHolder extends RecyclerView.ViewHolder
    {
        private RecyclerView rcv;
        public Adapter_List_Product_HorizontalViewHolder(@NonNull View itemView) {
            super(itemView);
            rcv=itemView.findViewById(R.id.rcv_list_product_horizotal);
        }
    }
}
