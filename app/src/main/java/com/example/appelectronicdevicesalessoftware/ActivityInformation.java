package com.example.appelectronicdevicesalessoftware;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.PermissionChecker;
import Object.Const;
import Object.Personal_Information;
import Object.GetApiSuccess;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.Api;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import CreateRealPathImg.RealPathUtil;
import Retrofit.ApiInformation;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityInformation extends AppCompatActivity {
private EditText edt_FullName,edt_Date_Of_Birth,edt_Telephone_Number,edt_Address;
 private ImageView img_Avatar;
 private Uri mUri;
 private ImageButton imgbtn_back;
 private String run;
 private TextView tv_fix_information;
 private int Uid;
 private String LinkImgAvatar;
 private String strRealPath;
 private String TelephoneNumber,FullName,Address,Date_Of_Birth;
 private static final int MY_REQUEST_CODE=10;
 private Button btn_ConfirmInfor;
 private List<Personal_Information> personal_informationList;
 private ProgressDialog progressDialog;
 //Lấy uri của ảnh
 private ActivityResultLauncher<Intent> mActivityResultLauncher=registerForActivityResult(
         new ActivityResultContracts.StartActivityForResult()
         , new ActivityResultCallback<ActivityResult>() {
             @Override
             public void onActivityResult(ActivityResult result) {
                 if(result.getResultCode()== Activity.RESULT_OK)
                 {
                     Intent data=result.getData();
                     if(data==null)
                     {
                         return;
                     }
                     Uri uri=data.getData();
                     mUri=uri;
                     try {
                         Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                         img_Avatar.setImageBitmap(bitmap);
                     }catch (IOException e)
                     {
                         e.printStackTrace();                    }
                 }
             }
         }
 );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        Mapping();
        getUidUser();
        GetdataInformation();
        OnClick();


    }
    //Lấy dữ liệu từ ui
    private void getUidUser()
    {
        int Uid=getIntent().getIntExtra("Uid",1);
        setUid(Uid);
    }
    //Ánh xạ
    private void Mapping(){
    imgbtn_back=findViewById(R.id.btn_back_update_information);
    img_Avatar=findViewById(R.id.img_avatar);
    edt_FullName=findViewById(R.id.edt_FullName);
    tv_fix_information=findViewById(R.id.tv_fix_information);
    edt_Date_Of_Birth=findViewById(R.id.edt_Day_Of_Birth);
    edt_Telephone_Number=findViewById(R.id.edt_TelephoneNumber);
    edt_Address=findViewById(R.id.edt_Address);
    btn_ConfirmInfor=findViewById(R.id.btn_ConfirmInfor);
    progressDialog=new ProgressDialog(this);
    personal_informationList=new ArrayList<>();
    }
    //Lấy dữ liệu thông tin cá nhân từ api
    private void GetdataInformation(){
        ApiInformation.apiInfo.GetPersonalInformation().enqueue(new Callback<List<Personal_Information>>() {
            @Override
            public void onResponse(Call<List<Personal_Information>> call, Response<List<Personal_Information>> response) {
                personal_informationList=response.body();
                List<Personal_Information> l=new ArrayList<>();
                if(personal_informationList==null)
                {
                    return;
                }
                for(Personal_Information personal_information:personal_informationList)
                {
                    if(personal_information!=null)
                    {
                        if(personal_information.getUid_Login()==getUid())
                        {
                            l.add(personal_information);
                            Glide.with(ActivityInformation.this).load(personal_information.getImg_Avatar()).error(R.drawable.logo).into(img_Avatar);
                            edt_FullName.setText(personal_information.getFullName());
                            setFullName(personal_information.getFullName());
                            edt_Date_Of_Birth.setText(personal_information.getDate_Of_Birth());
                            setDate_Of_Birth(personal_information.getDate_Of_Birth());
                            edt_Telephone_Number.setText(personal_information.getTelephone_Number());
                            setTelephoneNumber(personal_information.getTelephone_Number());
                            edt_Address.setText(personal_information.getAddress());
                            setAddress(personal_information.getAddress());
                            setLinkImgAvatar(personal_information.getImg_Avatar());
                            if(personal_information.getFullName().length()==0 ||personal_information.getDate_Of_Birth().length()==0||personal_information.getAddress().length()==0||personal_information.getTelephone_Number().length()==0 )
                            {
                                btn_ConfirmInfor.setVisibility(View.VISIBLE);

                            }
                            else
                            {
                                btn_ConfirmInfor.setVisibility(View.INVISIBLE);
                                setPersonal_informationList(l);

                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Personal_Information>> call, Throwable t) {

            }
        });
    }
    //Bắt dự kiện với button
    private void OnClick()
    {
        imgbtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        img_Avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnClickRequestPermisson();
            }
        });
        btn_ConfirmInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String FullName=edt_FullName.getText().toString().trim();
                String DateOfBirth=edt_Date_Of_Birth.getText().toString().trim();
                String TelephoneNumber=edt_Telephone_Number.getText().toString().trim();
                String Address=edt_Address.getText().toString().trim();
                if(mUri==null) {
                    String strRealPath="";
                    setStrRealPath(strRealPath);
                }
                else
                {
                    String strRealPath = RealPathUtil.getRealPath(ActivityInformation.this, mUri);
                    setStrRealPath(strRealPath);
                }
                TurnOnProcessDiaLog();
                ConfirmInfromation( getUid(),FullName,DateOfBirth,TelephoneNumber,Address);
            }
        });
        tv_fix_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_ConfirmInfor.setVisibility(View.VISIBLE);
            }
        });
    }
    //Sử dụng processdiglog trong lúc xử lí
    private void TurnOnProcessDiaLog()
    {
        progressDialog.show();
        String Active="run";
        setRun(Active);
        progressDialog.setContentView(R.layout.set_content_processdialog);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
    //Xác nhận thông tin đã điền vào trang thông tin cá nhân
    private void ConfirmInfromation(int  Uid,String FullName,String DateOfBirth,String TelephoneNumber,String Address)
    {
        if (Uid>0 && Address.length()>0 && TelephoneNumber.length()>0 && DateOfBirth.length()>0 && FullName.length()>0)
           {

            if(getAddress()!=null && getFullName()!=null && getDate_Of_Birth()!=null && getAddress()!=null)
                {
                    UpdateInformation(Uid,FullName,DateOfBirth,TelephoneNumber,Address);
                }
            else
                {
                    AddInformation(Uid,FullName,DateOfBirth,TelephoneNumber,Address);
                }

            }
       else
           {
                progressDialog.dismiss();
                Toast.makeText(ActivityInformation.this,"Thông tin nhập chưa đầy đủ",Toast.LENGTH_SHORT).show();
           }

    }
    //Cập nhật dữ liệu sau khi sửa
    private void UpdateInformation(int  Uid,String FullName,String DateOfBirth,String TelephoneNumber,String Address)
    {
        if(strRealPath.length()>0)
        {
            UploadFileImgAvatar();
        }
        ApiInformation.apiInfo.UpdateInformation(Uid,getLinkImgAvatar(),FullName,DateOfBirth,TelephoneNumber,Address).enqueue(new Callback<GetApiSuccess>() {
            @Override
            public void onResponse(Call<GetApiSuccess> call, Response<GetApiSuccess> response) {
                GetApiSuccess getApiSuccess = (GetApiSuccess) response.body();
                progressDialog.dismiss();
                if (getApiSuccess.getOk() == 1 && getApiSuccess.getMessage().equals("Success")) {

                    btn_ConfirmInfor.setVisibility(View.INVISIBLE);
                    Toast.makeText(ActivityInformation.this, "Cập nhật  thông tin thành công ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ActivityInformation.this, "Cập nhật  thông tin thất bại", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<GetApiSuccess> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ActivityInformation.this,"Cập nhật thông tin cá nhân  tin thất bại",Toast.LENGTH_SHORT).show();

            }
        });
    }
    //Thêm dữ liệu thông tin cá nhân
    private void AddInformation(int Uid,String Fullname,String DateOfBirth,String TelephoneNumber,String Address)
    {
        if(getStrRealPath().length()>0)
        {
            UploadFileImgAvatar();
        }
        ApiInformation.apiInfo.ConfirmInformation(Uid,getLinkImgAvatar(),Fullname,DateOfBirth,TelephoneNumber,Address).enqueue(new Callback<GetApiSuccess>() {
            @Override
            public void onResponse(Call<GetApiSuccess> call, Response<GetApiSuccess> response) {
                progressDialog.dismiss();
                GetApiSuccess getApiSuccess = (GetApiSuccess) response.body();
                if (getApiSuccess.getOk() == 1 && getApiSuccess.getMessage().equals("Success")) {
                    btn_ConfirmInfor.setVisibility(View.INVISIBLE);
                    Toast.makeText(ActivityInformation.this, "Thêm  thông tin cá nhân thành công ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(ActivityInformation.this,"Thêm  thông tin cá nhân  tin thất bại",Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<GetApiSuccess> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ActivityInformation.this,"Chưa thêm được thông tin cá nhân ",Toast.LENGTH_SHORT).show();

            }
        });
    }
    //Thêm avatar của người dùng
    private void UploadFileImgAvatar(){
        File file = new File(getStrRealPath());
        String file_path = file.getAbsolutePath();
        String[] namefile = file_path.split("\\.");
        file_path = namefile[0] + System.currentTimeMillis() + "." + namefile[1];
        String[] NameFileImagAvatar=file_path.split("\\/");
        setLinkImgAvatar(Const.Key_UrlPathAvatar+NameFileImagAvatar[6]);
        RequestBody requestBodyAvatar = RequestBody.create(MediaType.parse("multipart/from-data"), file);
        MultipartBody.Part multipartAvatar = MultipartBody.Part.createFormData("uploadfile", file_path, requestBodyAvatar);
        ApiInformation.apiInfo.UploadAvatar(multipartAvatar).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    //Xin phép cấp quyền cho ứng dụng
    private void OnClickRequestPermisson()
    {
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M)
        {
            OpenGrallery();
            return;
        }
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
        {
            OpenGrallery();
        }
        else
        {
            String[] permissions={Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permissions,MY_REQUEST_CODE);

        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==MY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                OpenGrallery();
            }
        }
    }
    //Mở thư viện để lấy ảnh
    private void OpenGrallery()
    {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent,"Select Picture"));
    }

    @Override
    public void onBackPressed() {
            progressDialog.dismiss();

    }

    public String getLinkImgAvatar() {
        return LinkImgAvatar;
    }

    public void setLinkImgAvatar(String linkImgAvatar) {
        LinkImgAvatar = linkImgAvatar;
    }

    public int getUid() {
        return Uid;
    }

    public void setUid(int uid) {
        Uid = uid;
    }

    public String getRun() {
        return run;
    }

    public void setRun(String run) {
        this.run = run;
    }

    public String getStrRealPath() {
        return strRealPath;
    }

    public void setStrRealPath(String strRealPath) {
        this.strRealPath = strRealPath;
    }

    public List<Personal_Information> getPersonal_informationList() {
        return personal_informationList;
    }

    public void setPersonal_informationList(List<Personal_Information> personal_informationList) {
        this.personal_informationList = personal_informationList;
    }

    public String getTelephoneNumber() {
        return TelephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        TelephoneNumber = telephoneNumber;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDate_Of_Birth() {
        return Date_Of_Birth;
    }

    public void setDate_Of_Birth(String date_Of_Birth) {
        Date_Of_Birth = date_Of_Birth;
    }
}