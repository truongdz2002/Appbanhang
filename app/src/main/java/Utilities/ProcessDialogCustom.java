package Utilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.example.appelectronicdevicesalessoftware.R;

public class ProcessDialogCustom {
    private ProgressDialog progressDialog;
    private Context context;

    public ProcessDialogCustom(Context context) {
        this.context = context;
        progressDialog = new ProgressDialog(context);
    }

    public void TurnOnProcessDiaLog()
    {
        progressDialog.show();
        progressDialog.setContentView(R.layout.set_content_processdialog);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
    public void TurnOffProgressDialog()
    {
        progressDialog.dismiss();
    }

}
