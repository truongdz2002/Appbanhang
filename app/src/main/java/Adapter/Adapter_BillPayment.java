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

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.List;

import Object.BillPayment;

public class Adapter_BillPayment extends RecyclerView.Adapter<Adapter_BillPayment.Adapter_BillPaymentViewHolder> {
   private View view;
   private List<BillPayment> billPaymentList;
   private Context context;

    public Adapter_BillPayment(Context context) {
        this.context = context;
    }
    public void Setdata(List<BillPayment> billPaymentList)
    {
        this.billPaymentList=billPaymentList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Adapter_BillPaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_billpayment,parent,false);
        return new Adapter_BillPaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_BillPaymentViewHolder holder, int position) {
        BillPayment billPayment=billPaymentList.get(position);
        String pattern="###,###,###";
        DecimalFormat decimalFormat=new DecimalFormat(pattern);
      holder.tv_ProductName_In_Bill.setText(billPayment.getProductName());
      holder.tv_TelephoneNumber_In_Bill.setText(billPayment.getTelephoneNumber());
      holder.tv_Payment_Method.setText(billPayment.getPaymentMethod());
      holder.tv_Total_monney_In_Bill.setText(String.valueOf(decimalFormat.format(billPayment.getTotalMoney()))+"VND");
      holder.tv_UserNameInBill.setText(billPayment.getUserName());
      holder.tv_OrderDate.setText(billPayment.getOrderDate());
      holder.tv_CodeBill.setText(String.valueOf(billPayment.getId()));
      holder.tv_AddressUser_In_Bill.setText(billPayment.getAddressUser());
        Glide.with(holder.img_product_in_bill.getContext()).load(billPayment.getImg_Product()).error(R.drawable.logo).into(holder.img_product_in_bill);
    }

    @Override
    public int getItemCount() {
        if(billPaymentList!=null)
        {
            return billPaymentList.size();
        }
        return 0;
    }

    public class Adapter_BillPaymentViewHolder extends RecyclerView.ViewHolder
  {
      TextView tv_Payment_Method,tv_Total_monney_In_Bill,tv_OrderDate,tv_ProductName_In_Bill,tv_AddressUser_In_Bill,tv_TelephoneNumber_In_Bill,tv_UserNameInBill,tv_CodeBill;
      ImageView img_product_in_bill;
      public Adapter_BillPaymentViewHolder(@NonNull View itemView) {
          super(itemView);
          tv_AddressUser_In_Bill=itemView.findViewById(R.id.tv_AddressUser_In_Bill);
          tv_CodeBill=itemView.findViewById(R.id.tv_CodeBill);
          tv_OrderDate=itemView.findViewById(R.id.tv_OrderDate);
          tv_Payment_Method=itemView.findViewById(R.id.tv_Payment_Method);
          tv_UserNameInBill=itemView.findViewById(R.id.tv_UserNameInBill);
          tv_Total_monney_In_Bill=itemView.findViewById(R.id.tv_Total_monney_In_Bill);
          tv_TelephoneNumber_In_Bill=itemView.findViewById(R.id.tv_TelephoneNumber_In_Bill);
          tv_ProductName_In_Bill=itemView.findViewById(R.id.tv_ProductName_In_Bill);
          img_product_in_bill=itemView.findViewById(R.id.img_product_in_bill);
      }
  }
}
