package com.example.footballfans.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.footballfans.Adapters.AdapterTableOfLeague;
import com.example.footballfans.Models.ModelEvents;
import com.example.footballfans.Models.ModelTable;
import com.example.footballfans.Models.ModelTable2;
import com.example.footballfans.Network.Client;
import com.example.footballfans.Network.ServicesApi;
import com.example.footballfans.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TableOfLeague extends Fragment {

    View view;
    RecyclerView mRecyclerView;
    LinearLayoutManager manager;
    AVLoadingIndicatorView loadingIndicator;
    AdapterTableOfLeague mAdapter;
    SharedPreferences preferences;
    LinearLayout headOfTable;

    String leagueId;
    public TableOfLeague() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_table_of_league, container, false);
        initView();
        return view;
    }

    private void initView(){
        mRecyclerView = view.findViewById(R.id.rv_table_league);
        manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        loadingIndicator = view.findViewById(R.id.loading);
        headOfTable = view.findViewById(R.id.headOfTable);

        preferences = getActivity().getSharedPreferences("myShared",Context.MODE_PRIVATE);
        leagueId = preferences.getString("leagId","133604");

        loadingIndicator.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        headOfTable.setVisibility(View.GONE);

        ServicesApi servicesApi = Client.getClient().create(ServicesApi.class);
        Call<ModelTable> call = servicesApi.getTable(leagueId);
        call.enqueue(new Callback<ModelTable>() {
            @Override
            public void onResponse(Call<ModelTable> call, Response<ModelTable> response) {
                if (response.isSuccessful()){
                    List<ModelTable2> tableList = response.body().getTableList();
                    mAdapter = new AdapterTableOfLeague(getContext(),tableList);
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    headOfTable.setVisibility(View.VISIBLE);
                    loadingIndicator.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ModelTable> call, Throwable t) {
                loadingIndicator.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Error connection", Toast.LENGTH_SHORT).show();
            }
        });
    }



}
