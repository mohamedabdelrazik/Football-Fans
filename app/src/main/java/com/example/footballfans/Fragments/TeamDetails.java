package com.example.footballfans.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.footballfans.Activites.MainActivity;
import com.example.footballfans.Models.ModelTeams;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * create an instance of this fragment.
 */
public class TeamDetails extends Fragment {

    private View view;
    private TextView teamAlternate ,teamYear , teamStadium , teamKeywords , teamStadiumLocation , teamStadiumCapacity
            ,teamWebsite , teamFace , teamYoutube , teamDescription;
    private ImageView teamLogo , imageTeamStadium , imageTeamShirt;
    String id;
    private LinearLayout linearDetails;
    private AVLoadingIndicatorView loadingIndicator;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public TeamDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_team_details, container, false);
        initView();
        action();
        return view;
    }

    private void initView(){
        teamAlternate = view.findViewById(R.id.teamAlternate);
        teamYear = view.findViewById(R.id.teamYear);
        teamStadium = view.findViewById(R.id.teamStadium);
        teamKeywords = view.findViewById(R.id.teamKeywords);
        teamStadiumLocation = view.findViewById(R.id.teamStadiumLocation);
        teamStadiumCapacity = view.findViewById(R.id.teamStadiumCapacity);
        teamWebsite = view.findViewById(R.id.teamWebsite);
        teamFace = view.findViewById(R.id.teamFace);
        teamYoutube = view.findViewById(R.id.teamYoutube);
        teamDescription = view.findViewById(R.id.teamDescription);
        teamLogo = view.findViewById(R.id.teamLogo);
        imageTeamStadium = view.findViewById(R.id.imageTeamStadium);
        imageTeamShirt = view.findViewById(R.id.imageTeamShirt);
        linearDetails = view.findViewById(R.id.linearDetails);
        loadingIndicator = view.findViewById(R.id.loading);
        MainActivity.fragNum = 3;

        Bundle bundle = this.getArguments();
        if (bundle != null){
            id = bundle.getString("idTeam");
        }

        preferences = getActivity().getSharedPreferences("myShared",Context.MODE_PRIVATE);

    }

    private void action(){

        linearDetails.setVisibility(View.GONE);
        loadingIndicator.setVisibility(View.VISIBLE);

        ServicesApi servicesApi = Client.getClient().create(ServicesApi.class);
        Call<ModelTeams> call = servicesApi.getTeamDetails(id);
        call.enqueue(new Callback<ModelTeams>() {
            @Override
            public void onResponse(Call<ModelTeams> call, Response<ModelTeams> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    storeImage(teamLogo,response.body().getTeams().get(0).getStrTeamLogo());
                    storeImage(imageTeamStadium,response.body().getTeams().get(0).getStrStadiumThumb());
                    storeImage(imageTeamShirt,response.body().getTeams().get(0).getStrTeamJersey());
                    storeImage(MainActivity.headerImage,response.body().getTeams().get(0).getStrTeamFanart1());
                    teamAlternate.setText(response.body().getTeams().get(0).getStrAlternate());
                    teamYear.setText(response.body().getTeams().get(0).getIntFormedYear());
                    teamStadium.setText(response.body().getTeams().get(0).getStrStadium());
                    teamKeywords.setText(response.body().getTeams().get(0).getStrKeywords());
                    teamStadiumLocation.setText(response.body().getTeams().get(0).getStrStadiumLocation());
                    teamStadiumCapacity.setText(response.body().getTeams().get(0).getIntStadiumCapacity());
                    teamWebsite.setText(response.body().getTeams().get(0).getStrWebsite());
                    teamFace.setText(response.body().getTeams().get(0).getStrFacebook());
                    teamYoutube.setText(response.body().getTeams().get(0).getStrYoutube());
                    teamDescription.setText(response.body().getTeams().get(0).getStrDescriptionEN());
                    MainActivity.mainTextView.setText(response.body().getTeams().get(0).getStrTeam());

                    linearDetails.setVisibility(View.VISIBLE);
                    loadingIndicator.setVisibility(View.GONE);

                    editor = getActivity().getSharedPreferences("myShared",Context.MODE_PRIVATE).edit();
                    editor.putString("teamId",response.body().getTeams().get(0).getIdTeam());
                    editor.putString("teamLogo",response.body().getTeams().get(0).getStrTeamFanart1());
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

    private void storeImage(ImageView imageView,String url){
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
