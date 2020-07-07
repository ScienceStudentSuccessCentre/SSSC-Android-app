package ghelani.kshamina.sssc_android_app.ui.grades.terms.dagger;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import ghelani.kshamina.sssc_android_app.repository.CourseRepository;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_course.AddCourseContract;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_course.AddCourseFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_course.AddCoursePresenterImpl;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


@Module
public abstract class CoursePresenterModule {
    @Binds
    abstract AddCourseContract.View bindAddCourseView(AddCourseFragment fragment);

    @Provides
    public static AddCourseContract.Presenter providesAddCoursePresenter(AddCourseContract.View view, CourseRepository courseRepository) {
        return new AddCoursePresenterImpl(view, Schedulers.io(), AndroidSchedulers.mainThread(), courseRepository);
    }
}
