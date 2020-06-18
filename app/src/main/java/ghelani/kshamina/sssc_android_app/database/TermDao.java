package ghelani.kshamina.sssc_android_app.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import ghelani.kshamina.sssc_android_app.entity.Term;
import io.reactivex.Single;

@Dao
public abstract class TermDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertTerm(Term term);

    @Delete
    public abstract void deleteTerm(Term term);

    @Query("SELECT * FROM terms ORDER BY term_year DESC")
    public abstract Single<List<Term>> getAllTerms();

    @Query("SELECT * FROM terms where term_id=:termId")
    abstract Term getTermById(String termId);
}