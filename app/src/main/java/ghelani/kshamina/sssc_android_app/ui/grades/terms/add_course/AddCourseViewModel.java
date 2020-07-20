package ghelani.kshamina.sssc_android_app.ui.grades.terms.add_course;

import android.text.InputType;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ghelani.kshamina.sssc_android_app.database.CourseDao;
import ghelani.kshamina.sssc_android_app.database.GradesDatabase;
import ghelani.kshamina.sssc_android_app.database.WeightDao;
import ghelani.kshamina.sssc_android_app.entity.CourseEntity;
import ghelani.kshamina.sssc_android_app.entity.Weight;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.DiffItem;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.InputItem;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.TextItem;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.WeightItem;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.input_form.InputFormFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.input_form.InputFormViewModel;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddCourseViewModel extends InputFormViewModel {

    private CourseDao courseDao;
    private WeightDao weightDao;
    private Scheduler backgroundScheduler;
    private Scheduler mainScheduler;
    private List<Weight> weights;
    private CourseEntity newCourse;

    @Inject
    public AddCourseViewModel(GradesDatabase gradesDatabase) {

        this.courseDao = gradesDatabase.getCourseDao();
        this.weightDao = gradesDatabase.getWeightDao();
        this.backgroundScheduler = Schedulers.io();
        this.mainScheduler = AndroidSchedulers.mainThread();
        weights = new ArrayList<>();
        newCourse = new CourseEntity();

        createItemsList();
    }

    private void createItemsList() {
        List<DiffItem> inputItems = new ArrayList<>();

        inputItems.add(new TextItem("COURSE INFO"));

        inputItems.add(new InputItem("Operating Systems", "Name", InputType.TYPE_CLASS_TEXT, (item, value) -> {
            newCourse.courseName = value;
            ((InputItem) item).setValue(value);
            isCreateAvailable();
        }));

        inputItems.add(new InputItem("COMP 3000", "Code", InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS, (item, value) -> {
            newCourse.courseCode = value;
            ((InputItem) item).setValue(value);
            isCreateAvailable();
        }));

        inputItems.add(new InputItem("0.5", "Credits", (InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_DECIMAL), (item, value) -> {
            newCourse.courseCredits = value.isEmpty() ? -1 : Double.parseDouble(value);
            ((InputItem) item).setValue(value);
            isCreateAvailable();
        }));

        inputItems.add(new InputItem("Y/N", "Counts Towards Major CGPA", InputItem.InputStyle.SWITCH, 1, (item, value) -> {
            newCourse.courseIsMajorCourse = !newCourse.courseIsMajorCourse;
            ((InputItem) item).setValue(value);
            isCreateAvailable();
        }));

        inputItems.add(new TextItem("ASSIGNMENT WEIGHTS"));

        inputItems.add(new InputItem("", "ADD NEW WEIGHT", InputItem.InputStyle.BUTTON, InputType.TYPE_CLASS_TEXT, (item, value) -> {
            createWeightInput("", "");
            isCreateAvailable();
        }));

        inputItems.add(new TextItem("Assignment weights must total 100%. Example:\nQuizzes (40%), Midterm (25%), Final Exam (35%)", false));
        inputItems.add(new TextItem("OVERRIDE CALCULATED GRADE"));

        inputItems.add(new InputItem("None", "", "Final Grade", InputItem.InputStyle.SELECTION_SCREEN, InputType.TYPE_CLASS_TEXT, (item, value) -> {
            navigationEvent.setValue(InputFormFragment.newInstance("", InputFormFragment.FormType.SELECT_FINAL_GRADE.toString()));
            newCourse.courseFinalGrade = value;
            ((InputItem) item).setValue(value);
            isCreateAvailable();
        }));
        inputItems.add(new TextItem("If you have already received a final grade from Carleton for this course, " +
                "enter it here to ensure GPA calculation accuracy.", false));

        items.setValue(inputItems);
    }

    private void createWeightInput(String name, String value) {
        weights.add(new Weight("", -1, newCourse.courseId));
        items.getValue().add(items.getValue().size() - 5, new WeightItem(weights.size() - 1, name, value,
                (item, weightName) -> {
                    weights.get(((WeightItem) item).getIndex()).weightName = weightName;
                    isCreateAvailable();
                },
                (item, weightValue) -> {
                    weights.get(((WeightItem) item).getIndex()).weightValue = Double.parseDouble(weightValue);
                    isCreateAvailable();
                }));

        items.setValue(items.getValue());
    }

    @Override
    public void onCreate() {
        Completable.fromAction(() -> courseDao.insertCourse(newCourse))
                .subscribeOn(backgroundScheduler)
                .observeOn(mainScheduler)
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {

                        if (!weights.isEmpty()) {
                            for (Weight weight : weights) {
                                if (!weight.weightName.isEmpty() && weight.weightValue != -1) {
                                    Completable.fromAction(() -> weightDao.insertWeight(weight))
                                            .subscribeOn(backgroundScheduler)
                                            .observeOn(mainScheduler)
                                            .subscribe();
                                }
                            }
                        }
                        createComplete.setValue(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

    @Override
    public void setId(String termId) {
        newCourse.courseTermId = termId;
    }

    public void setFinalGrade(String grade) {
        ((InputItem) items.getValue().get(items.getValue().size() - 2)).setValue(grade);
        items.setValue(items.getValue());
        newCourse.courseFinalGrade = (grade.equals("None") ? "N/A" : grade);
    }

    private void isCreateAvailable() {
        double totalWeightPercentage = 0;
        if (!weights.isEmpty()) {
            for (Weight weight : weights) {
                totalWeightPercentage += weight.weightValue;
                if (weight.weightName.isEmpty() && weight.weightValue != -1 || !weight.weightName.isEmpty() && weight.weightValue == -1) {
                    createEnabled.setValue(false);
                    return;
                }
            }
            if(totalWeightPercentage != 100){
                createEnabled.setValue(false);
                return;
            }
        }
        createEnabled.setValue((!newCourse.courseName.isEmpty() && !newCourse.courseCode.isEmpty() && newCourse.courseCredits != -1));
    }
}