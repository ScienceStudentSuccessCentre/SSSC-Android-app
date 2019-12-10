package ghelani.kshamina.sssc_android_app.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ghelani.kshamina.sssc_android_app.entity.Assignment;

@Dao
public interface AssignmentDao {
    @Insert
    void insertAssignment(Assignment assignment);

    @Update
    void updateAssignment(Assignment assignment);

    @Delete
    void deleteAssignment(Assignment assignment);

    @Query("SELECT * FROM assignment")
    List<Assignment> getAllAssignments();

    @Query("SELECT * From assignment WHERE assignment_id = :id")
    List<Assignment> getAssignmentsByID(String id);

    @Query("SELECT * FROM assignment WHERE assignment_course_id = :courseId")
    List<Assignment> getAssignmentsByCourseId(String courseId);
}
