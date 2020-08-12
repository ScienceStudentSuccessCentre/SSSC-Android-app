package ghelani.kshamina.sssc_android_app.ui.grades.terms.add_assignment;

import android.text.InputType;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ghelani.kshamina.sssc_android_app.database.AssignmentDao;
import ghelani.kshamina.sssc_android_app.database.GradesDatabase;
import ghelani.kshamina.sssc_android_app.database.WeightDao;
import ghelani.kshamina.sssc_android_app.entity.Assignment;
import ghelani.kshamina.sssc_android_app.entity.Weight;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.DiffItem;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.InputItem;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.TextItem;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.SelectItemViewModel;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.input_form.InputFormFragment;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddAssignmentViewModel extends SelectItemViewModel<Weight> {

    private MutableLiveData<List<DiffItem>> items = new MutableLiveData<>();
    private Assignment newAssignment;
    private AssignmentDao assignmentDao;
    private Weight weight;
    private boolean updating;
    private WeightDao weightDao;

    @Inject
    public AddAssignmentViewModel(GradesDatabase db) {
        newAssignment = new Assignment("", -1, 0, "", "");
        this.assignmentDao = db.getAssignmentDao();
        this.weightDao = db.getWeightDao();
        updating = false;
    }

    @Override
    protected void createItemsList() {
        List<DiffItem> displayItems = new ArrayList<>();

        displayItems.add(new TextItem("ASSIGNMENT INFO"));

        displayItems.add(new InputItem(newAssignment.assignmentName, "Assignment 1", "Name", InputType.TYPE_CLASS_TEXT, (item, value) -> {
            newAssignment.assignmentName = value;
            ((InputItem) item).setValue(value);
            checkCreateAvailable();
        }));

        displayItems.add(new InputItem(newAssignment.assignmentGradeEarned == -1 ? "" : String.valueOf(newAssignment.assignmentGradeEarned), "26", "Grade Earned", (InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_DECIMAL), (item, value) -> {
            newAssignment.assignmentGradeEarned = value.isEmpty() ? -1 : Double.parseDouble(value);
            ((InputItem) item).setValue(value);
            checkCreateAvailable();
        }));

        displayItems.add(new InputItem(newAssignment.assignmentGradeTotal == 0 ? "" : String.valueOf(newAssignment.assignmentGradeTotal), "30", "Maximum Grade", (InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_DECIMAL), (item, value) -> {
            newAssignment.assignmentGradeTotal = value.isEmpty() ? 0 : Double.parseDouble(value);
            ((InputItem) item).setValue(value);
            checkCreateAvailable();
        }));

        displayItems.add(new InputItem(weight == null ? "" : weight.weightName, "", "Weight", InputItem.InputStyle.SELECTION_SCREEN, InputType.TYPE_CLASS_TEXT, (item, value) -> {
            navigationEvent.setValue(InputFormFragment.newInstance(newAssignment.assignmentCourseId, InputFormFragment.FormType.SELECT_WEIGHT.toString()));
            ((InputItem) item).setValue(value);
        }));
        checkCreateAvailable();
        items.setValue(displayItems);
    }

    @Override
    public void onSubmit() {

        Completable.fromAction(() -> assignmentDao.insertAssignment(newAssignment))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        submitted.setValue(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });

    }

    public void setWeight(Weight weight) {
        this.weight = weight;
        newAssignment.assignmentWeightId = weight.weightId;
        checkCreateAvailable();
    }

    private void getAssignmentWeight(String weightID) {

        weightDao.getWeightByID(weightID).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Weight>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(Weight newWeight) {
                        weight = newWeight;
                        createItemsList();
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

    public void setAssignmentCourseID(String courseID) {
        this.newAssignment.assignmentCourseId = courseID;
    }

    public LiveData<List<DiffItem>> getInputItems() {
        return items;
    }

    private void checkCreateAvailable() {
        if (!newAssignment.assignmentName.isEmpty() && newAssignment.assignmentGradeEarned != -1
                && newAssignment.assignmentGradeTotal > 0 && !newAssignment.assignmentWeightId.isEmpty()) {
            submitEnabled.setValue(true);
        } else {
            submitEnabled.setValue(false);
        }
    }

    public void fetchAssignmentToUpdate(String id) {
        updating = true;
        assignmentDao.getAssignmentByID(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Assignment>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(Assignment assignment) {
                        newAssignment = assignment;
                        getAssignmentWeight(newAssignment.assignmentWeightId);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

    @Override
    public void setSelectedItem(Weight item) {
        this.weight = item;
        newAssignment.assignmentWeightId = item.weightId;
        checkCreateAvailable();
    }

    public Assignment getNewAssignment() {
        return newAssignment;
    }
}
