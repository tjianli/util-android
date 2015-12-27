package appcoo.util.string;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;
import java.util.TimeZone;

public abstract class DataStringUtil {

    private DataStringUtil() {

    }

    public static String fromTimeToString(long timeMs) {

        long totalSeconds = timeMs / 1000;// / 1000;

        long seconds = totalSeconds % 60;
        long minutes = totalSeconds / 60 % 60;
        long hours = totalSeconds / 3600;

        Formatter formatter = new Formatter();
        if (hours > 0) {
            return formatter.format("%02d:%02d:%02d",
                    new Object[]{Long.valueOf(hours), Long.valueOf(minutes), Long.valueOf(seconds)}).toString();
        } else {
            return formatter.format("%02d:%02d",
                    new Object[]{Long.valueOf(minutes), Long.valueOf(seconds)}).toString();
        }
    }

    public static String fromTimeToString2(long timeMs) {
        long totalSeconds = timeMs / 1000;// / 1000;

        long seconds = totalSeconds % 60;
        long minutes = totalSeconds / 60 % 60;
        long hours = totalSeconds / 3600;

        Formatter formatter = new Formatter();
        if (hours > 0) {
            return formatter.format("%02d:%02d'%02d''",
                    new Object[]{Long.valueOf(hours), Long.valueOf(minutes),
                            Long.valueOf(seconds)}).toString();
        } else {
            return formatter.format("%02d'%02d''",
                    new Object[]{Long.valueOf(minutes), Long.valueOf(seconds)}).toString();
        }
    }

    public static String formatDate(Context context, String sDate) {
        String result = "";
        if (sDate == null || sDate.equals("")) {
            return "";
        }
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        SimpleDateFormat cursdf = new SimpleDateFormat("yyyy-MM-dd");
        String curdate = cursdf.format(new java.util.Date());
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf2.parse(sDate);
            if (cursdf.format(date).equals(curdate)) {
                result = time.format(date);
            } else {
                result = sdf1.format(date);
            }

        } catch (ParseException e) {
            e.printStackTrace();
            result = "";
        }
        return result;
    }

    public static String formatDateChannel(String sDate) {
        String result = "";
        if (sDate == null || sDate.equals("")) {
            return "";
        }
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd ");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf2.parse(sDate);
            result = sdf1.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            result = "";
        }
        return result;
    }

    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }

    public static Date getDay(Date date, int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, i);
        date = calendar.getTime();
        return date;
    }

    public static String getTimeString(long l) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.UK);
        sdf.setTimeZone(TimeZone.getDefault()); // 设置时区为GMT
        Date date = new Date(l * 1000L);
        String str = sdf.format(date);
        return str;
    }

    public static String getDateString(long l) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.UK);
        sdf.setTimeZone(TimeZone.getDefault()); // 设置时区为GMT
        Date date = new Date(l * 1000L);
        String str = sdf.format(date);
        return str;
    }

    public static int getVideoLen(String len) {
        try {
            int hour = Integer.parseInt(len.split(":")[0]) * 60 * 60;
            int min = Integer.parseInt(len.split(":")[1]) * 60;
            int sec = Integer.parseInt(len.split(":")[2]);
            return hour + min + sec;
        } catch (Exception e) {
            return 0;
        }
    }

    public static String getYearDateString(long l) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM", Locale.UK);
        sdf.setTimeZone(TimeZone.getDefault()); // 设置时区为GMT
        Date date = new Date(l * 1000L);
        String str = sdf.format(date);
        return str;
    }
}
