package ghelani.kshamina.sssc_android_app.ui.grades.terms.input_form;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;
import ghelani.kshamina.sssc_android_app.ui.common.events.SingleLiveEvent;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.DiffItem;

public abstract class InputFormViewModel extends ViewModel {

    protected MutableLiveData<List<DiffItem>> items = new MutableLiveData<>();
    protected MutableLiveData<Boolean> submitEnabled = new MutableLiveData<>(false);
    protected SingleLiveEvent<Boolean> submitted = new SingleLiveEvent<>();
    protected final SingleLiveEvent<Fragment> navigationEvent = new SingleLiveEvent<>();

    public abstract void onSubmit();

    public LiveData<List<DiffItem>> getInputItems(){
        return items;
    }

    public LiveData<Boolean> isSubmitEnabled(){
        return submitEnabled;
    }

    public SingleLiveEvent<Boolean> isSubmitted(){
        return submitted;
    }

    public SingleLiveEvent<Fragment> getNavigationEvent() {
        return navigationEvent;
    }

    public abstract void setId(String id);
}
