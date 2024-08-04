package Reminder.src;

import java.util.Calendar;
import java.util.Date;

// 予定日時、タイトル、詳細説明を含むスケジュール型
public class ScheduleData {

    public Date date;
    public String title;
    public String detail;

    ScheduleData(Date date, String title, String detail) {
        this.date = date;
        this.title = title;
        this.detail = detail;
    }

    // long型のスケジュール時刻を返す
    public long getTime() {
        return date.getTime();
    }

    // スケジュールの時間が現在より前かを返す
    public boolean isPast() {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MONTH, c.get(Calendar.MONTH)-1);
        return c.getTime().before(Calendar.getInstance().getTime());
    }
}
