package myuitest.someday.cn.myuitest;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 *
 * 下拉式多条件排序选择 控件
 *
 * Created by shaojunjie on 17-5-12.
 */

public class MultipleConditionalSortView extends ConstraintLayout implements View.OnClickListener
{
    public MultipleConditionalSortView(Context context) {super(context);initialize();}
    public MultipleConditionalSortView(Context context, @Nullable AttributeSet attrs) {super(context, attrs);initialize();}
    public MultipleConditionalSortView(Context context, AttributeSet attrs, int defStyleAttr) {super(context, attrs, defStyleAttr);initialize();}


    private TextView mConditionalType_1;
    private TextView mConditionalType_2;
    private TextView mConditionalType_3;
    private LinearLayout mConditionalItmeLayout_1;
    private LinearLayout mConditionalItmeLayout_2;
    private LinearLayout mConditionalItmeLayout_3;

    private Adapter mAdapter;
    private View mCurrentOpen;

    public void setAdapter(Adapter adapter)
    {
        if(null == adapter)
            throw new IllegalArgumentException("Adatper is null");
        mAdapter = adapter;
        refreshLayout();
    }

    private void initialize()
    {
        LayoutInflater.from(getContext()).inflate(R.layout.view_multiple_conditional_sort,this,true);
        mConditionalType_1 = (TextView) findViewById(R.id.m_conditional_type_1);
        mConditionalType_2 = (TextView) findViewById(R.id.m_conditional_type_2);
        mConditionalType_3 = (TextView) findViewById(R.id.m_conditional_type_3);
        mConditionalItmeLayout_1 = (LinearLayout) findViewById(R.id.conditional_1_items);
        mConditionalItmeLayout_2 = (LinearLayout) findViewById(R.id.conditional_2_items);
        mConditionalItmeLayout_3 = (LinearLayout) findViewById(R.id.conditional_3_items);

        mConditionalType_1.setOnClickListener(this);
        mConditionalType_2.setOnClickListener(this);
        mConditionalType_3.setOnClickListener(this);

    }

    /**
     * 进行条件显图初始化
     * {@link #setAdapter(Adapter)}
     */
    private void refreshLayout()
    {
        String[] condtitionTypeNames = mAdapter.getConditionTypeNames();
        if(null == condtitionTypeNames || condtitionTypeNames.length != 3)
            throw new IllegalArgumentException("names array is null or array.length != 3");

        mConditionalType_1.setText(condtitionTypeNames[0]);
        mConditionalType_1.setText(condtitionTypeNames[1]);
        mConditionalType_1.setText(condtitionTypeNames[2]);

        addConditionItem(0,condtitionTypeNames[0],mConditionalItmeLayout_1,mConditionalType_1);
        addConditionItem(1,condtitionTypeNames[1],mConditionalItmeLayout_2,mConditionalType_2);
        addConditionItem(2,condtitionTypeNames[2],mConditionalItmeLayout_3,mConditionalType_3);

    }

    private void addConditionItem(int index, String typeName, LinearLayout conditionalItmeLayout,TextView conditionalTypeLayout)
    {
        conditionalItmeLayout.removeAllViews();

        ConditionItem[] itemNames = mAdapter.getConditionItemNames(index,typeName);

        for(ConditionItem item : itemNames )
        {
            item.setBindTypeView(conditionalTypeLayout);
            conditionalItmeLayout.addView(createItemView(item));
        }
    }

    private View createItemView(ConditionItem item)
    {
        TextView itemTextView = new TextView(getContext());
        itemTextView.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

        itemTextView.setTag(item);
        itemTextView.setText(item.name);
        itemTextView.setGravity(Gravity.CENTER);
        itemTextView.setOnClickListener(this);
        return itemTextView;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.m_conditional_type_1:
                openView(mConditionalItmeLayout_1);
                break;
            case R.id.m_conditional_type_2:
                openView(mConditionalItmeLayout_2);
                break;
            case R.id.m_conditional_type_3:
                openView(mConditionalItmeLayout_3);
                break;
            default:
                selectItemView(v);
                mAdapter.onSelect(mConditionalType_1.getTag(),mConditionalType_2.getTag(),mConditionalType_3.getTag());
                closeAllView();
                break;
        }
    }

    private void selectItemView(View view)
    {
        if(null != view.getTag())
        {
            ConditionItem conditionItem = (ConditionItem) view.getTag();
            conditionItem.bindTypeView.setText(conditionItem.name);
            conditionItem.bindTypeView.setTag(conditionItem.tag);
            mCurrentOpen = null;
        }
    }

    private void openView(LinearLayout conditionalItmeLayout)
    {

        if(null == mCurrentOpen )
        {
            mCurrentOpen = conditionalItmeLayout;
            mCurrentOpen.setVisibility(VISIBLE);
        }
        else if(mCurrentOpen.getId() == conditionalItmeLayout.getId())
        {
            /**
             * 如果该类型是已经显示，则关闭该类型view
             */
            mCurrentOpen.setVisibility(GONE);
            mCurrentOpen = null;
        }
        else
        {
            mCurrentOpen.setVisibility(GONE);
            conditionalItmeLayout.setVisibility(VISIBLE);
            mCurrentOpen = conditionalItmeLayout;
        }
    }
    private void closeAllView()
    {
        mConditionalItmeLayout_1.setVisibility(GONE);
        mConditionalItmeLayout_2.setVisibility(GONE);
        mConditionalItmeLayout_3.setVisibility(GONE);
        mCurrentOpen = null;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        /**
         * 当点击位置是空白地方时，隐藏全部弹出选择view
         */
        boolean isDispatch = super.dispatchTouchEvent(ev);
        if(!isDispatch)
            closeAllView();
        return isDispatch;
    }

    interface Adapter<T>
    {

        /**
         * 条件类型名
         * 目前只支持3条
         * @return 类型名数组
         */
        String[] getConditionTypeNames();

        /**
         * 获取指定类型的所有可选项
         * @param conditionTypeIndex
         * @param conditionTypeName
         * @return
         */
        ConditionItem[] getConditionItemNames(int conditionTypeIndex,String conditionTypeName);

        /**
         * 当前所选的三个条件
         * 要注意，三个参数都均有可能是空，必须要做好判断
         * 某参数为空时，表示用户没有选择该条件
         *
         * @param type1select
         * @param type2select
         * @param type3select
         */
        void onSelect(@Nullable T type1select,@Nullable T type2select,@Nullable T type3select);

    }

    /**
     * 条件类型的子项实体
     * @param <T>
     */
    public static class ConditionItem<T>
    {
        private String name;
        private T tag;
        private TextView bindTypeView;
        public ConditionItem(String name,T tag)
        {
            this.name = name;
            this.tag = tag;
        }

        void setBindTypeView(TextView bindTypeView)
        {
            this.bindTypeView = bindTypeView;
        }
    }

}
