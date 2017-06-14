package testrootapp.someday.cn.testaudioplay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 带进度显示的音频播放按钮
 * Created by shaojunjie on 17-6-14.
 */

public class PlayMusicButton extends View
{


    public PlayMusicButton(Context context) {super(context);}
    public PlayMusicButton(Context context, @Nullable AttributeSet attrs) {super(context, attrs);}
    public PlayMusicButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {super(context, attrs, defStyleAttr);}

    private int centerPointX = 0;
    private int centerPointY = 0;

    private int backgroundRadius = 0;
    private int progressRadius = 0;
    private int forgroundRadius = 0;
    private RectF iconRectF;
    private Bitmap playBitmap;
    private Bitmap stopBitmap;
    private Bitmap currentBitmap;

    private Paint backgroundPaint;
    private Paint progressPaint;
    private Paint forgroundPaint;

    private RectF arcOval;

    private int max = 0;
    private int progress = 0;

    public void setMax(int max)
    {
        this.max = max;
    }
    public void setProgress(int progress)
    {
        this.progress = (int) (new Double(progress) / new Double(max) * 360);
        invalidate();
    }

    private void initialize()
    {
        if(centerPointX != 0)
            return ;
        centerPointX = getWidth()  / 2;
        centerPointY = getHeight() / 2;
        int baseRadius = centerPointX >= centerPointY ? centerPointY : centerPointX;
        forgroundRadius = baseRadius - dip2px(2);
        backgroundRadius = baseRadius - dip2px(0.5f);
        progressRadius = baseRadius;

        backgroundPaint = new Paint();
        backgroundPaint.setColor(0xffdddddd);
        backgroundPaint.setAntiAlias(true);

        progressPaint = new Paint();
        progressPaint.setColor(0xff29cc96);
        progressPaint.setAntiAlias(true);
        arcOval = new RectF(centerPointX-baseRadius, centerPointY-baseRadius, centerPointX+baseRadius, centerPointY+baseRadius);
        playBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bofang_btn);
        stopBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.stop_btn);
        currentBitmap = playBitmap;
        iconRectF =new RectF(centerPointX-dip2px(6), centerPointY-dip2px(6), centerPointX+dip2px(6), centerPointY+dip2px(6)); ;

        forgroundPaint = new Paint();
        forgroundPaint.setColor(0xfff5f5f5);
        forgroundPaint.setAntiAlias(true);
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        initialize();
        drawBackground(canvas);
        drawProgress(canvas);
        drawForground(canvas);
        drawIcon(canvas);
    }


    private void drawBackground(Canvas canvas)
    {
        canvas.drawCircle(centerPointX,centerPointX,backgroundRadius,backgroundPaint);
    }
    private void drawForground(Canvas canvas)
    {
        canvas.drawCircle(centerPointX,centerPointX,forgroundRadius,forgroundPaint);
    }
    private void drawProgress(Canvas canvas)
    {
        if( this.progress == 0 )
            return;
        canvas.drawArc(arcOval, -90, this.progress, true, progressPaint);
    }

    private void drawIcon(Canvas canvas)
    {
        canvas.drawBitmap(currentBitmap,null,iconRectF,null);
    }

    private boolean isPlaying;
    public void play()
    {
        isPlaying = true;
        currentBitmap = stopBitmap;
        invalidate();
    }
    public void stop()
    {
        isPlaying = false;
        currentBitmap = playBitmap;
        invalidate();
    }

    public int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
