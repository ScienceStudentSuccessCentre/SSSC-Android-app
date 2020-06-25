package ghelani.kshamina.sssc_android_app.ui.grades.terms.terms_list;

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
    private List<ListItem> termsList = new ArrayList<>();

    @Inject
    public TermsViewModel(TermRepository termRepository) {
        super();
        this.termRepository = termRepository;
    }

    public void fetchTerms() {
        termsList.clear();
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
                        for(Term term : terms){
                           // System.out.println(term.getId() + ", " + term.getSeason() +" " + term.getYear());
                            termsList.add(createListItem(term));
                        }
                        state.setValue(new ViewState<>(false, false, true, "", termsList));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        state.setValue(new ViewState<>(false, true, true, e.getMessage(), Collections.emptyList()));
                    }
                });
    }

    private ListItem createListItem(Term term){
        return new ListItem(term.getId(),term.asShortString(),"",term.toString(), false, new ItemClickListener() {
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
                //isDeleteMode.setValue(!isDeleteMode.getValue());
                for(ListItem term: termsList){
                    if(term.getId().equals(id)){
                        term.setDeleteIconVisible(!term.isDeleteIconVisible());
                    }
                }
                state.setValue(new ViewState<>(false, false, true, "", termsList));
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
        for(ListItem listItem : termsList){
            termItems.add(listItem);
        }
        return termItems;
    }
    /*
    public List<DiffItem> getTermItems(){
        List<DiffItem> termItems = new ArrayList<>();
        for(Term term : state.getValue().getItems()){
            DiffItem item = new ListItem(term.getId(),term.asShortString(),"",term.toString(), false, new ItemClickListener() {
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
                    //isDeleteMode.setValue(!isDeleteMode.getValue());
                    for(ListItem term: termsList){
                        if(term.getId().equals(id)){
                            term.setDeleteIconVisible(!term.isDeleteIconVisible());
                        }
                    }

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
            });
            termItems.add(item);
            termsList.add((ListItem) item);
        }
        return termItems;
    }
    */

}





