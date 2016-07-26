package cadnunsdev.androidutils;

import android.app.Activity;

/**
 * Created by Tiago Silva on 26/07/2016.
 */
public class BaseActivity extends Activity {
    protected  <T>T findViewById(Class<T> type, int id){
        return type.cast(findViewById(id));
    }
}
