package com.example.footballfans.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.footballfans.Activites.MainActivity;
import com.example.footballfans.Fragments.AllLeagues;
import com.example.footballfans.Fragments.TeamDetails;
import com.example.footballfans.Fragments.TeamsOfLeague;
import com.example.footballfans.Models.ModelTeams2;
import com.example.footballfans.R;
import com.nostra13.universalimageloader.cache.disc.impl.LimitedAgeDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.List;

public class AdapterTeamsOfLeague extends RecyclerView.Adapter<AdapterTeamsOfLeague.TeamsHolder> {

    private Context context;
    private List<ModelTeams2> listOfTeams;
    private OnWebClickListener clickListener;

    public AdapterTeamsOfLeague(Context context , List<ModelTeams2> listOfTeams){
        this.context = context;
        this.listOfTeams = listOfTeams;
    }

    public class TeamsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name , year , websit;
        ImageView logo;
        public TeamsHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.team_name);
            year = itemView.findViewById(R.id.team_year);
            websit = itemView.findViewById(R.id.team_web);
            logo = itemView.findViewById(R.id.team_logo);
            websit.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if (clickListener != null) clickListener.onClick(v, getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public TeamsHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_teams_of_league,parent,false);
        return new TeamsHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull TeamsHolder holder, final int position) {


//        ImageLoader imageLoader = ImageLoader.getInstance();
//        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
//        imageLoader.displayImage(listOfTeams.get(position).getStrTeamBadge(),holder.logo);

        if (listOfTeams.get(position).getStrSport().equals("Soccer")) {
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
            ImageLoader.getInstance().displayImage(listOfTeams.get(position).getStrTeamBadge(), holder.logo, options);
//        Picasso.get()
//                .load(listOfTeams.get(position).getStrTeamBadge())
//                .centerCrop()
//                .resize(150,150)
//                .into(holder.logo);


            holder.name.setText(listOfTeams.get(position).getStrTeam());
            holder.year.setText(listOfTeams.get(position).getIntFormedYear());
            holder.websit.setText(listOfTeams.get(position).getStrWebsite());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.mainDrawer.setVisibility(View.GONE);
                    MainActivity.teamDrawer.setVisibility(View.VISIBLE);
                    Bundle bundle = new Bundle();
                    bundle.putString("idTeam", listOfTeams.get(position).getIdTeam());
                    TeamDetails teamDetails = new TeamDetails();
                    teamDetails.setArguments(bundle);
                    MainActivity.activity.getSupportFragmentManager()
                            .beginTransaction()
                            .addToBackStack(TeamsOfLeague.class.getName())
                            .replace(R.id.main_frame, teamDetails)
                            .commit();
                    MainActivity.mainTextView.setText(listOfTeams.get(position).getStrLeague());
                }

            });
        }

    }

    @Override
    public int getItemCount() {
        return listOfTeams == null ? 0 : listOfTeams.size();
    }

    public void setClickListener(OnWebClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public interface OnWebClickListener{
         void onClick(View view , int position);
    }
}
