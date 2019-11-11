package com.katrina.mp7_kat;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User on 06/03.
 */

public class Book {

    private static final ArrayList<Book> cartItems = new ArrayList<>();
    private static final ArrayList<Book> myItems = new ArrayList<>();
    private static ArrayAdapter<Book> itemAdapter;

    private String title, author, desc;
    private double price;

    public static void setListBasedOnString(String str) {
        myItems.clear();
        for (String s : str.split("[|]")) {
            for (int i = 0; i < cartItems.size(); i++) {
                Book item = cartItems.get(i);
                // check s if exist in cart
                if (s.equals(item.getTitle())) {
                    addToList(i);
                }
            }
        }
    }

    public static String getStringOfNames() {
        String str = "";
        for (Book item : myItems) {
            str += item.getTitle() + "|";
        }
        return str;
    }

    public static void addDefaultItems() {
        cartItems.clear();
        addToCart(
                "The Count of Monte Cristo",
                "Alexandre Dumas",
                "The story of Edmond Dantes and his road to revenge.",
                580
        );

        addToCart(
                "Pride and Prejudice",
                "Jane Austen",
                "The civilized sparring between the proud Mr. Darcy and" +
                "the prejudiced Elizabeth Bennet as they play out their spirited" +
                "courtship in a series of 18th century drawing-room intrigues.",
                580
        );

        addToCart(
                "A Midsummer Night's Dream",
                "William Shakespeare",
                "A confusing and complicated love polygon.",
                374
        );

        addToCart(
                "Les Misérables",
                "Victor Hugo",
                "An extravagant spectacle that dazzles the senses even as it touches the heart.",
                390
        );

        addToCart(
                "To Kill a Mockingbird",
                "Harper Lee",
                "The unforgettable novel of a childhood in a sleepy Southern town.",
                450
        );
    }

    public static String[] getStringArrayCart() {
        final String[] names = new String[cartItems.size()];
        for (int i = 0; i < names.length; i++) {
            Book item = cartItems.get(i);
            names[i] = item.getTitle() + " " + item.getFormattedPrice();
        }
        return names;
    }

    public static void removeMyItem(int position) {
        myItems.remove(position);
    }

    public static double getTotal() {
        double total = 0;
        for (Book item : myItems) {
            total += item.getPrice();
        }
        return total;
    }

    public static String getFormattedTotal() {
        return "₱"+String.format("%.2f", getTotal());
    }

    public static boolean isMyListEmpty() {
        return myItems.isEmpty();
    }

    private static class ItemAdapter extends ArrayAdapter<Book> {
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
            TextView tv_author = (TextView) view.findViewById(R.id.tv_subtitle);
            TextView tv_price = (TextView) view.findViewById(R.id.tv_price);

            Book item = getItem(position);

            tv_name.setText( item.getTitle() );
            tv_author.setText( item.getAuthor() );
            tv_price.setText( item.getFormattedPrice() );

            return view;
            // return super.getView(position, convertView, parent);
        }
    }

    public static ArrayAdapter<Book> setItemAdapter(Context context, int resouce) {
        if (itemAdapter == null)
            itemAdapter = new ItemAdapter(context, resouce);
        return itemAdapter;
    }

    public static ArrayAdapter<Book> getItemAdapter() {
        return itemAdapter;
    }

    private Book(String name, String author, String desc, double price) {
        this.title = name;
        this.author = author;
        this.desc = desc;
        this.price = price;
    }

    public static Book get(boolean fromCart, int position) {
        return fromCart ? cartItems.get(position) : myItems.get(position);
    }

    public static void addToCart(String name, String author, String desc, double price) {
        Book item = new Book(name, author, desc, price);
        cartItems.add(item);
    }

    public static void addToList(int position) {
        myItems.add(Book.get(true, position));
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDesc() {
        return desc;
    }

    public double getPrice() {
        return price;
    }

    public String getFormattedPrice() {
        return "₱"+String.format("%.2f", price);
    }
}
