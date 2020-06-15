package ghelani.kshamina.sssc_android_app.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ghelani.kshamina.sssc_android_app.entity.Course;

@Dao
public interface CourseDao {
    @Insert
    void insertCourse(Course course);

    @Update
    void updateCourse(Course course);

    @Delete
    void deleteCourse(Course course);

    @Query("SELECT * FROM course")
    List<Course> getAllCourses();

    @Query("SELECT * From course WHERE course_id = :id")
    List<Course> getCoursesByID(String id);

    @Query("SELECT * FROM course WHERE course_term_id = :courseTermId")
    List<Course> getCoursesByTermId(String courseTermId);
}
