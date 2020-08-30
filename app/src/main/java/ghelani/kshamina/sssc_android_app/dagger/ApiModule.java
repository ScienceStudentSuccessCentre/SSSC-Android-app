package ghelani.kshamina.sssc_android_app.dagger;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import ghelani.kshamina.sssc_android_app.network.NetworkManager;
import ghelani.kshamina.sssc_android_app.network.SSSCApiService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@InstallIn(ApplicationComponent.class)
@Module
public abstract class ApiModule {

    @Provides
    @Singleton
    public static NetworkManager providesNetworkManager() {
        SSSCApiService mentorAPI = new Retrofit.Builder()
                .baseUrl("https://sssc-carleton-app-server.herokuapp.com")
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(SSSCApiService.class);

        return new NetworkManager(mentorAPI);
    }
}
