package jy.com.libbanner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.nfc.Tag;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/*
 * created by Cherry on 2019-06-19
 **/
public class JBanner extends ConstraintLayout {

    private static final String TAG = "JBanner";

    private static final float MASK_HEIGHT_FACTOR = 2.0f;

    private ViewPager mViewPager;

    private ViewPager.OnPageChangeListener mOnPageChangeListener;

    private TextView mTvTitle;

    private JBannerIndicator mIndicator;

    private IJBannerAdapter mAdapter;

    private OnBannerItemClickListener mBannerItemClickListener;
    // 用户显示banner 图片的Banner 对象list，也是可以使 图片url 集合
    private List<?> mDatas;

    // 显示title 的集合，可以为空
    private List<String> mTitles;

    private int mId = 100;


    // 自动切换时，一页动画执行时间
    private int nDuration = 3000;

    // 自动轮播间隔时间，默认为一秒
    private int mInterval = 5000;


    // title 字体大小，单位px
    private int mTitleSize;
    private int mTitleColor;

    // title 左边边距
    private int mTitleLeftMargin;
    private int mTitleRightMargin;

    // 指示器左边边距
    private int mIndicatorLeftMargin;

    // 指示器右边的边距
    private int mIndicatorRightMargin;


    // title 顶部边距
    private int mTitleTopMargin;
    // title 底部边距
    private int mTitleBottomMargin;

    // 指示器半径
    private int mIndicatorRadius;

    // 指示器 选中颜色
    private int mIndicatorSelectColor;
    // 指示器未选中颜色
    private int mIndicatorUnSelectColor;

    private int mMaxTitleSize;

    // 是否开启自动轮播
    private boolean mIsLoop;

    //是否为手动滑动
    private boolean mIsManualScroll;

    //是否显示指示器
    private boolean mIsShowIndicator;


    public JBanner(Context context) {
        this(context, null);
    }

    public JBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }


    private void init(AttributeSet attrs, int defStyleAttr) {


        mMaxTitleSize = JBannerUtils.dp2px(getContext(), 20);

        mTitleColor = Color.WHITE;

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.JBanner);

        //默认 16dp
        mTitleSize = (int) typedArray.getDimension(R.styleable.JBanner_titleSize, JBannerUtils.dp2px(getContext(), 16));
        // 默认白色
        mTitleColor = typedArray.getColor(R.styleable.JBanner_titleColor, Color.WHITE);
        // 默认 5dp
        mIndicatorRadius = (int) typedArray.getDimension(R.styleable.JBanner_indicatorRadius, JBannerUtils.dp2px(getContext(), 3));

        // 默认 白色
        mIndicatorSelectColor = typedArray.getColor(R.styleable.JBanner_indicatorSelectedColor, Color.WHITE);

        // 默认 灰色
        mIndicatorUnSelectColor = typedArray.getColor(R.styleable.JBanner_indicatorNormalColor, Color.GRAY);


        mTitleLeftMargin = (int) typedArray.getDimension(R.styleable.JBanner_titleLeftMargin, JBannerUtils.dp2px(getContext(), 16));
        mTitleRightMargin = (int) typedArray.getDimension(R.styleable.JBanner_titleRightMargin, JBannerUtils.dp2px(getContext(), 16));
        mTitleTopMargin = (int) typedArray.getDimension(R.styleable.JBanner_titleTopMargin, JBannerUtils.dp2px(getContext(), 16));
        mTitleBottomMargin = (int) typedArray.getDimension(R.styleable.JBanner_titleBottomMargin, JBannerUtils.dp2px(getContext(), 16));

        mIndicatorRightMargin = (int) typedArray.getDimension(R.styleable.JBanner_indicatorRightMargin, JBannerUtils.dp2px(getContext(), 16));
        mIndicatorLeftMargin = (int) typedArray.getDimension(R.styleable.JBanner_indicatorRightMargin, JBannerUtils.dp2px(getContext(), 16));

        mIsShowIndicator = typedArray.getBoolean(R.styleable.JBanner_indicatorShow, true);

        typedArray.recycle();

        initView();
    }


    public void setAdapter(IJBannerAdapter adapter) {
        mAdapter = adapter;
        mViewPager.setAdapter(new InnerAdapter());

        if (mDatas != null && mDatas.size() > 1) {
            int i = Integer.MAX_VALUE / 2; // 取最大值的中间值

            int j = i % mDatas.size(); // 用中间值取余数
            if (j != 0) { // 如果余数不等于0
                i = (mDatas.size() - j) + i; // 用size - 余数，求出还是多少才能除size 取余等于0 ，然后再加到中间值，目的是为了让中间值除size 取余等于0
            }
            mViewPager.setCurrentItem(i); // 设置让viewpager 显示中间值，并且这个中间值除以 size 余数 为0
        }


    }


    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);

        if (visibility == VISIBLE  ) {
            // 如果自动轮播
            if (mIsLoop) {
                postDelayed(mLooper, mInterval);
            }
        } else {
            if (mIsLoop) {
                getHandler().removeCallbacks(mLooper);
            }
        }
    }



    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mIsLoop) {
            getHandler().removeCallbacks(mLooper);
        }
    }

    private Runnable mLooper = new Runnable() {
        @Override
        public void run() {
            int cr = mViewPager.getCurrentItem();
            mViewPager.setCurrentItem(++cr); // 每次切换一页page

            if (getHandler() != null) {
                getHandler().postDelayed(this, mInterval); //继续下一次轮播
            }

        }
    };

    public void setData(List<?> data, List<String> titles) {

        if (data != null && titles != null) {

            //在两个list 都有值的情况下，如果他们的size 不相等，那么就抛异常。因为图片个数和title 个数对不上。
            if (data.size() != titles.size()) {
                throw new IllegalArgumentException(" data size not equals title size");
            }
        }

        //设置指示器的个数
        mIndicator.setIndicatorCount(data == null ? 0 : data.size());

        mDatas = data;
        mTitles = titles;
    }

    public void setLoop(boolean loop) {
        mIsLoop = loop;
    }

    public void setTitleSize(int unit, int size) {
        int maxSize = JBannerUtils.dp2px(getContext(), 20);
        switch (unit) {
            case TypedValue.COMPLEX_UNIT_PX: {
                mTitleSize = Math.min(size, maxSize);
                break;
            }
            case TypedValue.COMPLEX_UNIT_DIP: {
                mTitleSize = Math.min(JBannerUtils.dp2px(getContext(), size), maxSize);
                break;
            }

            case TypedValue.COMPLEX_UNIT_SP: {
                mTitleSize = Math.min(JBannerUtils.sp2px(getContext(), size), maxSize);
                break;
            }
            default: {
                mTitleSize = Math.min(size, maxSize);
                break;
            }
        }

        mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleSize);

    }

    public void settTitleColor(int color) {
        mTitleColor = color;
        mTvTitle.setTextColor(mTitleColor);
    }

    public void setIndicatorRadius(int unit, int radius) {
        if (unit == TypedValue.COMPLEX_UNIT_DIP) {
            radius = JBannerUtils.dp2px(getContext(), radius);
        }
        mIndicator.setRadius(radius);
    }

    public void addOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        mOnPageChangeListener = onPageChangeListener;
    }

    public void setIndicatorSelectColor(int color) {
        mIndicator.setSelectedColor(color);
    }


    public void setIndicatorUnSelectColor(int color) {
        mIndicator.setUnSelectedColor(color);
    }

    public void setOnBannerItemClickListener(OnBannerItemClickListener listener) {
        mBannerItemClickListener = listener;
    }

    int getIndicatorRadius() {
        return mIndicatorRadius;
    }

    int getIndicatorSelectColor() {
        return mIndicatorSelectColor;
    }

    int getIndicatorUnSelectColor() {
        return mIndicatorUnSelectColor;
    }

    int getMaskMaxHeight() {
        return (int) (mMaxTitleSize * MASK_HEIGHT_FACTOR);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "按下");
                if (mIsLoop) {
                    getHandler().removeCallbacks(mLooper);
                }

                break;

            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "移动");
                // 只有有滑动操作，就认为是手动滑动
                mIsManualScroll = true;

                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "抬起");
                if (mIsLoop) {
                    getHandler().postDelayed(mLooper, mInterval);
                }

                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        mViewPager = new ViewPager(getContext());
        mViewPager.setId(mId++);

        setViewPagerScroller();

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (mOnPageChangeListener != null) {
                    mOnPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                // 页面被选中时更新title，更新指示器
                mTvTitle.setText(getTitleByPosition(position % mDatas.size()));
                mIndicator.setCurrentIndex(position % mDatas.size());
                if (mOnPageChangeListener != null) {
                    mOnPageChangeListener.onPageSelected(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    mIsManualScroll = false;
                }
                if (mOnPageChangeListener != null) {
                    mOnPageChangeListener.onPageScrollStateChanged(state);
                }
            }
        });


        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this);


        /**
         * 添加viewpager  并设置约束条件，
         *在约束布局中，我们是通过一条带方向箭头的线把两个控件进行连接来实现约束，线从一个控件的一边指向另一个控件的一边。 比如有两个Button ,A 和 B
         * 需要实现 A 在 B 的右边。那么链接的时候，就是从 A 的左边 拖动一条线链接到 B 的右边。线是从A 开始 到B 结束，所以  在connect（连接） 方法中。
         * 第一个参数 startId 表示 A 的ID, 第二个参数 startSidle 表示A 的左边，第三个参数 endId 表示B 的Id, 第四个参数 endSidle 表示B 的右边，
         * 如果需要设置margin 可以 使用 带5个参数的 connect 方法，最后一个就是 margin。
         */
        constraintSet.connect(mViewPager.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        constraintSet.connect(mViewPager.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.connect(mViewPager.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        constraintSet.connect(mViewPager.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);

        //对于约束布局，设置控件的宽高一定要使用constrainWidth 和 constrainHeight 如果使用LayoutParams 将会没有效果
        constraintSet.constrainWidth(mViewPager.getId(), ConstraintSet.MATCH_CONSTRAINT);
        constraintSet.constrainHeight(mViewPager.getId(), ConstraintSet.MATCH_CONSTRAINT);
        addView(mViewPager);





        mTvTitle = new TextView(getContext());
        mTvTitle.setId(mId++);
        mTvTitle.setMaxLines(1);
        mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleSize);
        mTvTitle.setEllipsize(TextUtils.TruncateAt.END);
        mTvTitle.setTextColor(mTitleColor);


        mTvTitle.measure(0, 0);
        Paint.FontMetrics fm = mTvTitle.getPaint().getFontMetrics();

        int titleHeight = mTvTitle.getMeasuredHeight();

        int titleContentHeight = (int) Math.abs(fm.ascent);

        int paddingTop = (int) (titleHeight - (fm.descent - fm.ascent));
        int paddingBottom = (int) (titleHeight - (-fm.ascent) - paddingTop);


        // title 的 半透明条
        ImageView imageView = new ImageView(getContext());
        imageView.setId(mId++);
        imageView.setBackgroundColor(Color.parseColor("#40000000"));
        constraintSet.connect(imageView.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        constraintSet.connect(imageView.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        constraintSet.connect(imageView.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        constraintSet.constrainWidth(imageView.getId(), ConstraintSet.MATCH_CONSTRAINT);
        constraintSet.constrainHeight(imageView.getId(), mTitleBottomMargin + mTitleTopMargin + titleContentHeight);
        addView(imageView);


        int indicatorId = mId++;

        constraintSet.connect(mTvTitle.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, mTitleLeftMargin);
        constraintSet.connect(mTvTitle.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, mTitleBottomMargin - paddingBottom);
        constraintSet.connect(mTvTitle.getId(), ConstraintSet.RIGHT, indicatorId, ConstraintSet.LEFT, mTitleRightMargin);
        constraintSet.constrainWidth(mTvTitle.getId(), ConstraintSet.MATCH_CONSTRAINT);
        constraintSet.constrainHeight(mTvTitle.getId(), ConstraintSet.WRAP_CONTENT);
        this.addView(mTvTitle);


        mIndicator = new JBannerIndicator(getContext(), this);
        mIndicator.setId(indicatorId);


        constraintSet.connect(mIndicator.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, mIndicatorRightMargin);
        constraintSet.connect(mIndicator.getId(), ConstraintSet.BOTTOM, mTvTitle.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(mIndicator.getId(), ConstraintSet.TOP, mTvTitle.getId(), ConstraintSet.TOP);
        constraintSet.constrainWidth(mIndicator.getId(), ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainHeight(mIndicator.getId(), ConstraintSet.WRAP_CONTENT);



        addView(mIndicator);

        if (mIsShowIndicator) {
            constraintSet.setVisibility(mIndicator.getId(),ConstraintSet.VISIBLE);
            mIndicator.setVisibility(VISIBLE);
        } else {
            constraintSet.setVisibility(mIndicator.getId(),ConstraintSet.GONE);
            mIndicator.setVisibility(GONE);
        }


        // 这样一定不要忘记了。约束头添加好后，必须把这些约束应用上
        constraintSet.applyTo(this);

    }

    /**
     * 通过position 获取title
     *
     * @param position viewpager 中的当前页的position
     * @return 如果有title 返回，没有title 返回空串
     */
    private String getTitleByPosition(int position) {
        if (mTitles == null || mTitles.size() == 0) {
            return "";
        }

        return mTitles.get(position);
    }

    /**
     * 通过反射修改 viewpager 自动切换下一页的时间，因为默认的时间只有200ms,太短
     */
    private void setViewPagerScroller() {

        try {
            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");

            scrollerField.setAccessible(true);

            Field interpolator = ViewPager.class.getDeclaredField("sInterpolator");


            interpolator.setAccessible(true);

            Scroller scroller = new Scroller(getContext(), (Interpolator) interpolator.get(null)) {
                @Override
                public void startScroll(int startX, int startY, int dx, int dy, int duration) {
                    int newDuration;
                    // 如果这次是手滑，那么剩下的那一部分滑动的时间我们不修改。就用默认的。
                    if (mIsManualScroll) {
                        newDuration = duration;
                    } else {
                        // 自动切换时，修改默认切换的时间
                        newDuration = nDuration;
                    }
                    super.startScroll(startX, startY, dx, dy, newDuration);    // 这里是关键，将duration变长或变短
                }
            };
            scrollerField.set(mViewPager, scroller);
        } catch (NoSuchFieldException e) {
            // Do nothing.
        } catch (IllegalAccessException e) {
            // Do nothing.
        }
    }

    /**
     * 内部正在的adapter ,这个adapter 就是设置给 viewpager 的
     */
    private class InnerAdapter extends PagerAdapter {

        private ImageView mCacheView;

        @Override
        public int getCount() {
            return mDatas == null ? 0 : Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }


        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {


            ImageView imageView = mCacheView;


            if (imageView == null) {
                imageView = new ImageView(container.getContext());
                imageView.setLayoutParams(new ViewPager.LayoutParams());
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mBannerItemClickListener != null) {
                            int position = mViewPager.getCurrentItem() % mDatas.size();
                            mBannerItemClickListener.onClick(mDatas.get(position),position);
                        }
                    }
                });
            } else {
                mCacheView = null;
            }


            // 相当于代理模式，让开发者设置的adapter 来处理对banner 的数据填充
            if (mAdapter != null) {
                // banner 里面不对图片加载做处理，所以通过回调让使用banner的开发者自己去加载图片，此处指需要把 imageView 和 数据等传给开发者就行
                mAdapter.fillBannerItemData(JBanner.this, imageView, mDatas.get(position % mDatas.size()), position % mDatas.size());
            }


            container.addView(imageView);

            return imageView;

        }


        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            // Log.d(TAG, "Position = " + position % mDatas.size() + " remove ImageView  and add to cache" + object.hashCode());
            //  mCache.add((ImageView) object);
            mCacheView = (ImageView) object;
            container.removeView((View) object);
        }
    }

    public interface OnBannerItemClickListener<M> {
        void onClick(M m,int position);
    }
}
