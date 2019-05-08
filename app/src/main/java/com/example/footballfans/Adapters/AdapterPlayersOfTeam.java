package com.example.footballfans.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.footballfans.Activites.MainActivity;
import com.example.footballfans.Fragments.PlayerDetails;
import com.example.footballfans.Fragments.TeamDetails;
import com.example.footballfans.Fragments.TeamsOfLeague;
import com.example.footballfans.Models.ModelPlayers2;
import com.example.footballfans.R;
import com.nostra13.universalimageloader.cache.disc.impl.LimitedAgeDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.List;

public class AdapterPlayersOfTeam extends RecyclerView.Adapter<AdapterPlayersOfTeam.PlayerHolder> {

    private Context context;
    private List<ModelPlayers2> listPlayers;

    public AdapterPlayersOfTeam(Context context ,List<ModelPlayers2> listPlayers ){
        this.context = context;
        this.listPlayers = listPlayers;
    }

    public class PlayerHolder extends RecyclerView.ViewHolder{
        TextView playerName , playerPosition;
        ImageView playerImage;
        public PlayerHolder(@NonNull View itemView) {
            super(itemView);
            playerImage = itemView.findViewById(R.id.player_image);
            playerName = itemView.findViewById(R.id.player_name);
            playerPosition = itemView.findViewById(R.id.player_position);
        }
    }

    @NonNull
    @Override
    public PlayerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_players_of_team,viewGroup,false);
        return new PlayerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerHolder holder, final int position) {

        if (listPlayers.get(position).getStrSport().equals("Soccer")) {
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .showImageForEmptyUri(R.mipmap.ball)
                    .showImageOnFail(R.mipmap.ball)
                    .cacheInMemory(true)
                    .build();

            File cacheDir = StorageUtils.getCacheDirectory(context);
            long cacheAge = 10L;
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                    .diskCache(new LimitedAgeDiskCache(cacheDir, cacheAge)) // this will make the cache to remain for 10 seconds only
                    .build();
            ImageLoader.getInstance().init(config);
            String imageUrl = listPlayers.get(position).getStrCutout();
            if (imageUrl != null) {
                ImageLoader.getInstance().displayImage(imageUrl, holder.playerImage, options);
            } else {
                ImageLoader.getInstance().displayImage(listPlayers.get(position).getStrThumb(), holder.playerImage, options);
            }

            holder.playerName.setText(listPlayers.get(position).getStrPlayer());
            holder.playerPosition.setText(listPlayers.get(position).getStrPosition());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("idPlayer", listPlayers.get(position).getIdPlayer());
                    PlayerDetails playerDetails = new PlayerDetails();
                    playerDetails.setArguments(bundle);
                    MainActivity.activity.getSupportFragmentManager()
                            .beginTransaction()
                            .addToBackStack(TeamsOfLeague.class.getName())
                            .replace(R.id.main_frame, playerDetails)
                            .commit();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listPlayers == null ? 0 : listPlayers.size();
    }

}
