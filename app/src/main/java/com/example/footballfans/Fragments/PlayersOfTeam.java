package com.example.footballfans.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.footballfans.Activites.MainActivity;
import com.example.footballfans.Adapters.AdapterPlayersOfTeam;
import com.example.footballfans.Models.ModelPlayers;
import com.example.footballfans.Models.ModelPlayers2;
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
 *  created by Mohamed .
 */
public class PlayersOfTeam extends Fragment {

    private View view;
    private ImageView background;
    private AVLoadingIndicatorView loadingIndicator ;
    private RecyclerView mRecyclerView;
    private GridLayoutManager manager;
    private AdapterPlayersOfTeam mAdapter;

    SharedPreferences preferences;
    String id , badge;
    public PlayersOfTeam() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_players_of_team, container, false);
        action();
        return view;
    }

    private void action(){
        background = view.findViewById(R.id.teamBackground);
        loadingIndicator = view.findViewById(R.id.loading);
        mRecyclerView = view.findViewById(R.id.rv_players_of_team);
        manager = new GridLayoutManager(getContext(),2);
        mRecyclerView.setLayoutManager(manager);
        preferences = getActivity().getSharedPreferences("myShared",Context.MODE_PRIVATE);
        id = preferences.getString("teamId","133604");
        badge = preferences.getString("teamLogo",null);
        if (badge != null) {
            storeImage(background, badge);
        }else {
            background.setImageResource(R.mipmap.ball);
        }

        loadingIndicator.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);

        ServicesApi servicesApi = Client.getClient().create(ServicesApi.class);
        Call<ModelPlayers> call = servicesApi.getTeamPlayerss(id);
        call.enqueue(new Callback<ModelPlayers>() {
            @Override
            public void onResponse(Call<ModelPlayers> call, Response<ModelPlayers> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    List<ModelPlayers2> listPlayers = response.body().getPlayersList();
                    mAdapter = new AdapterPlayersOfTeam(getContext(),listPlayers);
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    loadingIndicator.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ModelPlayers> call, Throwable t) {
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
