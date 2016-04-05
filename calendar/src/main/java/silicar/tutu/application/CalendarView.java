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

    public static final int PARENT_VIEW = -1;
    public static final int SELECT_VIEW = -2;
    public static final int LABEL_VIEW = -3;
    public static final int DATE_VIEW = -4;
    public static final int POSITION = -6;
    public static final int DATA = -7;

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
                dayView[i][j].setTag(PARENT_VIEW, this);
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

    private View getSelectView(View view){
        View select = (View) view.getTag(SELECT_VIEW);
        if (select == null)
            select = view.findViewById(R.id.select);
        return select;
    }

    public View getLabelView(ViewGroup dayView){
        View select = (View) dayView.getTag(LABEL_VIEW);
        if (select == null)
            select = dayView.findViewById(R.id.label);
        return select;
    }

    public View getDateView(ViewGroup dayView){
        View select = (View) dayView.getTag(DATE_VIEW);
        if (select == null)
            select = dayView.findViewById(R.id.day);
        return select;
    }

    public ViewGroup getDayView(int day){
        CalendarUtil.DayIndex index = calendarUtil.getDayIndex(day);
        return getDayView(index.week, index.day);
    }

    public ViewGroup getDayView(int week, int day) {
        return dayView[week][day];
    }

    public ViewGroup[][] getAllDayView() {
        return dayView;
    }

    public void setCalendar(long date){
        calendarUtil.setTimeInMillis(date);
        month = calendarUtil.get(Calendar.MONTH);
        day = calendarUtil.get(Calendar.DAY_OF_MONTH);
        dayTime = calendarUtil.getMonthTimes();
        calendarUtil.setTempTime(calendarUtil.getTimeInMillis());
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 7; j++){
                //清空选中
                getSelectView(dayView[i][j]).setVisibility(GONE);
                //清空标签
                setDayLabel(i, j, null);
                calendarUtil.setTimeInMillis(dayTime[i][j]);
                dayView[i][j].setTag(calendarUtil.get(Calendar.MONTH));
                if (calendarUtil.get(Calendar.MONTH) != month){
                    ((TextView)getDateView(dayView[i][j])).setTextColor(textGray);
                    dayView[i][j].setOnClickListener(null);
                }
                else{
                    ((TextView)getDateView(dayView[i][j])).setTextColor(textNormal);
                    dayView[i][j].setOnClickListener(mOnClickListener);
                }
                ((TextView)getDateView(dayView[i][j])).setText("" + calendarUtil.get(Calendar.DAY_OF_MONTH));
            }
        }
        calendarUtil.setTimeInMillis(calendarUtil.getTempTime());
        //LogUtils.e("" + day);
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

    public String getDayLabel(int week, int day) {
        return dayLabel[week][day];
    }

    public void setDayLabel(int day, String dayLabel) {
        CalendarUtil.DayIndex index = calendarUtil.getDayIndex(day);
        setDayLabel(index.week, index.day, dayLabel);
    }

    public void setDayLabel(int week, int day, String dayLabel) {
        this.dayLabel[week][day] = dayLabel;
        if (dayLabel != null){
            getLabelView(dayView[week][day]).setVisibility(VISIBLE);
            ((TextView)getLabelView(dayView[week][day])).setText(dayLabel);
        }else
            getLabelView(dayView[week][day]).setVisibility(GONE);
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

    public int getSelectDay() {
        return selectDay;
    }

    public void setSelectDay(int selectDay) {
        CalendarUtil.DayIndex index = getDayIndex(this.selectDay);
        //LogUtils.e("" + index.week);
        //LogUtils.e("" + index.day);
        getSelectView(dayView[index.week][index.day]).setVisibility(GONE);
        if (selectDay != 0) {
            this.selectDay = selectDay;
            index = getDayIndex(this.selectDay);
            getSelectView(dayView[index.week][index.day]).setVisibility(VISIBLE);
        }else {
            this.selectDay = selectDay;
            //for (int i = 0; i < 6; i++){
            //    for (int j = 0; j < 7; j++){
            //        getSelectView(dayView[i][j]).setVisibility(GONE);
            //    }
            //}
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
