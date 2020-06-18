package ghelani.kshamina.sssc_android_app.repository;

import java.util.List;

import javax.inject.Inject;

import ghelani.kshamina.sssc_android_app.database.GradesDatabase;
import ghelani.kshamina.sssc_android_app.entity.Term;
import io.reactivex.Single;


public class TermRepository {
    private  GradesDatabase database;

    @Inject
    public TermRepository(GradesDatabase gradesDatabase) {
         this.database = gradesDatabase;

    }

    public void insert(Term term){
        database.getTermDao().insertTerm(term);
    }

    public void delete(Term term){
        database.getTermDao().deleteTerm(term);
    }

    public Single<List<Term>> getAllTerms(){

        return database.getTermDao().getAllTerms();
    }
}
