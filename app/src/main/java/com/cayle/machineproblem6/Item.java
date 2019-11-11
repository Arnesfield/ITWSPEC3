package com.cayle.machineproblem6;

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

/**
 * Created by User on 05/23.
 */

public class Item {

    private static final ArrayList<Item> ITEMS = new ArrayList<>();

    private String name, desc;
    private double price;
    private int smallImageResource, largeImageResource;

    private static class ItemAdapter extends ArrayAdapter<Item> {
        public ItemAdapter(@NonNull Context context, @LayoutRes int resource) {
            super(context, resource, ITEMS);
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

            Item item = ITEMS.get(position);

            tv_name.setText( item.getName() );
            iv_main.setImageResource( item.getSmallImageResource() );

            return view;
            // return super.getView(position, convertView, parent);
        }
    }

    public static ArrayAdapter<Item> getItemAdapter(Context context, int resouce) {
        return new ItemAdapter(context, resouce);
    }

    private Item(String name, String desc, double price, int smallImageResource, int largeImageResource) {
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.smallImageResource = smallImageResource;
        this.largeImageResource = largeImageResource;
    }

    public static Item getItem(int position) {
        return ITEMS.get(position);
    }

    public static void addItem(String name, String desc, double price, int smallImageResource, int largeImageResource) {
        Item item = new Item(name, desc, price, smallImageResource, largeImageResource);
        ITEMS.add(item);
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
        return "P"+String.format("%.2f", price);
    }

    public int getSmallImageResource() {
        return smallImageResource;
    }

    public int getLargeImageResource() {
        return largeImageResource;
    }
}
