package com.example.footballfans.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.footballfans.Models.ModelTable2;
import com.example.footballfans.R;

import java.util.List;

public class AdapterTableOfLeague extends RecyclerView.Adapter<AdapterTableOfLeague.TableHolder> {

    private Context context ;
    private List<ModelTable2> tableList;

    public AdapterTableOfLeague (Context context , List<ModelTable2> tableList){
        this.context = context;
        this.tableList = tableList;
    }

    public class TableHolder extends RecyclerView.ViewHolder{
        TextView tableTeamArrange , tableTeamName ,TeamPlayed , TeamWin , TeamDraw ,
                TeamLoss , TeamGoals , TeamGoalsAgainst , TeamGoalsDifference , TeamPoints;
        public TableHolder(@NonNull View itemView) {
            super(itemView);
            tableTeamArrange = itemView.findViewById(R.id.tableTeamArrange);
            tableTeamName = itemView.findViewById(R.id.tableTeamName);
            TeamPlayed = itemView.findViewById(R.id.TeamPlayed);
            TeamWin = itemView.findViewById(R.id.TeamWin);
            TeamDraw = itemView.findViewById(R.id.TeamDraw);
            TeamLoss = itemView.findViewById(R.id.TeamLoss);
            TeamGoals = itemView.findViewById(R.id.TeamGoals);
            TeamGoalsAgainst = itemView.findViewById(R.id.TeamGoalsAgainst);
            TeamGoalsDifference = itemView.findViewById(R.id.TeamGoalsDifference);
            TeamPoints = itemView.findViewById(R.id.TeamPoints);
        }
    }

    @NonNull
    @Override
    public TableHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.rv_table_of_league,viewGroup,false);
        return new TableHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TableHolder holder, int position) {
        holder.tableTeamArrange.setText(String.valueOf(position + 1));
        holder.tableTeamName.setText(tableList.get(position).getName());
        holder.TeamPlayed.setText(tableList.get(position).getPlayed());
        holder.TeamWin.setText(tableList.get(position).getWin());
        holder.TeamDraw.setText(tableList.get(position).getDraw());
        holder.TeamLoss.setText(tableList.get(position).getLoss());
        holder.TeamGoals.setText(tableList.get(position).getGoalsfor());
        holder.TeamGoalsAgainst.setText(tableList.get(position).getGoalsagainst());
        holder.TeamGoalsDifference.setText(tableList.get(position).getGoalsdifference());
        holder.TeamPoints.setText(tableList.get(position).getTotal());
    }

    @Override
    public int getItemCount() {
        return tableList.size();
    }


}
