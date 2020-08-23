package ghelani.kshamina.sssc_android_app.ui.mentoring;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import ghelani.kshamina.sssc_android_app.entity.Mentor;
import ghelani.kshamina.sssc_android_app.network.NetworkManager;
import ghelani.kshamina.sssc_android_app.ui.common.events.SingleLiveEvent;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.DiffItem;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.TwoLineItem;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MentorListViewModel extends ViewModel {

    private NetworkManager networkManager;
    private MutableLiveData<List<DiffItem>> mentors = new MutableLiveData<>();
    private SingleLiveEvent<Fragment> navigationEvent = new SingleLiveEvent<>();

    @Inject
    public MentorListViewModel(NetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    public void fetchMentors() {
        networkManager.getMentors()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Mentor>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<Mentor> mentorsList) {
                        Collections.sort(mentorsList);
                        mentors.setValue(createListOfDisplayableItem(mentorsList));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    private List<DiffItem> createListOfDisplayableItem(List<Mentor> mentorList) {
        List<DiffItem> displayableMentorItem = new ArrayList<>();

        for (Mentor mentor : mentorList) {
            mentor.setImageUrl(mentor.getImageUrl().replace("http://", "https://"));
            displayableMentorItem.add(new TwoLineItem(mentor.getImageUrl(), mentor.getName(), mentor.getDegree(),
                    () -> navigationEvent.setValue(MentorDetailFragment.newInstance(mentor.getImageUrl(), mentor.getName(), mentor.getBio(), mentor.getDegree(), mentor.getTeam()))));
        }

        return displayableMentorItem;
    }

    public SingleLiveEvent<Fragment> getNavigationEvent() {
        return navigationEvent;
    }

    public LiveData<List<DiffItem>> getMentors() {
        return mentors;
    }
}
