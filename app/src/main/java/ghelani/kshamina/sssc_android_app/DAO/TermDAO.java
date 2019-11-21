package ghelani.kshamina.sssc_android_app.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import ghelani.kshamina.sssc_android_app.entity.Term;

@Dao
public interface TermDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertTerm(Term term);

    @Delete
    public void deleteTerm(Term term);

    @Query("SELECT * FROM term")
    public Term[] getAllTerms();
}