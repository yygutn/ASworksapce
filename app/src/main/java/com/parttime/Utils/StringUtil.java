package com.parttime.Utils;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jumy on 15/12/8 下午2:44.
 * deadline is the first productivity
 */
public class StringUtil {
    public static boolean isNullOrEmpty(String string) {
        return string == null || string.length() == 0;
    }

    public static int dp2px(Context context, float dipValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);
    }

    public static int px2dp(Context context, float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }

    /**
     * 将秒转换为详细的时间
     * @param seconds
     * @return
     */
    public static String getSecondTotime(int seconds){
        Date date = new Date((long)seconds * 1000);
        Date nowDate = new Date();
        if(date.getYear() < nowDate.getYear()) return StringUtil.formatDate(seconds, "yyyy-MM-dd");
        else if(date.getMonth() < nowDate.getMonth() || date.getDate() < nowDate.getDate() - 1) return (StringUtil.formatDate(seconds, "MM-dd"));
        else if(date.getDate() == nowDate.getDate() - 1) return (StringUtil.formatDate(seconds, "昨天: HH:mm"));
        else return (StringUtil.formatDate(seconds, "今天: HH:mm"));
    }

    /**
     * 时间转换
     * @param date
     * @param format
     * @return
     */
    public static String formatDate(Date date, String format) {
        SimpleDateFormat dateformat = new SimpleDateFormat(format);
        return dateformat.format(date);
    }

    /**
     * 时间转换
     * @param second
     * @param format
     * @return
     */
    public static String formatDate(int second, String format) {
        return formatDate(new Date(((long) second) * 1000), format);
    }
}
