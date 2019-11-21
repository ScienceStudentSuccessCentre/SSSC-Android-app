package ghelani.kshamina.sssc_android_app;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ghelani.kshamina.sssc_android_app.DAO.AssignmentDAO;
import ghelani.kshamina.sssc_android_app.DAO.CourseDAO;
import ghelani.kshamina.sssc_android_app.DAO.TermDAO;
import ghelani.kshamina.sssc_android_app.DAO.WeightDAO;
import ghelani.kshamina.sssc_android_app.entity.Assignment;
import ghelani.kshamina.sssc_android_app.entity.Course;
import ghelani.kshamina.sssc_android_app.entity.Term;
import ghelani.kshamina.sssc_android_app.entity.Weight;

@Database(version = 1, entities = { Term.class, Course.class, Assignment.class, Weight.class })
public abstract class GradesDatabase extends RoomDatabase {
    abstract public TermDAO getTermDao();
    abstract public CourseDAO getCourseDao();
    abstract public AssignmentDAO getAssignmentDao();
    abstract public WeightDAO getWeightDao();
}