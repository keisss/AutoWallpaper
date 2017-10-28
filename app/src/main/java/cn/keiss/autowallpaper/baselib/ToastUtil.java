package cn.keiss.autowallpaper.baselib;

import android.content.Context;
import android.widget.Toast;

/**
 * @author hekai
 * @date 2017/10/17
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
