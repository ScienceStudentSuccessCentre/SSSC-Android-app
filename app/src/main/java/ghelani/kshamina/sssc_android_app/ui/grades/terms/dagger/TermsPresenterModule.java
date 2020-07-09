package ghelani.kshamina.sssc_android_app.ui.grades.terms.dagger;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import ghelani.kshamina.sssc_android_app.dagger.ViewModelKey;
import ghelani.kshamina.sssc_android_app.database.GradesDatabase;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_term.AddTermContract;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_term.AddTermFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_term.AddTermPresenterImpl;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.terms_list.TermsViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public abstract class TermsPresenterModule {

    @Binds
    @IntoMap
    @ViewModelKey(TermsViewModel.class)
    public abstract ViewModel bindTermsViewModel(TermsViewModel viewModel);


    @Binds
    abstract AddTermContract.View bindUserView(AddTermFragment fragment);

    @Provides
    public static AddTermContract.Presenter providesUserPresenter(AddTermContract.View view, GradesDatabase gradesDatabase) {
        return new AddTermPresenterImpl(view, Schedulers.io(), AndroidSchedulers.mainThread(), gradesDatabase);
    }
}
