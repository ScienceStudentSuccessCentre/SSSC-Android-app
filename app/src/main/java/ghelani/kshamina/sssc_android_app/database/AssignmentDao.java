package ghelani.kshamina.sssc_android_app.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ghelani.kshamina.sssc_android_app.entity.Assignment;
import io.reactivex.Single;

@Dao
public interface AssignmentDao {
    @Insert
    void insertAssignment(Assignment assignment);

    @Update
    void updateAssignment(Assignment assignment);

    @Delete
    void deleteAssignment(Assignment assignment);

    @Query("SELECT * FROM assignments")
    List<Assignment> getAllAssignments();

    @Query("SELECT * From assignments WHERE assignment_id = :id")
    List<Assignment> getAssignmentsByID(String id);

    @Query("SELECT * FROM assignments WHERE assignment_course_id = :courseId")
    Single<List<Assignment>> getAssignmentsByCourseId(String courseId);
}
