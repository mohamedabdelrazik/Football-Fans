package com.example.footballfans.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.footballfans.Models.ModelPlayerDetails;
import com.example.footballfans.Models.ModelPlayers;
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


public class PlayerDetails extends Fragment {

    private View view;
    private TextView playerName ,playerNationality , playerBornDate , playerSignedDate
            , playerPosition, playerHeight ,playerWeight , playerDescription ;
    private ImageView playerImage , playerImage2 , playerImage3 ;
    String playerId;
    private LinearLayout linearDetails;
    private AVLoadingIndicatorView loadingIndicator;

    public PlayerDetails() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_player_details, container, false);
        initView();
        action();
        return view;
    }

    private void initView(){

        linearDetails = view.findViewById(R.id.linearDetails);
        loadingIndicator = view.findViewById(R.id.loading);
        playerImage = view.findViewById(R.id.playerImage);
        playerImage2 = view.findViewById(R.id.playerImage2);
        playerImage3 = view.findViewById(R.id.playerImage3);
        playerName = view.findViewById(R.id.playerName);
        playerNationality = view.findViewById(R.id.playerNationality);
        playerBornDate = view.findViewById(R.id.playerBornDate);
        playerSignedDate = view.findViewById(R.id.playerSignedDate);
        playerPosition = view.findViewById(R.id.playerPosition);
        playerHeight = view.findViewById(R.id.playerHeight);
        playerWeight = view.findViewById(R.id.playerWeight);
        playerDescription = view.findViewById(R.id.playerDescription);



        Bundle bundle = this.getArguments();
        if (bundle != null){
            playerId = bundle.getString("idPlayer");
            Log.e("<<<PlayerId",playerId);

        }else {
            Toast.makeText(getContext(), "no id for player", Toast.LENGTH_SHORT).show();
        }
    }

    private void action(){
        linearDetails.setVisibility(View.GONE);
        loadingIndicator.setVisibility(View.VISIBLE);

        ServicesApi servicesApi = Client.getClient().create(ServicesApi.class);
        Call<ModelPlayerDetails> call = servicesApi.getPlayerDetails(playerId);
        call.enqueue(new Callback<ModelPlayerDetails>() {
            @Override
            public void onResponse(Call<ModelPlayerDetails> call, Response<ModelPlayerDetails> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    storeImage(playerImage,response.body().getPlayersList().get(0).getStrThumb());
                    storeImage(playerImage2,response.body().getPlayersList().get(0).getStrFanart1());
                    storeImage(playerImage3,response.body().getPlayersList().get(0).getStrFanart2());
                    playerName.setText(response.body().getPlayersList().get(0).getStrPlayer());
                    playerNationality.setText(response.body().getPlayersList().get(0).getStrNationality());
                    playerBornDate.setText(response.body().getPlayersList().get(0).getDateBorn());
                    playerSignedDate.setText(response.body().getPlayersList().get(0).getDateSigned());
                    playerPosition.setText(response.body().getPlayersList().get(0).getStrPosition());
                    playerHeight.setText(response.body().getPlayersList().get(0).getStrHeight());
                    playerWeight.setText(response.body().getPlayersList().get(0).getStrWeight());
                    playerDescription.setText(response.body().getPlayersList().get(0).getStrDescriptionEN());

                    linearDetails.setVisibility(View.VISIBLE);
                    loadingIndicator.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<ModelPlayerDetails> call, Throwable t) {
                loadingIndicator.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void storeImage(ImageView imageView,String url){
        if (url == null){
            imageView.setVisibility(View.GONE);
        }else {
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
            ImageLoader.getInstance().displayImage(url, imageView, options);
        }
    }
}
