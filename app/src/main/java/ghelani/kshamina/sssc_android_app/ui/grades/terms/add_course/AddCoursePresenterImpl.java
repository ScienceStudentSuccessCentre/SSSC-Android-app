package ghelani.kshamina.sssc_android_app.ui.grades.terms.add_course;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ghelani.kshamina.sssc_android_app.entity.CourseEntity;
import ghelani.kshamina.sssc_android_app.model.Course;
import ghelani.kshamina.sssc_android_app.repository.CourseRepository;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.DiffItem;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.InputItem;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.TextItem;
import io.reactivex.Completable;
import io.reactivex.Scheduler;

public class AddCoursePresenterImpl implements AddCourseContract.Presenter {

    private AddCourseContract.View view;
    private CourseRepository courseRepository;
    private Scheduler backgroundScheduler;
    private Scheduler mainScheduler;
    private List<InputItem> items;
    private Course newCourse;

    @Inject
    public AddCoursePresenterImpl(AddCourseContract.View view,
                                  Scheduler backgroundScheduler,
                                  Scheduler mainScheduler,
                                  CourseRepository courseRepository) {
        this.view = view;
        this.courseRepository = courseRepository;
        this.backgroundScheduler = backgroundScheduler;
        this.mainScheduler = mainScheduler;

        createItemsList();
        newCourse = new Course();

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
        newCourse.setCourseTermId(termID);
    }

    private void createItemsList() {
        items = new ArrayList<>();

        items.add(new InputItem("", "Operating Systems", "Name", (item, value) -> {
            newCourse.setCourseName(value);
            isCreateAvailable();
        }, InputItem.InputType.TEXT));
        items.add(new InputItem("", "COMP 3000", "Code", (item, value) -> {
            newCourse.setCourseCode(value);
            isCreateAvailable();
        }, InputItem.InputType.TEXT));
        items.add(new InputItem("", "0.5", "Credits", (item, value) -> {
            newCourse.setCourseCredits(value.isEmpty() ? -1 : Double.parseDouble(value));
            isCreateAvailable();
        }, InputItem.InputType.TEXT));
        items.add(new InputItem("", "Y/N", "Counts Towards Major CGPA", (item, value) -> {
            newCourse.setCourseIsMajorCourse(!newCourse.isCourseIsMajorCourse());
            isCreateAvailable();
        }, InputItem.InputType.SWITCH));
        items.add(new InputItem("", "", "ADD NEW WEIGHT", (item, value) -> {
            isCreateAvailable();
        }, InputItem.InputType.BUTTON));
        items.add(new InputItem("", "None", "Final Grade", (item, value) -> {
            newCourse.setCourseFinalGrade(value);
            isCreateAvailable();
        }, InputItem.InputType.TEXT));
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onCreate() {
        if (!newCourse.getCourseName().isEmpty() && !newCourse.getCourseCode().isEmpty() && newCourse.getCourseCredits() != -1) {
            Completable.fromAction(() -> courseRepository.insertCourse(new CourseEntity(newCourse)))
                    .subscribeOn(backgroundScheduler)
                    .observeOn(mainScheduler)
                    .subscribe();
            view.navigateToCoursesPage();
        }
    }

    private void isCreateAvailable() {
        view.setCreateEnabled((!newCourse.getCourseName().isEmpty() && !newCourse.getCourseCode().isEmpty() && newCourse.getCourseCredits() != -1));
    }
}
