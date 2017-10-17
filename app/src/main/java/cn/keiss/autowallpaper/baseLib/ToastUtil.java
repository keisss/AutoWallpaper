package cn.keiss.autowallpaper.baseLib;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by hekai on 2017/10/17.
 */

public class ToastUtil {
    private static Toast toast;


    public static void showToast(Context context,String content) {
        if (toast == null && context !=null) {
            toast = Toast.makeText(context,
                    content,
                    Toast.LENGTH_SHORT);
        } else {
            assert toast != null;
            toast.setText(content);
        }
        toast.show();
    }
}
