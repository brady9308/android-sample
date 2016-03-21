package silicar.tutu.application;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final CalendarView calendarView = (CalendarView) findViewById(R.id.view);
        calendarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = ((TextView)v.findViewById(R.id.day)).getText().toString();
                calendarView.setSelectDay(Integer.valueOf(str));
                CalendarUtil.DayIndex index = calendarView.getDayIndex(Integer.valueOf(str));
                calendarView.setDayLabel(index.week, index.day, str);
                Log.e("str", "" + str);
            }
        });
    }
}
