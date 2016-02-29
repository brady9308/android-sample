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

    private long[][] dayTime;
    private CalendarUtil calendarUtil;

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
        headView = LayoutInflater.from(context).inflate(R.layout.layout_month_head, this, false);
        contentView = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.layout_month, this, false);
        calendarUtil = CalendarUtil.getInstance();
        addView(headView);
        addView(contentView);
        dayView = new ViewGroup[6][7];
        dayTime = calendarUtil.getMonthTimes();
        calendarUtil.setTempTime(calendarUtil.getTimeInMillis());
        for (int i = 0; i < 6; i++){
            ViewGroup week = getWeekOfMonth(i);
            getDayOfWeek(week,0);
            for (int j = 0; j < 7; j++){
                dayView[i][j] = getDayOfWeek(week, j);
                calendarUtil.setTimeInMillis(dayTime[i][j]);
                ((TextView)dayView[i][j].findViewById(R.id.day)).setText("" + calendarUtil.get(Calendar.DAY_OF_MONTH));
            }
        }
        calendarUtil.setTempTime(calendarUtil.getTempTime());
    }

    public ViewGroup getWeekOfMonth(int week){
        return (ViewGroup) contentView.getChildAt(week * 2);
    }

    public ViewGroup getDayOfWeek(ViewGroup weekView, int day){
        return (ViewGroup) weekView.getChildAt(day * 2);
    }

    public void showDivision(boolean state){}

}
