package ghelani.kshamina.sssc_android_app.ui.grades.terms.add_course;

import android.text.InputType;
import android.util.Log;

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
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;

public class AddCoursePresenterImpl implements AddCourseContract.Presenter {

    private AddCourseContract.View view;
    private CourseDao courseDao;
    private WeightDao weightDao;
    private Scheduler backgroundScheduler;
    private Scheduler mainScheduler;
    private List<DiffItem> items;
    private List<Weight> weights;
    private CourseEntity newCourse;

    @Inject
    public AddCoursePresenterImpl(AddCourseContract.View view,
                                  Scheduler backgroundScheduler,
                                  Scheduler mainScheduler,
                                  GradesDatabase gradesDatabase) {
        this.view = view;
        this.courseDao = gradesDatabase.getCourseDao();
        this.weightDao = gradesDatabase.getWeightDao();
        this.backgroundScheduler = backgroundScheduler;
        this.mainScheduler = mainScheduler;
        weights = new ArrayList<>();

        createItemsList();
        newCourse = new CourseEntity();

    }

    @Override
    public void getInputItems() {
        view.displayItems(items);
    }

    @Override
    public void setTermId(String termID) {
        newCourse.courseTermId = termID;
    }

    private void createItemsList() {
        items = new ArrayList<>();

        items.add(new TextItem("COURSE INFO"));

        items.add(new InputItem("Operating Systems", "Name", InputType.TYPE_CLASS_TEXT, (item, value) -> {
            newCourse.courseName = value;
            ((InputItem) item).setValue(value);
            isCreateAvailable();
        }));

        items.add(new InputItem("COMP 3000", "Code", InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS, (item, value) -> {
            newCourse.courseCode = value;
            ((InputItem) item).setValue(value);
            isCreateAvailable();
        }));

        items.add(new InputItem("0.5", "Credits", (InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_DECIMAL), (item, value) -> {
            newCourse.courseCredits = value.isEmpty() ? -1 : Double.parseDouble(value);
            ((InputItem) item).setValue(value);
            isCreateAvailable();
        }));

        items.add(new InputItem("Y/N", "Counts Towards Major CGPA", InputItem.InputStyle.SWITCH, 1, (item, value) -> {
            newCourse.courseIsMajorCourse = !newCourse.courseIsMajorCourse;
            ((InputItem) item).setValue(value);
            isCreateAvailable();
        }));

        items.add(new TextItem("ASSIGNMENT WEIGHTS"));

        items.add(new InputItem("", "ADD NEW WEIGHT", InputItem.InputStyle.BUTTON, InputType.TYPE_CLASS_TEXT, (item, value) -> {
            createWeightInput("", "");
            isCreateAvailable();
        }));

        items.add(new TextItem("Assignment weights must total 100%. Example:\nQuizzes (40%), Midterm (25%), Final Exam (35%)", false));
        items.add(new TextItem("OVERRIDE CALCULATED GRADE"));

        items.add(new InputItem("None", "Final Grade", InputType.TYPE_CLASS_TEXT, (item, value) -> {
            newCourse.courseFinalGrade = value;
            ((InputItem) item).setValue(value);
            isCreateAvailable();
        }));
        items.add(new TextItem("If you have already received a final grade from Carleton for this course, " +
                "enter it here to ensure GPA calculation accuracy.", false));
    }

    private void createWeightInput(String name, String value) {
        weights.add(new Weight("", -1, newCourse.courseId));
        items.add(items.size() - 5, new WeightItem(weights.size() - 1, name, value,
                (item, weightName) -> {
                    weights.get(((WeightItem) item).getIndex()).weightName = weightName;
                    isCreateAvailable();
                },
                (item, weightValue) -> {
                    weights.get(((WeightItem) item).getIndex()).weightValue = Double.parseDouble(weightValue);
                    isCreateAvailable();
                }));
        view.displayItems(items);
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
                        view.navigateToCoursesPage();
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });





    }

    private void isCreateAvailable() {
        if (!weights.isEmpty()) {
            for (Weight weight : weights) {
                if (weight.weightName.isEmpty() && weight.weightValue != -1 || !weight.weightName.isEmpty() && weight.weightValue == -1) {
                    view.setCreateEnabled(false);
                    return;
                }
            }
        }
        view.setCreateEnabled((!newCourse.courseName.isEmpty() && !newCourse.courseCode.isEmpty() && newCourse.courseCredits != -1));
    }
}
