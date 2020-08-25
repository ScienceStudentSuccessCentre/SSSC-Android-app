package ghelani.kshamina.sssc_android_app.dagger;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ghelani.kshamina.sssc_android_app.MainActivity;
import ghelani.kshamina.sssc_android_app.entity.Event;
import ghelani.kshamina.sssc_android_app.ui.event.EventsFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.calculator.CalculatorFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_assignment.AddAssignmentFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_assignment.UpdateAssignmentFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_course.AddCourseFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_course.UpdateCourseFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_term.AddTermFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_term.AddTermViewModel;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.assignments.AssignmentListFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.course_list.CourseListFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.input_form.InputFormFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.required_final_grade.RequiredFinalGradeFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.select_grade.SelectGradeFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.select_weight.SelectWeightFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.terms_list.TermsFragment;
import ghelani.kshamina.sssc_android_app.ui.mentoring.MentorListFragment;

@Module
abstract class FragmentBuilderModule {
    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract MainActivity injectMainActivity();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract TermsFragment injectTermsFragment();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract CourseListFragment injectCourseListFragment();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract AssignmentListFragment injectAssignmentListFragment();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract InputFormFragment injectInputFormFragment();

    @ContributesAndroidInjector()
    abstract CalculatorFragment injectCalculatorFragment();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract EventsFragment injectEventsFragment();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract MentorListFragment injectMentorListFragment();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract AddAssignmentFragment injectAddAssignmentFragment();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract AddTermFragment injectAddTermFragment();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract AddCourseFragment injectAddCourseFragment();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract SelectWeightFragment injectSelectWeightFragment();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract UpdateCourseFragment injectUpdateCourseFragment();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract RequiredFinalGradeFragment injectRequiredFinalGradeFragment();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract SelectGradeFragment injectSelectGradeFragment();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract UpdateAssignmentFragment injectUpdateAssignmentFragment();
}
