package ghelani.kshamina.sssc_android_app.network;

import java.util.List;

import javax.inject.Inject;

import ghelani.kshamina.sssc_android_app.entity.Mentor;
import io.reactivex.Single;

public class NetworkManager {
    private MentorApiService mentorAPI;

    @Inject
    public NetworkManager(MentorApiService mentorAPI) {
        this.mentorAPI = mentorAPI;
    }

    public Single<List<Mentor>> getMentors() {
        return mentorAPI.getMentors();
    }
}
