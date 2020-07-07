package ghelani.kshamina.sssc_android_app.ui.grades.terms.dagger;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import ghelani.kshamina.sssc_android_app.repository.TermRepository;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_term.AddTermContract;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_term.AddTermFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_term.AddTermPresenterImpl;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public abstract class TermsPresenterModule {
    @Binds
    abstract AddTermContract.View bindUserView(AddTermFragment fragment);

    @Provides
    public static AddTermContract.Presenter providesUserPresenter(AddTermContract.View view, TermRepository termRepository) {
        return new AddTermPresenterImpl(view, Schedulers.io(), AndroidSchedulers.mainThread(), termRepository);
    }
}
