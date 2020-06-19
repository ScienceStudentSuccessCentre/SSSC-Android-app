package ghelani.kshamina.sssc_android_app.ui.common.list;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public abstract class ListViewModel extends ViewModel {
    public MutableLiveData<ViewState<DiffItem>> state = new MutableLiveData<>();
    public MutableLiveData<Boolean> isDeleteMode = new MutableLiveData(false);
    public abstract void deleteItem(DiffItem item);

    public abstract void addItem(DiffItem item);

    public abstract void updateItem(DiffItem item);
    public abstract void fetchItems();
    public abstract void onItemClicked(DiffItem item);

    public LiveData<Boolean> getIsDeleteMode() {
        return isDeleteMode;
    }

    public void setDeleteMode(boolean value) {
        isDeleteMode.setValue(value);
    }

    public int getDeleteModeVisibility() {
        return isDeleteMode.getValue() ? View.VISIBLE : View.GONE;
    }

    public void toggleDeleteMode(){
        isDeleteMode.setValue(!isDeleteMode.getValue());
    }


    public LiveData<ViewState<DiffItem>> getState(){
        return state;
    };
}
