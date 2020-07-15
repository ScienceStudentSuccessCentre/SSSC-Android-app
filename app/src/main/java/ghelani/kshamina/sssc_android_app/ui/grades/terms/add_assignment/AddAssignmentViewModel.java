package ghelani.kshamina.sssc_android_app.ui.grades.terms.add_assignment;

import android.text.InputType;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ghelani.kshamina.sssc_android_app.entity.Assignment;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.DiffItem;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.InputItem;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.TextItem;

public class AddAssignmentViewModel extends ViewModel {

    private MutableLiveData<List<DiffItem> >items = new MutableLiveData<>();
    private MutableLiveData<Boolean> createEnabled = new MutableLiveData<>(false);
    private Assignment newAssignment;

    @Inject
    public AddAssignmentViewModel(){
        items.setValue(createItemsList());
        newAssignment = new Assignment("",-1,0,"","");
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
            newAssignment.assignmentGradeTotal =  Double.parseDouble(value);
            ((InputItem) item).setValue(value);
            checkCreateAvailable();
        }));

        displayItems.add(new InputItem("Final", "Weight", InputType.TYPE_CLASS_TEXT , (item, value) -> {
            newAssignment.assignmentWeightId = value;
            ((InputItem) item).setValue(value);
            checkCreateAvailable();
        }));
        return  displayItems;

    }

    public LiveData<List<DiffItem>> getInputItems(){
        return items;
    }

    private void checkCreateAvailable(){
        if(!newAssignment.assignmentName.isEmpty() && newAssignment.assignmentGradeEarned != -1 && newAssignment.assignmentGradeTotal > 0 && !newAssignment.assignmentWeightId.isEmpty()){
            createEnabled.setValue(true);
        }else{
            createEnabled.setValue(false);
        }
    }

    public LiveData<Boolean> isCreateEnabled(){
        return createEnabled;
    }

}
