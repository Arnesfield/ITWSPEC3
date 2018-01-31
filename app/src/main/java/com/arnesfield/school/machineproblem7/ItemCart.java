package com.arnesfield.school.machineproblem7;

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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by User on 05/27.
 */

public final class ItemCart {
    private static ArrayList<Item> LIST_OF_ITEMS = new ArrayList<>();
    private ArrayAdapter<Item> itemArrayAdapter;

    public static String getStringIdsFrom(ItemCart itemCart) {
        String idList = "";
        ArrayList<Item> list = itemCart.getListOfItems();
        for (Item item : list) {
            // number of time to add
            for (int i = 0; i < item.getQuantity(); i++)
                idList += item.getId() + ":";
        }
        // idList = idList.substring(0, idList.length()-1);
        return idList;
    }

    public static ArrayList<Item> getListFromStringIds(String idList) {

        final ArrayList<Integer> list = new ArrayList<>();
        String integer = "";
        for (char c : idList.toCharArray()) {
            if (c == ':') {
                list.add(Integer.parseInt(integer));
                integer = "";
                continue;
            }
            integer += c;
        }

        // sort list
        Collections.sort(list);

        final ArrayList<Item> itemList = new ArrayList<>();

        int currval = -1, count = 0;
        for (int id : list) {
            Item item = ItemCart.getItemOfIdFrom(LIST_OF_ITEMS, id);
            if (currval == -1) {
                itemList.add(item);
                currval = id;
            }
            else if (currval != id) {
                itemList.add(item);
                currval = id;
                count = 0;
            }
            item.setQuantity(++count);
        }

        return itemList;
    }

    public boolean isEmpty() {
        return listOfItems.isEmpty();
    }

    private class ItemAdapter extends ArrayAdapter<Item> {
        public ItemAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Item> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = convertView;

            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.view_item_inst, null);
            }

            final Item item = listOfItems.get(position);

            final TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
            final TextView tv_subtitle = (TextView) view.findViewById(R.id.tv_subtitle);
            final TextView tv_subtitle2 = (TextView) view.findViewById(R.id.tv_subtitle2);
            final ImageView iv_main = (ImageView) view.findViewById(R.id.iv_main);

            tv_title.setText(item.getName());
            tv_subtitle.setText(item.getFormattedPrice());
            tv_subtitle2.setText("Qty: " + item.getQuantity());
            iv_main.setImageResource(item.getSmallImageResource());

            // return super.getView(position, convertView, parent);
            return view;
        }
    }

    public static final ArrayList<Item> getCartList() {
        return LIST_OF_ITEMS;
    }

    public static final void setCartList(ArrayList<Item> list) {
        ItemCart.LIST_OF_ITEMS = list;
    }

    public static final ItemCart create() {
        return new ItemCart();
    }

    public static final Item getCartItem(int index) {
        return LIST_OF_ITEMS.get(index);
    }

    public static String[] getNamesFrom(ArrayList<Item> list) {
        final String[] names = new String[list.size()];
        for (int i = 0; i < names.length; i++) {
            Item item = list.get(i);
            names[i] = item.getName() + " " + item.getFormattedPrice();
        }
        return names;
    }

    public static final boolean[] getCheckedItemsFrom(ArrayList<Item> list, ArrayList<Item> cart) {
        final boolean[] checkedItems = new boolean[cart.size()];
        // if items in cart matches items in item cart
        for (Item curr_item : list) {
            // get index of item
            for (int i = 0; i < cart.size(); i++) {
                if (cart.get(i).equals(curr_item)) {
                    checkedItems[i] = true;
                    break;
                }
            }
        }
        return checkedItems;
    }

    public static final void ADD_CART() {
        // default list
        try {
            // add static items
            ArrayList<Item> cartList = LIST_OF_ITEMS;
            Item.addTo(cartList, "1", "Pencil", R.mipmap.img_pencil, R.drawable.img_pencil, "Write things", "10", "4", "1");
            Item.addTo(cartList, "2", "Bottled Water", R.mipmap.img_water, R.drawable.img_water, "Cool drink", "15", "3.5", "1");
            Item.addTo(cartList, "3", "Food", R.mipmap.img_food, R.drawable.img_food, "Fill your stomach", "160", "4", "1");
            Item.addTo(cartList, "4", "Paper", R.mipmap.img_paper, R.drawable.img_paper, "To be written on", "22", "3", "1");
            Item.addTo(cartList, "5", "Notebook", R.mipmap.img_notebook, R.drawable.img_notebook, "To be written on", "28", "4.5", "1");
            Item.addTo(cartList, "6", "Calculator", R.mipmap.img_calculator, R.drawable.img_calculator, "To calculate things", "356", "4", "1");
            Item.addTo(cartList, "7", "School Bag", R.mipmap.img_bag, R.drawable.img_bag, "To keep things", "240", "4.5", "1");
            Item.addTo(cartList, "8", "Hand Soap", R.mipmap.img_soap, R.drawable.img_soap, "Clean your hands", "120", "4", "1");
            Item.addTo(cartList, "9", "Cup of Coffee", R.mipmap.img_coffee, R.drawable.img_coffee, "To become hyper", "50", "4", "1");
            Item.addTo(cartList, "10", "Tissue", R.mipmap.img_tissue, R.drawable.img_tissue, "Wipe things", "30", "3.5", "1");
            Item.addTo(cartList, "11", "Camera", R.mipmap.img_camera, R.drawable.img_camera, "Capture moments", "1200", "5", "1");
        } catch (Exception e) {}
    }

    public static Item getItemOfIdFrom(ArrayList<Item> list, int id) {
        for (Item item : list) {
            if (item.getId().equals(String.valueOf(id)))
                return item;
        }
        return null;
    }

    private ArrayList<Item> listOfItems;

    // constructor
    private ItemCart() {
        listOfItems = new ArrayList<>();
    }

    // instance methods
    public void add(Item item) {
        listOfItems.add(item);
    }

    public Item getItem(int position) {
        return listOfItems.get(position);
    }

    public ArrayList<Item> getListOfItems() {
        return listOfItems;
    }

    public ArrayAdapter<Item> setAdapter(Context context, int resource) {
        if (itemArrayAdapter == null)
            itemArrayAdapter = new ItemAdapter(context, resource, listOfItems);
        return itemArrayAdapter;
    }

    public ArrayAdapter<Item> getAdapter() {
        return itemArrayAdapter;
    }

    public void setListOfItems(ArrayList<Item> listOfItems) {
        this.listOfItems = listOfItems;
    }

    public String getFormattedTotalPrice() {
        double total = 0;
        for (Item item : listOfItems)
            total += item.getPrice();
        return String.format("₱%.2f", total);
    }
}
