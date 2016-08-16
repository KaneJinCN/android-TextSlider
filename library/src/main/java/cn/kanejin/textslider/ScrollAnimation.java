package cn.kanejin.textslider;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by Kane on 8/31/14.
 */
public class ScrollAnimation extends Animation {
    private int mScrollHeight;
    private float mFromTranslationY;

    private View mView;

    public ScrollAnimation(View view, float fromTranslationY, int scrollHeight) {
        mView = view;

        mScrollHeight = scrollHeight;
        mFromTranslationY = fromTranslationY;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {

        float newPosition = mFromTranslationY + (mScrollHeight * interpolatedTime);

        mView.setTranslationY(newPosition);
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }

}
