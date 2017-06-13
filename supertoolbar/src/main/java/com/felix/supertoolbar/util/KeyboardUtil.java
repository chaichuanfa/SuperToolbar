package com.felix.supertoolbar.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public final class KeyboardUtil {

    private KeyboardUtil() {
    }

    public static void hideSoftInput(View paramEditText, Context mContext) {
        ((InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(paramEditText.getWindowToken(), 0);
    }

    public static void showKeyBoard(final View paramEditText, final Context mContext) {
        paramEditText.requestFocus();
        paramEditText.post(new Runnable() {
            @Override
            public void run() {
                ((InputMethodManager) mContext
                        .getSystemService(Context.INPUT_METHOD_SERVICE))
                        .showSoftInput(paramEditText, 0);
            }
        });
    }
}
