package com.eslam.ebtikartask.di.module;


import com.eslam.ebtikartask.adapter.Person_Adapter;
import com.eslam.ebtikartask.di.scopes.ActivityScope;
import com.eslam.ebtikartask.ui.MainActivity;

import dagger.Module;
import dagger.Provides;

@Module(includes = {MainActivityContextModule.class})
public class AdapterModule {

    @Provides
    @ActivityScope
    public Person_Adapter getPeopleLIst(Person_Adapter.ClickListener clickListener , MainActivity context) {
        return new Person_Adapter(clickListener, context  );
    }

    @Provides
    @ActivityScope
    public Person_Adapter.ClickListener getClickListener(MainActivity mainActivity) {
        return mainActivity;
    }
}
