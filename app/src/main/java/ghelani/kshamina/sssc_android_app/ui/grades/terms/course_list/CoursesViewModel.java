package ghelani.kshamina.sssc_android_app.ui.grades.terms.course_list;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import ghelani.kshamina.sssc_android_app.model.Course;
import ghelani.kshamina.sssc_android_app.repository.CourseRepository;
import ghelani.kshamina.sssc_android_app.ui.common.events.ItemClickListener;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.DiffItem;
import ghelani.kshamina.sssc_android_app.ui.common.list.ViewState;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.ListItem;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CoursesViewModel extends ViewModel {

    private CourseRepository courseRepository;
    public MutableLiveData<ViewState<Course>> state = new MutableLiveData<>();
    public MutableLiveData<Boolean> isDeleteMode = new MutableLiveData<>(false);
    public MutableLiveData<Course> courseSelected = new MutableLiveData<>();

    @Inject
    public CoursesViewModel(CourseRepository courseRepository) {
        super();
        this.courseRepository = courseRepository;
    }

    public void fetchCoursesByTermId(String termId) {
        courseRepository.getCoursesByTermId(termId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Course>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        state.setValue(new ViewState<>(true, false, false, "", Collections.emptyList()));
                    }

                    @Override
                    public void onSuccess(List<Course> courses) {
                        state.setValue(new ViewState<Course>(false, false, true, "", courses));
                    }

                    @Override
                    public void onError(Throwable e) {
                        state.setValue(new ViewState<>(false, true, false, e.getMessage(), Collections.emptyList()));
                    }
                });
    }

    public List<DiffItem> getCourseItems() {
        List<DiffItem> listItems = new ArrayList<>();
        for (Course course : state.getValue().getItems()) {
            listItems.add(new ListItem(course.getCourseId(), course.getCourseFinalGrade(), course.getCourseCode(), course.getCourseName(), View.GONE, new ItemClickListener() {
                @Override
                public void onItemClicked(String id) {

                }

                @Override
                public boolean onItemLongClicked(String id) {

                    return false;
                }

                @Override
                public void toggleDeleteMode() {

                }

                @Override
                public void deleteItem(String id) {

                }
            }));
        }
        return listItems;
    }
}





