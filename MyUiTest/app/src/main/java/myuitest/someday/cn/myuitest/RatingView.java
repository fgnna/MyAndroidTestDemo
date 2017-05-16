package myuitest.someday.cn.myuitest;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 星星评级自定义VIEW
 * @author shaojunjie on 17-5-15
 * @Email fgnna@qq.com
 *
 */
public class RatingView extends ConstraintLayout
{
    public RatingView(Context context) {super(context);initialize();}
    public RatingView(Context context, AttributeSet attrs) {super(context, attrs);initialize();}
    public RatingView(Context context, AttributeSet attrs, int defStyleAttr) {super(context, attrs, defStyleAttr);initialize();}

    private TextView[] mRatingTypes;
    private View[][] mRatingTypeImgs;

    private void initialize()
    {
        LayoutInflater.from(getContext()).inflate(R.layout.view_rating,this);
        mRatingTypes = new TextView[]{
                (TextView) findViewById(R.id.m_rating_type_1),
                (TextView) findViewById(R.id.m_rating_type_2),
                (TextView) findViewById(R.id.m_rating_type_3),
        };
        mRatingTypeImgs = new View[][]{
                {
                    findViewById(R.id.m_rating_type_1_img_1),
                    findViewById(R.id.m_rating_type_1_img_2),
                    findViewById(R.id.m_rating_type_1_img_3),
                    findViewById(R.id.m_rating_type_1_img_4),
                    findViewById(R.id.m_rating_type_1_img_5),
                },
                {
                    findViewById(R.id.m_rating_type_2_img_1),
                    findViewById(R.id.m_rating_type_2_img_2),
                    findViewById(R.id.m_rating_type_2_img_3),
                    findViewById(R.id.m_rating_type_2_img_4),
                    findViewById(R.id.m_rating_type_2_img_5),
                },
                {
                    findViewById(R.id.m_rating_type_3_img_1),
                    findViewById(R.id.m_rating_type_3_img_2),
                    findViewById(R.id.m_rating_type_3_img_3),
                    findViewById(R.id.m_rating_type_3_img_4),
                    findViewById(R.id.m_rating_type_3_img_5),
                }
        };
    }


    public void setRating(String name1,int level1)
    {
        setOneRating(0,name1,level1);
    }
    public void setRating(String name1,int level1,String name2,int level2)
    {
        setOneRating(0,name1,level1);
        setOneRating(1,name2,level2);

    }
    public void setRating(String name1,int level1,String name2,int level2,String name3,int level3)
    {
        setOneRating(0,name1,level1);
        setOneRating(1,name2,level2);
        setOneRating(2,name3,level3);
    }

    private void setOneRating(int typeIndex,String typeName,final int level)
    {
        if(level<=0)
            throw new IllegalArgumentException("level not > 0");

        mRatingTypes[typeIndex].setText(typeName);
        mRatingTypes[typeIndex].setVisibility(VISIBLE);
        for (int i = 0; i < 5; i++)
        {
            mRatingTypeImgs[typeIndex][i].setVisibility( i < level ? VISIBLE : GONE );
            mRatingTypeImgs[typeIndex][i].setBackgroundColor(0xffdcdcdc);
        }
    }













}
