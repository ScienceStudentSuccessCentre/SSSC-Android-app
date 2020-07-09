package ghelani.kshamina.sssc_android_app.ui.grades.terms.add_course;

import android.text.InputType;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ghelani.kshamina.sssc_android_app.database.CourseDao;
import ghelani.kshamina.sssc_android_app.database.GradesDatabase;
import ghelani.kshamina.sssc_android_app.entity.CourseEntity;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.DiffItem;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.InputItem;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.TextItem;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;

public class AddCoursePresenterImpl implements AddCourseContract.Presenter {

    private AddCourseContract.View view;
    private CourseDao courseDao;
    private Scheduler backgroundScheduler;
    private Scheduler mainScheduler;
    private List<InputItem> items;
    private CourseEntity newCourse;

    @Inject
    public AddCoursePresenterImpl(AddCourseContract.View view,
                                  Scheduler backgroundScheduler,
                                  Scheduler mainScheduler,
                                  GradesDatabase gradesDatabase) {
        this.view = view;
        this.courseDao = gradesDatabase.getCourseDao();
        this.backgroundScheduler = backgroundScheduler;
        this.mainScheduler = mainScheduler;

        createItemsList();
        newCourse = new CourseEntity();

    }

    @Override
    public List<DiffItem> getInputItems() {
        List<DiffItem> diffItems = new ArrayList<>();
        diffItems.add(new TextItem("COURSE INFO"));
        diffItems.add(items.get(0));
        diffItems.add(items.get(1));
        diffItems.add(items.get(2));
        diffItems.add(items.get(3));
        diffItems.add(new TextItem("ASSIGNMENT WEIGHTS"));
        diffItems.add(items.get(4));
        diffItems.add(new TextItem("Assignment weights must total 100%. Example:\nQuizzes (40%), Midterm (25%), Final Exam (35%)", false));
        diffItems.add(new TextItem("OVERRIDE CALCULATED GRADE"));
        diffItems.add(items.get(5));
        diffItems.add(new TextItem("If you have already received a final grade from Carleton for this course, " +
                "enter it here to ensure GPA calculation accuracy.", false));

        return diffItems;
    }

    @Override
    public void setTermId(String termID) {
        newCourse.courseTermId = termID;
    }

    private void createItemsList() {
        items = new ArrayList<>();

        items.add(new InputItem("Operating Systems", "Name", InputType.TYPE_CLASS_TEXT, (item, value) -> {
            newCourse.courseName = value;
            isCreateAvailable();
        }));

        items.add(new InputItem("COMP 3000", "Code", InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS, (item, value) -> {
            newCourse.courseCode = value;
            isCreateAvailable();
        }));

        items.add(new InputItem("0.5", "Credits", (InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_DECIMAL), (item, value) -> {
            newCourse.courseCredits = value.isEmpty() ? -1 : Double.parseDouble(value);
            isCreateAvailable();
        }));

        items.add(new InputItem("Y/N", "Counts Towards Major CGPA", InputItem.InputStyle.SWITCH, InputType.TYPE_CLASS_TEXT, (item, value) -> {
            newCourse.courseIsMajorCourse = !newCourse.courseIsMajorCourse;
            isCreateAvailable();
        }));

        items.add(new InputItem("", "ADD NEW WEIGHT", InputItem.InputStyle.BUTTON, InputType.TYPE_CLASS_TEXT, (item, value) -> {
           // newCourse.setCourseIsMajorCourse(Boolean.getBoolean(value));
            isCreateAvailable();
        }));

        items.add(new InputItem("None", "Final Grade", InputType.TYPE_CLASS_TEXT, (item, value) -> {
            newCourse.courseFinalGrade = value;
            isCreateAvailable();
        }));
    }

    @Override
    public void onCreate() {
        if (!newCourse.courseName.isEmpty() && !newCourse.courseCode.isEmpty() && newCourse.courseCredits != -1) {
            Completable.fromAction(() ->  courseDao.insertCourse(newCourse))
                    .subscribeOn(backgroundScheduler)
                    .observeOn(mainScheduler)
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onComplete() {
                            view.navigateToCoursesPage();
                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    });

        }
    }

    private void isCreateAvailable() {
        view.setCreateEnabled((!newCourse.courseName.isEmpty() && !newCourse.courseCode.isEmpty() && newCourse.courseCredits != -1));
    }
}
