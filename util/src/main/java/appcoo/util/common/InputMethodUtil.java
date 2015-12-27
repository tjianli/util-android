package appcoo.util.common;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class InputMethodUtil {

	public static void hideSoftKeyboard(Context context , EditText view){
		((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)). 
	     hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS); 
	}
	
	public static void showSoftKeyboard(Context context , EditText view){
		
		InputMethodManager inputManager =
                (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(view, 0);
	}
}
