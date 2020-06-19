package ghelani.kshamina.sssc_android_app.ui.grades.terms.add_term;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import ghelani.kshamina.sssc_android_app.model.Term;
import ghelani.kshamina.sssc_android_app.repository.TermRepository;
import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class AddTermPresenterImpl implements AddTermContract.Presenter {

    private AddTermContract.View view;
    private Scheduler backgroundScheduler;
    private Scheduler mainScheduler;
    private TermRepository termRepository;


    private int yearSelected;
    private int seasonSelected;
    private List<String> seasons;
    private List<String> years;

    @Inject
    public AddTermPresenterImpl(AddTermContract.View view,
                                Scheduler backgroundScheduler,
                                Scheduler mainScheduler,
                                TermRepository termRepository) {
        this.view = view;
        this.backgroundScheduler = backgroundScheduler;
        this.mainScheduler = mainScheduler;
        this.termRepository = termRepository;

        years = generateYearList();
        seasons = new ArrayList<>(Arrays.asList("FALL", "SUMMER", "WINTER"));
        yearSelected = -1;
        seasonSelected = -1;
    }

    @Override
    public void getOptions() {
        view.displaySeasons(seasons, seasonSelected);
        view.displayYears(years, yearSelected);
    }

    @Override
    public void onItemSelected(int position, int type) {
        switch (type) {
            case SEASON:
                seasonSelected = position;
                view.displaySeasons(seasons, seasonSelected);
                break;
            case YEAR:
                yearSelected = position;
                view.displayYears(years, yearSelected);
                break;

        }
        view.setCreateButtonEnabled(seasonSelected != -1 && yearSelected != -1);
    }

    @Override
    public void onCancel() {
        view.navigateToTermsPage();
    }

    @Override
    public void onCreate() {
        Term newTerm = new Term(seasons.get(seasonSelected), years.get(yearSelected));
        Completable.fromAction(() -> termRepository.insert(newTerm))
                .subscribeOn(Schedulers.io())
                .observeOn(mainScheduler)
                .subscribe();
        view.navigateToTermsPage();
    }

    private List<String> generateYearList() {
        List<String> years = new ArrayList<>();
        int year = Calendar.getInstance().get(Calendar.YEAR) + 1;
        for (int i = 0; i < 10; i++) {
            years.add(String.valueOf(year - i));
        }
        return years;
    }
}
