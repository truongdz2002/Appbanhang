package com.example.appelectronicdevicesalessoftware;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import Utilities.AlertDeleteHistorySearch;

import java.util.ArrayList;
import java.util.List;

import Adapter.Adapter_History_Search_Of_User;
import Object.HistorySearch;
import Retrofit.ApiHistorySearch;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityHistorySearch extends AppCompatActivity {
private RecyclerView rcv_List_text_searched;
private Adapter_History_Search_Of_User adapter_history_search_of_user;
private List<HistorySearch> historysearchlist,HistorySearchList;
private EditText edt_Search;
private ImageButton btn_search2,btn_Back_History_Search_Of_User;
private String TextSearch;
private AlertDeleteHistorySearch alertDeleteHistorySearch;
private String TextSearched;
private int Uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_history_search);
        Mapping();
        Initialization();
        GetDataIntent();
        SetOnClick();


    }
    //Ánh xạ
    private void Mapping()
    {
        btn_Back_History_Search_Of_User=findViewById(R.id.btn_back_history_search);
        edt_Search=findViewById(R.id.edt_search);
        btn_search2=findViewById(R.id.btn_Search2);
        rcv_List_text_searched=findViewById(R.id.rcv_list_history_search);
    }
    //Khởi tạo biến
    private void Initialization()
    {
        historysearchlist=new ArrayList<>();
        HistorySearchList=new ArrayList<>();
        alertDeleteHistorySearch=new AlertDeleteHistorySearch(this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        rcv_List_text_searched.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rcv_List_text_searched.addItemDecoration(dividerItemDecoration);
        adapter_history_search_of_user=new Adapter_History_Search_Of_User(this, new Adapter_History_Search_Of_User.ItemClick() {
            @Override
            public void ClickText(HistorySearch historySearch) {
                edt_Search.setText(historySearch.getTextSearch());
                GetDataUi();
                if (getTextSearch().length()>0) {
                    AddTextSearchedUpApi();
                    Intent intent = new Intent(ActivityHistorySearch.this, ActivityProductSearch.class);
                    intent.putExtra("KeySearch", getTextSearch());
                    intent.putExtra("Uid", getUid());
                    startActivity(intent);
                }
            }

            @Override
            public void ClickDeleteTextSearched(HistorySearch historySearch) {
               alertDeleteHistorySearch.DeleteHistoryTextSearched(historySearch.getTextSearch());
               historysearchlist.remove(historySearch);
               adapter_history_search_of_user.setdata(historysearchlist);

            }
        });

                rcv_List_text_searched.setAdapter(adapter_history_search_of_user);

    }
    //Nhận dữ liệu từ Intent
    private void GetDataIntent()
    {
        int Uid=getIntent().getIntExtra("Uid",1);
        setUid(Uid);
    }
    //Set dữ liệu màn hình ui
    private void GetDataUi()
    {
        String textSearch=edt_Search.getText().toString().trim();
        setTextSearch(textSearch);
    }
    //Thêm dữ liệu đã tìm kiếm len api
    private void AddTextSearchedUpApi()
    {
        for(int i=0;i<historysearchlist.size();i++)
        {
            if(historysearchlist.get(i).getTextSearch().equals(getTextSearch()))
            {
                DeleteTextSearched(historysearchlist.get(i).getTextSearch());
            }
        }
        ApiHistorySearch.apiHistorySearch.AddTextHistorySearchOfUser(getUid(),getTextSearch()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
    //Xoá text đã search
    private void DeleteTextSearched(String textSearch)
    {
        ApiHistorySearch.apiHistorySearch.DeleteProduct_list_history_searched(textSearch).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    //Nhận các text đã tìm kiếm từ api
    private void GetTextSearchedApi()
    {
        historysearchlist.clear();
        ApiHistorySearch.apiHistorySearch.GetlistHistorySearch().enqueue(new Callback<List<HistorySearch>>() {
            @Override
            public void onResponse(Call<List<HistorySearch>> call, Response<List<HistorySearch>> response) {
                List<HistorySearch>  list=new ArrayList<>();
                list=response.body();
                for(HistorySearch historySearch:list)
                {
                    if(historySearch.getUid()==getUid() )
                    {
                             historysearchlist.add(historySearch);
                             adapter_history_search_of_user.setdata( historysearchlist);
                    }

                }


            }

            @Override
            public void onFailure(Call<List<HistorySearch>> call, Throwable t) {

            }
        });

    }
    //Bắt sự kiện button
    private void SetOnClick()
    {
        edt_Search.setText(getIntent().getStringExtra("KeySearch"));

            btn_search2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                           GetDataUi();
                        if (getTextSearch().length()>0) {
                            AddTextSearchedUpApi();
                            Intent intent = new Intent(ActivityHistorySearch.this, ActivityProductSearch.class);
                            intent.putExtra("KeySearch", getTextSearch());
                            intent.putExtra("Uid", getUid());
                            startActivity(intent);
                        }

                    }


            });
            edt_Search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if(i== EditorInfo.IME_ACTION_SEARCH)
                    {
                        GetDataUi();
                        if (getTextSearch().length()>0) {
                            AddTextSearchedUpApi();
                            Intent intent = new Intent(ActivityHistorySearch.this, ActivityProductSearch.class);
                            intent.putExtra("KeySearch", getTextSearch());
                            intent.putExtra("Uid", getUid());
                            startActivity(intent);
                        }
                    }
                    return false;
                }
            });



        btn_Back_History_Search_Of_User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public String getTextSearch() {
        return TextSearch;
    }

    public void setTextSearch(String textSearch) {
        TextSearch = textSearch;
    }

    public String getTextSearched() {
        return TextSearched;
    }

    public void setTextSearched(String textSearched) {
        TextSearched = textSearched;
    }

    public int getUid() {
        return Uid;
    }
    public void setUid(int uid) {
        Uid = uid;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        GetTextSearchedApi();
    }
}