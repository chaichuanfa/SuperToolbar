package com.felix.supertoolbar.config;

import android.support.annotation.ColorInt;

import java.util.List;

/**
 * Created by chaichuanfa on 17/5/27.
 */

public class ContextMenu {

    private List<String> itemTitles;

    private List<Integer> itemDrawable;

    private int background;

    private int textColor;

    private int dividerColor;

    private ContextMenu(Builder builder) {
        this.itemTitles = builder.itemTitles;
        this.itemDrawable = builder.itemDrawable;
        this.background = builder.background;
        this.textColor = builder.textColor;
        this.dividerColor = builder.dividerColor;
    }

    public List<String> getItemTitles() {
        return itemTitles;
    }

    public List<Integer> getItemDrawable() {
        return itemDrawable;
    }

    public int getBackground() {
        return background;
    }

    public int getTextColor() {
        return textColor;
    }

    public int getDividerColor() {
        return dividerColor;
    }

    public static class Builder {

        List<String> itemTitles;

        List<Integer> itemDrawable;

        @ColorInt
        int background;

        @ColorInt
        int textColor;

        @ColorInt
        int dividerColor;

        public Builder itemTitles(List<String> itemTitles) {
            this.itemTitles = itemTitles;
            return this;
        }

        public Builder itemDrawable(List<Integer> itemDrawable) {
            this.itemDrawable = itemDrawable;
            return this;
        }

        public Builder background(@ColorInt int background) {
            this.background = background;
            return this;
        }

        public Builder textColor(@ColorInt int textColor) {
            this.textColor = textColor;
            return this;
        }

        public Builder dividerColor(@ColorInt int dividerColor) {
            this.dividerColor = dividerColor;
            return this;
        }

        public ContextMenu build() {
            if (itemTitles == null || itemTitles.isEmpty()) {
                throw new NullPointerException("itemTitles can not be null");
            }

            if (itemDrawable != null && itemTitles.size() != itemDrawable.size()) {
                throw new IllegalArgumentException(
                        "itemTitles size must be equals itemDrawable size");
            }
            return new ContextMenu(this);
        }

    }

}
