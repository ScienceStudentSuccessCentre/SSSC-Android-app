package ghelani.kshamina.sssc_android_app.ui.grades.terms.terms_list;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import ghelani.kshamina.sssc_android_app.model.Term;
import ghelani.kshamina.sssc_android_app.repository.TermRepository;
import ghelani.kshamina.sssc_android_app.ui.common.list.DiffItem;
import ghelani.kshamina.sssc_android_app.ui.common.list.ViewState;
import ghelani.kshamina.sssc_android_app.ui.common.model.TermItem;
import io.reactivex.Completable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TermsViewModel extends ViewModel {

    private TermRepository termRepository;
    public MutableLiveData<ViewState<Term>> state = new MutableLiveData<>();
    public MutableLiveData<Boolean> isDeleteMode = new MutableLiveData<>(false);
    public MutableLiveData<Term> termSelected = new MutableLiveData<>();

    @Inject
    public TermsViewModel(TermRepository termRepository) {
        super();
        this.termRepository = termRepository;
    }

    public void fetchTerms() {
        termRepository.getAllTerms()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Term>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        state.setValue(new ViewState<Term>(true, false, false, "", Collections.emptyList()));
                    }

                    @Override
                    public void onSuccess(@NonNull List<Term> terms) {
                        for(Term term : terms){
                            System.out.println(term.getId() + ", " + term.getSeason() +" " + term.getYear());
                        }
                        state.setValue(new ViewState<Term>(false, false, true, "", terms));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        state.setValue(new ViewState<Term>(false, true, true, e.getMessage(), Collections.emptyList()));
                    }
                });
    }
    public List<DiffItem> getTermItems(){
        List<DiffItem> termItems = new ArrayList<>();
        for(Term term : state.getValue().getItems()){
            termItems.add(new TermItem(term));
        }
        return termItems;
    }

    public void deleteItem(Term term) {
        Completable.fromAction(() -> termRepository.delete(term))
                .subscribeOn(Schedulers.io())
                .subscribe();
        fetchTerms();
    }

    public void addItem(Term term) {
        Completable.fromAction(() -> termRepository.insert(term))
                .subscribeOn(Schedulers.io())
                .subscribe();
        fetchTerms();
    }

    public void updateItem(Term item) {

    }

    public void onItemClicked(Term item) {
       termSelected.setValue(item);
    }

    public boolean onItemLongClicked(Term item) {
        isDeleteMode.setValue(!isDeleteMode.getValue());
        return true;
    }

}





