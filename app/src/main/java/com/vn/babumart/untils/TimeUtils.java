package com.vn.babumart.untils;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 9/2/16
 * Time: 6:07 PM
 * Desc: TimeUtils
 */
public class TimeUtils {

    /**
     * Parse the time in milliseconds into String with the format: hh:mm:ss or mm:ss
     *
     * @param duration The time needs to be parsed.
     */
    @SuppressLint("DefaultLocale")
    public static String formatDuration(int duration) {
        duration /= 1000; // milliseconds into seconds
        int minute = duration / 60;
        int hour = minute / 60;
        minute %= 60;
        int second = duration % 60;
        if (hour != 0)
            return String.format("%2d giờ:%02d:%02d", hour, minute, second);
        else
            return String.format("%02d:%02d", minute, second);
    }

    public static boolean compare_two_date(String inputDate, String outputDate,
                                           String inputDateFormat, String outputDateFormat) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(inputDateFormat);
            Date date_start = sdf.parse(inputDate);
            // convent date 2
            SimpleDateFormat sdf2 = new SimpleDateFormat(outputDateFormat);
            Date date_end = sdf2.parse(outputDate);
            if (System.currentTimeMillis() < date_start.getTime()) {
                return false;
            }
           /* if (System.currentTimeMillis() < date_2.getTime()) {
                return false;
            }*/
            //long_date2 = System.currentTimeMillis();
            if (date_end.getTime() >= date_start.getTime()) {
                return true;
            } else
                return false;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String formatTime_lambai(int duration) {
        duration /= 1000; // milliseconds into seconds
        int minute = duration / 60;
        int hour = minute / 60;
        minute %= 60;
        int second = duration % 60;
        if (hour != 0)
            return String.format("%2d giờ %02d phút %02d giây", hour, minute, second);
        else
            return String.format("%02d phút %02d giây", minute, second);
    }

    public static String convent_date(String sDateinput, String fomatDateinput, String fomatDateoutput) {
        String strDateTime = "";
        DateFormat inputFormatter = new SimpleDateFormat(fomatDateinput);
        Date da = null;
        try {
            //  inputFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
            //   inputFormatter.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
            da = (Date) inputFormatter.parse(sDateinput);
            DateFormat outputFormatter = new SimpleDateFormat(fomatDateoutput);
            strDateTime = outputFormatter.format(da);
            return strDateTime;
        } catch (ParseException e) {
            e.printStackTrace();

        }
        return strDateTime;
    }

    public static String StringTimetoDate(Date sDateinput, String fomatDateoutput) {
        String strDateTime = "";
        Date da = null;
        try {
            DateFormat outputFormatter = new SimpleDateFormat(fomatDateoutput);
            strDateTime = outputFormatter.format(sDateinput);
            return strDateTime;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return strDateTime;
    }

    public static boolean compare_date_time(String sDateinput, String fomatDateinput) {
        long long_date1 = 0;
        long long_date2 = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(fomatDateinput);
            Date date_1 = sdf.parse(sDateinput);
            long_date1 = date_1.getTime();
            // convent date 2
            long_date2 = System.currentTimeMillis();
            Date date = new Date(long_date2);
            SimpleDateFormat df2 = new SimpleDateFormat(fomatDateinput);
            String da = df2.format(date);
            Log.i("abc", da);
            //
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long time = long_date2 - long_date1;
        long hour = TimeUnit.HOURS.convert(time, TimeUnit.MILLISECONDS);
        if (hour > 24) {
            return true;
        } else return false;
    }

    public static boolean compare_two_date_currenttime(String inputDate, String outputDate) {
        boolean isTrue = false;
        try {
            String pattern = "HH:mm:ss dd/MM/yyyy";
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            Date Date1 = formatter.parse("00:00:01 " + inputDate);
            Date Date2 = formatter.parse("23:59:01 " + outputDate);
            //    String pattern = "EEEE dd-MMM-yyyy";
            DateFormat df = new SimpleDateFormat(pattern);
            Date date_current = Calendar.getInstance().getTime();
            if (Date1 != null && Date2 != null) {
                if (date_current.before(Date1)) {
                    return false;
                }
                if (date_current.after(Date2))
                    return false;
                if (date_current.equals(Date1) || date_current.equals(Date2)) {
                    return true;
                }
                if (date_current.after(Date1) && date_current.before(Date2)) {
                    return true;
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return isTrue;
    }
}
