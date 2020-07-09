package ghelani.kshamina.sssc_android_app.ui.grades.terms.add_term;

import java.util.List;

import ghelani.kshamina.sssc_android_app.entity.TermEntity;

/**
 * Using MVP architecture this is the contract
 * of available methods visible for the View and Presenter
 */

public interface AddTermContract {
    interface View {
        void navigateToTermsPage();

        void setCreateButtonEnabled(boolean isEnabled);

        void displaySeasons(List<String> options, int selected);

        void displayYears(List<String> options, int selected);
    }

    interface Presenter {
        int YEAR = 0;
        int SEASON = 1;

        void getOptions();

        void onItemSelected(int position, int type);

        TermEntity createTerm();
    }
}
