package com.paezand.mvpexample.root;

import android.app.Application;

import com.paezand.mvpexample.http.ApiModule;
import com.paezand.mvpexample.ui.login.LoginModule;

// This is where Dagger will live through the entire life time of the application

public class App extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .apiModule(new ApiModule())
                .loginModule(new LoginModule())
                .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
