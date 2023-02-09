package Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.telephony.mbms.MbmsErrors;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import DatabaseUserOffline.DataBaseUserOffline;

import com.example.appelectronicdevicesalessoftware.MainActivity;
import com.example.appelectronicdevicesalessoftware.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import Object.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import Retrofit.ApiUser;
import Utilities.ProcessDialogCustom;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import Object.SaveAccountUserOffline;

public class ActivityLogin extends AppCompatActivity {
    private Button btn_Login;
    private EditText edt_UserName_Login, edt_Password_Login;
    private TextView tv_Sign_Up;
    private ProcessDialogCustom progressDialog;
    private List<User> listAccountUser;
    private List<SaveAccountUserOffline> listAccountUserInDatabaseOffline;
    private int Uid;
    private int Ok;
    private String Username;
    String str_UserName;
    String str_PassWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Ánh xạ tới các thành phần trên giao diện
        Mapping();
        //Khởi tạo dữ liệu cho các biến
        Initialization();
        //Nhận dữ liệu sau khi đăng kí thành công (tiện ích ko cần nhập )
        GetDataIntent();
        //Nhận danh sách tài khoản trên sever mysql thông qua retrofit Call Api
        GetDataAcountUserToLogin();
        SetdataUi();
        //Bắt các sự kiện khi Nhấp vào
        SetClick();


    }
 //Ánh xạ tới các thẻ
    private void Mapping() {
        //Buttom đăng nhập
        btn_Login = findViewById(R.id.btn_Login);
        // Editext nhập tài khoản
        edt_UserName_Login = findViewById(R.id.edt_UserName_Login);
        //Edittext nhập password
        edt_Password_Login = findViewById(R.id.edt_Password_Login);
        //Textview nếu chưa có tài khoản sẽ chuyển sang màn hình đăng kí tài khoản
        tv_Sign_Up=findViewById(R.id.tv_sign_up);
    }
    //Khởi tạo
    private void Initialization()
    {
        //Khởi tạo progressdiaglog từ class ProcessDialogCustom
        progressDialog = new ProcessDialogCustom(this);
        listAccountUser=new ArrayList<>();
        listAccountUserInDatabaseOffline=new ArrayList<>();
    }
    private void SetdataUi()
    {
        listAccountUserInDatabaseOffline=DataBaseUserOffline.getInstance(ActivityLogin.this).userOffline().GetListSavePhone();
       for(SaveAccountUserOffline saveAccountUserOffline:listAccountUserInDatabaseOffline)
       {
            edt_UserName_Login.setText(saveAccountUserOffline.getUserName());
            edt_Password_Login.setText(saveAccountUserOffline.getPassword());
        }

    }
    private void GetDataAcountUserToLogin()
    {
        ApiUser.apiUser.GetAccountUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
              List<User> l=new ArrayList<>();
                l=response.body();
                setListAccountUser(l);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
            }
        });

    }
    private void GetDataIntent(){

        if(getIntent().getStringExtra("UserName")==null && getIntent().getStringExtra("Password")==null)
        {
            return;
        }
        String u=getIntent().getStringExtra("UserName").toString().trim();
        String p=getIntent().getStringExtra("PassWord").toString().trim();
        edt_UserName_Login.setText(u);
        edt_Password_Login.setText(p);

    }

    //Nhận dữ liệu từ giao diện người dùng
    private void GetDataUi()
    {
        str_UserName=edt_UserName_Login.getText().toString().trim();
        setStr_UserName(str_UserName);
        str_PassWord=edt_Password_Login.getText().toString().trim();
        setStr_PassWord(str_PassWord);
    }
    //Lưu tài khoản đăng nhập vào databaseOffline
    private void AddAccountUserToDataBaseOffline(int Uid ,String UserName,String PassWord)
    {
        SaveAccountUserOffline saveAccountUserOffline=new SaveAccountUserOffline(Uid,UserName,PassWord);
        DataBaseUserOffline.getInstance(this).userOffline().InsertAccount(saveAccountUserOffline);
    }
    //Xử lí sự kiện với các button
    private void SetClick() {
        tv_Sign_Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Chuyển sang màn hình đăng kí
                Intent intent = new Intent(ActivityLogin.this, Activity_SignUp.class);
                startActivity(intent);
            }
        });
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Nhận dữ liệu từ giao diện để đăng nhập
                GetDataUi();
                //Xác nhận có thoải mãn điều kiện để đăng nhập và trả về giá trị ok=1
                CallAccountUser();
                //kiểm tra giá trị ok để chuyển các dữ liệu sang mainactivity
                SetConditionToLogin();
            }
        });
    }
    private void SetConditionToLogin()
    {
        progressDialog.TurnOnProcessDiaLog();
        if(getOk()==1)
        {
            DataBaseUserOffline.getInstance(ActivityLogin.this).userOffline().DeleteUserSaveOffline();
            AddAccountUserToDataBaseOffline(getUid(),getStr_UserName(),getStr_PassWord());
            Toast.makeText(ActivityLogin.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(ActivityLogin.this,MainActivity.class);
            intent.putExtra("UserName",getUsername());
            intent.putExtra("UidLogin",getUid());
            startActivity(intent);
            progressDialog.TurnOffProgressDialog();
            finishAffinity();
        }
        else
        {
            progressDialog.TurnOffProgressDialog();
            Toast.makeText(ActivityLogin.this, "Tên tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
        }
    }

    //Lấy danh sách người dung trên databaseOnline(mysql)
    private void CallAccountUser() {


        for (User user :getListAccountUser()) {
            if (user.getUserName().equals(getStr_UserName()) && user.getPassword().equals(getStr_PassWord())) {
                setUid(user.getId());
                setUsername(user.getUserName());
                setOk(1);
                return;

            } else {
                setOk(0);
            }

        }
    }

//Tạo ProcessDiaLog tự Customize


    @Override
    public void onBackPressed() {
        progressDialog.TurnOffProgressDialog();
    }

    public String getStr_PassWord() {
        return str_PassWord;
    }

    public void setStr_PassWord(String str_PassWord) {
        this.str_PassWord = str_PassWord;
    }

    public String getStr_UserName() {
        return str_UserName;
    }

    public void setStr_UserName(String str_UserName) {
        this.str_UserName = str_UserName;
    }

    public int getUid() {
        return Uid;
    }

    public void setUid(int uid) {
        Uid = uid;
    }

    public int getOk() {
        return Ok;
    }

    public void setOk(int ok) {
        Ok = ok;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public List<User> getListAccountUser() {
        return listAccountUser;
    }

    public void setListAccountUser(List<User> listAccountUser) {
        this.listAccountUser = listAccountUser;
    }
}