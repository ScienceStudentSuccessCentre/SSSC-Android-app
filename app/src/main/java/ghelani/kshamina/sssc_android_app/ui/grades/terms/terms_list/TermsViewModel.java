package ghelani.kshamina.sssc_android_app.ui.grades.terms.terms_list;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import ghelani.kshamina.sssc_android_app.model.Term;
import ghelani.kshamina.sssc_android_app.repository.TermRepository;
import ghelani.kshamina.sssc_android_app.ui.common.list.ListViewModel;
import ghelani.kshamina.sssc_android_app.ui.common.list.ViewState;
import io.reactivex.Completable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TermsViewModel extends ListViewModel<Term> {

    private TermRepository termRepository;


    @Inject
    public TermsViewModel(TermRepository termRepository) {
        super();
        this.termRepository = termRepository;
    }
    @Override
    public void addItem(Term term) {
        Completable.fromAction(() -> termRepository.insert(term))
                .subscribeOn(Schedulers.io())
                .subscribe();
        fetchItems();
    }

    @Override
    public void deleteItem(Term term) {
        Completable.fromAction(() -> termRepository.delete(term))
                .subscribeOn(Schedulers.io())
                .subscribe();
        fetchItems();
    }
    @Override
    public void fetchItems() {
        termRepository.getAllTerms()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Term>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        state.setValue(new ViewState<>(true, false, false, "", Collections.emptyList()));
                    }

                    @Override
                    public void onSuccess(@NonNull List<Term> terms) {
                        state.setValue(new ViewState(false, false, true, "", terms));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        state.setValue(new ViewState(false, true, true, e.getMessage(), Collections.emptyList()));
                    }
                });
    }


    @Override
    public void updateItem(Term item) {

    }

    @Override
    public void onItemClicked(Term item) {

    }
}





