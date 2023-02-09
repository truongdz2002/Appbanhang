package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appelectronicdevicesalessoftware.R;

import java.text.DecimalFormat;
import java.util.List;

import Object.BillPayment;

public class Adapter_History_Transaction extends RecyclerView.Adapter<Adapter_History_Transaction.adapter_item_bill_list_monthly_ViewHolder> {
   private View view;
   private Context context;
   private List<BillPayment> billPaymentList;
   private ItemClick itemClick;
   public interface ItemClick
   {
       void ItemClick(BillPayment billPayment);
   }

    public Adapter_History_Transaction(Context context, ItemClick itemClick) {
        this.context = context;
        this.itemClick=itemClick;
    }
    public void setdata(List<BillPayment> billPaymentList)
    {
      this.billPaymentList=billPaymentList;
      notifyDataSetChanged();
    }

    @NonNull
    @Override
    public adapter_item_bill_list_monthly_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_bill_monthly,parent,false);
        return new adapter_item_bill_list_monthly_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_item_bill_list_monthly_ViewHolder holder, int position) {
          String pattern="###,###,###";
          DecimalFormat decimalFormat=new DecimalFormat(pattern);
          BillPayment payment=billPaymentList.get(position);
          holder.tv_Total_monney_item_list_bill_monthly.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  itemClick.ItemClick(payment);
              }
          });
          holder.tv_Total_monney_item_list_bill_monthly.setText(String.valueOf(decimalFormat.format(payment.getTotalMoney()))+"VND");
          holder.tv_OrderDate_item_list_bill_monthly.setText(payment.getOrderDate());
           holder.tv_OrderDate_item_list_bill_monthly.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   itemClick.ItemClick(payment);
               }
           });
          holder.tv_CodeBill_item_list_bill_monthly.setText(String.valueOf(payment.getId()));
          holder.tv_CodeBill_item_list_bill_monthly.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  itemClick.ItemClick(payment);
              }
          });
   }

    @Override
    public int getItemCount() {
       if(billPaymentList!=null)
       {
           return billPaymentList.size();
       }
       return 0;
    }


    public class adapter_item_bill_list_monthly_ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tv_OrderDate_item_list_bill_monthly,tv_Total_monney_item_list_bill_monthly,tv_CodeBill_item_list_bill_monthly;
        public adapter_item_bill_list_monthly_ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_CodeBill_item_list_bill_monthly=itemView.findViewById(R.id.tv_CodeBill_item_list_bill_monthly);
            tv_OrderDate_item_list_bill_monthly=itemView.findViewById(R.id.tv_OrderDate_item_list_bill_monthly);
            tv_Total_monney_item_list_bill_monthly=itemView.findViewById(R.id.tv_Total_monney_item_list_bill_monthly);
        }
    }

}
