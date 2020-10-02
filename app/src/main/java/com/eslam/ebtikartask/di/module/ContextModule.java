package com.eslam.ebtikartask.di.module;

import android.content.Context;


import com.eslam.ebtikartask.di.qualifier.ApplicationContext;
import com.eslam.ebtikartask.di.scopes.ApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {
    private Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides
    @ApplicationScope
    @ApplicationContext
    public Context provideContext() {
        return context;
    }
}
