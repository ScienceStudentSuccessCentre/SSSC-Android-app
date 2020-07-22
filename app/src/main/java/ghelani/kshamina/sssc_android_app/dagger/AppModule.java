package ghelani.kshamina.sssc_android_app.dagger;

import android.content.Context;

import androidx.room.Room;


import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import ghelani.kshamina.sssc_android_app.MainApplication;
import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.database.GradesDatabase;

@Module
public abstract class AppModule {

    @Binds
    public abstract Context bindsApplicationContext(MainApplication application);

    @Provides
    @Singleton
    public static GradesDatabase providesGradesDatabase(Context context) {
        return Room.databaseBuilder(context, GradesDatabase.class, "grades-database")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    public static String[] providesLetterGradeArray(Context context){
        return context.getResources().getStringArray(R.array.grades_array);
    };
}
