package com.cayle.mp7_cayle;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User on 06/01.
 */

public class Item {

    private static final ArrayList<Item> cartItems = new ArrayList<>();
    private static final ArrayList<Item> myItems = new ArrayList<>();
    private static ArrayAdapter<Item> itemAdapter;

    private String name, desc;
    private double price;
    private int smallImageResource, largeImageResource;

    public static void setListBasedOnString(String str) {
        myItems.clear();
        for (String s : str.split("[|]")) {
            for (int i = 0; i < cartItems.size(); i++) {
                Item item = cartItems.get(i);
                // check s if exist in cart
                if (s.equals(item.getName())) {
                    addToList(i);
                }
            }
        }
    }

    public static String getStringOfNames() {
        String str = "";
        for (Item item : myItems) {
            str += item.getName() + "|";
        }
        return str;
    }

    public static void addDefaultItems() {
        cartItems.clear();
        addToCart("Coke", "Refreshing", 20, R.mipmap.img_coke, R.drawable.img_coke);
        addToCart("Burger", "Meaty", 40, R.mipmap.img_burger, R.drawable.img_burger);
        addToCart("Fries", "Crispy", 30, R.mipmap.img_fries, R.drawable.img_fries);
        addToCart("Chocolate", "Sweet", 70, R.mipmap.img_chocolate, R.drawable.img_chocolate);
        addToCart("Ice Cream", "Cool", 20, R.mipmap.img_ice_cream, R.drawable.img_ice_cream);
        addToCart("Doughnut", "Tasty", 120, R.mipmap.img_doughnut, R.drawable.img_doughnut);
    }

    public static String[] getStringArrayCart() {
        final String[] names = new String[cartItems.size()];
        for (int i = 0; i < names.length; i++) {
            Item item = cartItems.get(i);
            names[i] = item.getName() + " " + item.getFormattedPrice();
        }
        return names;
    }

    public static void removeMyItem(int position) {
        myItems.remove(position);
    }

    public static double getTotal() {
        double total = 0;
        for (Item item : myItems) {
            total += item.getPrice();
        }
        return total;
    }

    public static String getFormattedTotal() {
        return "P"+String.format("%.2f", getTotal());
    }

    public static boolean isMyListEmpty() {
        return myItems.isEmpty();
    }

    private static class ItemAdapter extends ArrayAdapter<Item> {
        public ItemAdapter(@NonNull Context context, @LayoutRes int resource) {
            super(context, resource, myItems);
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
            TextView tv_price = (TextView) view.findViewById(R.id.tv_price);
            ImageView iv_main = (ImageView) view.findViewById(R.id.iv_main);

            Item item = getItem(position);

            tv_name.setText( item.getName() );
            tv_price.setText( item.getFormattedPrice() );
            iv_main.setImageResource( item.getSmallImageResource() );

            return view;
            // return super.getView(position, convertView, parent);
        }
    }

    public static ArrayAdapter<Item> setItemAdapter(Context context, int resouce) {
        if (itemAdapter == null)
            itemAdapter = new ItemAdapter(context, resouce);
        return itemAdapter;
    }

    public static ArrayAdapter<Item> getItemAdapter() {
        return itemAdapter;
    }

    private Item(String name, String desc, double price, int smallImageResource, int largeImageResource) {
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.smallImageResource = smallImageResource;
        this.largeImageResource = largeImageResource;
    }

    public static Item get(boolean fromCart, int position) {
        return fromCart ? cartItems.get(position) : myItems.get(position);
    }

    public static void addToCart(String name, String desc, double price, int smallImageResource, int largeImageResource) {
        Item item = new Item(name, desc, price, smallImageResource, largeImageResource);
        cartItems.add(item);
    }

    public static void addToList(int position) {
        myItems.add(Item.get(true, position));
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
