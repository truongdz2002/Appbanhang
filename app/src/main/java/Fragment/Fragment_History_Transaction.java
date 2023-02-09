package Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appelectronicdevicesalessoftware.ActivityDetailBill;
import com.example.appelectronicdevicesalessoftware.MainActivity;
import com.example.appelectronicdevicesalessoftware.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.Adapter_History_Transaction;
import Object.BillPayment;
import Retrofit.ApiBillPayment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment_History_Transaction extends Fragment {
private RecyclerView rcv_transaction_history;
private Adapter_History_Transaction adapter_item_bill_list_monthly;
private  View view;
private MainActivity activity;
private List<BillPayment>billPaymentList;


    public Fragment_History_Transaction() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view=inflater.inflate(R.layout.fragment__history__transaction, container, false);
         Mapping();
         Init();
         GetDataBillPayMent();
         return view;
    }
    //Ánh xạ
    private void Mapping()
    {
        rcv_transaction_history=view.findViewById(R.id.rcv_transtion_history);
    }
    //Khởi tạo biến
    private void Init()
    {
        activity=(MainActivity) getActivity();
        billPaymentList=new ArrayList<BillPayment>();
        adapter_item_bill_list_monthly=new Adapter_History_Transaction(activity, new Adapter_History_Transaction.ItemClick() {
            @Override
            public void ItemClick(BillPayment billPayment) {
                Intent intent=new Intent(activity, ActivityDetailBill.class);
                intent.putExtra("BillPayment" ,billPayment);
                startActivity(intent);
            }
        });
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(activity);
        rcv_transaction_history.setLayoutManager(linearLayoutManager);
        rcv_transaction_history.setAdapter(adapter_item_bill_list_monthly);
    }
    //Nhận dữ liệu hóa đơn đã thanh toán
    private void GetDataBillPayMent()
    {
        ApiBillPayment.apiBillPayment.GetDataBillPayment().enqueue(new Callback<List<BillPayment>>() {
            @Override
            public void onResponse(Call<List<BillPayment>> call, Response<List<BillPayment>> response) {
                List<BillPayment> l = new ArrayList<BillPayment>();
                l = response.body();
                for (BillPayment billpayment : l) {
                    if (billpayment.getUid() == activity.getUID_Login()) {
                        billPaymentList.add(billpayment);
                        adapter_item_bill_list_monthly.setdata(billPaymentList);

                    }
                }
            }

            @Override
            public void onFailure(Call<List<BillPayment>> call, Throwable t) {

            }
        });
    }

}