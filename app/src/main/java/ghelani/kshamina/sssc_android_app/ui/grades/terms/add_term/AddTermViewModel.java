package ghelani.kshamina.sssc_android_app.ui.grades.terms.add_term;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import ghelani.kshamina.sssc_android_app.database.GradesDatabase;
import ghelani.kshamina.sssc_android_app.database.TermDao;
import ghelani.kshamina.sssc_android_app.entity.TermEntity;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.DiffItem;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.SelectionItem;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.TextItem;
import ghelani.kshamina.sssc_android_app.ui.grades.GradesFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.input_form.InputFormViewModel;
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

    @Inject
    public AddTermViewModel(GradesDatabase db) {
        createItemsList();
        season = "";
        year = "";
        selectedSeasonIndex = -1;
        selectedYearIndex = -1;
        this.termDao = db.getTermDao();
    }

    private void createItemsList() {
        List<DiffItem> selectionItems = new ArrayList<>();
        selectionItems.add(new TextItem("SELECT A TERM"));

        for (String seasonOption : Arrays.asList("Fall", "Summer", "Winter")) {
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

        selectionItems.add(new TextItem("SELECT A SEASON"));

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
            createEnabled.setValue(false);
        } else {
            createEnabled.setValue(true);
        }
    }

    @Override
    public void onCreate() {
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

    @Override
    public void setId(String id) {

    }
}
