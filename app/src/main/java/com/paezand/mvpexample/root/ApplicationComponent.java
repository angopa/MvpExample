package com.paezand.mvpexample.root;

import com.paezand.mvpexample.login.LoginActivity;
import com.paezand.mvpexample.login.LoginModule;

import javax.inject.Singleton;

import dagger.Component;

// This class is use by Dagger in order to know where to inject the dependencies
// in Dagger 2 the injecter class is call the component, this component assign
// references in our activities or fragments to have access to the singleton which we already defined

@Singleton
@Component(modules = {ApplicationModule.class, LoginModule.class})
public interface ApplicationComponent {

    void inject(LoginActivity target);

}
