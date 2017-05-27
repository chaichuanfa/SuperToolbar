package com.felix.supertoolbar.bean;

/**
 * Created by chaichuanfa on 17/5/27.
 */

public class MenuItem {

    private String title;
    private int drawableRes;

    public static MenuItem create(String title, int drawableRes) {
        return new MenuItem(title, drawableRes);
    }

    public MenuItem(String title, int drawableRes) {
        this.title = title;
        this.drawableRes = drawableRes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDrawableRes() {
        return drawableRes;
    }

    public void setDrawableRes(int drawableRes) {
        this.drawableRes = drawableRes;
    }
}
