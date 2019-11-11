package com.cayle.mp8_cayle;

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

public class Item {

    public static class ItemAdapter extends ArrayAdapter<Item> {
        private List<Item> listOfItems;

        public ItemAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Item> objects) {
            super(context, resource, objects);
            listOfItems = objects;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = convertView;

            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.item_layout, null);
            }

            final Item item = listOfItems.get(position);

            final TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
            final TextView tv_price = (TextView) view.findViewById(R.id.tv_price);

            tv_title.setText(item.getName());
            tv_price.setText(item.getFormattedPrice());

            // return super.getView(position, convertView, parent);
            return view;
        }
    }

    // properties
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

    // mutators and accessors
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getPrice() {
        return price;
    }

    public String getFormattedPrice() {
        return String.format("₱%.2f", getPrice());
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
