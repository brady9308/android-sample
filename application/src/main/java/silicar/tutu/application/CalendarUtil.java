package silicar.tutu.application;

import android.view.ViewGroup;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by tutu on 2016/2/29.
 */
public class CalendarUtil extends GregorianCalendar {
    private long tempTime;

    public static synchronized CalendarUtil getInstance() {
        return new CalendarUtil();
    }

    public CalendarUtil() {
        super();
    }

    public CalendarUtil(Locale locale) {
        super(locale);
    }

    public CalendarUtil(TimeZone timezone) {
        super(timezone);
    }

    public CalendarUtil(TimeZone timezone, Locale locale) {
        super(timezone, locale);
    }

    public CalendarUtil(int year, int month, int day) {
        super(year, month, day);
    }

    public CalendarUtil(int year, int month, int day, int hour, int minute) {
        super(year, month, day, hour, minute);
    }

    public CalendarUtil(int year, int month, int day, int hour, int minute, int second) {
        super(year, month, day, hour, minute, second);
    }

    /**
     * 获取一个月所有时间
     * @return
     */
    public long[][] getMonthTimes(){
        tempTime = getTimeInMillis();
        int start = 1 - getFirstDayWeek();
        set(Calendar.DAY_OF_MONTH, 1);
        add(Calendar.DAY_OF_YEAR, start);
        long[][] month = new long[6][7];
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 7; j++){
                month[i][j] = getTimeInMillis();
                add(Calendar.DAY_OF_YEAR, 1);
            }
        }
        setTimeInMillis(time);
        return month;
    }

    /**
     * 当月第一天星期几
     * @return
     */
    public int getFirstDayWeek(){
        tempTime = getTimeInMillis();
        set(Calendar.DAY_OF_MONTH, 1);
        int week = get(DAY_OF_WEEK);
        setTimeInMillis(tempTime);
        return week;
    }

    /**
     * 当月最大值
     * @return
     */
    public int getActualMaxDay(){
        return getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public long getTempTime() {
        return tempTime;
    }

    public void setTempTime(long tempTime) {
        this.tempTime = tempTime;
    }
}
