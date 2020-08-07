package ghelani.kshamina.sssc_android_app.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ghelani.kshamina.sssc_android_app.entity.Weight;
import io.reactivex.Single;

@Dao
public interface WeightDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWeight(Weight weight);

    @Update
    void updateWeight(Weight weight);

    @Delete
    void deleteWeight(Weight weight);

    @Query("SELECT * FROM weights")
    List<Weight> getAllWeights();

    @Query("SELECT * From weights WHERE weight_id = :id")
    Single<Weight> getWeightByID(String id);

    @Query("SELECT * FROM weights WHERE weight_course_id = :courseId")
    Single<List<Weight>> getWeightsByCourseId(String courseId);
}
