package ghelani.kshamina.sssc_android_app.ui.grades.terms.required_final_grade;

import android.text.InputType;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.SavedStateHandle;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import ghelani.kshamina.sssc_android_app.database.CourseDao;
import ghelani.kshamina.sssc_android_app.database.GradesDatabase;
import ghelani.kshamina.sssc_android_app.entity.CourseEntity;
import ghelani.kshamina.sssc_android_app.entity.CourseWithAssignmentsAndWeights;
import ghelani.kshamina.sssc_android_app.entity.Weight;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.SelectItemViewModel;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.select_weight.SelectWeightFragment;
import ghelani.kshamina.sssc_android_app.ui.utils.list.model.DiffItem;
import ghelani.kshamina.sssc_android_app.ui.utils.list.model.InputItem;
import ghelani.kshamina.sssc_android_app.ui.utils.list.model.TextItem;
import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RequiredFinalGradeViewModel extends SelectItemViewModel<Weight> {

    private CourseEntity course;
    private double currentCourseGrade;
    private double desiredFinalGrade;
    private Weight finalExamWeight;
    private double requiredExamGrade;
    private final SavedStateHandle savedStateHandle;
    private DecimalFormat df;

    @ViewModelInject
    public RequiredFinalGradeViewModel(@Assisted SavedStateHandle savedStateHandle) {
        df = new DecimalFormat("#0.0");
        desiredFinalGrade = -1;
        this.savedStateHandle = savedStateHandle;
    }

    public void setCourse(CourseWithAssignmentsAndWeights course) {
        this.course = course.course;
        currentCourseGrade = course.calculateGradePercentage();
        createItemsList();
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

        inputItems.add(new InputItem(String.valueOf(currentCourseGrade == -1 ? "" : df.format(currentCourseGrade)), "90", "Current Course Grade", (InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_DECIMAL), (item, value) -> {
            if (value.equals(".")) {
                value = "0.";
            }
            currentCourseGrade = value.isEmpty() ? -1 : Double.parseDouble(value);
            ((InputItem) item).setValue(value);
            calculateRequiredExamGrade();
        }));

        inputItems.add(new InputItem(String.valueOf(desiredFinalGrade == -1 ? "" : desiredFinalGrade), "90%", "Desired Final Grade", (InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_DECIMAL), (item, value) -> {
            if (value.equals(".")) {
                value = "0.";
            }
            desiredFinalGrade = value.isEmpty() ? -1 : Double.parseDouble(value);
            ((InputItem) item).setValue(value);
            calculateRequiredExamGrade();
        }));

        inputItems.add(new InputItem(finalExamWeight != null ? finalExamWeight.weightName : "", "", "Final Exam Weight", InputItem.InputStyle.SELECTION_SCREEN, InputType.TYPE_CLASS_TEXT, (item, value) -> {
            navigationEvent.setValue(SelectWeightFragment.newInstance(this, course.courseId));
        }));

        inputItems.add(new TextItem("MINIMUM FINAL GRADE REQUIRED"));

        InputItem finalGrade = new InputItem(isFormFilled() ? String.valueOf(df.format(requiredExamGrade)) : "", "Enter Info Above", "Grade", InputType.TYPE_CLASS_TEXT, (item, value) -> {
        });
        finalGrade.setEnabled(false);

        inputItems.add(finalGrade);

        items.setValue(inputItems);
    }

    private boolean isFormFilled() {
        return desiredFinalGrade != -1 && finalExamWeight != null;
    }

    private void calculateRequiredExamGrade() {
        requiredExamGrade = 0;
        if (isFormFilled()) {
            requiredExamGrade = ((desiredFinalGrade - currentCourseGrade) / finalExamWeight.weightValue * 100) + currentCourseGrade;
            requiredExamGrade = requiredExamGrade < 0 ? 0 : requiredExamGrade;
            requiredExamGrade = Math.round(requiredExamGrade * 10) / 10.0;
            createItemsList();
        }
    }

    @Override
    public void setSelectedItem(Weight item) {
        this.finalExamWeight = item;
        calculateRequiredExamGrade();
    }
}
