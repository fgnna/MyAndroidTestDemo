package myuitest.someday.cn.myuitest;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private MultipleConditionalSortView mMultipleConditionalSortView;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMultipleConditionalSortView = (MultipleConditionalSortView)findViewById(R.id.multipleConditionalSortView);

        mMultipleConditionalSortView.setAdapter(new MultipleConditionalSortView.Adapter<Integer>()
        {
            @Override
            public String[] getConditionTypeNames() {return new String[]{"条件1","条件2","条件3"};}

            @Override
            public MultipleConditionalSortView.ConditionItem[] getConditionItemNames(int conditionTypeIndex, String conditionTypeName)
            {
                switch (conditionTypeIndex)
                {
                    case 0:
                        return new MultipleConditionalSortView.ConditionItem[]{
                                new MultipleConditionalSortView.ConditionItem("子项1",1),
                                new MultipleConditionalSortView.ConditionItem("子项2",2),
                                new MultipleConditionalSortView.ConditionItem("子项3",3),};
                    case 1:
                        return new MultipleConditionalSortView.ConditionItem[]{
                                new MultipleConditionalSortView.ConditionItem("子项4",4),
                                new MultipleConditionalSortView.ConditionItem("子项5",5),
                                new MultipleConditionalSortView.ConditionItem("子项6",6),
                                new MultipleConditionalSortView.ConditionItem("子项7",7),};
                    case 2:
                        return new MultipleConditionalSortView.ConditionItem[]{
                                new MultipleConditionalSortView.ConditionItem("子项8",8),
                                new MultipleConditionalSortView.ConditionItem("子项9",9),};
                }
                return null;
            }

            @Override
            public void onSelect(Integer selectType1,Integer selectType2,Integer selectType3)
            {
                Log.d("onSelect","selectType1="+selectType1+"; selectType2="+selectType2+"; selectType3="+selectType3);
            }

        });


        final RatingView ratingView = (RatingView) findViewById(R.id.ratingView);
        ratingView.postDelayed(new Runnable() {
            @Override
            public void run() {
                ratingView.setRating("类1",2);
            }
        },2000);
        ratingView.postDelayed(new Runnable() {
            @Override
            public void run() {
                ratingView.setRating("类1",2,"类2",4);
            }
        },5000);
        ratingView.postDelayed(new Runnable() {
            @Override
            public void run() {
                ratingView.setRating("类1",2,"类2",3,"类1",2);
            }
        },8000);


        View view = findViewById(R.id.text);
        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d("adsfsadfasdf","asdfasdfdas");
            }
        });
    }

    private class WelfareCardDrawable extends Drawable
    {
        private Paint mBgPaint;
        private Paint mRoundPaint;
        private int space = 0;
        private RectF mBgRectF;
        private int mAvgRoundHeight;
        private int mRadius;

        public WelfareCardDrawable()
        {
            mBgPaint = new Paint();
            mBgPaint.setColor(Color.parseColor("#3bca7f"));
            mBgPaint.setAntiAlias(true);
            mRoundPaint = new Paint();
            mRoundPaint.setColor(Color.WHITE);
            mRoundPaint.setAntiAlias(true);
        }

        @Override
        public void draw(@NonNull Canvas canvas)
        {
            if(space == 0)
            {
                mBgRectF = new RectF(0,0,canvas.getWidth(),canvas.getHeight());// 设置个新的长方形
                space = canvas.getHeight()/11/4;
                mAvgRoundHeight = (canvas.getHeight()-(space*12))/11;
                mRadius = mAvgRoundHeight / 2;
            }

            canvas.drawRoundRect(mBgRectF,10,10,mBgPaint);
            //计算每个圆的高度
            for(int i = 0; i < 11; i++)
            {
                canvas.drawCircle(0, mRadius+i*mAvgRoundHeight+space*(i+1), mRadius, mRoundPaint);// 小圆
            }
            Log.d("WelfareCardDrable",canvas.getHeight()+"");
        }

        @Override
        public void setAlpha(@IntRange(from = 0, to = 255) int alpha)
        {

        }

        @Override
        public void setColorFilter(@Nullable ColorFilter colorFilter)
        {

        }

        @SuppressWarnings("WrongConstant")
        @Override
        public int getOpacity()
        {
            return 0;
        }
    }
}
