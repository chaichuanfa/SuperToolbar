package com.felix.demo;

import com.felix.supertoolbar.adapter.OnMenuItemClickCallback;
import com.felix.supertoolbar.config.ContextMenu;
import com.felix.supertoolbar.toolbar.FelixToolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FelixToolbar mFelixToolBar;

    int badge = 0;

    boolean hasMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFelixToolBar = (FelixToolbar) findViewById(R.id.mFelixToolBar);

        mFelixToolBar.setLeftButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "left click", Toast.LENGTH_SHORT).show();
            }
        });

        mFelixToolBar.setRightButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "right click", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void startProgress(View v) {
        mFelixToolBar.showProgress(true);
    }

    public void stopProgress(View v) {
        mFelixToolBar.showProgress(false);
    }

    public void add(View v) {
        badge++;
        mFelixToolBar.setBadgeCount(badge);
    }

    public void reduce(View v) {
        badge--;
        if (badge < 0) {
            badge = 0;
        }
        mFelixToolBar.setBadgeCount(badge);
    }

    public void hasMessage(View v) {

        mFelixToolBar.setHasMessageHint(!hasMessage);

        hasMessage = !hasMessage;
    }

    public void register(View v) {
        List<String> titles = new ArrayList<>();
        titles.add("添加好友");
        titles.add("创建群组");

        List<Integer> drawableRes = new ArrayList<>();
        drawableRes.add(R.mipmap.buddle_add_friend);
        drawableRes.add(R.mipmap.buddle_create_group);

        mFelixToolBar.registerRightButtonContextMenu(
                new ContextMenu.Builder().itemDrawable(drawableRes).itemTitles(titles)
                        .background(Color.WHITE)
                        .textColor(Color.BLACK)
                        .dividerColor(Color.GRAY).build(),
                new OnMenuItemClickCallback() {
                    @Override
                    public void onMenuItemClick(int position) {
                        Toast.makeText(MainActivity.this, "click " + position, Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }

    public void unregister(View v) {
        mFelixToolBar.unRegisterRightButtonContexMenu();
    }

    public void styles(View v) {

        startActivity(new Intent(this, StylesActivity.class));

    }

    public void enable(View v) {

        mFelixToolBar.setRightButtonEnable(true);

    }

    public void disable(View v) {
        mFelixToolBar.setRightButtonEnable(false);

    }
}
