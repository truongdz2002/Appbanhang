package Adapter;

import android.content.Context;
import android.icu.number.CompactNotation;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.bumptech.glide.Glide;
import com.example.appelectronicdevicesalessoftware.R;

import java.util.List;

import Object.Imageadvertisement;

public class AdapterImageAdvetisement  extends RecyclerView.Adapter<AdapterImageAdvetisement.AdapterImageAdvetisementViewHolder>{
   private List<Imageadvertisement> imageadvertisementList;
   private Context context;

    public AdapterImageAdvetisement(Context context) {
        this.context = context;
    }

    public void setdata(List<Imageadvertisement> imageadvertisementList)
   {
       this.imageadvertisementList=imageadvertisementList;
       notifyDataSetChanged();
   }
    @NonNull
    @Override
    public AdapterImageAdvetisementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item__advertisement,parent,false);
        return new AdapterImageAdvetisementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterImageAdvetisementViewHolder holder, int position) {
    Imageadvertisement imageadvertisement=imageadvertisementList.get(position);
    if (imageadvertisement==null)
    {
        return;
    }
        Glide.with(holder.img_Advetisement.getContext()).load(imageadvertisement.getLinkImageAdvertisement()).error(R.drawable.logo).into(holder.img_Advetisement);

    }

    @Override
    public int getItemCount() {
       if(imageadvertisementList!=null)
       {
           return imageadvertisementList.size();
       }
        return 0;
    }

    public class AdapterImageAdvetisementViewHolder extends RecyclerView.ViewHolder{
        private ImageView img_Advetisement;
        public AdapterImageAdvetisementViewHolder(@NonNull View itemView) {
            super(itemView);
            img_Advetisement=itemView.findViewById(R.id.item_advertisement);

        }
    }

}
