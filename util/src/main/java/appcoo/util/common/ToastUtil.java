package appcoo.util.common;

import android.content.Context;
import android.widget.Toast;

/**
 * <hr/>
 *
 * @author www.TheWk.cn.vc
 */
public abstract class ToastUtil {
    public static void showLong(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void showShort(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
