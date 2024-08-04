package Reminder.src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.TimeZone;

public class Utilities {

    public static final int YEAR = 0;
    public static final int MONTH = 1;
    public static final int DAY = 2;
    public static final int HOUR = 3;
    public static final int MINUTE = 4;
    public static final int SECOND = 5;

    private static final String[] dow = {"日", "月", "火", "水", "木", "金", "土"};

    // 現在時刻 "year年 month月 day日 hour:minute:second" を返す
    public static String date() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        return String.format(
            "%d年 %d月 %d日 %02d:%02d:%02d", 
            calendar.get(Calendar.YEAR),        calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH),
            calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),  calendar.get(Calendar.SECOND)
        );
        // 月は0月~11月になっているため+1
    }

    // 現在の 年/月/日/時/分 を返す
    public static int getTime(int code) {
        Calendar c = Calendar.getInstance(TimeZone.getDefault());
        switch (code) {
            case YEAR   : return c.get(Calendar.YEAR);
            case MONTH  : return c.get(Calendar.MONTH)+1;
            case DAY    : return c.get(Calendar.DAY_OF_MONTH);
            case HOUR   : return c.get(Calendar.HOUR_OF_DAY);
            case MINUTE : return c.get(Calendar.MINUTE);
            case SECOND : return c.get(Calendar.SECOND);
            default     : return -1;
        }
        // 月は0月~11月になっているため+1
    }

    // 現在の曜日を返す
    public static String getDow() {
        Calendar c = Calendar.getInstance(TimeZone.getDefault());
        return dow[c.get(Calendar.DAY_OF_WEEK)-1];
    }

    // ScheduleDataのリストを昇順ソートして返す
    public static ArrayList<ScheduleData> sortSchedule(ArrayList<ScheduleData> list) {
        Object[] oArray = list.toArray();
        ScheduleData[] array = new ScheduleData[oArray.length];
        for (int i=0; i<oArray.length; i++) array[i] = (ScheduleData)oArray[i];

        Arrays.sort(array, Comparator.comparing(ScheduleData::getTime));
        
        ArrayList<ScheduleData> sortedList = new ArrayList<>();
        for (ScheduleData d: array) {
            sortedList.add(d);
        }
        return sortedList;
    }
}
