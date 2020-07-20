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
import ghelani.kshamina.sssc_android_app.ui.grades.terms.input_form.InputFormFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.input_form.InputFormViewModel;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddAssignmentViewModel extends InputFormViewModel {

    private MutableLiveData<List<DiffItem>> items = new MutableLiveData<>();
    private MutableLiveData<Boolean> createEnabled = new MutableLiveData<>(false);
    private Assignment newAssignment;
    private AssignmentDao assignmentDao;
    private WeightDao weightDao;

    @Inject
    public AddAssignmentViewModel(GradesDatabase db) {
        items.setValue(createItemsList());
        newAssignment = new Assignment("", -1, 0, "", "");
        this.assignmentDao = db.getAssignmentDao();
        this.weightDao = db.getWeightDao();
    }

    private List<DiffItem> createItemsList() {
        List<DiffItem> displayItems = new ArrayList<>();

        displayItems.add(new TextItem("ASSIGNMENT INFO"));

        displayItems.add(new InputItem("Assignment 1", "Name", InputType.TYPE_CLASS_TEXT, (item, value) -> {
            newAssignment.assignmentName = value;
            ((InputItem) item).setValue(value);
            checkCreateAvailable();
        }));

        displayItems.add(new InputItem("26", "Grade Earned", (InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_DECIMAL), (item, value) -> {
            newAssignment.assignmentGradeEarned = Double.parseDouble(value);
            ((InputItem) item).setValue(value);
            checkCreateAvailable();
        }));

        displayItems.add(new InputItem("30", "Maximum Grade", (InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_DECIMAL), (item, value) -> {
            newAssignment.assignmentGradeTotal = Double.parseDouble(value);
            ((InputItem) item).setValue(value);
            checkCreateAvailable();
        }));

        displayItems.add(new InputItem("Final", "", "Weight", InputItem.InputStyle.SELECTION_SCREEN, InputType.TYPE_CLASS_TEXT, (item, value) -> {
            navigationEvent.setValue(InputFormFragment.newInstance(newAssignment.assignmentCourseId, InputFormFragment.FormType.SELECT_WEIGHT.toString()));
            ((InputItem) item).setValue(value);
        }));
        return displayItems;

    }

    @Override
    public void onCreate() {
        Completable.fromAction(() -> assignmentDao.insertAssignment(newAssignment))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        isCreateComplete().setValue(true);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void setWeight(Weight weight){
        newAssignment.assignmentWeightId= weight.weightId;
        checkCreateAvailable();
    }

    public LiveData<List<DiffItem>> getInputItems() {
        return items;
    }

    private void checkCreateAvailable() {
        if (!newAssignment.assignmentName.isEmpty() && newAssignment.assignmentGradeEarned != -1 && newAssignment.assignmentGradeTotal > 0 && !newAssignment.assignmentWeightId.isEmpty()) {
            createEnabled.setValue(true);
        } else {
            createEnabled.setValue(false);
        }
    }

    public LiveData<Boolean> isCreateEnabled() {
        return createEnabled;
    }

    @Override
    public void setId(String courseId) {
        newAssignment.assignmentCourseId = courseId;
    }

}
