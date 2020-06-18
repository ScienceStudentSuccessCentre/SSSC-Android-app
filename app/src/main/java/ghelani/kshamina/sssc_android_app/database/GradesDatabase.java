package ghelani.kshamina.sssc_android_app.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Context;

import ghelani.kshamina.sssc_android_app.entity.Assignment;
import ghelani.kshamina.sssc_android_app.entity.Course;
import ghelani.kshamina.sssc_android_app.entity.Term;
import ghelani.kshamina.sssc_android_app.entity.Weight;

@Database(version = 1, entities = {Term.class, Course.class, Assignment.class, Weight.class})
public abstract class GradesDatabase extends RoomDatabase {

    public abstract TermDao getTermDao();

    public abstract CourseDao getCourseDao();

    public abstract AssignmentDao getAssignmentDao();

    public abstract WeightDao getWeightDao();

}