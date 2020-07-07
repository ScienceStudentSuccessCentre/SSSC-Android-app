package ghelani.kshamina.sssc_android_app.ui.grades.terms.terms_list;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import ghelani.kshamina.sssc_android_app.model.Term;
import ghelani.kshamina.sssc_android_app.repository.TermRepository;
import ghelani.kshamina.sssc_android_app.ui.common.events.ListItemEventListener;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.DiffItem;
import ghelani.kshamina.sssc_android_app.ui.common.list.ViewState;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.ListItem;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TermsViewModel extends ViewModel {

    private TermRepository termRepository;
    public MutableLiveData<ViewState<ListItem>> state = new MutableLiveData<>();
    public MutableLiveData<Term> termSelected = new MutableLiveData<>();
    private boolean isDeleteMode;

    @Inject
    public TermsViewModel(TermRepository termRepository) {
        super();
        this.termRepository = termRepository;
        isDeleteMode = false;
    }

    public void fetchTerms() {
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
                        Collections.sort(terms);
                        Collections.reverse(terms);
                        List<ListItem> items = new ArrayList<>();
                        for(Term term : terms){
                            items.add(createListItem(term));
                        }
                        state.setValue(new ViewState<>(false, false, true, "", items));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        state.setValue(new ViewState<>(false, true, true, e.getMessage(), Collections.emptyList()));
                    }
                });
    }

    private ListItem createListItem(Term term){
        return new ListItem(term.getId(),term.asShortString(),"",term.toString(), isDeleteMode, new ListItemEventListener() {
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
                for(ListItem term: state.getValue().getItems()){
                    if(term.getId().equals(id)){
                        term.setDeleteIconVisible(!term.isDeleteIconVisible());
                    }
                }
                state.setValue(new ViewState<>(false, false, true, "", state.getValue().getItems()));
                return true;
            }

            @Override
            public void toggleDeleteMode() {

            }

            @Override
            public void deleteItem(String id) {
                Completable.fromAction(() -> termRepository.delete(id))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onComplete() {
                                fetchTerms();
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        });
            }
        });
    }

    public List<DiffItem> getTermItems(){
        List<DiffItem> termItems = new ArrayList<>();
        for(ListItem listItem : state.getValue().getItems()){
            termItems.add(listItem);
        }
        return termItems;
    }

    public boolean isDeleteMode() {
        return isDeleteMode;
    }

    public void setDeleteMode(boolean deleteMode) {
        isDeleteMode = deleteMode;
    }
}





