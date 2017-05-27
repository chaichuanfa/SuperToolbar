package com.felix.supertoolbar.util;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;

public final class ScreenUtils {

    public static final int DEFAULT_STATUS_BAR_HEIGHT_DP = 25;

    private ScreenUtils() {
        //no instance
    }

    public static int getScreenOrientation(Context mContext) {
        return mContext.getResources().getConfiguration().orientation;
    }

    //CHECKSTYLE:OFF
    public static int dip2px(int dipValue, Context mContext) {
        float reSize = mContext.getResources().getDisplayMetrics().density;
        return (int) ((dipValue * reSize) + 0.5);
    }

    public static int px2dip(int pxValue, Context mContext) {
        float reSize = mContext.getResources().getDisplayMetrics().density;
        return (int) ((pxValue / reSize) + 0.5);
    }

    public static float sp2px(int spValue, Context mContext) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue,
                mContext.getResources().getDisplayMetrics());
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getActionBarHeight(Context context) {
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data,
                    context.getResources().getDisplayMetrics());
        }
        return 0;
    }

    public static int getStatusBarHeight(Context context) {
        int result = dip2px(DEFAULT_STATUS_BAR_HEIGHT_DP, context);
        int resourceId = context.getResources()
                .getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int getMeasureSize(int defaultSize, int measureSpec) {
        int mySize = defaultSize;
        int mode = View.MeasureSpec.getMode(measureSpec);
        int size = View.MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case View.MeasureSpec.UNSPECIFIED: {//如果没有指定大小，就设置为默认大小
                mySize = defaultSize;
                break;
            }
            case View.MeasureSpec.AT_MOST: {//如果测量模式是最大取值为size
                //我们将大小取最大值,你也可以取其他值
                mySize = defaultSize;
                break;
            }
            case View.MeasureSpec.EXACTLY: {//如果是固定的大小，那就不要去改变它
                mySize = size;
                break;
            }
        }
        return mySize;
    }
}
