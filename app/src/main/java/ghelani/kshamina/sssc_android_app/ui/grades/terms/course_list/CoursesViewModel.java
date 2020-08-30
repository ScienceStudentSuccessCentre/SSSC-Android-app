package ghelani.kshamina.sssc_android_app.ui.grades.terms.course_list;

import androidx.fragment.app.Fragment;
import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ghelani.kshamina.sssc_android_app.database.CourseDao;
import ghelani.kshamina.sssc_android_app.database.GradesDatabase;
import ghelani.kshamina.sssc_android_app.entity.CourseEntity;
import ghelani.kshamina.sssc_android_app.ui.grades.Grading;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.assignments.AssignmentListFragment;
import ghelani.kshamina.sssc_android_app.ui.utils.events.EventListener;
import ghelani.kshamina.sssc_android_app.ui.utils.events.SingleLiveEvent;
import ghelani.kshamina.sssc_android_app.ui.utils.list.ViewState;
import ghelani.kshamina.sssc_android_app.ui.utils.list.model.DiffItem;
import ghelani.kshamina.sssc_android_app.ui.utils.list.model.ListItem;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CoursesViewModel extends ViewModel {

    private CourseDao courseDao;
    public MutableLiveData<ViewState<DiffItem>> state = new MutableLiveData<>();
    public boolean isDeleteMode;
    private List<CourseEntity> courseItemList = new ArrayList<>();
    public MutableLiveData<Double> creditsState = new MutableLiveData<>();
    public MutableLiveData<Double> termGPA = new MutableLiveData<>();
    public final SingleLiveEvent<Fragment> navigationEvent = new SingleLiveEvent<>();
    private final SavedStateHandle savedStateHandle;

    @ViewModelInject
    public CoursesViewModel(GradesDatabase gradesDatabase, @Assisted SavedStateHandle savedStateHandle) {
        super();
        this.courseDao = gradesDatabase.getCourseDao();
        isDeleteMode = false;
        this.savedStateHandle = savedStateHandle;
    }

    public void fetchCoursesByTermId(String termId) {
        courseItemList.clear();
        courseDao.getCoursesByTermId(termId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<CourseEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        state.setValue(new ViewState<>(true, false, false, "", Collections.emptyList()));
                    }

                    @Override
                    public void onSuccess(List<CourseEntity> courses) {
                        courseItemList = courses;
                        double credits = 0;
                        double gpa = Grading.calculateTermGPA(courses);
                        List<DiffItem> items = new ArrayList<>();
                        for (CourseEntity course : courses) {
                            credits += course.courseCredits;
                            items.add(createListItem(course));
                        }
                        state.setValue(new ViewState<>(false, false, true, "", items));
                        creditsState.setValue(credits);
                        termGPA.setValue(gpa);
                    }

                    @Override
                    public void onError(Throwable e) {
                        state.setValue(new ViewState<>(false, true, false, e.getMessage(), Collections.emptyList()));
                    }
                });
    }

    private DiffItem createListItem(CourseEntity course) {
        return new ListItem(course.courseId, course.courseFinalGrade, course.courseCode, course.courseName, isDeleteMode, new EventListener.ListItemEventListener() {
            @Override
            public void onItemClicked(String id) {
                navigationEvent.setValue(AssignmentListFragment.newInstance(id));
            }

            @Override
            public boolean onItemLongClicked(int index) {
                ListItem item = ((ListItem)state.getValue().getItems().get(index));
                item.setDeleteIconVisible(!item.isDeleteIconVisible());
                state.setValue(state.getValue());
                return true;
            }

            @Override
            public void deleteItem(int index) {
                deleteCourse(index);
            }
        });
    }

    public void deleteCourse(int index) {
        CourseEntity course = courseItemList.get(index);
        Completable.fromAction(() -> courseDao.deleteCourse(course.courseId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        fetchCoursesByTermId(course.courseTermId);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
}





