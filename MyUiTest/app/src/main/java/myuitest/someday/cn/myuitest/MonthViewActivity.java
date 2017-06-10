package myuitest.someday.cn.myuitest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MonthViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MonthView monthView = (MonthView) findViewById(R.id.monthView);
        monthView.setMonthAdapter(new MonthView.MonthViewAdapter(2017,6)
        {
            @Override
            RecyclerView.ViewHolder createHolder(ViewGroup parent)
            {
                return new DayView(parent);
            }

            @Override
            void onBindView(int day, RecyclerView.ViewHolder viewHolder)
            {
                ((DayView)viewHolder).bindData(day);
            }
        });

    }

    private static class DayView extends RecyclerView.ViewHolder
    {
        private final TextView dayName;

        public DayView(View itemView)
        {
            super(LayoutInflater.from(itemView.getContext()).inflate(R.layout.view_monthview_day, (ViewGroup) itemView,false));
            dayName =(TextView)this.itemView.findViewById(R.id.day);
        }

        public void bindData(int day)
        {
            dayName.setText(String.valueOf(day));
        }
    }
}
