package com.lbbs.test.utils;

import android.util.Log;

import com.com.lbb.test.BuildConfig;

/**
 * Description:
 * Data：2018/5/14-14:27
 * Author: 刘兵兵
 */

public class LogUtils {
    // 自定义log参数
    private static final String LOG_TAG = "LogUtils";
    private static final int LOG_SIZE_LIMIT = 3500;
    /**
     * 统一自定义log，建议使用
     *
     * @param paramClass getClass()或xxx.class
     * @param param      需要打印Object
     */
    public static int LOG_D(Class<?> paramClass, Object param) {
        // debug模式才打印log,如果需要看release包下面日子则开启下面（设置为true）即可
        if (BuildConfig.DEBUG) {
            String paramString = param.toString();
            String str = paramClass.getName();
            if (str != null) {
                str = str.substring(1 + str.lastIndexOf("."));
            }
            int i = paramString.length();
            if (i > LOG_SIZE_LIMIT) {
                int j = 0;
                int k = 1 + i / LOG_SIZE_LIMIT;
                while (j < k + -1) {
                    Log.d(LOG_TAG, paramString.substring(j * LOG_SIZE_LIMIT,
                            LOG_SIZE_LIMIT * (j + 1)));
                    j++;
                }
                return Log.d(LOG_TAG, paramString.substring(j * LOG_SIZE_LIMIT, i));
            } else {
                return Log.d(LOG_TAG, str + " -> " + paramString);
            }
        }
        return 0;
    }
}
