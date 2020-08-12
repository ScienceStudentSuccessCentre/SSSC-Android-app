package ghelani.kshamina.sssc_android_app.network;

import java.util.List;

import javax.inject.Inject;

import ghelani.kshamina.sssc_android_app.entity.Event;
import ghelani.kshamina.sssc_android_app.entity.Mentor;
import io.reactivex.Single;

public class NetworkManager {
    private SSSCApiService ssscAPI;

    @Inject
    public NetworkManager(SSSCApiService ssscAPI) {
        this.ssscAPI = ssscAPI;
    }

    public Single<List<Mentor>> getMentors() {
        return ssscAPI.getMentors();
    }

    public Single<List<Event>> getEvents() {
        return ssscAPI.getEvents();
    }
}
