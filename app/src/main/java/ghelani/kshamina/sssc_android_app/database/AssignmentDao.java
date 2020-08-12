package ghelani.kshamina.sssc_android_app.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ghelani.kshamina.sssc_android_app.entity.Assignment;
import ghelani.kshamina.sssc_android_app.entity.AssignmentWithWeight;
import io.reactivex.Single;

@Dao
public interface AssignmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAssignment(Assignment assignment);

    @Delete
    void deleteAssignment(Assignment assignment);

    @Query ("DELETE FROM assignments where assignment_id = :id")
    void deleteAssignment(String id);

    @Query("SELECT * FROM assignments")
    List<Assignment> getAllAssignments();

    @Query("SELECT * From assignments WHERE assignment_id = :id")
    Single<Assignment> getAssignmentByID(String id);

    @Query("SELECT * FROM assignments WHERE assignment_course_id = :courseId")
    Single<List<AssignmentWithWeight>> getAssignmentsByCourseId(String courseId);
}
