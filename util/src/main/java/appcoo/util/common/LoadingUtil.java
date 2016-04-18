package appcoo.util.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.apkfuns.xprogressdialog.XProgressDialog;

/**
 * Created by tjianli on 15/8/11.
 */
public class LoadingUtil {

    private static XProgressDialog progressDialog;
    public static void show(Context contex,String msg){

        progressDialog = new XProgressDialog(contex, msg, XProgressDialog.THEME_HORIZONTAL_SPOT);;
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public static XProgressDialog getProgressDialog(){
        return progressDialog;
    }

    public static void dismiss(){

        if(progressDialog!=null) {
            progressDialog.dismiss();
        }
    }
    public static void setMessage(String msg){
        progressDialog.setMessage(msg);
    }

    public static void setCancelListener(DialogInterface.OnCancelListener listener){
        progressDialog.setCancelable(true);
        progressDialog.setOnCancelListener(listener);
    }
}
