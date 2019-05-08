package com.example.footballfans.Activites;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Vibrator;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.footballfans.Fragments.AllLeagues;
import com.example.footballfans.Fragments.NextMatches;
import com.example.footballfans.Fragments.PlayersOfTeam;
import com.example.footballfans.Fragments.Search;
import com.example.footballfans.Fragments.TableOfLeague;
import com.example.footballfans.Fragments.TeamDetails;
import com.example.footballfans.R;
import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    DrawerLayout mDrawer;
    public static CircularImageView openDrawer;
    public static TextView mainTextView;
    FrameLayout mFrameLayout_main;
    public static AppCompatActivity activity;
    public static ImageView headerImage;
    private LinearLayout teamPlayers , goToHomePage , logOut , tableOfLeague , searchLayout ;
    public  static  LinearLayout leagueTable;
    public static View teamDrawer;
    public static ScrollView mainDrawer;
    public static int fragNum , searNum ;
    Dialog dialog;
    Vibrator mVibrator;
    Animation mAnimationShake;
    TextInputLayout searchTextInputLayout;
    EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//  (GMT+02:00) Egypt Time
        initView();
        action();
    }

    private void initView(){
        mainTextView = findViewById(R.id.mainText);
        openDrawer = findViewById(R.id.openDrawer);
        mDrawer = findViewById(R.id.mDrawer);
        mFrameLayout_main = findViewById(R.id.main_frame);
        headerImage = findViewById(R.id.headerImage);
        teamPlayers = findViewById(R.id.teamPlayers);
        activity = this;
        teamDrawer = findViewById(R.id.teamDrawer);
        mainDrawer = findViewById(R.id.mainDrawer);
        goToHomePage = findViewById(R.id.goToHomePage);
        logOut = findViewById(R.id.logOut);
        tableOfLeague = findViewById(R.id.tableOfLeague);
        leagueTable = findViewById(R.id.leagueTable);
        searchLayout = findViewById(R.id.searchLayout);
        searchTextInputLayout = findViewById(R.id.searchTextInputLayout);
        searchEditText = findViewById(R.id.searchEditText);
        mAnimationShake = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake);
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e("Token is ",newToken);
            }
        });



    }

    private void action(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frame, new AllLeagues())
                .commit();

        openDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.openDrawer(GravityCompat.START);
            }
        });

        teamPlayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(TeamDetails.class.getName())
                        .replace(R.id.main_frame, new PlayersOfTeam())
                        .commit();
            }
        });

        goToHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(TeamDetails.class.getName())
                        .replace(R.id.main_frame, new AllLeagues())
                        .commit();
                teamDrawer.setVisibility(View.GONE);
                mainDrawer.setVisibility(View.VISIBLE);
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitDialog();
            }
        });

        tableOfLeague.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(TeamDetails.class.getName())
                        .replace(R.id.main_frame, new TableOfLeague())
                        .commit();
            }
        });

        leagueTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(TeamDetails.class.getName())
                        .replace(R.id.main_frame, new TableOfLeague())
                        .commit();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Fragment fg = getSupportFragmentManager().findFragmentById(R.id.main_frame);

        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        }else if (fg.getClass().getName().equals(new AllLeagues().getClass().getName())){
            exitDialog();
        }else  {
            super.onBackPressed();
        }



        if (fragNum == 1 | fragNum == 2){
            teamDrawer.setVisibility(View.GONE);
            mainDrawer.setVisibility(View.VISIBLE);
            openDrawer.setVisibility(View.VISIBLE);
        }else if (fragNum == 4){
            openDrawer.setVisibility(View.GONE);
        } else {
            teamDrawer.setVisibility(View.VISIBLE);
            mainDrawer.setVisibility(View.GONE);
        }
    }

    public void teamMatches(View view) {
        mDrawer.closeDrawer(GravityCompat.START);
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(TeamDetails.class.getName())
                .replace(R.id.main_frame, new NextMatches())
                .commit();
    }

    private void exitDialog(){
        dialog = new Dialog(this,android.R.style.Theme_Black_NoTitleBar);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setContentView(R.layout.card_dialog);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        Button ok = dialog.findViewById(R.id.ok_dialog);
        Button cancel = dialog.findViewById(R.id.cancel_dialog);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                MainActivity.this.finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

    }

    public void logout(View view) {
        exitDialog();
    }

    public void search(View view) {

        if (searchEditText.getText().toString().isEmpty()){
            searchTextInputLayout.setErrorEnabled(true);
            searchTextInputLayout.setError(getText(R.string.enter_name_plz));
            searchEditText.setAnimation(mAnimationShake);
            searchEditText.startAnimation(mAnimationShake);
            mVibrator.vibrate(120);
        }else {
            String name = searchEditText.getText().toString().toLowerCase();
            if (searNum == 2){
                Bundle bundle = new Bundle();
                bundle.putString("textSearch",name);
                bundle.putInt("number",searNum);
                Search searchPlayer = new Search();
                searchPlayer.setArguments(bundle);

                mDrawer.closeDrawer(GravityCompat.START);
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(TeamDetails.class.getName())
                        .replace(R.id.main_frame, searchPlayer)
                        .commit();
            }else if (searNum == 1){
                Bundle bundle = new Bundle();
                bundle.putString("textSearch",name);
                bundle.putInt("number",searNum);
                Search searchTeam = new Search();
                searchTeam.setArguments(bundle);

                mDrawer.closeDrawer(GravityCompat.START);
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(TeamDetails.class.getName())
                        .replace(R.id.main_frame, searchTeam)
                        .commit();
            }
        }

        searchLayout.setVisibility(View.GONE);
    }

    public void TeamByName(View view) {
        if (searNum == 2){
            searNum = 1;
            searchEditText.setHint(getText(R.string.enter_name_team));
        }
        searNum = 1;
        if (searchLayout.getVisibility() == View.GONE) {
                searchLayout.setVisibility(View.VISIBLE);
                searchEditText.getText().clear();
                searchEditText.setHint(getText(R.string.enter_name_team));
            }else{
                searchLayout.setVisibility(View.GONE);
            }

    }

    public void PlayerByName(View view) {
        if (searNum == 1){
            searNum = 2;
            searchEditText.setHint(getText(R.string.enter_name_player));
        }
        searNum = 2;
        if (searchLayout.getVisibility() == View.GONE) {
            searchLayout.setVisibility(View.VISIBLE);
            searchEditText.getText().clear();
            searchEditText.setHint(getText(R.string.enter_name_player));
        }else{
            searchLayout.setVisibility(View.GONE);
        }
    }

    public void dayMatches(View view) {

        searNum = 3;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = simpleDateFormat.format(new Date());
        Log.e("<<<<todayDate",todayDate);

        Calendar yesCal = Calendar.getInstance();
        yesCal.setTime(new Date());
        yesCal.add(Calendar.DATE,-1);
        String yesterdayDate = simpleDateFormat.format(yesCal.getTime());
        Log.e("<<<<yesterday",yesterdayDate);

        Calendar tomCal  = Calendar.getInstance();
        tomCal.setTime(new Date());
        tomCal.add(Calendar.DATE,+1);
        String tomorrowDate = simpleDateFormat.format(tomCal.getTime());
        Log.e("<<<<tomorrow",tomorrowDate);

        Bundle bundle = new Bundle();
        bundle.putString("todayDate",todayDate);
        bundle.putString("yesterdayDate",yesterdayDate);
        bundle.putString("tomorrowDate",tomorrowDate);
        bundle.putInt("number",searNum);
        Search searchTeam = new Search();
        searchTeam.setArguments(bundle);

        mDrawer.closeDrawer(GravityCompat.START);
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(TeamDetails.class.getName())
                .replace(R.id.main_frame, searchTeam)
                .commit();
    }
}
