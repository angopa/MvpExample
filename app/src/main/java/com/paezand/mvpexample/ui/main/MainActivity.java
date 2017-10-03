package com.paezand.mvpexample.ui.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.paezand.mvpexample.R;
import com.paezand.mvpexample.http.TwitchAPI;
import com.paezand.mvpexample.http.model.Top;
import com.paezand.mvpexample.http.model.Twitch;
import com.paezand.mvpexample.root.App;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class MainActivity extends AppCompatActivity {

    @Inject
    TwitchAPI twitchAPI;

    private String api_key = "uo6dggojyb8d6soh92zknwmi5ej1q2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((App)getApplication()).getComponent().inject(this);

        Call<Twitch> call = twitchAPI.getTopGames(api_key);

        call.enqueue(new Callback<Twitch>() {
            @Override
            public void onResponse(Call<Twitch> call, Response<Twitch> response) {
                if (response != null) {
                    List<Top> gameList = response.body().getTop();

                    for (Top top : gameList) {
                        Log.d("From Retrofit", top.getGame().getName());
                    }
                }
            }

            @Override
            public void onFailure(Call<Twitch> call, Throwable t) {
                t.printStackTrace();
            }
        });

        twitchAPI.getTopGamesObservable(api_key).flatMap(new Function<Twitch, ObservableSource<Top>>() {
            @Override
            public ObservableSource<Top> apply(Twitch twitch) throws Exception {
                return Observable.fromIterable(twitch.getTop());
            }
        }).flatMap(new Function<Top, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Top top) throws Exception {
                return Observable.just(top.getGame().getName());
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String value) {
                Log.d("From Observable", value);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });


//        twitchAPI.getTopGamesObservable(api_key).flatMap(new Func1<Twitch, Observable<Top>>() {
//            @Override
//            public Observable<Top> call(Twitch twitch) {
//                return null;
//            }
//        }).flatMap(new Func1<Top, Observable<String>>() {
//            @Override
//            public Observable<String> call(Top top) {
//                return Observable.just(top.getGame().getName());
//            }
//        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
//            @Override
//            public void onCompleted() {
//                Log.d("on Completed", "Empty");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d("on Error", e.getMessage());
//            }
//
//            @Override
//            public void onNext(String s) {
//                Log.d("From Observable", s);
//            }
//        });
    }
}
