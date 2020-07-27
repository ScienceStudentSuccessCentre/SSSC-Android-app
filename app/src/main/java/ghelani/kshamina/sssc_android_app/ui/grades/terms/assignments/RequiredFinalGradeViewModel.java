package ghelani.kshamina.sssc_android_app.ui.grades.terms.assignments;

import android.text.InputType;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ghelani.kshamina.sssc_android_app.database.CourseDao;
import ghelani.kshamina.sssc_android_app.database.GradesDatabase;
import ghelani.kshamina.sssc_android_app.database.WeightDao;
import ghelani.kshamina.sssc_android_app.entity.CourseEntity;
import ghelani.kshamina.sssc_android_app.entity.CourseWithAssignmentsAndWeights;
import ghelani.kshamina.sssc_android_app.entity.Weight;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.DiffItem;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.InputItem;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.TextItem;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.input_form.InputFormViewModel;
import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RequiredFinalGradeViewModel extends InputFormViewModel {

    private CourseDao courseDao;
    private WeightDao weightDao;
    private Scheduler backgroundScheduler;
    private Scheduler mainScheduler;
    private CourseEntity course;
    private double currentCourseGrade;
    private double desiredFinalGrade;
    private List<Weight> weightOptions;
    private Weight finalExamWeight;

    @Inject
    public RequiredFinalGradeViewModel(GradesDatabase gradesDatabase) {
        this.courseDao = gradesDatabase.getCourseDao();
        this.weightDao = gradesDatabase.getWeightDao();
        this.backgroundScheduler = Schedulers.io();
        this.mainScheduler = AndroidSchedulers.mainThread();
    }

    public void getCourseAndWeights(String courseID) {
        courseDao.getCourseWithWeightsByID(courseID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CourseWithAssignmentsAndWeights>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(CourseWithAssignmentsAndWeights courseWithWeights) {
                        course = courseWithWeights.course;
                        weightOptions = courseWithWeights.weight;
                        createItemsList();
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }


    @Override
    public void onSubmit() {

    }

    @Override
    protected void createItemsList() {
        List<DiffItem> inputItems = new ArrayList<>();

        inputItems.add(new TextItem("If you want a certain final grade in a course, you can use this section to determine " +
                "what grade you should aim for on your final exam."));

        inputItems.add(new TextItem("COURSE DETAILS - " + course.courseCode));

        inputItems.add(new InputItem(String.valueOf(currentCourseGrade), "90", "Current Course Grade", (InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_DECIMAL), (item, value) -> {

        }));

        inputItems.add(new InputItem("", "90%", "Desired Final Grade", (InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_DECIMAL), (item, value) -> {

        }));

        inputItems.add(new InputItem("", "", "Final Exam Weight", InputItem.InputStyle.SELECTION_SCREEN, InputType.TYPE_CLASS_TEXT, (item, value) -> {

        }));

        inputItems.add(new TextItem("MINIMUM FINAL GRADE REQUIRED"));

        inputItems.add(new InputItem("","Enter Info Above", "Grade",InputType.TYPE_CLASS_TEXT, (item, value) -> {

        }));


        items.setValue(inputItems);
    }
}
