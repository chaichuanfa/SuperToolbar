package com.felix.supertoolbar.toolbar;

import com.felix.supertoolbar.util.ScreenUtils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by chaichuanfa on 17/5/26.
 * 基类
 */

public abstract class BaseToolbar extends FrameLayout {

    public BaseToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs, defStyleAttr);
        initToolBar();
    }

    abstract void initAttrs(@NonNull Context context, AttributeSet attrs,
            int defStyleAttr);

    abstract void initToolBar();

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = ScreenUtils
                .getMeasureSize(ScreenUtils.getScreenWidth(getContext()), widthMeasureSpec);
        int height = ScreenUtils
                .getMeasureSize(ScreenUtils.getActionBarHeight(getContext()), heightMeasureSpec);
        setMeasuredDimension(width, height);
    }
}
