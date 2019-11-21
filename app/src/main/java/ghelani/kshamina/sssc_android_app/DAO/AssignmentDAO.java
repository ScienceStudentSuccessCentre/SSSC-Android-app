package ghelani.kshamina.sssc_android_app.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import ghelani.kshamina.sssc_android_app.entity.Assignment;

@Dao
public interface AssignmentDAO {
    @Insert
    public void insertAssignment(Assignment assignment);

    @Update
    public void updateAssignment(Assignment assignment);

    @Delete
    public void deleteAssignment(Assignment assignment);

    @Query("SELECT * FROM assignment")
    public Assignment[] getAllAssignments();

    @Query("SELECT * From assignment WHERE assignment_id = :id")
    public Assignment[] getAssignmentsByID(String id);

    @Query("SELECT * FROM assignment WHERE asssignment_course_id = :courseId")
    public Assignment[] getAssignmentsByCourseId(String courseId);
}
