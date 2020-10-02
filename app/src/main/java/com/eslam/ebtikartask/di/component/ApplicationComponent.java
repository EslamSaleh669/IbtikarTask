package com.eslam.ebtikartask.di.component;

import android.content.Context;

import com.eslam.ebtikartask.MyApplication;
import com.eslam.ebtikartask.di.module.ContextModule;
import com.eslam.ebtikartask.di.module.RetrofitModule;
import com.eslam.ebtikartask.di.qualifier.ApplicationContext;
import com.eslam.ebtikartask.di.scopes.ApplicationScope;
import com.eslam.ebtikartask.retrofit.APIInterface;

import dagger.Component;

@ApplicationScope
@Component(modules = {ContextModule.class, RetrofitModule.class})
public interface ApplicationComponent {

    public APIInterface getApiInterface();

    @ApplicationContext
    public Context getContext();


    public void injectApplication(MyApplication myApplication);
}
