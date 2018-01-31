package com.arnesfield.school.machineproblem7;

import java.util.ArrayList;

/**
 * Created by User on 05/27.
 */

public final class Item {
    private String id, name, desc;
    private double price, rating;
    private int quantity;
    private ItemCart itemCart;
    private int smallImageResource, largeImageResource;

    private Item(
            ItemCart itemCart, String id, String name, int smallImageResource, int largeImageResource,
            String desc, String price, String rating, String quantity
    ) throws Exception {
        this.itemCart = itemCart;
        this.id = verifyID(itemCart, id);
        this.name = verifyName(name);
        this.smallImageResource = smallImageResource;
        this.largeImageResource = largeImageResource;
        this.desc = desc;
        this.price = verifyPrice(price);
        this.rating = verifyRating(rating);
        this.quantity = verifyQuantity(quantity);
    }

    static Item addTo(
            ItemCart itemCart, String id, String name, int smallImageResource, int largeImageResource,
            String desc, String price, String rating, String quantity
    ) throws Exception {
        Item item = new Item(itemCart, id, name, smallImageResource, largeImageResource, desc, price, rating, quantity);
        try {
            itemCart.add(item);
        } catch (Exception e) {}
        return item;
    }

    static void addTo(ItemCart itemCart, Item item) throws Exception {
        try {
            itemCart.add(item);
        } catch (Exception e) {}
    }

    static Item addTo(
            ArrayList<Item> list, String id, String name, int smallImageResource, int largeImageResource,
            String desc, String price, String rating, String quantity
    ) throws Exception {
        Item item = new Item(null, id, name, smallImageResource, largeImageResource, desc, price, rating, quantity);
        list.add(item);
        return item;
    }

    static void addTo(ArrayList<Item> list, Item item) throws Exception {
        list.add(item);
    }

    // validations
    private static String verifyID(ItemCart itemCart, String id) throws DuplicateIDException, IDlessException {
        if (id.isEmpty() || id.matches("[\\s]+"))
            throw new IDlessException();
        for (Item item : itemCart.getCartList()) {
            if (id.equals(item.getId()))
                throw new DuplicateIDException();
        }
        return id;
    }

    private static String verifyName(String name) throws NamelessException {
        if (name.isEmpty() || name.matches("[\\s]+"))
            throw new NamelessException();
        return name;
    }

    private static double verifyPrice(String price) throws PricelessException, InvalidPriceException {
        if (price.isEmpty() || price.matches("[\\s]+"))
            throw new PricelessException();
        try {
            return Double.parseDouble(price);
        } catch (Exception e) {
            throw new InvalidPriceException();
        }
    }

    private static double verifyRating(String price) throws InvalidRatingException {
        try {
            return Double.parseDouble(price);
        } catch (Exception e) {
            throw new InvalidRatingException();
        }
    }

    private static int verifyQuantity(String quantity) throws InvalidQuantityException {
        try {
            return Integer.parseInt(quantity);
        } catch (Exception e) {
            throw new InvalidQuantityException();
        }
    }

    // exceptions
    public static class IDlessException extends Exception {
        public IDlessException() {}
    }

    public static class NamelessException extends Exception {
        public NamelessException() {}
    }

    public static class PricelessException extends Exception {
        public PricelessException() {}
    }

    public static class DuplicateIDException extends Exception {
        public DuplicateIDException() {}
    }

    public static class InvalidPriceException extends Exception {
        public InvalidPriceException() {}
    }

    public static class InvalidRatingException extends Exception {
        public InvalidRatingException() {}
    }

    public static class InvalidQuantityException extends Exception {
        public InvalidQuantityException() {}
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSmallImageResource() {
        return smallImageResource;
    }

    public int getLargeImageResource() {
        return largeImageResource;
    }

    public String getDesc() {
        return desc;
    }

    public double getPrice() {
        return price*quantity;
    }

    public String getFormattedPrice() {
        return String.format("₱%.2f", getPrice());
    }

    public float getRating() {
        return (float) rating;
    }

    public int getQuantity() {
        return quantity;
    }

    public void addQuantity() { quantity++; }

    public void setQuantity(int quantity) { this.quantity = quantity; }
}
