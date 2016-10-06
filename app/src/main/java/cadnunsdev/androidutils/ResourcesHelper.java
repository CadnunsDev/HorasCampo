package cadnunsdev.androidutils;

import android.content.Context;

import java.io.InputStream;


/**
 * Created by Tiago Silva on 19/08/2016.
 */
public class ResourcesHelper {
    public static String LoadFile(Context ctx, String filenameInAssets){
        try {

            InputStream in_s = ctx.getAssets().open(filenameInAssets);
            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            return new String(b);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
