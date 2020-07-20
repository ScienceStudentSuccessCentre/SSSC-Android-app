package ghelani.kshamina.sssc_android_app.dagger;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_assignment.AddAssignmentViewModel;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_assignment.WeightViewModel;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_course.AddCourseViewModel;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_course.FinalGradeViewModel;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_term.AddTermViewModel;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.assignments.AssignmentViewModel;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.course_list.CoursesViewModel;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.terms_list.TermsViewModel;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AssignmentViewModel.class)
    public abstract ViewModel bindAssignmentViewModel(AssignmentViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AddAssignmentViewModel.class)
    public abstract ViewModel bindAddAssignmentViewModel(AddAssignmentViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CoursesViewModel.class)
    public abstract ViewModel bindCourseViewModel(CoursesViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AddCourseViewModel.class)
    public abstract ViewModel bindAddCourseViewModel(AddCourseViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TermsViewModel.class)
    public abstract ViewModel bindTermsViewModel(TermsViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AddTermViewModel.class)
    public abstract ViewModel bindAddTermViewModel(AddTermViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(FinalGradeViewModel.class)
    public abstract ViewModel bindFinalGradeViewModel(FinalGradeViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(WeightViewModel.class)
    public abstract ViewModel bindWeightViewModel(WeightViewModel viewModel);

}
