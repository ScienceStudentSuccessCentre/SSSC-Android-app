package ghelani.kshamina.sssc_android_app.ui.grades.terms.add_course;

import android.text.InputType;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;

import java.util.ArrayList;
import java.util.List;

import ghelani.kshamina.sssc_android_app.database.AssignmentDao;
import ghelani.kshamina.sssc_android_app.database.CourseDao;
import ghelani.kshamina.sssc_android_app.database.GradesDatabase;
import ghelani.kshamina.sssc_android_app.database.WeightDao;
import ghelani.kshamina.sssc_android_app.entity.Assignment;
import ghelani.kshamina.sssc_android_app.entity.CourseEntity;
import ghelani.kshamina.sssc_android_app.entity.Weight;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.SelectItemViewModel;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.select_grade.SelectGradeFragment;
import ghelani.kshamina.sssc_android_app.ui.utils.events.SingleLiveEvent;
import ghelani.kshamina.sssc_android_app.ui.utils.list.model.DiffItem;
import ghelani.kshamina.sssc_android_app.ui.utils.list.model.InputItem;
import ghelani.kshamina.sssc_android_app.ui.utils.list.model.TextItem;
import ghelani.kshamina.sssc_android_app.ui.utils.list.model.WeightItem;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddCourseViewModel extends SelectItemViewModel<String> {

    private CourseDao courseDao;
    private WeightDao weightDao;
    private AssignmentDao assignmentDao;
    private Scheduler backgroundScheduler;
    private Scheduler mainScheduler;
    private List<Weight> weights;
    private SingleLiveEvent<DiffItem> addWeightItem = new SingleLiveEvent<>();
    private SingleLiveEvent<Integer> removeWeightItem = new SingleLiveEvent<>();
    private SingleLiveEvent<DiffItem> showDialog = new SingleLiveEvent<>();
    private CourseEntity newCourse;
    private boolean updating;
    private final SavedStateHandle savedStateHandle;

    @ViewModelInject
    public AddCourseViewModel(GradesDatabase gradesDatabase, @Assisted SavedStateHandle savedStateHandle) {

        this.courseDao = gradesDatabase.getCourseDao();
        this.weightDao = gradesDatabase.getWeightDao();
        this.assignmentDao = gradesDatabase.getAssignmentDao();
        this.backgroundScheduler = Schedulers.io();
        this.mainScheduler = AndroidSchedulers.mainThread();
        weights = new ArrayList<>();
        newCourse = new CourseEntity();
        updating = false;
        this.savedStateHandle = savedStateHandle;
    }

    @Override
    protected void createItemsList() {

        List<DiffItem> inputItems = new ArrayList<>();

        inputItems.add(new TextItem("COURSE INFO"));

        inputItems.add(new InputItem(newCourse.courseName, "Operating Systems", "Name", InputType.TYPE_CLASS_TEXT, (item, value) -> {
            newCourse.courseName = value;
            ((InputItem) item).setValue(value);
            isSubmitAvailable();
        }));

        inputItems.add(new InputItem(newCourse.courseCode, "COMP 3000", "Code", InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS, (item, value) -> {
            newCourse.courseCode = value;
            ((InputItem) item).setValue(value);
            isSubmitAvailable();
        }));

        inputItems.add(new InputItem(newCourse.courseCredits == -1 ? "" : String.valueOf(newCourse.courseCredits), "0.5", "Credits", (InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_DECIMAL), (item, value) -> {
            if(value.equals(".")){
                value = "0.";
            }
            newCourse.courseCredits = value.isEmpty() ? -1 : Double.parseDouble(value);
            ((InputItem) item).setValue(value);
            isSubmitAvailable();
        }));

        inputItems.add(new InputItem(String.valueOf(newCourse.courseIsMajorCourse), "Y/N", "Counts Towards Major CGPA", InputItem.InputStyle.SWITCH, 1, (item, value) -> {
            newCourse.courseIsMajorCourse = !newCourse.courseIsMajorCourse;
            ((InputItem) item).setValue(value);
            isSubmitAvailable();
        }));

        inputItems.add(new TextItem("ASSIGNMENT WEIGHTS"));

        for (Weight weight : weights) {
            inputItems.add(new WeightItem(weights.indexOf(weight), weight.weightName, weight.weightValue == 0 ? "" : String.valueOf(weight.weightValue),
                    (item, weightName) -> {
                        weight.weightName = weightName;
                        ((WeightItem) item).setName(weightName);
                        isSubmitAvailable();
                    },
                    (item, weightValue) -> {
                        if(weightValue.equals(".")){
                            weightValue = "0.";
                        }
                        weight.weightValue = weightValue.isEmpty() ? -1 : Double.parseDouble(weightValue);
                        ((WeightItem) item).setValue(weightValue + "%");
                        isSubmitAvailable();
                    }));
        }

        inputItems.add(new InputItem("", "ADD NEW WEIGHT", InputItem.InputStyle.BUTTON, InputType.TYPE_CLASS_TEXT, (item, value) -> {
            createWeightInput(new Weight("", 0, newCourse.courseId));
            isSubmitAvailable();
        }));

        inputItems.add(new TextItem("Assignment weights must total 100%. Example:\nQuizzes (40%), Midterm (25%), Final Exam (35%)"));
        inputItems.add(new TextItem("OVERRIDE CALCULATED GRADE"));

        inputItems.add(new InputItem(newCourse.courseFinalGrade, "", "Final Grade", InputItem.InputStyle.SELECTION_SCREEN, InputType.TYPE_CLASS_TEXT, (item, value) -> {
            navigationEvent.setValue(SelectGradeFragment.newInstance(this));
            newCourse.courseFinalGrade = value;
            ((InputItem) item).setValue(value);
            isSubmitAvailable();
        }));
        inputItems.add(new TextItem("If you have already received a final grade from Carleton for this course, " +
                "enter it here to ensure GPA calculation accuracy."));

        items.setValue(inputItems);
    }

    private void createWeightInput(Weight weight) {
        weights.add(weight);

        DiffItem newItem = new WeightItem(weights.indexOf(weight), weight.weightName, weight.weightValue == 0 ? "" : String.valueOf(weight.weightValue),
                (item, weightName) -> {
                    weight.weightName = weightName;
                    ((WeightItem) item).setName(weightName);
                    isSubmitAvailable();
                },
                (item, weightValue) -> {
                    weight.weightValue = weightValue.isEmpty() ? -1 : Double.parseDouble(weightValue);
                    ((WeightItem) item).setValue(weightValue);
                    isSubmitAvailable();
                });

        items.getValue().add(items.getValue().size() - 5, newItem);

        addWeightItem.setValue(newItem);
        isSubmitAvailable();
    }

    public void removeWeight(int index) {
        WeightItem removeWeight = (WeightItem) items.getValue().get(index);
        assignmentDao.getAssignmentsByWeight(weights.get(removeWeight.getIndex()).weightId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Assignment>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(List<Assignment> assignments) {

                        items.getValue().remove(index);
                        Weight weight = weights.remove(index - 6);
                        removeWeightItem.setValue(index);
                        for (Assignment assignment : assignments) {
                            if (assignment.assignmentWeightId.equals(weight.weightId)) {
                                items.getValue().add(index, removeWeight);
                                weights.add(index - 6, weight);
                                addWeightItem.setValue(removeWeight);
                                showDialog.setValue(removeWeight);
                                break;
                            }
                        }
                        isSubmitAvailable();
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

    @Override
    public void onSubmit() {
        if (updating) {
            updateCourse();
        } else {
            insertCourse();
        }
    }

    private void updateCourse() {
        Completable.fromAction(() -> courseDao.updateCourse(newCourse))
                .subscribeOn(backgroundScheduler)
                .observeOn(mainScheduler)
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        addWeights();
                        submitted.setValue(true);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    private void insertCourse() {
        Completable.fromAction(() -> courseDao.insertCourse(newCourse))
                .subscribeOn(backgroundScheduler)
                .observeOn(mainScheduler)
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        addWeights();
                        submitted.setValue(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

    public void fetchCourseToUpdate(CourseEntity course) {
        updating = true;
        newCourse = course;
        if (items.getValue() == null || items.getValue().isEmpty()) {
            getWeights(newCourse.courseId);
        }

    }

    private void getWeights(String courseID) {
        weightDao.getWeightsByCourseId(courseID)
                .subscribeOn(backgroundScheduler)
                .observeOn(mainScheduler)
                .subscribe(new SingleObserver<List<Weight>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<Weight> weightList) {
                        weights = weightList;
                        isSubmitAvailable();
                        createItemsList();

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    private void addWeights() {
        if (!weights.isEmpty()) {
            for (Weight weight : weights) {
                if (!weight.weightName.isEmpty() && weight.weightValue != 0) {
                    Completable.fromAction(() -> weightDao.insertWeight(weight))
                            .subscribeOn(backgroundScheduler)
                            .observeOn(mainScheduler)
                            .subscribe();
                }
            }
        }
    }

    private void isSubmitAvailable() {
        double totalWeightPercentage = 0;
        if (!weights.isEmpty()) {
            for (Weight weight : weights) {
                totalWeightPercentage += weight.weightValue;
                if (weight.weightName.isEmpty() && weight.weightValue != 0 || !weight.weightName.isEmpty() && weight.weightValue == 0) {
                    submitEnabled.setValue(false);
                    return;
                }
            }
            if (totalWeightPercentage != 100) {
                submitEnabled.setValue(false);
                return;
            }
        }
        submitEnabled.setValue((!newCourse.courseName.isEmpty() && !newCourse.courseCode.isEmpty() && newCourse.courseCredits != -1));
    }

    public LiveData<DiffItem> getAddWeightItem() {
        return addWeightItem;
    }

    public LiveData<Integer> getRemoveWeightItem() {
        return removeWeightItem;
    }

    public SingleLiveEvent<DiffItem> getShowDialog() {
        return showDialog;
    }

    public void setTermId(String termID) {
        newCourse.courseTermId = termID;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    @Override
    public void setSelectedItem(String grade) {
        ((InputItem) items.getValue().get(items.getValue().size() - 2)).setValue(grade);
        newCourse.courseFinalGrade = grade;
        items.setValue(items.getValue());
    }
}