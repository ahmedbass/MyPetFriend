package com.ahmedbass.mypetfriend;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ThreeFourImageView extends ImageView {
    public ThreeFourImageView(Context context) {
        super(context);
    }

    public ThreeFourImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ThreeFourImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ThreeFourImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override //this is the key part
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            int threeForthHeightSpec = MeasureSpec
                    .makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec) * 3/4, MeasureSpec.EXACTLY);
            super.onMeasure(widthMeasureSpec, threeForthHeightSpec);
        } else {
            int fullScreenHeightSpec = MeasureSpec
                    .makeMeasureSpec(Resources.getSystem().getDisplayMetrics().heightPixels, MeasureSpec.EXACTLY);
            super.onMeasure(widthMeasureSpec, fullScreenHeightSpec);
        }
    }
}