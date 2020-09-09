package ghelani.kshamina.sssc_android_app.ui.grades.terms.add_term;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.SavedStateHandle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import ghelani.kshamina.sssc_android_app.database.GradesDatabase;
import ghelani.kshamina.sssc_android_app.database.TermDao;
import ghelani.kshamina.sssc_android_app.entity.TermEntity;
import ghelani.kshamina.sssc_android_app.ui.grades.GradesFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.input_form.InputFormViewModel;
import ghelani.kshamina.sssc_android_app.ui.utils.list.model.DiffItem;
import ghelani.kshamina.sssc_android_app.ui.utils.list.model.SelectionItem;
import ghelani.kshamina.sssc_android_app.ui.utils.list.model.TextItem;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddTermViewModel extends InputFormViewModel {

    private String year;
    private String season;
    private int selectedYearIndex;
    private int selectedSeasonIndex;
    private TermDao termDao;
    private final SavedStateHandle savedStateHandle;

    @ViewModelInject
    public AddTermViewModel(GradesDatabase db, @Assisted SavedStateHandle savedStateHandle) {
        season = "";
        year = "";
        selectedSeasonIndex = -1;
        selectedYearIndex = -1;
        this.termDao = db.getTermDao();
        this.savedStateHandle = savedStateHandle;
    }

    @Override
    protected void createItemsList() {
        List<DiffItem> selectionItems = new ArrayList<>();
        selectionItems.add(new TextItem("SELECT A SEASON"));

        for (String seasonOption : Arrays.asList("Fall", "Winter", "Summer")) {
            selectionItems.add(new SelectionItem(selectionItems.size(), seasonOption, false, index -> {
                season = seasonOption;
                if (selectedSeasonIndex != -1) {
                    ((SelectionItem) items.getValue().get(selectedSeasonIndex)).setSelected(false);
                }
                ((SelectionItem) items.getValue().get(index)).setSelected(true);
                selectedSeasonIndex = index;
                items.setValue(items.getValue());
                checkCreateAvailable();
            }));
        }

        selectionItems.add(new TextItem("SELECT A YEAR"));

        int currentYear = Calendar.getInstance().get(Calendar.YEAR) + 1;
        for (int i = 0; i < 10; i++) {
            int yearOption = currentYear - i;
            selectionItems.add(new SelectionItem(selectionItems.size(), String.valueOf(yearOption), false, index -> {
                year = String.valueOf(yearOption);
                if (selectedYearIndex != -1) {
                    ((SelectionItem) items.getValue().get(selectedYearIndex)).setSelected(false);
                }
                ((SelectionItem) items.getValue().get(index)).setSelected(true);
                selectedYearIndex = index;
                items.setValue(items.getValue());
                checkCreateAvailable();
            }));
        }

        items.setValue(selectionItems);

    }

    private void checkCreateAvailable() {
        if (season.isEmpty() || year.isEmpty()) {
            submitEnabled.setValue(false);
        } else {
            submitEnabled.setValue(true);
        }
    }

    @Override
    public void onSubmit() {
        TermEntity newTerm = new TermEntity(TermEntity.Season.valueOf(season.toUpperCase()), year);
        Completable.fromAction(() -> termDao.insertTerm(newTerm))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        navigationEvent.setValue(new GradesFragment());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
}
