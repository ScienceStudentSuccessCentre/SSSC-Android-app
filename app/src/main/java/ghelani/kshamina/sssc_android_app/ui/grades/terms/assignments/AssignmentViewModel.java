package ghelani.kshamina.sssc_android_app.ui.grades.terms.assignments;

import android.os.AsyncTask;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ghelani.kshamina.sssc_android_app.database.AssignmentDao;
import ghelani.kshamina.sssc_android_app.database.CourseDao;
import ghelani.kshamina.sssc_android_app.database.GradesDatabase;
import ghelani.kshamina.sssc_android_app.database.WeightDao;
import ghelani.kshamina.sssc_android_app.entity.Assignment;
import ghelani.kshamina.sssc_android_app.entity.AssignmentWithWeight;
import ghelani.kshamina.sssc_android_app.entity.CourseWithAssignmentsAndWeights;
import ghelani.kshamina.sssc_android_app.entity.Weight;
import ghelani.kshamina.sssc_android_app.ui.common.events.ListItemEventListener;
import ghelani.kshamina.sssc_android_app.ui.common.events.SingleLiveEvent;
import ghelani.kshamina.sssc_android_app.ui.common.list.ViewState;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.ListItem;
import ghelani.kshamina.sssc_android_app.ui.grades.GradesFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.Grading;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.input_form.InputFormFragment;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AssignmentViewModel extends ViewModel {

    private AssignmentDao assignmentDao;
    private CourseDao courseDao;
    private MutableLiveData<ViewState<ListItem>> state = new MutableLiveData<>();
    private MutableLiveData<String> courseGradeText = new MutableLiveData<>();
    private SingleLiveEvent<Fragment> navigationEvent = new SingleLiveEvent<>();
    private CourseWithAssignmentsAndWeights course;
    private boolean deleteMode;

    @Inject
    public AssignmentViewModel(GradesDatabase gradesDatabase) {
        super();
        this.assignmentDao = gradesDatabase.getAssignmentDao();
        this.courseDao = gradesDatabase.getCourseDao();
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
                        List<ListItem> assignmentItems = new ArrayList<>();
                        for (AssignmentWithWeight assignment : courseData.assignments) {
                            assignmentItems.add(createListItem(assignment.getAssignment()));
                        }
                        calculateCourseGrade();
                        state.setValue(new ViewState<>(false, false, true, "", assignmentItems));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
        /*
        assignmentDao.getAssignmentsByCourseId(courseID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<AssignmentWithWeight>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<AssignmentWithWeight> assignments) {
                        List<ListItem> assignmentItems = new ArrayList<>();
                        for (AssignmentWithWeight assignment : assignments) {
                            assignmentItems.add(createListItem(assignment.getAssignment()));
                        }
                        calculateCourseGrade(assignments);
                        state.setValue(new ViewState<>(false, false, true, "", assignmentItems));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

         */
    }

    private void calculateCourseGrade() {
        double percentage = course.calculateGradePercentage();
        if(percentage == -1){
            courseGradeText.setValue("N/A");
            return;
        }
        courseGradeText.setValue(percentage + "%");
        /*
        if (assignments.isEmpty()) {
            courseGradeText.setValue("N/A");
            return;
        }

        double totalEarned = 0;
        double totalWeight = 0;
        for (AssignmentWithWeight assignment : assignments) {
            double numAssignmentsWithWeight = assignments.stream()
                    .filter(currAssignment -> currAssignment.assignment.assignmentWeightId.equals(assignment.assignment.assignmentWeightId))
                    .count();
            double calculatedWeight = assignment.weight.weightValue / numAssignmentsWithWeight;

            totalEarned += (assignment.assignment.assignmentGradeEarned / assignment.assignment.assignmentGradeTotal * 100) * calculatedWeight / 100;
            totalWeight += calculatedWeight;
        }

        double percentage = totalEarned / totalWeight * 100;
        percentage = Math.round(percentage * 10) / 10.0;
        courseGradeText.setValue(percentage + "%");

         */
    }

    private ListItem createListItem(Assignment assignment) {
        int percentage = (int) ((assignment.assignmentGradeEarned / assignment.assignmentGradeTotal) * 100);
        return new ListItem(assignment.assignmentId, Grading.gradeToLetter.floorEntry(percentage).getValue(),
                assignment.assignmentName, percentage + "%",
                deleteMode, new ListItemEventListener() {
            @Override
            public void onItemClicked(String id) {
                navigationEvent.setValue(InputFormFragment.newInstance(id, InputFormFragment.FormType.UPDATE_ASSIGNMENT.toString()));
            }

            @Override
            public void deleteItem(String id) {
                AsyncTask.execute(() -> {
                    assignmentDao.deleteAssignment(id);
                    fetchCourseAssignments(assignment.assignmentCourseId);
                });

            }
        });
    }

    public SingleLiveEvent<Fragment> getNavigationEvent() {
        return navigationEvent;
    }

    public LiveData<ViewState<ListItem>> getState() {
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
}
