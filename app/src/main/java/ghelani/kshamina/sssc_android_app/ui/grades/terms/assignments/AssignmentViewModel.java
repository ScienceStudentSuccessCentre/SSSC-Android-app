package ghelani.kshamina.sssc_android_app.ui.grades.terms.assignments;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ghelani.kshamina.sssc_android_app.database.AssignmentDao;
import ghelani.kshamina.sssc_android_app.database.GradesDatabase;
import ghelani.kshamina.sssc_android_app.entity.Assignment;
import ghelani.kshamina.sssc_android_app.ui.common.events.ListItemEventListener;
import ghelani.kshamina.sssc_android_app.ui.common.events.SingleLiveEvent;
import ghelani.kshamina.sssc_android_app.ui.common.list.ViewState;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.ListItem;
import ghelani.kshamina.sssc_android_app.ui.grades.Grading;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.input_form.InputFormFragment;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AssignmentViewModel extends ViewModel {

    private AssignmentDao assignmentDao;
    private MutableLiveData<ViewState<ListItem>> state = new MutableLiveData<>();
    private SingleLiveEvent<Fragment> navigationEvent = new SingleLiveEvent<>();
    private boolean deleteMode;

    @Inject
    public AssignmentViewModel(GradesDatabase gradesDatabase) {
        super();
        this.assignmentDao = gradesDatabase.getAssignmentDao();
    }

    public void fetchCourseAssignments(String courseID) {
        assignmentDao.getAssignmentsByCourseId(courseID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Assignment>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<Assignment> assignments) {
                        List<ListItem> assignmentItems = new ArrayList<>();
                        for (Assignment assignment : assignments) {
                            assignmentItems.add(createListItem(assignment));
                        }
                        state.setValue(new ViewState<>(false, false, true, "", assignmentItems));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    private ListItem createListItem(Assignment assignment){
        int percentage = (int)((assignment.assignmentGradeEarned / assignment.assignmentGradeTotal)*100);
        return new ListItem(assignment.assignmentId, Grading.gradeToLetter.floorEntry(percentage).getValue(),
                assignment.assignmentName, String.valueOf(percentage),
                deleteMode, new ListItemEventListener() {
            @Override
            public void onItemClicked(String id) {
                navigationEvent.setValue(InputFormFragment.newInstance(id, InputFormFragment.FormType.UPDATE_ASSIGNMENT.toString()));
            }

            @Override
            public void deleteItem(String id) {
                Completable.fromAction(() -> assignmentDao.deleteAssignment(id))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onComplete() {
                                fetchCourseAssignments(assignment.assignmentCourseId);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        });
            }
        });
    }

    public SingleLiveEvent<Fragment> getNavigationEvent() {
        return navigationEvent;
    }

    public LiveData<ViewState<ListItem>> getState(){
        return state;
    }

    public boolean isDeleteMode() {
        return deleteMode;
    }

    public void setDeleteMode(boolean deleteMode) {
        this.deleteMode = deleteMode;
    }
}
