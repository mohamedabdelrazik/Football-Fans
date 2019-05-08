package com.example.footballfans.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.footballfans.Activites.MainActivity;
import com.example.footballfans.Adapters.AdapterNextMatches;
import com.example.footballfans.Adapters.AdapterPlayersOfTeam;
import com.example.footballfans.Adapters.AdapterTeamsOfLeague;
import com.example.footballfans.Models.ModelEvents;
import com.example.footballfans.Models.ModelEvents2;
import com.example.footballfans.Models.ModelPlayers;
import com.example.footballfans.Models.ModelPlayers2;
import com.example.footballfans.Models.ModelTeams;
import com.example.footballfans.Models.ModelTeams2;
import com.example.footballfans.Network.Client;
import com.example.footballfans.Network.ServicesApi;
import com.example.footballfans.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *  created by Mohamed .
 */
public class Search extends Fragment {

    View view;
    String textSearch , todayDate , tomorrowDate ,yesterdayDate;
    int number ;
    RecyclerView mRecyclerView;
    AdapterPlayersOfTeam mAdapter;
    AdapterTeamsOfLeague adapter;
    AdapterNextMatches adapterTodayMatches;
    GridLayoutManager manager;
    LinearLayoutManager layoutManager;
    AVLoadingIndicatorView loadingIndicator ;
    TextView correctText;
    TabLayout tabOfMatches;
    private static final String score = "Soccer";
    public Search() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment Search.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);
        initView();
        return view;
    }

    private void initView(){

        MainActivity.mainTextView.setText(getText(R.string.search));
        MainActivity.openDrawer.setVisibility(View.GONE);
        MainActivity.fragNum = 4;
        loadingIndicator = view.findViewById(R.id.loading);
        textSearch = getArguments().getString("textSearch");
        todayDate = getArguments().getString("todayDate");
        yesterdayDate = getArguments().getString("yesterdayDate");
        tomorrowDate = getArguments().getString("tomorrowDate");
        number = getArguments().getInt("number");
        mRecyclerView = view.findViewById(R.id.rv_search);
        correctText = view.findViewById(R.id.correctText);
        manager = new GridLayoutManager(getContext(),2);
        mRecyclerView.setLayoutManager(manager);
        tabOfMatches = view.findViewById(R.id.tabOfMatches);
        if (number == 2){
            loadingIndicator.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);

            ServicesApi servicesApi = Client.getClient().create(ServicesApi.class);
            Call<ModelPlayers> call = servicesApi.searchForPlayer(textSearch);
            call.enqueue(new Callback<ModelPlayers>() {
                @Override
                public void onResponse(Call<ModelPlayers> call, Response<ModelPlayers> response) {
                    if (response.isSuccessful()) {

                            List<ModelPlayers2> list = response.body().getPlayersList();
                            mAdapter = new AdapterPlayersOfTeam(getContext(),list);
                            if(mAdapter.getItemCount() != 0){
                            mRecyclerView.setAdapter(mAdapter);

                            mRecyclerView.setVisibility(View.VISIBLE);
                            loadingIndicator.setVisibility(View.GONE);
                        }else {
                            loadingIndicator.setVisibility(View.GONE);
                            correctText.setVisibility(View.VISIBLE);
                            correctText.setText(getText(R.string.correct_player));
                        }
                    }
                }

                @Override
                public void onFailure(Call<ModelPlayers> call, Throwable t) {
                    Toast.makeText(getContext(), getText(R.string.correct_player), Toast.LENGTH_SHORT).show();
                }
            });
        }else if (number == 1) {

            loadingIndicator.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);

            ServicesApi servicesApi = Client.getClient().create(ServicesApi.class);
            Call<ModelTeams> call = servicesApi.searchForTeam(textSearch);
            call.enqueue(new Callback<ModelTeams>() {
                @Override
                public void onResponse(Call<ModelTeams> call, Response<ModelTeams> response) {
                    if (response.isSuccessful()) {

                        List<ModelTeams2> list = response.body().getTeams();
                        adapter = new AdapterTeamsOfLeague(getContext(),list);
                        if(adapter.getItemCount() != 0){
                            mRecyclerView.setAdapter(adapter);

                            mRecyclerView.setVisibility(View.VISIBLE);
                            loadingIndicator.setVisibility(View.GONE);
                        }else {
                            loadingIndicator.setVisibility(View.GONE);
                            correctText.setVisibility(View.VISIBLE);
                            correctText.setText(getText(R.string.correct_player));
                        }
                    }
                }

                @Override
                public void onFailure(Call<ModelTeams> call, Throwable t) {
                    Toast.makeText(getContext(), getText(R.string.correct_player), Toast.LENGTH_SHORT).show();
                }
            });
        }else  if (number == 3){
            MainActivity.mainTextView.setText(getText(R.string.matches_today));
            layoutManager = new  LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(layoutManager);


            tabOfMatches.setVisibility(View.VISIBLE);

            TabLayout.Tab yesterday = tabOfMatches.newTab();
            yesterday.setText(getText(R.string.yesterday));

            TabLayout.Tab today = tabOfMatches.newTab();
            today.setText(getText(R.string.today));

            TabLayout.Tab tomorrow = tabOfMatches.newTab();
            tomorrow.setText(getText(R.string.tomorrow));


            tabOfMatches.addTab(yesterday,0 , false);
            tabOfMatches.addTab(today,1,true);
            tabOfMatches.addTab(tomorrow,2 , false);

            getMatches(todayDate);

            tabOfMatches.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    if (tab.getPosition()== 0){getMatches(yesterdayDate);}
                    if (tab.getPosition()==1){getMatches(todayDate);}
                    if (tab.getPosition()==2){getMatches(tomorrowDate);}
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }
    }


    private void getMatches(String date){
        loadingIndicator.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);

        ServicesApi servicesApi = Client.getClient().create(ServicesApi.class);
        Call<ModelEvents> call = servicesApi.getTodayMatches(date,score);
        call.enqueue(new Callback<ModelEvents>() {
            @Override
            public void onResponse(Call<ModelEvents> call, Response<ModelEvents> response) {
                if (response.isSuccessful()){
                    List<ModelEvents2> eventsList = response.body().getEventsList();
                    adapterTodayMatches = new AdapterNextMatches(getContext(),eventsList);
                    if (adapterTodayMatches.getItemCount() != 0){
                        mRecyclerView.setAdapter(adapterTodayMatches);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        loadingIndicator.setVisibility(View.GONE);
                    }else {
                        loadingIndicator.setVisibility(View.GONE);
                        correctText.setVisibility(View.VISIBLE);
                        correctText.setText(getText(R.string.no_matches));
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelEvents> call, Throwable t) {

            }
        });
    }

}
