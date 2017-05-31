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
import android.graphics.drawable.GradientDrawable;
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

public class FelixToolbar extends BaseToolbar {

    private static final int LEFT = -1;

    private static final int CENTER = 0;

    private static final int RIGHT = 1;

    private static final int BUTTON_FONT_DEFAULT_SIZE = 16;

    private static final int TITLE_FONT_DEFAULT_SIZE = 18;

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

    private boolean has_badge;

    private int badge_background_color;

    private int badge_text_color;

    private TextView mTvTitle;

    private TextView mLeftButton;

    private TextView mRightButton;

    private TextView mTvBadgeCount;

    private View mHasMessageHint;

    private SmoothProgressBar mSpbBottom;

    private ContextMenu mContextMenu;

    private OnMenuItemClickCallback mCallback;

    private PopupWindow mContextMenuWindow;

    private OnClickListener mRightListener;

    private boolean isHint;

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
        TypedArray a = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.FelixToolbar, defStyleAttr,
                        0);
        title_color = a.getColor(R.styleable.FelixToolbar_title_color,
                text_default_color);
        title_size = a
                .getDimensionPixelSize(R.styleable.FelixToolbar_title_font_size,
                        TITLE_FONT_DEFAULT_SIZE);
        title_gravity = a.getInt(R.styleable.FelixToolbar_title_gravity, CENTER);

        title_text = a.getString(R.styleable.FelixToolbar_title_text);

        right_text_color = a.getColorStateList(R.styleable.FelixToolbar_right_button_text_color);
        right_text_size = a
                .getDimensionPixelSize(R.styleable.FelixToolbar_right_button_text_font_size,
                        BUTTON_FONT_DEFAULT_SIZE);
        right_text = a.getString(R.styleable.FelixToolbar_right_button_text);

        left_text_color = a.getColorStateList(R.styleable.FelixToolbar_left_button_text_color);
        left_text_size = a
                .getDimensionPixelSize(R.styleable.FelixToolbar_left_button_text_font_size,
                        BUTTON_FONT_DEFAULT_SIZE);
        left_text = a.getString(R.styleable.FelixToolbar_left_button_text);

        right_button_src = a.getResourceId(R.styleable.FelixToolbar_right_button_src, 0);

        left_button_src = a.getResourceId(R.styleable.FelixToolbar_left_button_src, 0);

        bottom_progressbar = a.getBoolean(R.styleable.FelixToolbar_bottom_progressbar, false);

        bottom_progressbar_color = a.getColor(R.styleable.FelixToolbar_bottom_progressbar_color,
                getResources().getColor(R.color.felix_bar_default_progressbar_color));

        has_badge = a.getBoolean(R.styleable.FelixToolbar_has_badge, false);

        badge_background_color = a.getColor(R.styleable.FelixToolbar_badge_background_color,
                getResources().getColor(R.color.default_badge_background));

        badge_text_color = a.getColor(R.styleable.FelixToolbar_badge_text_color,
                getResources().getColor(R.color.default_badge_text_color));
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

        if (has_badge) {
            ViewStub mBadge = (ViewStub) findViewById(R.id.mBadge);
            View rootView = mBadge.inflate();
            mTvBadgeCount = (TextView) rootView.findViewById(R.id.mTvBadgeCount);
            mHasMessageHint = rootView.findViewById(R.id.mHasMessageHint);
            mTvBadgeCount.setTextColor(badge_text_color);
            GradientDrawable gd = new GradientDrawable();
            gd.setColor(badge_background_color);
            gd.setCornerRadius(ScreenUtils.dip2px(15, getContext()));
            gd.setShape(GradientDrawable.RECTANGLE);
            mTvBadgeCount.setBackground(gd);

            GradientDrawable hintGD = new GradientDrawable();
            hintGD.setColor(badge_background_color);
            hintGD.setShape(GradientDrawable.OVAL);
            mHasMessageHint.setBackground(hintGD);
        }

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
        if (mContextMenu.getBackground() != 0) {
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

    public void setTitleClickListener(OnClickListener listener) {
        if (mTvTitle != null) {
            mTvTitle.setOnClickListener(listener);
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

    public void registerRightButtonContextMenu(ContextMenu menu,
            OnMenuItemClickCallback mCallback) {
        mContextMenu = menu;
        this.mCallback = mCallback;
    }

    public void unRegisterRightButtonContexMenu() {
        mContextMenu = null;
        mCallback = null;
    }

    public void setHasMessageHint(boolean isHint) {
        if (mHasMessageHint != null && mTvBadgeCount != null) {
            if (isHint && mTvBadgeCount.getVisibility() != VISIBLE) {
                mHasMessageHint.setVisibility(VISIBLE);
            } else {
                mHasMessageHint.setVisibility(GONE);
            }
            this.isHint = isHint;
        }
    }

    /**
     * 当count大于99时,显示99+
     */
    public void setBadgeCount(int count) {
        if (mHasMessageHint != null && mTvBadgeCount != null) {
            if (count <= 0) {
                mTvBadgeCount.setVisibility(GONE);
                setHasMessageHint(isHint);
            } else if (count > 99) {
                mHasMessageHint.setVisibility(GONE);
                mTvBadgeCount.setVisibility(VISIBLE);
                mTvBadgeCount.setText("99+");
            } else {
                mHasMessageHint.setVisibility(GONE);
                mTvBadgeCount.setVisibility(VISIBLE);
                mTvBadgeCount.setText(String.valueOf(count));
            }
        }
    }

}
