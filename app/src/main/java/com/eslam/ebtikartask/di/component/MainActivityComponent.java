package com.eslam.ebtikartask.di.component;

import android.content.Context;


import com.eslam.ebtikartask.di.module.AdapterModule;
import com.eslam.ebtikartask.di.qualifier.ActivityContext;
import com.eslam.ebtikartask.di.scopes.ActivityScope;
import com.eslam.ebtikartask.ui.MainActivity;

import dagger.Component;


@ActivityScope
@Component(modules = AdapterModule.class, dependencies = ApplicationComponent.class)
public interface MainActivityComponent {

    @ActivityContext
    Context getContext();


    void injectMainActivity(MainActivity mainActivity);
}
