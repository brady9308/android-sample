package silicar.tutu.application;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

/**
 * 日历选择
 * Created by tutu on 2016/2/22.
 */
public class CalendarView extends LinearLayout {

    private View headView;
    private ViewGroup contentView;
    private ViewGroup[][] dayView;
    private OnClickListener mOnClickListener;
    private int selectDay;

    private int month;
    private int day;
    private long[][] dayTime;
    private String[][] dayLabel;
    private CalendarUtil calendarUtil;

    private int bgGray = 0x00eeeeee;
    private int bgRed = 0x00ff0033;
    private int textGray = 0xff959595;
    private int textNormal = 0xff313131;

    public CalendarView(Context context) {
        super(context);
        init(context);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context){
        setOrientation(VERTICAL);
        headView = LayoutInflater.from(context).inflate(R.layout.layout_calendar_month_head, this, false);
        contentView = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.layout_calendar_month, this, false);
        addView(headView);
        addView(contentView);
        dayView = new ViewGroup[6][7];
        dayLabel = new String[6][7];
        calendarUtil = CalendarUtil.getInstance();

        for (int i = 0; i < 6; i++){
            ViewGroup week = getWeekOfMonth(i);
            getDayOfWeek(week,0);
            for (int j = 0; j < 7; j++){
                dayView[i][j] = getDayOfWeek(week, j);
            }
        }
        setCalendar(calendarUtil.getTimeInMillis());
    }

    private ViewGroup getWeekOfMonth(int week){
        return (ViewGroup) contentView.getChildAt(week * 2);
    }

    private ViewGroup getDayOfWeek(ViewGroup weekView, int day){
        return (ViewGroup) weekView.getChildAt(day * 2);
    }

    public void setCalendar(long date){
        calendarUtil.setTimeInMillis(date);
        month = calendarUtil.get(Calendar.MONTH);
        day = calendarUtil.get(Calendar.DAY_OF_MONTH);
        dayTime = calendarUtil.getMonthTimes();
        calendarUtil.setTempTime(calendarUtil.getTimeInMillis());
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 7; j++){
                calendarUtil.setTimeInMillis(dayTime[i][j]);
                dayView[i][j].setTag(calendarUtil.get(Calendar.MONTH));
                if (calendarUtil.get(Calendar.MONTH) != month){
                    ((TextView)dayView[i][j].findViewById(R.id.day)).setTextColor(textGray);
                    dayView[i][j].setOnClickListener(null);
                }
                else{
                    ((TextView)dayView[i][j].findViewById(R.id.day)).setTextColor(textNormal);
                    dayView[i][j].setOnClickListener(mOnClickListener);
                }
                ((TextView)dayView[i][j].findViewById(R.id.day)).setText("" + calendarUtil.get(Calendar.DAY_OF_MONTH));
            }
        }
        calendarUtil.setTimeInMillis(calendarUtil.getTempTime());
        setSelectDay(day);
        //CalendarUtil.DayIndex index = getDayIndex(25);
        //calendarUtil.setTimeInMillis(dayTime[index.week][index.day]);
        //Log.e("day", "" + calendarUtil.get(Calendar.DAY_OF_MONTH));
    }

    public void showDivision(boolean state){}

    public long getDayTime(int week, int day){
        return dayTime[week][day];
    }

    public void setDayTime(int week, int day, long dayTime) {
        this.dayTime[week][day] = dayTime;
    }

    public String getDayLabel(int day) {
        CalendarUtil.DayIndex index = calendarUtil.getDayIndex(day);
        return getDayLabel(index.week, index.day);
    }

    public void setDayLabel(int day, String dayLabel) {
        CalendarUtil.DayIndex index = calendarUtil.getDayIndex(day);
        setDayLabel(index.week, index.day, dayLabel);
    }

    public String getDayLabel(int week, int day) {
        return dayLabel[week][day];
    }

    public void setDayLabel(int week, int day, String dayLabel) {
        this.dayLabel[week][day] = dayLabel;
        if (dayLabel != null){
            dayView[week][day].findViewById(R.id.label).setVisibility(VISIBLE);
            ((TextView)dayView[week][day].findViewById(R.id.label)).setText(dayLabel);
        }else
            dayView[week][day].findViewById(R.id.label).setVisibility(GONE);
    }

    public ViewGroup getDayView(int week, int day) {
        return dayView[week][day];
    }

    public CalendarUtil.DayIndex getDayIndex(int day){
        return calendarUtil.getDayIndex(day);
    }

    public long[][] getDayTimes() {
        return dayTime;
    }

    public String[][] getDayLabels() {
        return dayLabel;
    }

    public ViewGroup[][] getDayViews() {
        return dayView;
    }

    public int getSelectDay() {
        return selectDay;
    }

    public void setSelectDay(int selectDay) {
        CalendarUtil.DayIndex index = getDayIndex(this.selectDay);
        dayView[index.week][index.day].findViewById(R.id.select).setVisibility(GONE);
        if (selectDay != 0) {
            this.selectDay = selectDay;
            index = getDayIndex(this.selectDay);
            dayView[index.week][index.day].findViewById(R.id.select).setVisibility(VISIBLE);
        }else {
            this.selectDay = selectDay;
        }
    }

    public OnClickListener getOnClickListener() {
        return mOnClickListener;
    }

    public void setOnClickListener(OnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 7; j++){
                if (((int)dayView[i][j].getTag()) == month) {
                    dayView[i][j].setOnClickListener(mOnClickListener);
                }
            }
        }
    }
}
