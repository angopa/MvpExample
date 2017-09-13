package com.paezand.mvpexample.ui.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.paezand.mvpexample.R;
import com.paezand.mvpexample.http.TwitchAPI;
import com.paezand.mvpexample.http.model.Top;
import com.paezand.mvpexample.http.model.Twitch;
import com.paezand.mvpexample.root.App;

import java.util.List;

import javax.inject.Inject;

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
                        Log.d("MainActivity", top.getGame().getName());
                    }
                }
            }

            @Override
            public void onFailure(Call<Twitch> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
