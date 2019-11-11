package com.sherry.feutech.mp6_sherry;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements ListViewFragment.ListItemClickListener {

    private static int currPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            savedInstanceState.clear();
        } catch (Exception e) {}
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Anime.DEFAULT_ADD();

        // set fragment
        Fragment fragment = new ListViewFragment();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.view_for_list, fragment);
        transaction.commit();

        onItemClick(currPos);
    }

    @Override
    public void onItemClick(int position) {
        currPos = position;
        if (currPos == -1)
            return;

        Fragment fragment = new AnimeViewFragment(position);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        // check position
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            transaction.add(R.id.view_for_list, fragment);
            transaction.addToBackStack(null);
        }
        // if landscape
        else {
            transaction.replace(R.id.view_for_info, fragment);
            currPos = -1;
        }

        transaction.commit();
    }
}
