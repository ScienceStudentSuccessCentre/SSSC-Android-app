package ghelani.kshamina.sssc_android_app.ui.grades.terms.add_course;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ghelani.kshamina.sssc_android_app.ui.common.events.SingleLiveEvent;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.DiffItem;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.SelectionItem;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.TextItem;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.input_form.InputFormViewModel;

public class FinalGradeViewModel extends InputFormViewModel {

    private SingleLiveEvent<String> selectGrade = new SingleLiveEvent<>();
    private int selectedIndex;
    private String[] letterGrades;

    @Inject
    public FinalGradeViewModel(String[] letterGrades) {
        selectedIndex = -1;
        this.letterGrades = letterGrades;
        createOptionsList();
    }

    private void createOptionsList(){
        List<DiffItem> optionsList = new ArrayList<>();

        optionsList.add(new TextItem("SELECT A GRADE"));
        for(String grade: letterGrades){
            optionsList.add(new SelectionItem(optionsList.size(), grade, false, index -> {
                if(selectedIndex != -1){
                    ((SelectionItem) items.getValue().get(selectedIndex)).setSelected(false);
                }
                ((SelectionItem)items.getValue().get(index)).setSelected(false);
                items.setValue(items.getValue());
                selectGrade.setValue(grade);
            }));
        }
        items.setValue(optionsList);
    }

    public LiveData<String> getSelectedGrade(){
        return selectGrade;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void setId(String id) {

    }
}
