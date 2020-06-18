package ghelani.kshamina.sssc_android_app;

import android.content.Context;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;
import dagger.android.support.AndroidSupportInjection;

public class BaseDaggerFragment extends BaseFragment implements HasAndroidInjector {

    @Inject
    DispatchingAndroidInjector<Object> childFragmentInjector;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        return childFragmentInjector;
    }
}