package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import Object.HistorySearch;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appelectronicdevicesalessoftware.R;

import java.util.List;

public class Adapter_History_Search_Of_User extends  RecyclerView.Adapter<Adapter_History_Search_Of_User.Adapter_History_Search_Of_UserViewHolder> {
    private View view;
    private Context context;
    private List<HistorySearch> historySearchList;
    public ItemClick clicktext;
    public interface  ItemClick{
        void ClickText(HistorySearch historySearch);
        void ClickDeleteTextSearched(HistorySearch historySearch);
    }
   public void setdata(List<HistorySearch> historySearchList)
   {
       this.historySearchList=historySearchList;
       notifyDataSetChanged();
   }
    public Adapter_History_Search_Of_User(Context context,ItemClick itemClick) {
        this.context = context;
        clicktext=itemClick;
    }

    @NonNull
    @Override
    public Adapter_History_Search_Of_UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_history,parent,false);
        return new Adapter_History_Search_Of_UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_History_Search_Of_UserViewHolder holder, int position) {
            HistorySearch historySearch= historySearchList.get(position);
            if(historySearch==null)
            {
                return;
            }
            holder.tv_searched.setText(historySearch.getTextSearch());
            holder.tv_searched.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clicktext.ClickText(historySearch);
                }
            });
            holder.btn_delete_text_searched.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clicktext.ClickDeleteTextSearched(historySearch);
                }
            });
    }

    @Override
    public int getItemCount() {
       if(historySearchList!=null)
       {
           return historySearchList.size();
       }
        return 0;
    }

    public class Adapter_History_Search_Of_UserViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tv_searched;
        private ImageButton btn_delete_text_searched;
        public Adapter_History_Search_Of_UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_searched=itemView.findViewById(R.id.tv_search_history);
            btn_delete_text_searched=itemView.findViewById(R.id.btn_delete_text_searched);
        }
    }
}
