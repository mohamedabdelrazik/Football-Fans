package com.example.footballfans.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.footballfans.Activites.MainActivity;
import com.example.footballfans.Fragments.AllLeagues;
import com.example.footballfans.Fragments.TeamsOfLeague;
import com.example.footballfans.Models.ModelAllLeagues2;
import com.example.footballfans.R;

import java.util.List;

public class AdapterAllLeagues extends RecyclerView.Adapter<AdapterAllLeagues.LeaguesViewHolder>{

    private Context context;
    private List<ModelAllLeagues2> leaguesList;

    public AdapterAllLeagues (Context context , List<ModelAllLeagues2> leaguesList){
        this.context = context;
        this.leaguesList = leaguesList;
    }

    public class LeaguesViewHolder extends RecyclerView.ViewHolder {
        TextView nameOfLeague;
        public LeaguesViewHolder(@NonNull View itemView) {
            super(itemView);
            nameOfLeague = itemView.findViewById(R.id.le_name);
        }
    }

    @NonNull
    @Override
    public LeaguesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.rv_competitions,parent,false);
        return new LeaguesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaguesViewHolder holder, final int position) {

        if (leaguesList.get(position).getStrSport().equals("Soccer")){
                holder.nameOfLeague.setText(leaguesList.get(position).getStrLeague());

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("idLeague",leaguesList.get(position).getIdLeague());
                TeamsOfLeague teamsOfLeague = new TeamsOfLeague();
                teamsOfLeague.setArguments(bundle);
                MainActivity.activity.getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(AllLeagues.class.getName())
                        .replace(R.id.main_frame,teamsOfLeague)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 13;
    }


}
