package ghelani.kshamina.sssc_android_app.dagger;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import ghelani.kshamina.sssc_android_app.database.GradesDatabase;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_assignment.AddAssignmentViewModel;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_course.AddCourseContract;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_course.AddCourseFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_course.AddCoursePresenterImpl;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_term.AddTermContract;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_term.AddTermFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_term.AddTermPresenterImpl;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.assignments.AssignmentViewModel;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.course_list.CoursesViewModel;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.terms_list.TermsViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
    abstract AddCourseContract.View bindAddCourseView(AddCourseFragment fragment);

    @Provides
    public static AddCourseContract.Presenter providesAddCoursePresenter(AddCourseContract.View view, GradesDatabase gradesDatabase) {
        return new AddCoursePresenterImpl(view, Schedulers.io(), AndroidSchedulers.mainThread(), gradesDatabase);
    }

    @Binds
    @IntoMap
    @ViewModelKey(CoursesViewModel.class)
    public abstract ViewModel bindCourseViewModel(CoursesViewModel viewModel);

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
