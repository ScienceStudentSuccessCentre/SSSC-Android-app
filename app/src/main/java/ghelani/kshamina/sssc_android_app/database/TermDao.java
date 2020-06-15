package ghelani.kshamina.sssc_android_app.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import ghelani.kshamina.sssc_android_app.entity.Term;

@Dao
public interface TermDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTerm(Term term);

    @Delete
    void deleteTerm(Term term);

    @Query("SELECT * FROM term")
    List<Term> getAllTerms();

    @Query("SELECT * FROM term where term_id=:termId")
    Term getTermById(String termId);
}