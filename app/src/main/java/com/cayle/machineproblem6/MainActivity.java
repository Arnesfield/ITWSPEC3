package com.cayle.machineproblem6;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements ListFragment.OnItemClickInterface {

    private static void DO_DEFAULT() {
        // set items here
        Item.addItem("Coke", "Refreshing", 20, R.mipmap.img_coke, R.drawable.img_coke);
        Item.addItem("Burger", "Meaty", 40, R.mipmap.img_burger, R.drawable.img_burger);
        Item.addItem("Fries", "Crispy", 30, R.mipmap.img_fries, R.drawable.img_fries);
        Item.addItem("Chocolate", "Sweet", 70, R.mipmap.img_chocolate, R.drawable.img_chocolate);
        Item.addItem("Ice Cream", "Cool", 20, R.mipmap.img_ice_cream, R.drawable.img_ice_cream);
        Item.addItem("Doughnut", "Tasty", 120, R.mipmap.img_doughnut, R.drawable.img_doughnut);
    }

    private static boolean BOOL_DO = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            savedInstanceState.clear();
        } catch (Exception e) {}

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (BOOL_DO) {
            DO_DEFAULT();
            BOOL_DO = false;
        }


        Fragment fragment = new ListFragment();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.view_frag_list, fragment);
        fragmentTransaction.commit();


    }

    @Override
    public void onItemClick(Item item) {
        // check if orientation is landscape
        Fragment fragment = new ViewFragment(item);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // if landscape, replace on new view
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fragmentTransaction.replace(R.id.view_frag_view, fragment);
        }
        // if portrait, add to stack
        else {
            fragmentTransaction.add(R.id.view_frag_list, fragment);
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.commit();
    }
}
