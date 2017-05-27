package com.felix.supertoolbar.toolbar;

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
}
