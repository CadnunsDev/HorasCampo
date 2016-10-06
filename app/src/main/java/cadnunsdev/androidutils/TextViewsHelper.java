package cadnunsdev.androidutils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class TextViewsHelper {
	
	public static <T extends TextView> int  getInt(Class<T> type,  Activity act, int resourceId){
        String valor = "0";
        try {
			T view = (T)act.findViewById(resourceId);
			valor = view.getText().toString();
			return Integer.parseInt(valor);
		} catch (NumberFormatException e) {
			String msg = e.getMessage();
			return 0;
		}
	}
}
