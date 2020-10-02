package com.eslam.ebtikartask;

import android.app.Activity;
import android.app.Application;

import com.eslam.ebtikartask.di.component.ApplicationComponent;
import com.eslam.ebtikartask.di.component.DaggerApplicationComponent;
import com.eslam.ebtikartask.di.module.ContextModule;

public class MyApplication extends Application {

    ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder().contextModule(new ContextModule(this)).build();
        applicationComponent.injectApplication(this);

    }

    public static MyApplication get(Activity activity){
        return (MyApplication) activity.getApplication();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}

