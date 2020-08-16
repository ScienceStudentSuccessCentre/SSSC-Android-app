package ghelani.kshamina.sssc_android_app.dagger;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import ghelani.kshamina.sssc_android_app.FeaturesViewModel;
import ghelani.kshamina.sssc_android_app.ui.event.EventListViewModel;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_assignment.AddAssignmentViewModel;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_assignment.SelectWeightViewModel;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_course.AddCourseViewModel;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_course.SelectFinalGradeViewModel;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_term.AddTermViewModel;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.assignments.AssignmentViewModel;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.assignments.RequiredFinalGradeViewModel;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.course_list.CoursesViewModel;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.terms_list.TermsViewModel;
import ghelani.kshamina.sssc_android_app.ui.mentoring.MentorListViewModel;

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
    @ViewModelKey(SelectFinalGradeViewModel.class)
    public abstract ViewModel bindFinalGradeViewModel(SelectFinalGradeViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SelectWeightViewModel.class)
    public abstract ViewModel bindWeightViewModel(SelectWeightViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RequiredFinalGradeViewModel.class)
    public abstract ViewModel bindRequiredFinalGradeViewModel(RequiredFinalGradeViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MentorListViewModel.class)
    public abstract ViewModel bindMentorListViewModel(MentorListViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(EventListViewModel.class)
    public abstract ViewModel bindEventListViewModel(EventListViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(FeaturesViewModel.class)
    public abstract ViewModel bindFeaturesViewModel(FeaturesViewModel viewModel);

}
