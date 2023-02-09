package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appelectronicdevicesalessoftware.R;

import java.util.List;

import Object.Option_sort_price;

public class Adapter_optoin_sort_price extends ArrayAdapter<Option_sort_price> {

    public Adapter_optoin_sort_price(@NonNull Context context, int resource, @NonNull List<Option_sort_price> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_printer_sort_price,parent,false);
        Option_sort_price option_sort_price=getItem(position);
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.custem_lines,parent,false);
       TextView tv_option_sort_price=convertView.findViewById(R.id.tv_option_sort_price);
        Option_sort_price option_sort_price=getItem(position);
        if(option_sort_price!=null)
        {
          tv_option_sort_price.setText(option_sort_price.getText_option_sort_price());
        }


        return convertView;
    }
}
