package ghelani.kshamina.sssc_android_app;


import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import ghelani.kshamina.sssc_android_app.dagger.DaggerAppComponent;

public class MainApplication extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }
}
