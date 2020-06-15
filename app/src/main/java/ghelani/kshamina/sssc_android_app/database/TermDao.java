package ghelani.kshamina.sssc_android_app.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

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