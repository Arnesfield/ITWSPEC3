package com.arnesfield.school.mp9_samp;

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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 06/30.
 */

public class Item {

    // item adapter
    public static class ItemListAdapter extends ArrayAdapter<Item> {

        public ItemListAdapter(@NonNull Context context, @LayoutRes int resource) {
            super(context, resource, LIST_OF_ITEMS);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = convertView;

            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.layout_list_item_inst, null);
            }

            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
            TextView tv_subtitle = (TextView) view.findViewById(R.id.tv_subtitle);
            ImageView iv_main = (ImageView) view.findViewById(R.id.iv_main);

            final Item item = getItem(position);

            tv_title.setText(item.getName());
            tv_subtitle.setText(item.getFormattedPrice());
            // iv_main.setImageResource(item.getSmallImageResource());

            return view;
        }
    }

    // class
    public static final ArrayList<Item> LIST_OF_ITEMS = new ArrayList<>();

    // instance
    private int id;
    private String name, desc;
    private double price;

    // constructor
    public Item(int id, String name, String desc, double price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }


    // accessors
    public int getId() {
        return id;
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
        return String.format("₱%.2f", getPrice());
    }
}
