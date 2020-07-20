package ghelani.kshamina.sssc_android_app.ui.grades.terms.input_form;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DividerItemDecoration;

import java.util.List;

import ghelani.kshamina.sssc_android_app.ui.common.events.SingleLiveEvent;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.DiffItem;
import io.reactivex.Single;

public abstract class InputFormViewModel extends ViewModel {

    protected MutableLiveData<List<DiffItem>> items = new MutableLiveData<>();
    protected MutableLiveData<Boolean> createEnabled = new MutableLiveData<>(false);
    protected SingleLiveEvent<Boolean> createComplete = new SingleLiveEvent<>();
    protected final SingleLiveEvent<Fragment> navigationEvent = new SingleLiveEvent<>();

    public abstract void onCreate();

    public LiveData<List<DiffItem>> getInputItems(){
        return items;
    }

    public LiveData<Boolean> isCreateEnabled(){
        return createEnabled;
    }

    public SingleLiveEvent<Boolean> isCreateComplete(){
        return createComplete;
    }

    public SingleLiveEvent<Fragment> getNavigationEvent() {
        return navigationEvent;
    }

    public abstract void setId(String id);
}
