package co.mobiwise.watchfacewater;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Calendar;

/**
 * Created by mertsimsek on 14/06/15.
 */
public class WaterView extends View{

    private Paint mBackgroundAmbientPaint;
    private Paint mBackgroundPaintFilled;
    private Paint mBackgroundPaintNotFilled;
    private Paint mPaintHourText;
    private Paint mPaintMinuteText;

    private int mWidth;
    private int mHeight;
    private float mCenterX;
    private float mCenterY;

    private Typeface mTypefaceRobotoThin;
    private Typeface mTypefaceRobotoLight;

    private String mHour;
    private String mMinute;

    private Calendar mCalendar;

    private Paint paint = null;

    public WaterView(Context context) {
        super(context);
        init();
    }

    public WaterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WaterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WaterView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){

        mTypefaceRobotoThin = Typeface.createFromAsset(getContext().getAssets(), "robotothin.ttf");
        mTypefaceRobotoLight = Typeface.createFromAsset(getContext().getAssets(), "robotolight.ttf");

        mBackgroundPaintFilled = new Paint();
        mBackgroundPaintFilled.setColor(getResources().getColor(R.color.color_bg));

        mBackgroundPaintNotFilled = new Paint();
        mBackgroundPaintNotFilled.setColor(getResources().getColor(R.color.color_gray));

        mBackgroundAmbientPaint = new Paint();
        mBackgroundAmbientPaint.setColor(Color.BLACK);
        mBackgroundAmbientPaint.setAntiAlias(true);

        mPaintHourText = new Paint();
        mPaintHourText.setColor(Color.WHITE);
        mPaintHourText.setAntiAlias(true);
        mPaintHourText.setTextSize(400);
        mPaintHourText.setTypeface(mTypefaceRobotoThin);

        mPaintMinuteText = new Paint();
        mPaintMinuteText.setColor(Color.WHITE);
        mPaintMinuteText.setAntiAlias(true);
        mPaintMinuteText.setTextSize(200);
        mPaintHourText.setTypeface(mTypefaceRobotoLight);

    }

    public void updateTime(){
        mCalendar = Calendar.getInstance();
        mHour = String.valueOf(mCalendar.get(Calendar.HOUR));
        mMinute = String.valueOf(mCalendar.get(Calendar.MINUTE));

        if(mMinute.length()<2)
            mMinute = "0" + mMinute;
        if(mHour.length()<2)
            mHour = "0" + mHour;

        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        mCenterX = mWidth / 2f;
        mCenterY = mHeight / 2f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * Background color percentage depends on time
         */
        //draw background multiple color. Calculation formula is  ((mTime/60f) * mHeight).
        canvas.drawRect(0, mHeight - (mCalendar.get(Calendar.MINUTE) / 60f) * mHeight, canvas.getWidth(), canvas.getHeight(), mBackgroundPaintFilled);
        canvas.drawRect(0, 0, canvas.getWidth(), mHeight - (mCalendar.get(Calendar.MINUTE) / 60f) * mHeight, mBackgroundPaintNotFilled);

        /**
         * Time positioning
         */
        //Get text height and width to draw hour text to center.
        Rect hourBound = new Rect();
        mPaintHourText.getTextBounds(mHour, 0, mHour.length(), hourBound);
        canvas.drawText(mHour, mCenterX - (hourBound.width() / 2f), mCenterY + (hourBound.height() / 2f), mPaintHourText);

        //Get text height and width to draw minute text to center.
        Rect minuteBound = new Rect();
        mPaintMinuteText.getTextBounds(mMinute, 0, mMinute.length(), minuteBound);
        canvas.drawText(mMinute, mCenterX + (mCenterX / 2f), mCenterY + (minuteBound.height() / 2f), mPaintMinuteText);

        canvas.save();
        canvas.restore();

    }
}
