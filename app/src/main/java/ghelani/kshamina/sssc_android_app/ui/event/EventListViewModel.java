package ghelani.kshamina.sssc_android_app.ui.event;

import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;

import androidx.fragment.app.Fragment;
import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ghelani.kshamina.sssc_android_app.entity.Event;
import ghelani.kshamina.sssc_android_app.network.NetworkManager;
import ghelani.kshamina.sssc_android_app.ui.utils.events.EventListener;
import ghelani.kshamina.sssc_android_app.ui.utils.events.SingleLiveEvent;
import ghelani.kshamina.sssc_android_app.ui.utils.list.model.DiffItem;
import ghelani.kshamina.sssc_android_app.ui.utils.list.model.ListItem;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class EventListViewModel extends ViewModel {

    private NetworkManager networkManager;
    private MutableLiveData<List<DiffItem>> events = new MutableLiveData<>();
    private SingleLiveEvent<Fragment> navigationEvent = new SingleLiveEvent<>();
    private final SavedStateHandle savedStateHandle;


    @ViewModelInject
    public EventListViewModel(NetworkManager networkManager, @Assisted SavedStateHandle savedStateHandle) {
        this.networkManager = networkManager;
        this.savedStateHandle = savedStateHandle;
    }

    public void fetchEvents() {
        networkManager.getEvents()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Event>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<Event> eventList) {
                        Collections.sort(eventList);
                        List<DiffItem> displayEvents = new ArrayList<>();
                        for (Event event : eventList) {
                            displayEvents.add(createListItem(event));
                        }
                        events.setValue(displayEvents);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    private DiffItem createListItem(Event event) {
        String date = event.getDateDisplayString();
        SpannableString dateSize = new SpannableString(date);
        dateSize.setSpan(new RelativeSizeSpan(0.65f), 0,dateSize.length() - 2, 0);

        return new ListItem(event.getId(), dateSize, "", event.getName(), false, new EventListener.ListItemEventListener() {
            @Override
            public void onItemClicked(String id) {
                navigationEvent.setValue(EventSingleFragment.newInstance(event));
            }

            @Override
            public boolean onItemLongClicked(int index) {
                return true;
            }

            @Override
            public void deleteItem(int index) {

            }
        });
    }

    public LiveData<List<DiffItem>> getEvents() {
        return events;
    }

    public SingleLiveEvent<Fragment> getNavigationEvent() {
        return navigationEvent;
    }
}
