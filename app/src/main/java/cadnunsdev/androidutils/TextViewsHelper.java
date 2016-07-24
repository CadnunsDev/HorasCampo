package cadnunsdev.androidutils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class TextViewsHelper {
	
	public static int getInt(Activity act, int resourceId){
		try {
			TextView view = (TextView)act.findViewById(resourceId);
			return Integer.parseInt(view.getText().toString());
		} catch (NumberFormatException e) {
			return 0;
		}
	}
}
