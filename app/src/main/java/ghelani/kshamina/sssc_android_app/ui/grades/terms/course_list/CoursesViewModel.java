package ghelani.kshamina.sssc_android_app.ui.grades.terms.course_list;

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
import ghelani.kshamina.sssc_android_app.ui.grades.Grading;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CoursesViewModel extends ViewModel {

    private CourseRepository courseRepository;
    public MutableLiveData<ViewState<ListItem>> state = new MutableLiveData<>();
    public boolean isDeleteMode;
    private List<ListItem> courseItemList = new ArrayList<>();
    public MutableLiveData<String> courseSelected = new MutableLiveData<>();
    public MutableLiveData<Double> creditsState = new MutableLiveData<>();
    public MutableLiveData<Double> termGPA = new MutableLiveData<>();

    @Inject
    public CoursesViewModel(CourseRepository courseRepository) {
        super();
        this.courseRepository = courseRepository;
        isDeleteMode = false;
    }

    public void fetchCoursesByTermId(String termId) {
        courseItemList.clear();
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
                        double credits = 0;
                        double gpa = Grading.calculateTermGPA(courses);
                        for (Course course : courses) {
                            credits += course.getCourseCredits();
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

    private ListItem createListItem(Course course) {
        return new ListItem(course.getCourseId(), course.getCourseFinalGrade(), course.getCourseCode(), course.getCourseName(), isDeleteMode, new ItemClickListener() {
            @Override
            public void onItemClicked(String id) {
                courseSelected.setValue(id);
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
                Completable.fromAction(() -> courseRepository.deleteCourse(courseId))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onComplete() {
                                fetchCoursesByTermId(course.getCourseTermId());
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





