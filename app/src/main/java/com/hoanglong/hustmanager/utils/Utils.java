package com.hoanglong.hustmanager.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Utils {
    public static String getTime() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        Date currentTime = Calendar.getInstance().getTime();
        String time = calendar.get(Calendar.YEAR) + "" + calendar.get(Calendar.MONTH) + "" + calendar.get(Calendar.DATE)
                + "" + currentTime.getHours() + "" + currentTime.getMinutes() + "" + currentTime.getSeconds();
        return time;
    }
}
