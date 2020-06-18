package ghelani.kshamina.sssc_android_app.dagger;


import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import ghelani.kshamina.sssc_android_app.MainApplication;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        FragmentBuilderModule.class
})
public interface AppComponent extends AndroidInjector<MainApplication> {
    @Override
    void inject(MainApplication instance);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AppComponent.Builder application(MainApplication application);

        AppComponent build();
    }
}
