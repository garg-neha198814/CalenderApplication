package com.shivam.testing.calenderapplication.ui.activities;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;


/**
 * Created by root on 14/4/16.
 */
public class MyApplication extends MultiDexApplication {


    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
