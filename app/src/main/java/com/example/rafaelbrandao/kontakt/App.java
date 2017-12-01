package com.example.rafaelbrandao.kontakt;

import android.app.Application;

import com.kontakt.sdk.android.common.KontaktSDK;

/**
 * Created by rafaelbrandao on 14/09/17.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        KontaktSDK.initialize(this);
    }
}
