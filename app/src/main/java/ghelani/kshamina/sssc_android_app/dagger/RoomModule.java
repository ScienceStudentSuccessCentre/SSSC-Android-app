package ghelani.kshamina.sssc_android_app.dagger;

import android.content.Context;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;
import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.database.GradesDatabase;

@InstallIn(ApplicationComponent.class)
@Module
public abstract class RoomModule {

    @Provides
    @Singleton
    public static GradesDatabase providesGradesDatabase(@ApplicationContext Context context) {
        GradesDatabase database = Room.databaseBuilder(context, GradesDatabase.class, "grades-database")
                .fallbackToDestructiveMigration()
                .build();
        // Thread thread = new Thread(() -> database.clearAllTables());
        // thread.start();
        return database;
    }

    @Provides
    @Singleton
    public static String[] providesLetterGradeArray(@ApplicationContext  Context context){
        return context.getResources().getStringArray(R.array.grades_array);
    }
}
