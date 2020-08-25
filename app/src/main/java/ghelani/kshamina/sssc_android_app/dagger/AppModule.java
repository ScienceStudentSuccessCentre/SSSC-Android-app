package ghelani.kshamina.sssc_android_app.dagger;

import android.content.Context;

import androidx.room.Room;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import ghelani.kshamina.sssc_android_app.MainApplication;
import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.database.GradesDatabase;
import ghelani.kshamina.sssc_android_app.network.SSSCApiService;
import ghelani.kshamina.sssc_android_app.network.NetworkManager;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public abstract class AppModule {

    @Binds
    public abstract Context bindsApplicationContext(MainApplication application);

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

    @Provides
    @Singleton
    public static GradesDatabase providesGradesDatabase(Context context) {
        GradesDatabase database = Room.databaseBuilder(context, GradesDatabase.class, "grades-database")
                .fallbackToDestructiveMigration()
                .build();
       // Thread thread = new Thread(() -> database.clearAllTables());
       // thread.start();
        return database;
    }

    @Provides
    @Singleton
    public static String[] providesLetterGradeArray(Context context){
        return context.getResources().getStringArray(R.array.grades_array);
    };
}
