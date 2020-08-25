package ghelani.kshamina.sssc_android_app;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import ghelani.kshamina.sssc_android_app.network.Features;
import ghelani.kshamina.sssc_android_app.network.NetworkManager;
import ghelani.kshamina.sssc_android_app.ui.utils.events.SingleLiveEvent;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FeaturesViewModel extends ViewModel {

        private NetworkManager networkManager;
        private MutableLiveData<Features> features = new MutableLiveData<>();
        private SingleLiveEvent<Fragment> navigationEvent = new SingleLiveEvent<>();

        @Inject
        public FeaturesViewModel(NetworkManager networkManager) {
            this.networkManager = networkManager;
        }

        public void fetchFeatures() {
            networkManager.getFeatures()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<Features>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onSuccess(Features featureSettings) {
                            features.setValue(featureSettings);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    });
        }

        public LiveData<Features> getFeatures() {
            return features;
        }
}
