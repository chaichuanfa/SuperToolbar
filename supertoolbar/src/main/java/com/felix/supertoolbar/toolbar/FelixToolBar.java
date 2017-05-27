package com.felix.supertoolbar.toolbar;

import com.daasuu.bl.BubbleLayout;
import com.daasuu.bl.BubblePopupHelper;
import com.felix.supertoolbar.R;
import com.felix.supertoolbar.adapter.MenuAdapter;
import com.felix.supertoolbar.adapter.OnMenuItemClickCallback;
import com.felix.supertoolbar.config.ContextMenu;
import com.felix.supertoolbar.util.ScreenUtils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.PopupWindow;
import android.widget.TextView;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import fr.castorflex.android.smoothprogressbar.SmoothProgressDrawable;

/**
 * Created by chaichuanfa on 17/5/26.
 */

public class FelixToolBar extends BaseToolbar {

    private static final int LEFT = -1;

    private static final int CENTER = 0;

    private static final int RIGHT = 1;

    private static final int FONT_DEFAULT_SIZE = 18;

    private int text_default_color;

    private int title_color;

    private int title_size;

    private int title_gravity;

    private String title_text = "";

    private ColorStateList right_text_color;

    private String right_text = "";

    private int right_text_size;

    private ColorStateList left_text_color;

    private String left_text = "";

    private int left_text_size;

    private int right_button_src;

    private int left_button_src;

    private boolean bottom_progressbar;

    private int bottom_progressbar_color;

    private TextView mTvTitle;

    private TextView mLeftButton;

    private TextView mRightButton;

    private SmoothProgressBar mSpbBottom;

    private ContextMenu mContextMenu;

    private OnMenuItemClickCallback mCallback;

    private PopupWindow mContextMenuWindow;

    private OnClickListener mRightListener;


    public FelixToolBar(Context context) {
        this(context, null);
    }

    public FelixToolBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FelixToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initAttrs(@NonNull Context context, AttributeSet attrs, int defStyleAttr) {
        text_default_color = getResources().getColor(R.color.felix_bar_font_default_color);
        TypedArray a = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.FelixToolBar, defStyleAttr,
                        0);
        title_color = a.getColor(R.styleable.FelixToolBar_title_color,
                text_default_color);
        title_size = a
                .getDimensionPixelSize(R.styleable.FelixToolBar_title_font_size, FONT_DEFAULT_SIZE);
        title_gravity = a.getInt(R.styleable.FelixToolBar_title_gravity, CENTER);

        title_text = a.getString(R.styleable.FelixToolBar_title_text);

        right_text_color = a.getColorStateList(R.styleable.FelixToolBar_right_button_text_color);
        right_text_size = a
                .getDimensionPixelSize(R.styleable.FelixToolBar_right_button_text_font_size,
                        FONT_DEFAULT_SIZE);
        right_text = a.getString(R.styleable.FelixToolBar_right_button_text);

        left_text_color = a.getColorStateList(R.styleable.FelixToolBar_left_button_text_color);
        left_text_size = a
                .getDimensionPixelSize(R.styleable.FelixToolBar_left_button_text_font_size,
                        FONT_DEFAULT_SIZE);
        left_text = a.getString(R.styleable.FelixToolBar_left_button_text);

        right_button_src = a.getResourceId(R.styleable.FelixToolBar_right_button_src, 0);

        left_button_src = a.getResourceId(R.styleable.FelixToolBar_left_button_src, 0);

        bottom_progressbar = a.getBoolean(R.styleable.FelixToolBar_bottom_progressbar, false);

        bottom_progressbar_color = a.getColor(R.styleable.FelixToolBar_bottom_progressbar_color,
                getResources().getColor(R.color.felix_bar_default_progressbar_color));
    }

    @Override
    protected void initToolBar() {
        LayoutInflater.from(getContext()).inflate(R.layout.felix_toolbar_layout, this);
        mTvTitle = (TextView) findViewById(R.id.mTvTitle);
        mTvTitle.setText(title_text);
        mTvTitle.setTextColor(title_color);
        mTvTitle.setTextSize(title_size);
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

        mLeftButton = (TextView) findViewById(R.id.mLeftButton);
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

            mLeftButton.setTextSize(left_text_size);
        }

        mRightButton = (TextView) findViewById(R.id.mRightButton);
        if (right_button_src != 0) {
            mRightButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, right_button_src, 0);
        }
        if (!TextUtils.isEmpty(right_text)) {
            mRightButton.setText(right_text);
            if (right_text_color != null) {
                mRightButton.setTextColor(right_text_color);
            } else {
                mRightButton.setTextColor(text_default_color);
            }
            mRightButton.setTextSize(right_text_size);
        }

        mRightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                rightButtonClick(v);
            }
        });

        if (bottom_progressbar) {
            ViewStub mProgressBarStub = (ViewStub) findViewById(R.id.mProgressBarStub);
            mSpbBottom = (SmoothProgressBar) mProgressBarStub.inflate();
            mSpbBottom.setSmoothProgressDrawableColor(bottom_progressbar_color);
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = ScreenUtils
                .getMeasureSize(ScreenUtils.getScreenWidth(getContext()), widthMeasureSpec);
        int height = ScreenUtils
                .getMeasureSize(ScreenUtils.getActionBarHeight(getContext()), heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mContextMenuWindow = null;
        mCallback = null;
    }

    private void rightButtonClick(View v) {
        if (mContextMenu != null) {
            if (mContextMenuWindow == null) {
                final BubbleLayout bubbleLayout = (BubbleLayout) LayoutInflater.from(getContext())
                        .inflate(R.layout.felix_toolbar_context_menu, null);
                initBubbleLayout(bubbleLayout);
                mContextMenuWindow = BubblePopupHelper.create(getContext(), bubbleLayout);
            }
            mContextMenuWindow.showAsDropDown(v);
        } else {
            if (mRightListener != null) {
                mRightListener.onClick(v);
            }
        }
    }

    private void initBubbleLayout(BubbleLayout bubbleLayout) {
        if(mContextMenu.getBackground() != 0) {
            bubbleLayout.setBubbleColor(mContextMenu.getBackground());
        }
        RecyclerView recyclerView = (RecyclerView) bubbleLayout.findViewById(R.id.mMenuView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MenuAdapter(mContextMenu, new OnMenuItemClickCallback() {
            @Override
            public void onMenuItemClick(int position) {
                if (mContextMenuWindow != null) {
                    mContextMenuWindow.dismiss();
                }
                if (mCallback != null) {
                    mCallback.onMenuItemClick(position);
                }
            }
        }));
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
            mRightListener = listener;
        }
    }

    public void showProgress(boolean isShow) {
        if (mSpbBottom != null) {
            mSpbBottom.setVisibility(isShow ? VISIBLE : GONE);
            if (isShow) {
                mSpbBottom.progressiveStart();
            } else {
                mSpbBottom.progressiveStop();
            }
        }
    }

    public void setProgressBarDrawable(@NonNull SmoothProgressDrawable drawable) {
        if (mSpbBottom != null) {
            mSpbBottom.setIndeterminateDrawable(drawable);
        }
    }

    public void setToolBarTitle(@NonNull String title) {
        if (mTvTitle != null) {
            mTvTitle.setText(title);
        }
    }

    public void setToolBarTitle(@StringRes int res) {
        if (mTvTitle != null) {
            mTvTitle.setText(res);
        }
    }

    public void setRightButtonText(@StringRes int res) {
        if (mRightButton != null) {
            mRightButton.setText(res);
        }
    }

    public void setLeftButtonText(@StringRes int res) {
        if (mLeftButton != null) {
            mLeftButton.setText(res);
        }
    }

    public void registerRightButtonContextMenu(ContextMenu menu, OnMenuItemClickCallback mCallback) {
        mContextMenu = menu;
        this.mCallback = mCallback;
    }

    public void unRegisterRightButtonContexMenu() {
        mContextMenu = null;
        mCallback = null;
    }

}
