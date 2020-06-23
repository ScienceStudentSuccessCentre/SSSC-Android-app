package ghelani.kshamina.sssc_android_app.ui.grades.terms.dagger;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import ghelani.kshamina.sssc_android_app.dagger.ViewModelKey;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.course_list.CoursesViewModel;

@Module
public abstract class CourseViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CoursesViewModel.class)
    public abstract ViewModel bindCourseViewModel(CoursesViewModel viewModel);
}
