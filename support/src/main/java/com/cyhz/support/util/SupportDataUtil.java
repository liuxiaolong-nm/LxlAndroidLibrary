package com.cyhz.support.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * Created by qinghua on 2015/6/16.
 */
public class SupportDataUtil {
    public static long dataToMillisecond(String format, String data) {
        try {
            SimpleDateFormat e = new SimpleDateFormat(format);
            return e.parse(data).getTime();
        } catch (ParseException var3) {
            var3.printStackTrace();
            return -1L;
        }
    }

    public static String getFormatedDateTime(String format, long dateTime) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(format);
        return sDateFormat.format(new Date(dateTime + 0L));
    }

    public static String getCurrentDate() {
        return getFormatDateTime(new Date(), "yyyy-MM-dd");
    }

    public static String getCurrentDateTime() {
        return getFormatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    public static String getFormatDateTime(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
}
