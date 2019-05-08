package com.example.footballfans.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.footballfans.Activites.MainActivity;
import com.example.footballfans.Adapters.AdapterAllLeagues;
import com.example.footballfans.Models.ModelAllLeagues;
import com.example.footballfans.Models.ModelAllLeagues2;
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
public class AllLeagues extends Fragment {
    private View view;
    RecyclerView mRecyclerView;
    LinearLayoutManager manager;
    AVLoadingIndicatorView loadingIndicator;
    AdapterAllLeagues mAdapter;

    public AllLeagues() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_all_leagues, container, false);
        MainActivity.fragNum = 1;
        MainActivity.leagueTable.setVisibility(View.GONE);
        MainActivity.mainTextView.setText(getText(R.string.all_leagues));
        initView();
        return view;
    }

   private void initView(){
        mRecyclerView = view.findViewById(R.id.rv_allLeagues);
        manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        loadingIndicator = view.findViewById(R.id.loading);

        loadingIndicator.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
       ServicesApi servicesApi = Client.getClient().create(ServicesApi.class);
       Call<ModelAllLeagues> call = servicesApi.getAllLeagues();
       call.enqueue(new Callback<ModelAllLeagues>() {
           @Override
           public void onResponse(@NonNull Call<ModelAllLeagues> call, @NonNull Response<ModelAllLeagues> response) {
               if (response.isSuccessful()){
                   assert response.body() != null;
                   if (response.body().getLeagues() != null){
                   List<ModelAllLeagues2> leagues2List = response.body().getLeagues();
                   mAdapter = new AdapterAllLeagues(getActivity(),leagues2List);
                   mRecyclerView.setAdapter(mAdapter);
                   mRecyclerView.setVisibility(View.VISIBLE);
                   loadingIndicator.setVisibility(View.GONE);
                   }
               }

           }

           @Override
           public void onFailure(Call<ModelAllLeagues> call, Throwable t) {
               loadingIndicator.setVisibility(View.GONE);
               Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();

           }
       });

       FragmentManager fm = getActivity().getSupportFragmentManager();
       for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
           fm.popBackStack();
       }
   }
}
