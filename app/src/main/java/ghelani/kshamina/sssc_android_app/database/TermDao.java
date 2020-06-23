package ghelani.kshamina.sssc_android_app.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import ghelani.kshamina.sssc_android_app.entity.TermEntity;
import io.reactivex.Single;

@Dao
public abstract class TermDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertTerm(TermEntity term);

    @Query ("DELETE FROM terms where term_id = :termId")
    public abstract void deleteTerm(String termId);

    @Query("SELECT * FROM terms ORDER BY term_year DESC")
    public abstract Single<List<TermEntity>> getAllTerms();

    @Query("SELECT * FROM terms where term_id=:termId")
    public abstract Single<TermEntity> getTermById(String termId);
}