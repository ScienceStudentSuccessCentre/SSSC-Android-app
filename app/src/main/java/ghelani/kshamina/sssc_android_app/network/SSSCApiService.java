package ghelani.kshamina.sssc_android_app.network;

import java.util.List;

import ghelani.kshamina.sssc_android_app.entity.Event;
import ghelani.kshamina.sssc_android_app.entity.Mentor;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface SSSCApiService {
    @GET("mentors")
    Single<List<Mentor>> getMentors();

    @GET("events")
    Single<List<Event>> getEvents();

    @GET("features")
    Single<Features> getFeatures();
}
