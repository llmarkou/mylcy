package com.lbbs.test.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Description:
 * Data：2018/3/14-15:07
 * Author: 刘兵兵
 */

public class ToolUtil {
    private static Toast toast;

    public static void showToast(Context context,
                                 String content) {
        if (toast == null) {
            toast = Toast.makeText(context.getApplicationContext(),
                    content,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }
}
