package cadnunsdev.androidutils;

import android.app.Activity;
import android.app.Fragment;
import android.widget.Toast;

public class ToastExt {
    public static void ShowMsg(Fragment ctx, String msg){
        ShowMsg(ctx.getActivity(), msg);
    }
    public static void ShowMsg(Activity act, String msg){
    	Toast toast = Toast.makeText(act.getApplicationContext(),msg,Toast.LENGTH_LONG);
        toast.show();
    }
}

