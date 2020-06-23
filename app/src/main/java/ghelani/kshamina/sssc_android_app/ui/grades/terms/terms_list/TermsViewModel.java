package ghelani.kshamina.sssc_android_app.ui.grades.terms.terms_list;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import ghelani.kshamina.sssc_android_app.model.Term;
import ghelani.kshamina.sssc_android_app.repository.TermRepository;
import ghelani.kshamina.sssc_android_app.ui.common.events.ItemClickListener;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.DiffItem;
import ghelani.kshamina.sssc_android_app.ui.common.list.ViewState;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.ListItem;
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
            termItems.add(new ListItem(term.getId(),term.asShortString(),"",term.toString(), View.GONE, new ItemClickListener() {

                @Override
                public void onItemClicked(String id) {
                    termRepository.getTermById(id)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new SingleObserver<Term>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onSuccess(Term term) {
                                    termSelected.setValue(term);
                                }

                                @Override
                                public void onError(Throwable e) {

                                }
                            });
                }

                @Override
                public boolean onItemLongClicked(String id) {
                    isDeleteMode.setValue(!isDeleteMode.getValue());
                    return true;
                }

                @Override
                public void toggleDeleteMode() {

                }

                @Override
                public void deleteItem(String id) {
                    Completable.fromAction(() -> termRepository.delete(id))
                            .subscribeOn(Schedulers.io())
                            .subscribe();
                    fetchTerms();
                }
            }));
        }
        return termItems;
    }
}





