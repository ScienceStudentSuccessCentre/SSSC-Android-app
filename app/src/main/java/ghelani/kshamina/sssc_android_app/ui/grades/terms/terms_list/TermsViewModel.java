package ghelani.kshamina.sssc_android_app.ui.grades.terms.terms_list;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import ghelani.kshamina.sssc_android_app.entity.Term;
import ghelani.kshamina.sssc_android_app.repository.TermRepository;
import io.reactivex.Completable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TermsViewModel extends ViewModel {


    private TermRepository termRepository;
    public MutableLiveData<TermViewState> termsState = new MutableLiveData();
    public MutableLiveData<Boolean> isDeleteMode = new MutableLiveData(false);

    @Inject
    public TermsViewModel(TermRepository termRepository) {
        super();
        this.termRepository = termRepository;
    }

    public void addTerm(Term term) {
        Completable.fromAction(() -> termRepository.insert(term))
                .subscribeOn(Schedulers.io())
                .subscribe();
        getTerms();
    }

    public void deleteTerm(Term term) {
        Completable.fromAction(() -> termRepository.delete(term))
                .subscribeOn(Schedulers.io())
                .subscribe();
        termsState.getValue().getTerms().remove(term);
        termsState.setValue(new TermViewState(false, false, true, "", termsState.getValue().getTerms()));

    }

    public void getTerms() {
        termRepository.getAllTerms()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Term>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        termsState.setValue(new TermViewState(true, false, false, "", Collections.emptyList()));
                    }

                    @Override
                    public void onSuccess(@NonNull List<Term> terms) {
                        termsState.setValue(new TermViewState(false, false, true, "", terms));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        termsState.setValue(new TermViewState(false, true, true, e.getMessage(), Collections.emptyList()));
                    }
                });
    }

    public int getDeleteModeVisibility() {
        return isDeleteMode.getValue() ? View.VISIBLE : View.GONE;
    }

    public void toggleDeleteMode(){
        isDeleteMode.setValue(!isDeleteMode.getValue());
    }
}

class TermViewState {
    private boolean isLoading;
    private boolean isError;
    private boolean isSuccess;
    private String error;
    private List<Term> terms;

    public TermViewState(boolean isLoading, boolean isError, boolean isSuccess, String error, List<Term> terms) {
        this.isLoading = isLoading;
        this.isError = isError;
        this.isSuccess = isSuccess;
        this.error = error;
        this.terms = terms;
    }

    public boolean isError() {
        return isError;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Term> getTerms() {
        return terms;
    }

    public void setTerms(List<Term> terms) {
        this.terms = terms;
    }
}



