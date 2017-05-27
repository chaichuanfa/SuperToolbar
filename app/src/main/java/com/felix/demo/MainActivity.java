package com.felix.demo;

import com.felix.supertoolbar.adapter.OnMenuItemClickCallback;
import com.felix.supertoolbar.config.ContextMenu;
import com.felix.supertoolbar.toolbar.FelixToolBar;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FelixToolBar mFelixToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFelixToolBar = (FelixToolBar) findViewById(R.id.mFelixToolBar);

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
                        Toast.makeText(MainActivity.this, "click "+position, Toast.LENGTH_SHORT).show();
                    }
                });




    }

    public void startProgress(View v) {
        mFelixToolBar.showProgress(true);
    }

    public void stopProgress(View v) {
        mFelixToolBar.showProgress(false);
    }

    public void waveSwipe(View v) {
    }
}
