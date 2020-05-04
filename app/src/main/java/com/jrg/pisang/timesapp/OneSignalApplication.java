package com.jrg.pisang.timesapp;
import android.app.Application;
import com.onesignal.OneSignal;

public class OneSignalApplication extends Application {

    private static OneSignalApplication mInstance;

    public OneSignalApplication(){
        mInstance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }

    public static synchronized OneSignalApplication getInstance(){
        return mInstance;
    }
}
