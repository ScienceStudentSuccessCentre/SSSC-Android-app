package ghelani.kshamina.sssc_android_app.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ghelani.kshamina.sssc_android_app.entity.CourseEntity;
import io.reactivex.Single;

@Dao
public interface CourseDao {
    @Insert
    void insertCourse(CourseEntity courseEntity);

    @Update
    void updateCourse(CourseEntity courseEntity);

    @Query("DELETE FROM courses WHERE course_id =:id")
    void deleteCourse(String id);

    @Query("SELECT * FROM courses")
    List<CourseEntity> getAllCourses();

    @Query("SELECT * From courses WHERE course_id = :id")
    Single<List<CourseEntity>>getCoursesByID(String id);

    @Query("SELECT * FROM courses WHERE course_term_id = :courseTermId")
    Single<List<CourseEntity>> getCoursesByTermId(String courseTermId);
}
