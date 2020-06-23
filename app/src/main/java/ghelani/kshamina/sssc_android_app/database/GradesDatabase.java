package ghelani.kshamina.sssc_android_app.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ghelani.kshamina.sssc_android_app.entity.Assignment;
import ghelani.kshamina.sssc_android_app.entity.CourseEntity;
import ghelani.kshamina.sssc_android_app.entity.TermEntity;
import ghelani.kshamina.sssc_android_app.entity.Weight;

@Database(version = 1, entities = {TermEntity.class, CourseEntity.class, Assignment.class, Weight.class})
public abstract class GradesDatabase extends RoomDatabase {

    public abstract TermDao getTermDao();

    public abstract CourseDao getCourseDao();

    public abstract AssignmentDao getAssignmentDao();

    public abstract WeightDao getWeightDao();

}