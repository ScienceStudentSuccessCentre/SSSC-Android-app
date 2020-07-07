package ghelani.kshamina.sssc_android_app.repository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ghelani.kshamina.sssc_android_app.database.GradesDatabase;
import ghelani.kshamina.sssc_android_app.entity.TermEntity;
import ghelani.kshamina.sssc_android_app.model.Term;
import io.reactivex.Single;

/**
 * Abstraction over accessing the Term DAO through the database directly
 * Convert domain models into entities
 */
public class TermRepository {
    private GradesDatabase database;

    @Inject
    public TermRepository(GradesDatabase gradesDatabase) {
        this.database = gradesDatabase;

    }

    public String insert(Term term) {
        TermEntity termEntity = new TermEntity(Term.Season.valueOf(term.getSeason()), term.getYear());
        database.getTermDao().insertTerm(termEntity);
        return termEntity.getTermId();
    }

    public void delete(String termID) {
        database.getTermDao().deleteTerm(termID);
    }

    public Single<List<Term>> getAllTerms() {
        return database.getTermDao().getAllTerms().flatMap(termEntities -> {
            List<Term> termsList = new ArrayList<>();
            for (TermEntity termEntity : termEntities) {
                termsList.add(new Term(termEntity.getTermId(), termEntity.getTermSeason(), termEntity.getTermYear()));
            }
            return Single.just(termsList);
        });
    }

    public Single<Term> getTermById(String id) {
        return database.getTermDao().getTermById(id).flatMap(
                termEntity -> Single.just(new Term(termEntity.getTermId(), termEntity.getTermSeason(), termEntity.getTermYear())));
    }
}
