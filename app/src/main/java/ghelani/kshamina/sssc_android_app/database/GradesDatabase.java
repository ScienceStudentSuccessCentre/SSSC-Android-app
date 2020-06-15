package ghelani.kshamina.sssc_android_app.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import ghelani.kshamina.sssc_android_app.entity.Assignment;
import ghelani.kshamina.sssc_android_app.entity.Course;
import ghelani.kshamina.sssc_android_app.entity.Term;
import ghelani.kshamina.sssc_android_app.entity.Weight;

@Database(version = 1, entities = { Term.class, Course.class, Assignment.class, Weight.class })
public abstract class GradesDatabase extends RoomDatabase {
    private static GradesDatabase database = null;
    private static final String DATABASE_NAME = "grades-db";

    abstract public TermDao getTermDao();
    abstract public CourseDao getCourseDao();
    abstract public AssignmentDao getAssignmentDao();
    abstract public WeightDao getWeightDao();

    /**
     * Returns an instance of the grades database.
     * NOTE: database operations should be done on a separate thread.
     */
    public static GradesDatabase getInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(
                    context.getApplicationContext(), GradesDatabase.class, DATABASE_NAME).build();
        }

        return database;
    }

    /** ONLY USE FOR DEVELOPMENT PURPOSES */
    public static void emptyDatabase() {
        database.clearAllTables();
    }
}