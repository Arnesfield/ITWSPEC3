package com.sherry.feutech.mp6_sherry;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by User on 05/28.
 */

public class Anime {
    private static final ArrayList<Anime> listOfAnime = new ArrayList<>();
    private static final ArrayList<String> listOfNames = new ArrayList<>();
    private static ArrayAdapter<String> animeAdapter;

    public static ArrayAdapter<String> getAdapter(Context context, int resource) {
        if (animeAdapter == null)
            animeAdapter = new ArrayAdapter<>(context, resource, getListOfNames());
        return animeAdapter;
    }

    private static ArrayList<String> getListOfNames() {
        listOfNames.clear();
        for (Anime anime : listOfAnime)
            listOfNames.add(anime.getName());
        return listOfNames;
    }

    public static void add(String name, String power, String birthday, String height, String weight, String food, int imageResource) {
        Anime anime = new Anime(name, power, birthday, height, weight, food, imageResource);
        listOfAnime.add(anime);
    }

    public static Anime get(int position) {
        return listOfAnime.get(position);
    }

    public static void DEFAULT_ADD() {
        listOfAnime.clear();
        add("Nice", "Sonic Minimum and Ego", "May 10, 1997", "178cm", "60kg", null, R.drawable.nice);
        add("Murasaki", "Power Minimum/All or Nothing Minimum", "April 5, 1993", "185cm", "70kg", null, R.drawable.murasaki);
        add("Ratio", "Perspective Minimum", "September 3 1996", null, null, null, R.drawable.ratio);
        add("Birthday", "Lightning Minimum", null, null, null, null, R.drawable.birthday);
        add("Hajime", "Nihilist Minimum", null, null, null, "Hamburger", R.drawable.hajime);
        add("Art", "Regeneration Minimum", "February 25, 1993", "178cm", "57kg", null, R.drawable.art);
        add("Honey", "Analysis Minimum", null, null, null, null, R.drawable.honey);
        add("Three", "Beast Power", null, null, null, null, R.drawable.three);
    }

    private Anime(String name, String power, String birthday, String height, String weight, String food, int imageResource) {
        this.name = name;
        this.birthday = birthday;
        this.power = power;
        this.height = height;
        this.weight = weight;
        this.food = food;
        this.imageResource = imageResource;
    }

    private final String name, birthday, power, height, weight, food;
    private final int imageResource;

    public String getName() {
        return name;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getPower() {
        return power;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public String getFood() {
        return food;
    }

    public int getImageResource() {
        return imageResource;
    }
}
