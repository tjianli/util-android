package appcoo.util.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by tjianli on 15/8/11.
 */
public class LoadingUtil {

    private static ProgressDialog progressDialog;
    public static void show(Context contex,String msg){

        progressDialog = ProgressDialog.show(contex, "", msg, true);
        progressDialog.setCancelable(false);
    }

    public static ProgressDialog getProgressDialog(){
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
