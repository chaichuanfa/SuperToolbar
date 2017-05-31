package com.felix.demo;

import com.felix.supertoolbar.toolbar.FelixToolbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class StylesActivity extends AppCompatActivity {

    FelixToolbar mFelixToolBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_styles);

        mFelixToolBar = (FelixToolbar) findViewById(R.id.mFelixToolBar);
        mFelixToolBar.setLeftButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mFelixToolBar.setRightButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StylesActivity.this, "save", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
