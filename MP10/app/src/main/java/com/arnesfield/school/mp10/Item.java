package com.arnesfield.school.mp10;

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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by User on 06/07.
 */

public final class Item {

    private static final ArrayList<Item> itemList = new ArrayList<>();

    public static Item getItemAtPos(int pos) {
        return itemList.get(pos);
    }

    public static void addItem(
            int id, String name, String desc, double price, float rating, String imageResource
    ) {
        itemList.add(new Item(id, name, desc, price, rating, imageResource));
    }

    public static void clearList() {
        itemList.clear();
    }

    public static boolean isListEmpty() {
        return itemList.isEmpty();
    }

    public static class ItemAdapter extends ArrayAdapter<Item> {
        public ItemAdapter(@NonNull Context context, @LayoutRes int resource) {
            super(context, resource, itemList);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = convertView;

            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.list_item_inst, null);
            }

            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
            TextView tv_subtitle = (TextView) view.findViewById(R.id.tv_subtitle);
            ImageView iv_main = (ImageView) view.findViewById(R.id.iv_main);

            final Item item = getItem(position);

            tv_title.setText(item.getName());
            tv_subtitle.setText(item.getFormattedPrice());

            // use picasso here
            Picasso.with(getContext())
                    .load(item.getImageResource())
                    .resize(72, 72)
                    .centerCrop()
                    .into(iv_main);

            return view;
        }
    }

    public static ArrayAdapter<Item> getAdapter(@NonNull Context context, @LayoutRes int resource) {
        return new ItemAdapter(context, resource);
    }

    private int id;
    private String name, desc, imageResource;
    private double price;
    private float rating;

    public Item(int id, String name, String desc, double price, float rating, String smallImageResource) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.rating = rating;
        this.imageResource = smallImageResource;
    }

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

    public float getRating() {
        return rating;
    }

    public String getImageResource() {
        return imageResource;
    }
}
