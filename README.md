# SuperToolbar
Android custom toolbar


----------

## screenshot ##

![截图][1]
![截图][2]


----------

## Basic Usage ##

    <com.felix.supertoolbar.toolbar.FelixToolbar
            android:id="@+id/mFelixToolBar"
            android:layout_width="match_parent"
            android:layout_height="?attr/actionBarSize"
            android:background="@color/colorPrimary"
            app:title_text="标题标题标题标题标题标题"
            app:title_color="@android:color/white"
            app:title_gravity="center"
            app:left_button_text_color="@color/button_text_color_selector"
            app:right_button_text_color="@color/button_text_color_selector"
            app:bottom_progressbar="true"
            app:bottom_progressbar_color="@color/colorAccent"
            app:left_button_src="@drawable/iv_back"
            app:right_button_src="@mipmap/icon_add"
            app:has_badge="true"
            ></com.felix.supertoolbar.toolbar.FelixToolbar>


    <com.felix.supertoolbar.toolbar.SearchToolbar
            android:layout_width="match_parent"
            android:layout_height="44dp"
            android:background="@color/colorPrimary"
            android:layout_marginTop="8dp"
            app:search_text_hint="hint"
            app:search_toolbar_style="right_text"
            ></com.felix.supertoolbar.toolbar.SearchToolbar>


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

## Thanks ##

[BubbleLayout][3]




  [1]: https://raw.githubusercontent.com/chaichuanfa/SuperToolbar/master/screenshot/device-2017-05-31-190516.png
  [2]: https://raw.githubusercontent.com/chaichuanfa/SuperToolbar/master/screenshot/device-2017-05-31-190538.png
  [3]: https://github.com/MasayukiSuda/BubbleLayout