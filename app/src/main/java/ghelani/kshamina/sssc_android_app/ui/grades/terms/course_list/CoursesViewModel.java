package ghelani.kshamina.sssc_android_app.ui.grades.terms.course_list;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import ghelani.kshamina.sssc_android_app.database.CourseDao;
import ghelani.kshamina.sssc_android_app.database.GradesDatabase;
import ghelani.kshamina.sssc_android_app.entity.CourseEntity;
import ghelani.kshamina.sssc_android_app.ui.common.events.ListItemEventListener;
import ghelani.kshamina.sssc_android_app.ui.common.events.SingleLiveEvent;
import ghelani.kshamina.sssc_android_app.ui.common.list.ViewState;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.DiffItem;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.ListItem;
import ghelani.kshamina.sssc_android_app.ui.grades.Grading;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.assignments.AssignmentListFragment;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CoursesViewModel extends ViewModel {

    private CourseDao courseDao;
    public MutableLiveData<ViewState<ListItem>> state = new MutableLiveData<>();
    public boolean isDeleteMode;
    private List<ListItem> courseItemList = new ArrayList<>();
    public MutableLiveData<Double> creditsState = new MutableLiveData<>();
    public MutableLiveData<Double> termGPA = new MutableLiveData<>();
    public final SingleLiveEvent<Fragment> navigationEvent = new SingleLiveEvent<>();

    @Inject
    public CoursesViewModel(GradesDatabase gradesDatabase) {
        super();
        this.courseDao = gradesDatabase.getCourseDao();
        isDeleteMode = false;
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
                        double credits = 0;
                        double gpa = Grading.calculateTermGPA(courses);
                        for (CourseEntity course : courses) {
                            credits += course.courseCredits;
                            courseItemList.add(createListItem(course));
                        }
                        state.setValue(new ViewState<>(false, false, true, "", courseItemList));
                        creditsState.setValue(credits);
                        termGPA.setValue(gpa);
                    }

                    @Override
                    public void onError(Throwable e) {
                        state.setValue(new ViewState<>(false, true, false, e.getMessage(), Collections.emptyList()));
                    }
                });
    }

    private ListItem createListItem(CourseEntity course) {
        return new ListItem(course.courseId, course.courseFinalGrade, course.courseCode, course.courseName, isDeleteMode, new ListItemEventListener() {
            @Override
            public void onItemClicked(String id) {
                for(ListItem item: courseItemList){
                    if(item.getId().equals(id)){
                        navigationEvent.setValue(AssignmentListFragment.newInstance(id,item.getDescriptionText(),item.getHeaderText()));
                    }
                }
            }

            @Override
            public boolean onItemLongClicked(String id) {
                for(ListItem term: courseItemList){
                    if(term.getId().equals(id)){
                        term.setDeleteIconVisible(!term.isDeleteIconVisible());
                    }
                }
                state.setValue(new ViewState<>(false, false, true, "", courseItemList));
                return true;
            }

            @Override
            public void toggleDeleteMode() {

            }

            @Override
            public void deleteItem(String courseId) {
                Completable.fromAction(() -> courseDao.deleteCourse(courseId))
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
        });
    }

    public List<DiffItem> getCourseItems(){
        List<DiffItem> courseItems = new ArrayList<>();
        for(ListItem listItem : courseItemList){
            courseItems.add(listItem);
        }
        return courseItems;
    }

    public boolean isDeleteMode() {
        return isDeleteMode;
    }

    public void setIsDeleteMode(boolean value){
        isDeleteMode = value;
    }
}





