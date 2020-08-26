package ghelani.kshamina.sssc_android_app.ui.grades.terms.select_weight;
import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ghelani.kshamina.sssc_android_app.database.GradesDatabase;
import ghelani.kshamina.sssc_android_app.database.WeightDao;
import ghelani.kshamina.sssc_android_app.entity.Weight;
import ghelani.kshamina.sssc_android_app.ui.utils.events.SingleLiveEvent;
import ghelani.kshamina.sssc_android_app.ui.utils.list.model.DiffItem;
import ghelani.kshamina.sssc_android_app.ui.utils.list.model.SelectionItem;
import ghelani.kshamina.sssc_android_app.ui.utils.list.model.TextItem;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.input_form.InputFormViewModel;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SelectWeightViewModel extends InputFormViewModel {

    private WeightDao weightDao;
    private String courseId;
    private int selectedIndex;
    private SingleLiveEvent<Weight> selectedWeight = new SingleLiveEvent<>();
    private final SavedStateHandle savedStateHandle;

    @ViewModelInject
    public SelectWeightViewModel(GradesDatabase db, @Assisted SavedStateHandle savedStateHandle){
        weightDao = db.getWeightDao();
        selectedIndex = -1;
        this.savedStateHandle = savedStateHandle;
    }

    @Override
    protected void createItemsList() {
        weightDao.getWeightsByCourseId(courseId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Weight>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<Weight> weights) {
                        List<DiffItem> optionsList = new ArrayList<>();

                        optionsList.add(new TextItem("SELECT A WEIGHT"));
                        for(Weight weight: weights){
                            optionsList.add(new SelectionItem(optionsList.size(), weight.weightName, false, index -> {
                                if(selectedIndex != -1){
                                    ((SelectionItem) items.getValue().get(selectedIndex)).setSelected(false);
                                }
                                ((SelectionItem)items.getValue().get(index)).setSelected(false);
                                items.setValue(items.getValue());
                                selectedWeight.setValue(weight);
                            }));
                        }
                        items.setValue(optionsList);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public LiveData<Weight> getSelectedWeight(){
        return selectedWeight;
    }

    @Override
    public void onSubmit() {

    }

    public void setId(String id) {
        courseId = id;
    }
}
