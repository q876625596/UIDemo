package com.uidemo;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {

    private View topBar;
    private View contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topBar = (View) findViewById(R.id.topBar);
        contentView = (View) findViewById(R.id.contentView);

        setStatus();
    }

    /**
     * 设置是否全屏
     *
     * @return true：全屏
     * false：非全屏
     */
    protected boolean isFullScreen() {
        return true;
    }

    /**
     * 设置非全屏状态下状态栏颜色
     *
     * @return 颜色Id
     */
    protected
    @ColorRes
    int getStatusColor() {
        return 0;
    }

    private void setStatus() {
        if (isFullScreen()) {
            if (Build.VERSION.SDK_INT >= 21) {
                View decorView = getWindow().getDecorView();
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            } else if (Build.VERSION.SDK_INT >= 19) {
                SystemUtils.topBarColorChange(this,Color.argb(1,0,0,0));
            }
        }
        //系统版本大于21使用系统自带沉浸式状态栏
        /*if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            ViewGroup.LayoutParams layoutParams = topBar.getLayoutParams();
            int topHeight = SystemUtils.getTopHeight(this);
            layoutParams.height += topHeight;
            topBar.setLayoutParams(layoutParams);
        } else if (Build.VERSION.SDK_INT >= 19) {//小于21大于19使用第三方沉浸式状态栏
            SystemUtils.topBarColorChange(this, R.color.alpha);
            ViewGroup.LayoutParams layoutParams = topBar.getLayoutParams();
            int topHeight = SystemUtils.getTopHeight(this);
            layoutParams.height += topHeight;
            topBar.setLayoutParams(layoutParams);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }*/
    }


}
