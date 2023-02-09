package Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import Object.User;
import com.example.appelectronicdevicesalessoftware.MainActivity;
import com.example.appelectronicdevicesalessoftware.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import Retrofit.ApiUser;
import Utilities.ProcessDialogCustom;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import Object.User;

public class Activity_SignUp extends AppCompatActivity {
    private EditText edt_Username_SigUp;
    private ProcessDialogCustom  progressDialog;
    private EditText edt_Password_SigUp;
    private EditText edtEnterPassword_SigUp;
    String str_UserName;
    String str_Password;
    String str_Enterpassword;
    int ok;
    private Button btn_Register;
    private List<User> listUserAccount,listuseraccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //Ánh xạ tới các thành phần trên giao diện
        Mapping();
        //Lấy danh sách trên Mysql thông qua retrofit call api
        GetUserAccount();
        //Bắt sự kiện với các controler
        SetClick();


    }
    private void Mapping()
    {
        edt_Username_SigUp=findViewById(R.id.edt_Username_SignUp);
        edtEnterPassword_SigUp=findViewById(R.id.edt_Cofirmpassword_SignUp);
        edt_Password_SigUp=findViewById(R.id.edt_Password_SignUp);
        btn_Register=findViewById(R.id.btn_Register);
        //Khởi tạo progressDiglog tự custem
        progressDialog=new ProcessDialogCustom(this);
        listuseraccount=new ArrayList<>();
    }
    private void CheckRegister()
    {
        if(getOk()==1)
        {
            progressDialog.TurnOnProcessDiaLog();
            //TurnOnProcessDiaLog();
            //Dùng Retrofit để post dữ liệu thỏa mãn đăng kí lên sever và chuyển sang màn hình login
            CallRegisterAccount();

        }

    }
    private void GetdataUi()
    {
        str_UserName=edt_Username_SigUp.getText().toString().trim();
        setStr_UserName(str_UserName);
        str_Password=edt_Password_SigUp.getText().toString().trim();
        setStr_Password(str_Password);
        str_Enterpassword=edtEnterPassword_SigUp.getText().toString().trim();
        setStr_Enterpassword(str_Enterpassword);
    }

    private void SetClick()
    {
        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Lấy dữ liệu từ giao diện
                GetdataUi();
                //kiểm tra các điều kiện để cho phép đăng đăng kí
                CheckValiDateForm();
                //Check xem tài khoản nào đã chính xác để xác nhận đăng kí thành công
                CheckRegister();


            }
        });
    }

    private void CheckValiDateForm()
    {
          // Check rỗng
         if(getStr_UserName().equals("") || getStr_Password().equals(""))
         {
             Toast.makeText(this, "Bạn cần nhập tài khoản và mật khẩu để đăng kí", Toast.LENGTH_SHORT).show();
             return;
         }//Check password và xác nhận password phải giống nhau
         else if(getStr_Enterpassword().equals("")||!getStr_Enterpassword().equals(getStr_Password()))
         {
             Toast.makeText(this, "Xác nhận  mật khẩu đăng kí sai  hoặc không được để trống", Toast.LENGTH_SHORT).show();
             return;
             //Check số lượng kí tự của tài khoản ko quá 30 và mật khẩu không đk dưới 0 và nhỏ hơn 8 kí tự
         }else if(getStr_UserName().length() > 30 || getStr_Password().length() >8 ||getStr_Enterpassword().length()>8)
         {
             Toast.makeText(this, "Không được quá kí tự cho phép Tài khoản (30 kí tự) và mật khẩu (8 kí tự)", Toast.LENGTH_SHORT).show();
             return;
         }
         //Check  xem tài khoản mới tạo đã tồn tại hay chưa
         //nếu chưa trả lại ok=1
          for(User user:   getListUserAccount())
          {
              if(user==null)
              {
                  return;
              }
              if(user.getUserName().equals(getStr_UserName()))
              {
                int   ok=0;
                setOk(ok);
                  Toast.makeText(Activity_SignUp.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                  return;
              }
              else
              {
                 int ok=1;
                  setOk(ok);
              }


          }









    }
    private void GetUserAccount()
    {
        ApiUser.apiUser.GetAccountUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                listuseraccount=response.body();
                setListUserAccount(listuseraccount);

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }

    private  void CallRegisterAccount()
    {
        ApiUser.apiUser.RegisterAccount(getStr_UserName(),getStr_Password()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.TurnOffProgressDialog();
            Intent intent=new Intent(Activity_SignUp.this,ActivityLogin.class);
            intent.putExtra("UserName",getStr_UserName());
            intent.putExtra("PassWord",getStr_Password());
            Toast.makeText(Activity_SignUp.this, "Đăng kí tài khoản thành công", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finishAffinity();
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.TurnOffProgressDialog();
                Toast.makeText(Activity_SignUp.this, "Đăng kí tài khoản thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public void onBackPressed() {
        progressDialog.TurnOffProgressDialog();
        Intent intent=new Intent(Activity_SignUp.this,ActivityLogin.class);
        startActivity(intent);
    }
    public String getStr_UserName() {
        return str_UserName;
    }

    public void setStr_UserName(String str_UserName) {
        this.str_UserName = str_UserName;
    }

    public String getStr_Password() {
        return str_Password;
    }

    public void setStr_Password(String str_Password) {
        this.str_Password = str_Password;
    }

    public String getStr_Enterpassword() {
        return str_Enterpassword;
    }

    public void setStr_Enterpassword(String str_Enterpassword) {
        this.str_Enterpassword = str_Enterpassword;
    }

    public List<User> getListUserAccount() {
        return listUserAccount;
    }

    public void setListUserAccount(List<User> listUserAccount) {
        this.listUserAccount = listUserAccount;
    }

    public int getOk() {
        return ok;
    }

    public void setOk(int ok) {
        this.ok = ok;
    }
}