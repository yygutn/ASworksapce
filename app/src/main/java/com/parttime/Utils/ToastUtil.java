package com.parttime.Utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;
import com.parttime.Application.GraduationApp;
import com.parttime.BaseLibs.BaseActivity;

/**
 * 显示Toast工具类
 * Created by Dog on 2015/4/4.
 */
public class ToastUtil {
    public static void toastShort(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void toastLong(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void showToast(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        Context context = BaseActivity.getTopActivity().get();
        if (context == null) {
            context = GraduationApp.getInstance().getBaseContext();
        }
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
