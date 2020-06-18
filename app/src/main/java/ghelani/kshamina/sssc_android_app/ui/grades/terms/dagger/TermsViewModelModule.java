package ghelani.kshamina.sssc_android_app.ui.grades.terms.dagger;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import ghelani.kshamina.sssc_android_app.dagger.ViewModelKey;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.terms_list.TermsViewModel;

@Module
public abstract class TermsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(TermsViewModel.class)
    public abstract ViewModel bindTermsViewModel(TermsViewModel viewModel);

}
