package cn.kanejin.textslider;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Random;

import cn.kanejin.textslider.log.ViewLogUtil;

/**
 * Created by Kane on 8/12/16.
 */
public class TextSlider extends RelativeLayout {
    private boolean isRunning = false;

    private int mTextColor;
    private int mTextSize;

    private int mDuration;
    private int mDelay;
    private boolean mLoop;
    private boolean mAutoPlay;

    private int mRows;
    private int mRowHeight;
    private int mStep;

    private TextSliderAdapter mAdapter;
    private TextSliderListener mListener;

    private int mCurrentAdIndex = 0;

    public TextSlider(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.TextSlider,
                0, 0);

        try {

            mDuration = a.getInteger(R.styleable.TextSlider_duration, 1000);
            mDelay = a.getInteger(R.styleable.TextSlider_delay, 5000);
            mLoop = a.getBoolean(R.styleable.TextSlider_loop, true);
            mAutoPlay = a.getBoolean(R.styleable.TextSlider_autoPlay, true);

            mRows = a.getInteger(R.styleable.TextSlider_rows, 1);
            mRowHeight = a.getDimensionPixelSize(R.styleable.TextSlider_rowHeight, dip2px(getContext(), 24.0f));
            mStep = a.getInteger(R.styleable.TextSlider_step, 1);
            mStep = Math.min(mStep, mRows);

            mTextColor = a.getColor(R.styleable.TextSlider_textColor, getContext().getResources().getColor(android.R.color.black));
            mTextSize = a.getDimensionPixelSize(R.styleable.TextSlider_textSize, sp2px(getContext(), 16.0f));

        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(getMeasuredWidth(), mRowHeight * mRows);
    }

    public TextSlider(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    Handler mTimerHandler = new Handler();
    Runnable mTimerRunnable = new Runnable() {

        @Override
        public void run() {
            scrollToNext();

            mTimerHandler.postDelayed(this, mDelay);
        }
    };

    public void play() {

        if (!isRunning) {
            mTimerHandler.postDelayed(mTimerRunnable, mDelay);
            isRunning = true;
        }
    }

    public void pause() {
        if (isRunning) {
            mTimerHandler.removeCallbacks(mTimerRunnable);
            isRunning = false;
        }
    }

    public void setListener(TextSliderListener mListener) {
        this.mListener = mListener;
    }


    public TextSliderAdapter getAdapter() {
        return mAdapter;
    }

    private DataSetObserver mDataSetObserver;

    public void setAdapter(TextSliderAdapter adapter) {
        if (mAdapter != null && mDataSetObserver != null) {
            mAdapter.unregisterDataSetObserver(mDataSetObserver);
        }

        this.mAdapter = adapter;

        if (mAdapter != null) {

            mDataSetObserver = new DataSetObserver() {
                @Override
                public void onChanged() {
                    rebuildTextSlider();
                }
            };
            mAdapter.registerDataSetObserver(mDataSetObserver);
        }

        rebuildTextSlider();

        if (mAutoPlay)
            play();
    }

    private void rebuildTextSlider() {
        boolean isRunningState = isRunning;
        pause();

        removeAllViewsInLayout();

        for (int i = 0; i < Math.min(mAdapter.getCount(), mRows); i++) {

            final TextItem text = mAdapter.getItem(i);

            appendTextView(i, i, text);
        }

        requestLayout();
        invalidate();

        if (isRunningState)
            play();
    }


    private void appendTextView(int position, final int index, final TextItem text) {

        TextView txtView = new TextView(getContext());

        txtView.setText(text.getText());
        txtView.setClickable(true);
        txtView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        txtView.setTextColor(mTextColor);
        txtView.setGravity(Gravity.CENTER_VERTICAL);

        txtView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onTextClick(TextSlider.this, index, text);
                }
            }
        });

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mRowHeight);
        txtView.setTranslationY(position * mRowHeight);

        addView(txtView, lp);
    }

    public void scrollToNext() {
        final int nextIndex = offsetIndex(mStep);

        if (nextIndex < 0) {
            pause();
            return ;
        }

        // 当只有一个时, 不滚动
        if (mCurrentAdIndex == nextIndex) {
            return ;
        }

        for (int i = 0; i < mStep; i++) {
            int n = offsetIndex(i + mRows);

            appendTextView(mRows + i, n, mAdapter.getItem(n));
        }

        mCurrentAdIndex = nextIndex;

        for (int i = 0; i < getChildCount(); i++) {
            final int vi = i;
            final View v = getChildAt(i);

            ScrollAnimation animation = new ScrollAnimation(v, i * mRowHeight, -mRowHeight*mStep);

            animation.setDuration(mDuration);
            animation.setFillAfter(true);

            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (vi < mStep) {
                        removeView(v);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });

            getChildAt(i).startAnimation(animation);
        }


        /*
        AnimationSet animSet = new AnimationSet(true);
        animSet.addAnimation(new TranslateAnimation(0f, 0f, 0f, -mRowHeight * mStep));
        //animSet.addAnimation(new ScrollAnimation(mTextList, -mRowHeight * mStep));
        animSet.setDuration(mDuration);
        animSet.setFillAfter(false);

        animSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mCurrentAdIndex = nextIndex;

                for (int i = 0; i < mStep; i++) {
                    mTextList.getChildAt(i).setVisibility(View.GONE);
                }

                System.out.println("++++++++++++++++++++++++");
                System.out.println(mTextList.getHeight());
                System.out.println(ViewLogUtil.debugView(mTextList, 0));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mTextList.startAnimation(animSet);
        */
    }

    private int offsetIndex(int offset) {
        int nextIndex = mCurrentAdIndex + offset;

        if (nextIndex > mAdapter.getCount() - 1)
            return mLoop ? nextIndex % mAdapter.getCount() : -1;

        return nextIndex;
    }

    private static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
