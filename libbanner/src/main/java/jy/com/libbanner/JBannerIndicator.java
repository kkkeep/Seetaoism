package jy.com.libbanner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;


/*
 * created by taofu on 2019-06-18
 **/
public class JBannerIndicator extends View {

    private static final int MAX_COUNT = 10;

    private int mIndicatorCount; // 总共小圆的个数

    private int mCurrentIndex; // 选中的小圆 index

    private Paint mPaint; // 画小圆的画笔
    private int mSelectedColor; // 选中的颜色
    private int mUnSelectedColor; // 未选中的颜色

    private int mRadius; // 小圆的半径

    private int mSpace; // 两个小圆之间的间距


    private int mWidth; // 指示器空间的宽度

    private int mHeight; // 指示器控件的高度

    private int mMaxRadius; // 最大半径


    private JBanner mBanner;



    public JBannerIndicator(Context context,JBanner banner) {
        super(context);
        mBanner = banner;
        initParams();

    }

    public JBannerIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);


    }

    public JBannerIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    private void initParams(){
        mMaxRadius = mBanner.getMaskMaxHeight() / 4;
        mRadius = mBanner.getIndicatorRadius();
        mSpace = mRadius;
        mSelectedColor  = mBanner.getIndicatorSelectColor();
        mUnSelectedColor = mBanner.getIndicatorUnSelectColor();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);// 去锯齿

    }


    public void setRadius(int radius){
        mRadius = Math.min(radius, mMaxRadius);
        calculateWidth();
    }


    public void setSelectedColor(int color){
        mSelectedColor = color;
        invalidate();
    }

    public void setUnSelectedColor(int color){
        mUnSelectedColor = color;
        invalidate();
    }

    public void setMaxRadius(int maxRadius){
        mMaxRadius = maxRadius;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if(mWidth == 0 || mHeight == 0){
            setMeasuredDimension(MeasureSpec.makeMeasureSpec(1, MeasureSpec.EXACTLY),MeasureSpec.makeMeasureSpec(1, MeasureSpec.EXACTLY));
        }else{
            setMeasuredDimension(MeasureSpec.makeMeasureSpec(mWidth, MeasureSpec.EXACTLY),MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.EXACTLY));
        }
    }



    /**
     * 计算控件的宽度
     */
    private void calculateWidth(){

        if(!isShown()){
            mWidth = 0;
            mHeight = 0;
            return;
        }

        mWidth = ((getIndicatorCount() * 2 * mRadius) + ((getIndicatorCount() -1) * mSpace) + mRadius);
        mHeight = 2 * mRadius;
        requestLayout();

    }



    private int getIndicatorCount(){
        return Math.min(mIndicatorCount, MAX_COUNT);
    }

    public void setIndicatorCount(int indicatorCount) {
        this.mIndicatorCount = indicatorCount;

        calculateWidth();
    }

    public void setCurrentIndex(int mCurrentIndex) {
        this.mCurrentIndex = mCurrentIndex;
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(mWidth == 0 || mHeight == 0){
            return;
        }

        for(int i = 0 ; i < getIndicatorCount(); i++){
            /**
             * 第一参数： cx 表示 圆心的x 坐标，
             * 第二参数： cy 表示圆心Y 的坐标，
             * 第三参数： radius 圆半径
             * 第四参数： paint 画笔
             */
            if(mCurrentIndex == i){
                mPaint.setColor(mSelectedColor);
            }else{
                mPaint.setColor(mUnSelectedColor);
            }
            canvas.drawCircle(((i * 2 * mRadius) + (i * mSpace)  + mRadius),mRadius,mRadius, mPaint);
        }





    }
}
