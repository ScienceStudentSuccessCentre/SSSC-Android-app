package ghelani.kshamina.sssc_android_app.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import ghelani.kshamina.sssc_android_app.entity.Course;

@Dao
public interface CourseDAO {
    @Insert
    public void insertCourse(Course course);

    @Update
    public void updateCourse(Course course);

    @Delete
    public void deleteCourse(Course course);

    @Query("SELECT * FROM course")
    public Course[] getAllCourses();

    @Query("SELECT * From course WHERE course_id = :id")
    public Course[] getCoursesByID(String id);

    @Query("SELECT * FROM course WHERE course_term_id = :courseTermId")
    public Course[] getCoursesByTermId(String courseTermId);
}
