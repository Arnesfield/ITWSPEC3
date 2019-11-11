package com.arnesfield.school.mp9;

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
 * Created by User on 06/07.
 */

public final class Item {

    private static final int[] ITEM_SMALL_THUMBS = {
            R.mipmap.img_cart_3,
            R.mipmap.img_cart_1,
            R.mipmap.img_cart_4,
            R.mipmap.img_cart_2
    };

    private static final int[] ITEM_LARGE_THUMBS = {
            R.drawable.img_cart_3,
            R.drawable.img_cart_1,
            R.drawable.img_cart_4,
            R.drawable.img_cart_2
    };

    private static final ArrayList<Item> itemList = new ArrayList<>();

    public static Item getItemAtPos(int pos) {
        return itemList.get(pos);
    }

    public static void addItem(int id, String name, String desc, double price, float rating, int smallImageResource, int largeImageResource) {
        itemList.add(new Item(id, name, desc, price, rating, smallImageResource, largeImageResource));
    }

    public static void clearList() {
        itemList.clear();
    }

    public static boolean isListEmpty() {
        return itemList.isEmpty();
    }

    public static int getThumbAtPos(int pos, boolean isSmall) {
        int curr = pos;
        while (curr > ITEM_SMALL_THUMBS.length-1)
            curr -= ITEM_SMALL_THUMBS.length;

        return isSmall ? ITEM_SMALL_THUMBS[curr] : ITEM_LARGE_THUMBS[curr];
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
            iv_main.setImageResource(item.getSmallImageResource());

            return view;
        }
    }

    public static ArrayAdapter<Item> getAdapter(@NonNull Context context, @LayoutRes int resource) {
        return new ItemAdapter(context, resource);
    }

    private int id, smallImageResource, largeImageResource;
    private String name, desc;
    private double price;
    private float rating;

    public Item(int id, String name, String desc, double price, float rating, int smallImageResource, int largeImageResource) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.rating = rating;
        this.smallImageResource = smallImageResource;
        this.largeImageResource = largeImageResource;
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

    public int getSmallImageResource() {
        return smallImageResource;
    }

    public int getLargeImageResource() {
        return largeImageResource;
    }
}
