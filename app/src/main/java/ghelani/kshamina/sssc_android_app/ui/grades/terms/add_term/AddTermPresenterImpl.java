package ghelani.kshamina.sssc_android_app.ui.grades.terms.add_term;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import ghelani.kshamina.sssc_android_app.database.GradesDatabase;
import ghelani.kshamina.sssc_android_app.database.TermDao;
import ghelani.kshamina.sssc_android_app.entity.TermEntity;
import io.reactivex.Scheduler;

public class AddTermPresenterImpl implements AddTermContract.Presenter {

    private AddTermContract.View view;
    private Scheduler backgroundScheduler;
    private Scheduler mainScheduler;
    private TermDao termDao;

    private int yearSelected;
    private int seasonSelected;
    private List<String> seasons;
    private List<String> years;

    @Inject
    public AddTermPresenterImpl(AddTermContract.View view,
                                Scheduler backgroundScheduler,
                                Scheduler mainScheduler,
                                GradesDatabase db) {
        this.view = view;
        this.backgroundScheduler = backgroundScheduler;
        this.mainScheduler = mainScheduler;
        termDao = db.getTermDao();

        years = generateYearList();
        seasons = new ArrayList<>(Arrays.asList("Fall", "Summer", "Winter"));
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
    public TermEntity createTerm() {
        return new TermEntity(TermEntity.Season.valueOf(seasons.get(seasonSelected).toUpperCase()), years.get(yearSelected));
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