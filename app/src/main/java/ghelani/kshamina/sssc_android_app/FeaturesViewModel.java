package ghelani.kshamina.sssc_android_app;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import ghelani.kshamina.sssc_android_app.network.Features;
import ghelani.kshamina.sssc_android_app.network.NetworkManager;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FeaturesViewModel extends ViewModel {

        private final NetworkManager networkManager;
        private MutableLiveData<Features> features = new MutableLiveData<>();
        private final SavedStateHandle savedStateHandle;

        @ViewModelInject
        public FeaturesViewModel(NetworkManager networkManager, @Assisted SavedStateHandle  savedStateHandle) {
            this.networkManager = networkManager;
            this.savedStateHandle = savedStateHandle;
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
