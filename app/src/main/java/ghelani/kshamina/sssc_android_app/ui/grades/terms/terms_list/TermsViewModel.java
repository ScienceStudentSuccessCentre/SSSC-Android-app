package ghelani.kshamina.sssc_android_app.ui.grades.terms.terms_list;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import ghelani.kshamina.sssc_android_app.database.GradesDatabase;
import ghelani.kshamina.sssc_android_app.database.TermDao;
import ghelani.kshamina.sssc_android_app.entity.TermEntity;
import ghelani.kshamina.sssc_android_app.ui.common.events.EventListener;
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

    private TermDao termDao;
    public MutableLiveData<ViewState<DiffItem>> state = new MutableLiveData<>();
    public MutableLiveData<TermEntity> termSelected = new MutableLiveData<>();
    private boolean isDeleteMode;

    @Inject
    public TermsViewModel(GradesDatabase db) {
        super();
        this.termDao = db.getTermDao();
        isDeleteMode = false;
    }

    public void fetchTerms() {
        termDao.getAllTerms()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<TermEntity>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        state.setValue(new ViewState<>(true, false, false, "", Collections.emptyList()));
                    }

                    @Override
                    public void onSuccess(@NonNull List<TermEntity> terms) {
                        Collections.sort(terms);
                        Collections.reverse(terms);
                        List<DiffItem> items = new ArrayList<>();
                        for(TermEntity term : terms){
                            ListItem displayableItem = createListItem(term);
                            items.add(displayableItem);
                        }
                        state.setValue(new ViewState<>(false, false, true, "", items));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        state.setValue(new ViewState<>(false, true, true, e.getMessage(), Collections.emptyList()));
                    }
                });
    }

    private ListItem createListItem(TermEntity term){
        return new ListItem(term.getTermId(),term.asShortString(),"",term.toString(), isDeleteMode, new EventListener.ListItemEventListener() {
            @Override
            public void onItemClicked(String id) {
                termDao.getTermById(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<TermEntity>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onSuccess(TermEntity term) {
                                termSelected.setValue(term);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        });
            }

            @Override
            public boolean onItemLongClicked() {
                setDeleteMode(!isDeleteMode);
                fetchTerms();
                return true;
            }

            @Override
            public void deleteItem(String id) {
                Completable.fromAction(() -> termDao.deleteTerm(id))
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
        return state.getValue().getItems();

    }

    public void toggleDeleteMode(){
        setDeleteMode(!isDeleteMode);
        fetchTerms();
    }

    public boolean isDeleteMode() {
        return isDeleteMode;
    }

    public void setDeleteMode(boolean deleteMode) {
        isDeleteMode = deleteMode;
    }
}

