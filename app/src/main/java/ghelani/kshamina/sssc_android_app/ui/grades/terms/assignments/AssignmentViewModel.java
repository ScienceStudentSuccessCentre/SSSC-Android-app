package ghelani.kshamina.sssc_android_app.ui.grades.terms.assignments;

import android.os.AsyncTask;

import androidx.fragment.app.Fragment;
import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import ghelani.kshamina.sssc_android_app.database.AssignmentDao;
import ghelani.kshamina.sssc_android_app.database.CourseDao;
import ghelani.kshamina.sssc_android_app.database.GradesDatabase;
import ghelani.kshamina.sssc_android_app.entity.AssignmentWithWeight;
import ghelani.kshamina.sssc_android_app.entity.CourseEntity;
import ghelani.kshamina.sssc_android_app.entity.CourseWithAssignmentsAndWeights;
import ghelani.kshamina.sssc_android_app.ui.grades.Grading;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_assignment.UpdateAssignmentFragment;
import ghelani.kshamina.sssc_android_app.ui.utils.events.EventListener;
import ghelani.kshamina.sssc_android_app.ui.utils.events.SingleLiveEvent;
import ghelani.kshamina.sssc_android_app.ui.utils.list.ViewState;
import ghelani.kshamina.sssc_android_app.ui.utils.list.model.DiffItem;
import ghelani.kshamina.sssc_android_app.ui.utils.list.model.ListItem;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AssignmentViewModel extends ViewModel {

    private AssignmentDao assignmentDao;
    private CourseDao courseDao;
    private MutableLiveData<ViewState<DiffItem>> state = new MutableLiveData<>();
    private MutableLiveData<String> courseGradeText = new MutableLiveData<>();
    private SingleLiveEvent<Fragment> navigationEvent = new SingleLiveEvent<>();
    private CourseWithAssignmentsAndWeights course;
    private boolean deleteMode;
    private final SavedStateHandle savedStateHandle;

    @ViewModelInject
    public AssignmentViewModel(GradesDatabase gradesDatabase, @Assisted SavedStateHandle savedStateHandle) {
        super();
        this.assignmentDao = gradesDatabase.getAssignmentDao();
        this.courseDao = gradesDatabase.getCourseDao();
        this.savedStateHandle = savedStateHandle;
    }

    public void fetchCourseAssignments(String courseID) {

        courseDao.getCourseWithWeightsByID(courseID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CourseWithAssignmentsAndWeights>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(CourseWithAssignmentsAndWeights courseData) {
                        course = courseData;
                        List<DiffItem> assignmentItems = new ArrayList<>();
                        for (AssignmentWithWeight assignment : courseData.assignments) {
                            assignmentItems.add(createListItem(assignment));
                        }
                        calculateCourseGrade();
                        state.setValue(new ViewState<>(false, false, true, "", assignmentItems));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    private void calculateCourseGrade() {
        if (!course.course.courseFinalGrade.isEmpty()) {
            courseGradeText.setValue(course.course.courseFinalGrade);
            return;
        }
        double percentage = course.calculateGradePercentage();
        if (percentage == -1) {
            courseGradeText.setValue("N/A");
            return;
        }
        courseGradeText.setValue(percentage + "%");
    }

    private ListItem createListItem(AssignmentWithWeight assignment) {
        int percentage = (int) ((assignment.getAssignment().assignmentGradeEarned / assignment.getAssignment().assignmentGradeTotal) * 100);
        return new ListItem(assignment.getAssignment().assignmentId, Grading.gradeToLetter.floorEntry(percentage).getValue(),
                assignment.getAssignment().assignmentName, percentage + "%",
                deleteMode, new EventListener.ListItemEventListener() {
            @Override
            public void onItemClicked(String id) {
                //navigationEvent.setValue(InputFormFragment.newInstance(id, InputFormFragment.FormType.UPDATE_ASSIGNMENT.toString()));
                navigationEvent.setValue(UpdateAssignmentFragment.newInstance(assignment));
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
                deleteAssignment(index);
            }
        });
    }

    public void deleteAssignment(int index) {
        AsyncTask.execute(() -> {
            assignmentDao.deleteAssignment(course.assignments.get(index).getAssignment().assignmentId);
            fetchCourseAssignments(course.course.courseId);
        });
    }

    public SingleLiveEvent<Fragment> getNavigationEvent() {
        return navigationEvent;
    }

    public LiveData<ViewState<DiffItem>> getState() {
        return state;
    }

    public LiveData<String> getCourseGrade() {
        return courseGradeText;
    }

    public boolean isDeleteMode() {
        return deleteMode;
    }

    public void setDeleteMode(boolean deleteMode) {
        this.deleteMode = deleteMode;
    }

    public CourseEntity getCourse() {
        return course.course;
    }

    public CourseWithAssignmentsAndWeights getCourseData() {
        return course;
    }

    public boolean assignmentWeightsAvailable() {
        return !course.weight.isEmpty();
    }
}
