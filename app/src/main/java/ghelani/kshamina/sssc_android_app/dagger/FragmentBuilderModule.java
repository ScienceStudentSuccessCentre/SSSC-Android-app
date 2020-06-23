package ghelani.kshamina.sssc_android_app.dagger;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ghelani.kshamina.sssc_android_app.ui.grades.calculator.CalculatorFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_term.AddTermFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.course_list.CourseListFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.dagger.CourseViewModelModule;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.dagger.TermsPresenterModule;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.terms_list.TermsFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.dagger.TermsViewModelModule;

@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector(modules = {TermsViewModelModule.class})
    abstract TermsFragment injectTermsFragment();

    @ContributesAndroidInjector(modules = {TermsPresenterModule.class})
    abstract AddTermFragment injectAddTermFragment();

    @ContributesAndroidInjector(modules = {CourseViewModelModule.class})
    abstract CourseListFragment injectCourseListFragment();

    @ContributesAndroidInjector()
    abstract CalculatorFragment injectCalculatorFragment();

}
