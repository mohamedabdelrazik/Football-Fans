package com.example.footballfans.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.footballfans.Adapters.AdapterNextMatches;
import com.example.footballfans.Adapters.AdapterPlayersOfTeam;
import com.example.footballfans.Adapters.AdapterTeamsOfLeague;
import com.example.footballfans.Models.ModelEvents;
import com.example.footballfans.Models.ModelEvents2;
import com.example.footballfans.Models.ModelPlayers2;
import com.example.footballfans.Models.ModelTeams2;
import com.example.footballfans.Network.Client;
import com.example.footballfans.Network.ServicesApi;
import com.example.footballfans.R;
import com.nostra13.universalimageloader.cache.disc.impl.LimitedAgeDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * created by Mohamed 2-4-19.
 */
public class NextMatches extends Fragment {

    private View view;
    private ImageView background;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager manager;
    AVLoadingIndicatorView loadingIndicator;
    String leagueId ;
    private AdapterNextMatches mAdapter;
    SharedPreferences preferences;


    public NextMatches() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_next_matches, container, false);
        action();
        return view;
    }

    private void action(){
        background = view.findViewById(R.id.teamBackground);
        loadingIndicator = view.findViewById(R.id.loading);
        mRecyclerView = view.findViewById(R.id.rv_nextMatches_of_team);
        manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        preferences = getActivity().getSharedPreferences("myShared",Context.MODE_PRIVATE);
        leagueId = preferences.getString("teamId","133604");
//        badge = preferences.getString("teamLogo",null);
//        if (badge != null) {
//            storeImage(background, badge);
//        }else {
//            background.setImageResource(R.mipmap.ball);
//        }

        loadingIndicator.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);

        ServicesApi servicesApi = Client.getClient().create(ServicesApi.class);
        Call<ModelEvents> call = servicesApi.getNextMatches(leagueId);
        call.enqueue(new Callback<ModelEvents>() {
            @Override
            public void onResponse(Call<ModelEvents> call, Response<ModelEvents> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    List<ModelEvents2> eventsList = response.body().getEventsList();
                    mAdapter = new AdapterNextMatches(getContext(),eventsList);
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    loadingIndicator.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ModelEvents> call, Throwable t) {
                loadingIndicator.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Error connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void storeImage(ImageView imageView, String url){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.ball)
                .showImageOnFail(R.mipmap.ball)
                .cacheInMemory(true)
                .build();

        File cacheDir = StorageUtils.getCacheDirectory(getContext());
        long cacheAge = 10L;
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getContext())
                .diskCache(new LimitedAgeDiskCache(cacheDir, cacheAge)) // this will make the cache to remain for 10 seconds only
                .build();
        ImageLoader.getInstance().init(config);
        ImageLoader.getInstance().displayImage(url, imageView,options );
    }
}
