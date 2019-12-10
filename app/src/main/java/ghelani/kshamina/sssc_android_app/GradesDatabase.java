package ghelani.kshamina.sssc_android_app;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ghelani.kshamina.sssc_android_app.DAO.AssignmentDao;
import ghelani.kshamina.sssc_android_app.DAO.CourseDao;
import ghelani.kshamina.sssc_android_app.DAO.TermDao;
import ghelani.kshamina.sssc_android_app.DAO.WeightDao;
import ghelani.kshamina.sssc_android_app.entity.Assignment;
import ghelani.kshamina.sssc_android_app.entity.Course;
import ghelani.kshamina.sssc_android_app.entity.Term;
import ghelani.kshamina.sssc_android_app.entity.Weight;

@Database(version = 1, entities = { Term.class, Course.class, Assignment.class, Weight.class })
public abstract class GradesDatabase extends RoomDatabase {
    abstract public TermDao getTermDao();
    abstract public CourseDao getCourseDao();
    abstract public AssignmentDao getAssignmentDao();
    abstract public WeightDao getWeightDao();
}