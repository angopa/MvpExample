package com.paezand.mvpexample.http;

import com.paezand.mvpexample.http.model.Twitch;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;



public interface TwitchAPI {
    @GET("games/top")
    Call<Twitch> getTopGames(@Query("client_id") String api_key);

    @GET("games/top")
    Observable<Twitch> getTopGamesObservable(@Query("client_id") String api_key);
}
