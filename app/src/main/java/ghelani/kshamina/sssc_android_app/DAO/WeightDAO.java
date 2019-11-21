package ghelani.kshamina.sssc_android_app.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import ghelani.kshamina.sssc_android_app.entity.Weight;

@Dao
public interface WeightDAO {
    @Insert
    public void insertWeight(Weight weight);

    @Update
    public void updateWeight(Weight weight);

    @Delete
    public void deleteWeight(Weight weight);

    @Query("SELECT * FROM weight")
    public Weight[] getAllWeights();

    @Query("SELECT * From weight WHERE weight_id = :id")
    public Weight[] getWeightsByID(String id);

    @Query("SELECT * FROM weight WHERE weight_course_id = :courseId")
    public Weight[] getWeightsByCourseId(String courseId);
}
