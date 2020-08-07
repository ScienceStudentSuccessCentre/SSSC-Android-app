package ghelani.kshamina.sssc_android_app.network;

import java.util.List;

import ghelani.kshamina.sssc_android_app.entity.Mentor;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface MentorApiService {
    @GET("mentors")
    Single<List<Mentor>> getMentors();
}
