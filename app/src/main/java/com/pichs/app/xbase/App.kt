package com.pichs.app.xbase;

import android.app.Application;

import com.pichs.common.widget.utils.XTypefaceHelper;

/**
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        XTypefaceHelper.init(this);
    }
}
