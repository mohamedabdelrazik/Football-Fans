package com.example.footballfans.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.footballfans.Activites.MainActivity;
import com.example.footballfans.Adapters.AdapterTeamsOfLeague;
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
 * create an instance of this fragment.
 */
public class TeamsOfLeague extends Fragment implements AdapterTeamsOfLeague.OnWebClickListener {
    private View view;
    private RecyclerView mRecyclerView;
    private GridLayoutManager manager;
    AVLoadingIndicatorView loadingIndicator;
    String id;
    private List<ModelTeams2> listOfTeam;
    private AdapterTeamsOfLeague mAdapter;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public TeamsOfLeague() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_teams_of_league, container, false);
        MainActivity.fragNum = 2;
        MainActivity.leagueTable.setVisibility(View.VISIBLE);
        action();
        return view;
    }

    private void action(){
        mRecyclerView = view.findViewById(R.id.rv_teams_of_leagues);
        manager = new GridLayoutManager(getContext(),2);
        mRecyclerView.setLayoutManager(manager);
        loadingIndicator = view.findViewById(R.id.loading);
        preferences = getActivity().getSharedPreferences("myShared", Context.MODE_PRIVATE);

        Bundle bundle = this.getArguments();
        if (bundle != null){
            id = bundle.getString("idLeague");
        }

        loadingIndicator.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);

        ServicesApi servicesApi = Client.getClient().create(ServicesApi.class);
        Call<ModelTeams> call = servicesApi.getTeamsOfLeagues(id);
        call.enqueue(new Callback<ModelTeams>() {
            @Override
            public void onResponse(Call<ModelTeams> call, Response<ModelTeams> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    listOfTeam = response.body().getTeams();
                    MainActivity.mainTextView.setText(response.body().getTeams().get(0).getStrLeague());
                    mAdapter = new AdapterTeamsOfLeague(getActivity(),listOfTeam);
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    loadingIndicator.setVisibility(View.GONE);
                    mAdapter.setClickListener(TeamsOfLeague.this);

                    editor = getActivity().getSharedPreferences("myShared",Context.MODE_PRIVATE).edit();
                    editor.putString("leagId",response.body().getTeams().get(0).getIdLeague());
                    editor.commit();
                }
            }

            @Override
            public void onFailure(Call<ModelTeams> call, Throwable t) {
                loadingIndicator.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view, int position) {
        ModelTeams2 modelTeams2 = listOfTeam.get(position);
        String url ="http://"+ modelTeams2.getStrWebsite();
        Log.e("<<<<<<web",url);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
        startActivity(browserIntent);
    }
}
