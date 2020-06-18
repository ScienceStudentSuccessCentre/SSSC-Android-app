package ghelani.kshamina.sssc_android_app.ui.grades.terms.add_term;

public interface ListClickListener {
    int YEAR = 0;
    int SEASON = 1;

    void onItemSelected(int position, int type);
}
