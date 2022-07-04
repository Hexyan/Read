package com.hjy.read.api.services;

import com.hjy.read.bean.daily.DailyTimeLine;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;


public interface DailyApi {

    @GET("homes/index/{num}.json")
    Observable<DailyTimeLine> getDailyTimeLine(@Path("num") String num);

    @GET("options/index/{id}/{num}.json")
    Observable<DailyTimeLine> getDailyFeedDetail(@Path("id") String id,@Path("num") String num);
}
