package com.example.footballfans.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.footballfans.Models.ModelEvents2;
import com.example.footballfans.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class AdapterNextMatches extends RecyclerView.Adapter<AdapterNextMatches.MatchesHolder> {

    private Context context;
    private List<ModelEvents2> eventsList;
//    private Date matchDate;

    public AdapterNextMatches (Context context,List<ModelEvents2> eventsList){
        this.context = context;
        this.eventsList = eventsList;
    }

    public class MatchesHolder extends RecyclerView.ViewHolder {

        TextView league_name , matchDate , matchTime , home_team , away_team , home_team_score , away_team_score;
        LinearLayout matchScore , eventDate;
        public MatchesHolder(@NonNull View itemView) {
            super(itemView);
            league_name = itemView.findViewById(R.id.league_name);
            matchDate = itemView.findViewById(R.id.matchDate);
            matchTime = itemView.findViewById(R.id.matchTime);
            home_team = itemView.findViewById(R.id.home_team);
            away_team = itemView.findViewById(R.id.away_team);
            home_team_score = itemView.findViewById(R.id.home_team_score);
            away_team_score = itemView.findViewById(R.id.away_team_score);
            matchScore = itemView.findViewById(R.id.matchScore);
            eventDate = itemView.findViewById(R.id.eventDate);

        }
    }

    @NonNull
    @Override
    public AdapterNextMatches.MatchesHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.rv_next_matches,parent,false);
        return new MatchesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchesHolder holder, int position) {
        String time =  parseDat(eventsList.get(position).getStrTime());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        String todayDate = simpleDateFormat.format(today);
//        Log.e("><><todayDate",todayDate);
        String matchDate = eventsList.get(position).getDateEvent();

        if (todayDate.compareTo(matchDate)<0 | todayDate.compareTo(matchDate) == 0){
            holder.matchScore.setVisibility(View.GONE);
            holder.eventDate.setVisibility(View.VISIBLE);
        }else {
            holder.matchScore.setVisibility(View.VISIBLE);
            holder.away_team_score.setText(eventsList.get(position).getIntAwayScore());
            holder.home_team_score.setText(eventsList.get(position).getIntHomeScore());
            holder.eventDate.setVisibility(View.GONE);
        }
        holder.league_name.setText(eventsList.get(position).getStrLeague());
        holder.matchDate.setText(eventsList.get(position).getStrDate());
        holder.matchTime.setText(time);
        holder.home_team.setText(eventsList.get(position).getStrHomeTeam());
        holder.away_team.setText(eventsList.get(position).getStrAwayTeam());
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    private String parseDat (String time) {
        String inputPattern = "HH:mm:ss";
        String outputPattern = "h:mm a";
//        String timezoneID = TimeZone.getDefault().getID();
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern,Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern,Locale.getDefault());
        outputFormat.setTimeZone(TimeZone.getTimeZone("EET"));

        //(GMT+2:00) Africa/Cairo
        Date date ;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }


}
