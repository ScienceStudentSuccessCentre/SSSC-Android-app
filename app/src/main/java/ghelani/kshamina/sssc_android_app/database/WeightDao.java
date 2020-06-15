package ghelani.kshamina.sssc_android_app.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ghelani.kshamina.sssc_android_app.entity.Weight;

@Dao
public interface WeightDao {
    @Insert
    void insertWeight(Weight weight);

    @Update
    void updateWeight(Weight weight);

    @Delete
    void deleteWeight(Weight weight);

    @Query("SELECT * FROM weight")
    List<Weight> getAllWeights();

    @Query("SELECT * From weight WHERE weight_id = :id")
    List<Weight> getWeightsByID(String id);

    @Query("SELECT * FROM weight WHERE weight_course_id = :courseId")
    List<Weight> getWeightsByCourseId(String courseId);
}
