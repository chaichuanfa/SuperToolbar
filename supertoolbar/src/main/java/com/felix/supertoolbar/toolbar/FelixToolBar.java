package com.felix.supertoolbar.toolbar;

import com.felix.supertoolbar.R;
import com.felix.supertoolbar.util.ScreenUtils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by chaichuanfa on 17/5/26.
 */

public class FelixToolbar extends BaseToolbar {

    private static final int LEFT = -1;

    private static final int CENTER = 0;

    private static final int RIGHT = 1;

    private static final int BUTTON_FONT_DEFAULT_SIZE = 16;

    private static final int BUTTON_PADDING = 12;

    private static final int BOTTOM_DIVIDER_HEIGHT = 1;

    private static final int TITLE_FONT_DEFAULT_SIZE = 18;

    private int text_default_color;

    private int button_padding_offset;

    private int title_color;

    private int title_size;

    private int title_gravity;

    private String title_text = "";

    private ColorStateList right_text_color;

    private String right_text = "";

    private int right_text_size;

    private int right_button_padding_right;

    private ColorStateList left_text_color;

    private String left_text = "";

    private int left_text_size;

    private int left_button_padding_left;

    private int right_button_src;

    private int left_button_src;

    private boolean has_divider;

    private int divider_color;

    private int divider_height;

    private TextView mTvTitle;

    private TextView mLeftButton;

    private TextView mRightButton;

    private View mDivider;

    public FelixToolbar(Context context) {
        this(context, null);
    }

    public FelixToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FelixToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initAttrs(@NonNull Context context, AttributeSet attrs, int defStyleAttr) {
        text_default_color = getResources().getColor(R.color.felix_bar_font_default_color);
        button_padding_offset = ScreenUtils.dip2px(8, context);
        TypedArray a = null;

        try {
            a = context.getTheme()
                    .obtainStyledAttributes(attrs, R.styleable.FelixToolbar, defStyleAttr, 0);
            title_color = a.getColor(R.styleable.FelixToolbar_title_color,
                    text_default_color);
            title_size = (int) a
                    .getDimension(R.styleable.FelixToolbar_title_font_size,
                            ScreenUtils.sp2px(TITLE_FONT_DEFAULT_SIZE, context));
            title_gravity = a.getInt(R.styleable.FelixToolbar_title_gravity, CENTER);
            title_text = a.getString(R.styleable.FelixToolbar_title_text);

            right_text_color = a
                    .getColorStateList(R.styleable.FelixToolbar_right_button_text_color);
            right_text_size = (int) a
                    .getDimension(R.styleable.FelixToolbar_right_button_text_font_size,
                            ScreenUtils.sp2px(BUTTON_FONT_DEFAULT_SIZE, context));
            right_text = a.getString(R.styleable.FelixToolbar_right_button_text);
            right_button_padding_right = a
                    .getDimensionPixelSize(R.styleable.FelixToolbar_right_button_padding_right,
                            ScreenUtils.dip2px(BUTTON_PADDING, context));

            left_text_color = a.getColorStateList(R.styleable.FelixToolbar_left_button_text_color);
            left_text_size = (int) a
                    .getDimension(R.styleable.FelixToolbar_left_button_text_font_size,
                            ScreenUtils.sp2px(BUTTON_FONT_DEFAULT_SIZE, context));
            left_text = a.getString(R.styleable.FelixToolbar_left_button_text);
            left_button_padding_left = a
                    .getDimensionPixelSize(R.styleable.FelixToolbar_left_button_padding_left,
                            ScreenUtils.dip2px(BUTTON_PADDING, context));

            right_button_src = a.getResourceId(R.styleable.FelixToolbar_right_button_src, 0);
            left_button_src = a.getResourceId(R.styleable.FelixToolbar_left_button_src, 0);

            has_divider = a.getBoolean(R.styleable.FelixToolbar_has_divider, false);
            divider_color = a.getColor(R.styleable.FelixToolbar_divider_color,
                    getResources().getColor(R.color.default_divider_color));
            divider_height = a.getDimensionPixelSize(R.styleable.FelixToolbar_divider_height,
                    ScreenUtils.dip2px(BOTTOM_DIVIDER_HEIGHT, context));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (a != null) {
                a.recycle();
            }
        }
    }

    @Override
    protected void initToolBar() {
        LayoutInflater.from(getContext()).inflate(R.layout.felix_toolbar_layout, this);
        mTvTitle = findViewById(R.id.mTvTitle);
        mTvTitle.setText(title_text);
        mTvTitle.setTextColor(title_color);
        mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, title_size);
        switch (title_gravity) {
            case LEFT:
                mTvTitle.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                break;
            case CENTER:
                mTvTitle.setGravity(Gravity.CENTER);
                break;
            case RIGHT:
                mTvTitle.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
                break;
        }

        mLeftButton = findViewById(R.id.mLeftButton);
        if (left_button_src != 0) {
            mLeftButton.setCompoundDrawablesWithIntrinsicBounds(left_button_src, 0, 0, 0);
        }
        if (!TextUtils.isEmpty(left_text)) {
            mLeftButton.setText(left_text);
            if (left_text_color != null) {
                mLeftButton.setTextColor(left_text_color);
            } else {
                mLeftButton.setTextColor(text_default_color);
            }

            mLeftButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, left_text_size);
        }
        mLeftButton.setPadding(left_button_padding_left, 0, button_padding_offset, 0);

        if (left_button_src == 0 && TextUtils.isEmpty(left_text)) {
            mLeftButton.setVisibility(GONE);
        }

        mRightButton = findViewById(R.id.mRightButton);
        if (right_button_src != 0) {
            mRightButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, right_button_src, 0);
        }
        if (!TextUtils.isEmpty(right_text)) {
            if (right_text_color != null) {
                mRightButton.setTextColor(right_text_color);
            } else {
                mRightButton.setTextColor(text_default_color);
            }
            mRightButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, right_text_size);
            setRightText(right_text);
        }
        mRightButton.setPadding(button_padding_offset, 0, right_button_padding_right, 0);

        if (right_button_src == 0 && TextUtils.isEmpty(right_text)) {
            mRightButton.setVisibility(GONE);
        }

        mDivider = findViewById(R.id.mDivider);
        if (has_divider) {
            mDivider.setVisibility(VISIBLE);
            mDivider.setBackgroundColor(divider_color);
            ViewGroup.LayoutParams params = mDivider.getLayoutParams();
            params.height = divider_height;
            mDivider.setLayoutParams(params);
        }

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDetachedFromWindow() {
        if (mLeftButton != null) {
            mLeftButton.setOnClickListener(null);
        }
        if (mRightButton != null) {
            mRightButton.setOnClickListener(null);
        }
        super.onDetachedFromWindow();
    }

    private void setRightText(String text) {
        right_text = text;
        mRightButton.setText(text);
        mRightButton.post(new Runnable() {
            @Override
            public void run() {
                int padding = mRightButton.getWidth() - ScreenUtils.dip2px(60, getContext());
                if (padding > 0) {
                    mTvTitle.setPadding(padding, 0, padding, 0);
                }
            }
        });
    }

    public void setLeftButtonVisible(int visible) {
        if (mLeftButton != null) {
            mLeftButton.setVisibility(visible);
        }
    }

    public void setRightButtonVisible(int visible) {
        if (mRightButton != null) {
            mRightButton.setVisibility(visible);
        }
    }

    public void setLeftButtonEnable(boolean enable) {
        if (mLeftButton != null) {
            mLeftButton.setEnabled(enable);
        }
    }

    public void setRightButtonEnable(boolean enable) {
        if (mRightButton != null) {
            mRightButton.setEnabled(enable);
        }
    }

    public void setLeftButtonClickListener(OnClickListener listener) {
        if (mLeftButton != null) {
            mLeftButton.setOnClickListener(listener);
        }
    }

    public void setRightButtonClickListener(OnClickListener listener) {
        if (mRightButton != null) {
            mRightButton.setOnClickListener(listener);
        }
    }

    public void setTitleClickListener(OnClickListener listener) {
        if (mTvTitle != null) {
            mTvTitle.setOnClickListener(listener);
        }
    }

    public void setToolBarTitle(@NonNull String title) {
        if (mTvTitle != null) {
            title_text = title;
            mTvTitle.setText(title);
        }
    }

    public void setToolBarTitle(@StringRes int res) {
        if (mTvTitle != null) {
            title_text = getResources().getString(res);
            mTvTitle.setText(res);
        }
    }

    public void setTootBarTitleColor(@ColorInt int color) {
        if (mTvTitle != null) {
            title_color = color;
            mTvTitle.setTextColor(color);
        }
    }

    public void setRightButtonText(@StringRes int res) {
        if (mRightButton != null) {
            setRightText(getResources().getString(res));
        }
    }

    public void setRightButtonText(@NonNull String text) {
        if (mRightButton != null) {
            setRightText(text);
        }
    }

    public void setRightButtonTextColor(ColorStateList color) {
        if (mRightButton != null) {
            right_text_color = color;
            mRightButton.setTextColor(color);
        }
    }

    public void setRightButtonResource(@DrawableRes int resource) {
        if (mRightButton != null) {
            right_button_src = resource;
            mRightButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, resource, 0);
        }
    }

    public void setLeftButtonText(@StringRes int res) {
        if (mLeftButton != null) {
            left_text = getResources().getString(res);
            mLeftButton.setText(res);
        }
    }

    public void setLeftButtonText(@NonNull String text) {
        if (mLeftButton != null) {
            left_text = text;
            mLeftButton.setText(text);
        }
    }

    public void setLeftButtonTextColor(ColorStateList color) {
        if (mLeftButton != null) {
            left_text_color = color;
            mLeftButton.setTextColor(color);
        }
    }

    public void setLeftButtonResource(@DrawableRes int resource) {
        if (mLeftButton != null) {
            left_button_src = resource;
            mLeftButton.setCompoundDrawablesWithIntrinsicBounds(resource, 0, 0, 0);
        }
    }

    public void setDividerHeight() {
        ViewGroup.LayoutParams params = mDivider.getLayoutParams();
        params.height = divider_height;
        mDivider.setLayoutParams(params);
    }

    public void setDividerColor(int color) {
        mDivider.setBackgroundColor(color);
    }

    public void setDividerVisibility(int visibility) {
        mDivider.setVisibility(visibility);
    }
}
