package com.felix.supertoolbar.toolbar;

import com.felix.supertoolbar.R;
import com.felix.supertoolbar.util.ScreenUtils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by chaichuanfa on 17/5/31.
 */

public class SearchToolbar extends BaseToolbar {

    private static final int LEFT_BACK = 0;

    private static final int RIGHT_TEXT = 1;

    private int search_toolbar_style = LEFT_BACK;

    private int right_text_color;

    private String right_text;

    private int right_text_size = 16;

    private int left_back_icon;

    private String search_text_hint;

    private int search_text_hint_color;

    private int search_text_size = 16;

    private int search_text_color;

    private int search_view_background;

    private TextView mTvBack;

    private View mSearchView;

    private EditText mEtSearch;

    private View mIvEtClear;

    private TextView mTvCancel;

    private OnSearchViewCallback mCallback;


    public SearchToolbar(Context context) {
        this(context, null);
    }

    public SearchToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    void initAttrs(@NonNull Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.SearchToolbar, defStyleAttr,
                        0);

        search_toolbar_style = a.getInt(R.styleable.SearchToolbar_search_toolbar_style, LEFT_BACK);

        right_text_color = a.getColor(R.styleable.SearchToolbar_right_text_color, Color.WHITE);

        right_text_size = a
                .getDimensionPixelSize(R.styleable.SearchToolbar_right_text_size,
                        16);

        right_text = a.getString(R.styleable.SearchToolbar_right_text);

        left_back_icon = a.getResourceId(R.styleable.SearchToolbar_left_back_icon, 0);

        search_text_hint = a.getString(R.styleable.SearchToolbar_search_text_hint);

        search_text_hint_color = a
                .getColor(R.styleable.SearchToolbar_search_text_hint_color, Color.GRAY);

        search_text_size = a.getDimensionPixelSize(R.styleable.SearchToolbar_search_text_size, 14);

        search_text_color = a.getColor(R.styleable.SearchToolbar_search_text_color, Color.BLACK);

        search_view_background = a
                .getColor(R.styleable.SearchToolbar_search_view_background, Color.WHITE);

    }

    @Override
    void initToolBar() {
        LayoutInflater.from(getContext()).inflate(R.layout.felix_search_toolbar_layout, this);

        mTvBack = (TextView) findViewById(R.id.mTvBack);
        mTvCancel = (TextView) findViewById(R.id.mTvCancel);
        mSearchView = findViewById(R.id.mSearchView);
        mEtSearch = (EditText) findViewById(R.id.mEtSearch);
        mIvEtClear = findViewById(R.id.mIvEtClear);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mSearchView.getLayoutParams();
        if (LEFT_BACK == search_toolbar_style) {
            mTvBack.setVisibility(VISIBLE);
            mTvCancel.setVisibility(GONE);
            if (left_back_icon != 0) {
                mTvBack.setCompoundDrawablesWithIntrinsicBounds(left_back_icon, 0, 0, 0);
            }

            params.leftMargin = 0;
        } else {
            mTvBack.setVisibility(GONE);
            mTvCancel.setVisibility(VISIBLE);
            mTvCancel.setTextColor(right_text_color);
            mTvCancel.setTextSize(right_text_size);
            if (!TextUtils.isEmpty(right_text)) {
                mTvCancel.setText(right_text);
            }
            params.rightMargin = 0;
        }

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(ScreenUtils.dip2px(4, getContext()));
        gd.setColor(search_view_background);
        mSearchView.setBackground(gd);

        mEtSearch.setTextColor(search_text_color);
        mEtSearch.setTextSize(search_text_size);
        if (!TextUtils.isEmpty(search_text_hint)) {
            mEtSearch.setHint(search_text_hint);
        }
        mEtSearch.setHintTextColor(search_text_hint_color);
        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mCallback != null) {
                    mCallback.onTextChange(s, start, before, count);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(mEtSearch.getText())) {
                    mIvEtClear.setVisibility(GONE);
                } else {
                    mIvEtClear.setVisibility(VISIBLE);
                }
            }
        });

        mIvEtClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtSearch.setText("");
            }
        });

        mEtSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (mCallback != null && !TextUtils.isEmpty(mEtSearch.getText())) {
                        mCallback.onEditorSearch(mEtSearch.getText().toString().trim());
                    }
                }
                return false;
            }
        });
    }

    public void setLeftButtonClickListener(OnClickListener listener) {
        mTvBack.setOnClickListener(listener);
    }

    public void setRightButtonClickListener(OnClickListener listener) {
        mTvCancel.setOnClickListener(listener);
    }

    public void setSearchFilter(InputFilter[] filters) {
        mEtSearch.setFilters(filters);
    }

    public void setSearchTextChange(OnSearchViewCallback mCallback) {
        this.mCallback = mCallback;
    }

    public void searchViewRequestFocus() {
        mEtSearch.requestFocus();
    }

    public interface OnSearchViewCallback {

        void onTextChange(CharSequence s, int start, int before, int count);

        void onEditorSearch(String searchText);
    }

}
