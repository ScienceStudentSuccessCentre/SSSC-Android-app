package ghelani.kshamina.sssc_android_app.dagger;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ghelani.kshamina.sssc_android_app.ui.grades.calculator.CalculatorFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_assignment.AddAssignmentFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_course.AddCourseFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_term.AddTermFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.assignments.AssignmentListFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.course_list.CourseListFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.terms_list.TermsFragment;

@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract TermsFragment injectTermsFragment();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract AddTermFragment injectAddTermFragment();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract CourseListFragment injectCourseListFragment();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract AddCourseFragment injectAddCourseFragment();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract AssignmentListFragment injectAssignmentListFragment();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract AddAssignmentFragment injectAddAssignmentListFragment();

    @ContributesAndroidInjector()
    abstract CalculatorFragment injectCalculatorFragment();

}
