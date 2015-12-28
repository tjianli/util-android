package appcoo.util.common;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by tjianli on 15/8/11.
 */
public class LoadingUtil {

    private static ProgressDialog progressDialog;
    public static void show(Context contex,String msg,boolean cancel){

        progressDialog = ProgressDialog.show(contex, "", msg, true);
        progressDialog.setCancelable(cancel);
    }

    public static void dismiss(){

        if(progressDialog!=null) {
            progressDialog.dismiss();
        }
    }
    public static void setMessage(String msg){
        progressDialog.setMessage(msg);
    }
}
