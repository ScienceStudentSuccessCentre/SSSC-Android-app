package ghelani.kshamina.sssc_android_app.ui.event;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import ghelani.kshamina.sssc_android_app.entity.Event;
import ghelani.kshamina.sssc_android_app.network.NetworkManager;
import ghelani.kshamina.sssc_android_app.ui.common.events.EventListener;
import ghelani.kshamina.sssc_android_app.ui.common.events.SingleLiveEvent;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.DiffItem;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.ListItem;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class EventListViewModel extends ViewModel {

    private NetworkManager networkManager;
    private MutableLiveData<List<DiffItem>> events = new MutableLiveData<>();
    private SingleLiveEvent<Fragment> navigationEvent = new SingleLiveEvent<>();

    @Inject
    public EventListViewModel(NetworkManager networkManager) {
        this.networkManager = networkManager;
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
        return new ListItem(event.getId(), event.getDateDisplayString(), "", event.getName(), false, new EventListener.ListItemEventListener() {
            @Override
            public void onItemClicked(String id) {
                navigationEvent.setValue(EventSingleFragment.newInstance(event));
            }

            @Override
            public boolean onItemLongClicked() {
                return false;
            }

            @Override
            public void deleteItem(String courseId) {

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