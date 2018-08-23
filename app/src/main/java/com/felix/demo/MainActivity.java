package com.felix.demo;

import com.felix.supertoolbar.toolbar.FelixToolbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    FelixToolbar mFelixToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFelixToolBar = findViewById(R.id.mFelixToolBar);

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

    public void enable(View v) {

        mFelixToolBar.setRightButtonEnable(true);

    }

    public void disable(View v) {
        mFelixToolBar.setRightButtonEnable(false);

    }
}
