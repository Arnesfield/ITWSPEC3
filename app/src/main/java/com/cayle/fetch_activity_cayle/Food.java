package com.cayle.fetch_activity_cayle;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by User on 06/04.
 */

public class Food {

    public static final int[][] IMAGE_RESOURCES = {
            // small
            {
                R.mipmap.img_coke, R.mipmap.img_burger, R.mipmap.img_fries,
                R.mipmap.img_chocolate, R.mipmap.img_ice_cream, R.mipmap.img_doughnut
            },
            {
                R.drawable.img_coke, R.drawable.img_burger, R.drawable.img_fries,
                R.drawable.img_chocolate, R.drawable.img_ice_cream, R.drawable.img_doughnut
            }
    };

    public static boolean isListEmpty() {
        return false;
    }

    public static class FoodAdapter extends ArrayAdapter<Food> {
        public FoodAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Food> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = convertView;

            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.item_layout, null);
            }

            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            ImageView iv_main = (ImageView) view.findViewById(R.id.iv_main);

            Food food = getItem(position);

            tv_name.setText( food.getName() );
            iv_main.setImageResource( food.getSmallImageResource() );

            return view;
            // return super.getView(position, convertView, parent);
        }
    }

    // instance
    private int smallImageResource, largeImageResource;
    private String name, desc;
    private double price;

    public Food(String name, String desc, double price, int smallImageResource, int largeImageResource) {
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.smallImageResource = smallImageResource;
        this.largeImageResource = largeImageResource;
    }

    public int getSmallImageResource() {
        return smallImageResource;
    }

    public int getLargeImageResource() {
        return largeImageResource;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public double getPrice() {
        return price;
    }

    public String getFormattedPrice() {
        return String.format("P%.2f", getPrice());
    }
}
