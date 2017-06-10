package myuitest.someday.cn.myuitest;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;

/**
 * 用于显示一整个月份日期的view
 * Created by je on 17-6-7.
 */

public class MonthView extends RecyclerView
{
    private static final int ONE_WEEK_DAYS = 7;//一个星期中的天数，7天

    public MonthView(Context context) {super(context);initialize();}
    public MonthView(Context context, @Nullable AttributeSet attrs) {super(context, attrs);initialize();}
    public MonthView(Context context, @Nullable AttributeSet attrs, int defStyle) {super(context, attrs, defStyle);initialize();}

    private int mBeforeNullDay;//本月第一周一号，前面要空多少格(天)
    private int mMaxDayOfMonth;//本月最大的天数

    private final int VIEWTYPE_WEEK = 1;//周
    private final int VIEWTYPE_NULL = 2;//空隔
    private final int VIEWTYPE_DAY = 3;//某一天
    private MonthViewAdapter mViewAdapter;

    private void initialize()
    {
        setLayoutManager(new GridLayoutManager(getContext(), ONE_WEEK_DAYS));
    }

    /**
     * 调用此方法不会有任何效果
     * 请调用{link #setMonthAdapter(MonthViewAdapter)}
     * @see #setMonthAdapter(MonthViewAdapter)
     * @param adapter
     */
    @Deprecated
    @Override
    public void setAdapter(RecyclerView.Adapter adapter)
    {
    }

    /**
     * 设置显示内容适配器
     * 必须要调用此方法后才能正确显示日历控件
     * @param viewAdapter
     */
    public void setMonthAdapter(MonthViewAdapter viewAdapter)
    {
        mViewAdapter = viewAdapter;
        initDays();
        super.setAdapter(new RecyclerAdapter());

    }

    /**
     * 计算指定月份第一天在周几，最后一天在周几
     * 第一周空隔多少天为一号
     * 最后一周有多少天的空隔
     */
    private void initDays()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(mViewAdapter.mYear,mViewAdapter.mMonth,1);
        mBeforeNullDay = calendar.get(Calendar.DAY_OF_WEEK)-1;//本月一号之前的空白天数
        mMaxDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);//本月最大天数

    }

    class RecyclerAdapter extends RecyclerView.Adapter
    {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            switch (viewType)
            {
                case VIEWTYPE_WEEK:
                    return new WeekView(parent);
                case VIEWTYPE_NULL:
                    return new NullDayView(parent);
                case VIEWTYPE_DAY:
                    return mViewAdapter.createHolder(parent);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position)
        {
            switch (getItemViewType(position))
            {
                case VIEWTYPE_WEEK:
                    ((WeekView)holder).bindData(position);
                    return;
                //case VIEWTYPE_NULL:
                case VIEWTYPE_DAY:
                    mViewAdapter.onBindView(position - ONE_WEEK_DAYS - mBeforeNullDay + 1 , holder);
                    return;
            }
        }

        @Override
        public int getItemViewType(int position)
        {
            if(position < ONE_WEEK_DAYS)
                return VIEWTYPE_WEEK;
            else if(position < (ONE_WEEK_DAYS + mBeforeNullDay))
                return VIEWTYPE_NULL;
            else
                return VIEWTYPE_DAY;
        }

        @Override
        public int getItemCount()
        {
            return ONE_WEEK_DAYS + mBeforeNullDay + mMaxDayOfMonth;
        }
    }

    class WeekView extends RecyclerView.ViewHolder
    {
        private final TextView weekName;

        public WeekView(View itemView)
        {
            super(LayoutInflater.from(itemView.getContext()).inflate(R.layout.view_monthview_week, (ViewGroup) itemView,false));
            weekName = (TextView)this.itemView.findViewById(R.id.week);
        }
        public void bindData(int position)
        {
            switch (position)
            {
                case 0:
                    weekName.setText("日");
                    break;
                case 1:
                    weekName.setText("一");
                    break;
                case 2:
                    weekName.setText("二");
                    break;
                case 3:
                    weekName.setText("三");
                    break;
                case 4:
                    weekName.setText("四");
                    break;
                case 5:
                    weekName.setText("五");
                    break;
                case 6:
                    weekName.setText("六");
                    break;
            }
        }
    }

    class NullDayView extends RecyclerView.ViewHolder
    {
        public NullDayView(View itemView)
        {
            super(LayoutInflater.from(itemView.getContext()).inflate(R.layout.view_monthview_nullday, (ViewGroup) itemView,false));
        }
    }


    public static abstract class MonthViewAdapter
    {
        int mYear;
        int mMonth;

        /**
         * @param year 年份
         * @param month 月份 从1开始
         */
        public MonthViewAdapter(int year,int month)
        {
            mYear = year;
            /**
             * 由于{link Calendar}时间处理类的月份是从0开始算
             * 所以要把传入的月份减1来计算
             */
            mMonth = month - 1;
        }

        /**
         * 创建一天的视图
         * @param parent
         * @return
         */
        abstract RecyclerView.ViewHolder createHolder(ViewGroup parent);

        /**
         * 视图数据绑定
         * @param day
         * @param viewHolder 需要自行强制类型转换
         */
        abstract void onBindView(int day,RecyclerView.ViewHolder viewHolder);
    }

}
