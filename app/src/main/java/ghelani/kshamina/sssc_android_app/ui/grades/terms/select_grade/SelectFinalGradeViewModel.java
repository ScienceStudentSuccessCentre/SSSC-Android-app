package ghelani.kshamina.sssc_android_app.ui.grades.terms.select_grade;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import java.util.ArrayList;
import java.util.List;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.input_form.InputFormViewModel;
import ghelani.kshamina.sssc_android_app.ui.utils.events.SingleLiveEvent;
import ghelani.kshamina.sssc_android_app.ui.utils.list.model.DiffItem;
import ghelani.kshamina.sssc_android_app.ui.utils.list.model.SelectionItem;
import ghelani.kshamina.sssc_android_app.ui.utils.list.model.TextItem;

public class SelectFinalGradeViewModel extends InputFormViewModel {

    private SingleLiveEvent<String> selectGrade = new SingleLiveEvent<>();
    private int selectedIndex;
    private String[] letterGrades;
    private final SavedStateHandle savedStateHandle;

    @ViewModelInject
    public SelectFinalGradeViewModel(String[] letterGrades, @Assisted SavedStateHandle savedStateHandle) {
        selectedIndex = -1;
        this.letterGrades = letterGrades;
        this.savedStateHandle = savedStateHandle;
    }

    @Override
    protected void createItemsList(){
        List<DiffItem> optionsList = new ArrayList<>();

        optionsList.add(new TextItem("SELECT A GRADE"));
        for(final String grade: letterGrades){
            optionsList.add(new SelectionItem(optionsList.size(), grade, false, index -> {
                String selectedGrade = grade;
                if(selectedIndex != -1){
                    ((SelectionItem) items.getValue().get(selectedIndex)).setSelected(false);
                }
                ((SelectionItem)items.getValue().get(index)).setSelected(false);
                items.setValue(items.getValue());
                switch(selectedGrade){
                    case "None":
                        selectedGrade = "";
                        break;
                    case "UNSAT":
                        selectedGrade = "UNS";
                        break;
                }
                selectGrade.setValue(selectedGrade);
            }));
        }
        items.setValue(optionsList);
    }

    public LiveData<String> getSelectedGrade(){
        return selectGrade;
    }

    @Override
    public void onSubmit() {

    }
}
