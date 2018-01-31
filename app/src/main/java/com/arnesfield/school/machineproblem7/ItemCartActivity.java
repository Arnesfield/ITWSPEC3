package com.arnesfield.school.machineproblem7;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemCartActivity extends AppCompatActivity
        implements RefreshableActivity, RetrievableStateActivity, DialogCreator.DialogActionListener {

    public static final String PREFS_TAG = "itemPrefs", ID_LIST_TAG = "list";

    private String idList;

    private View rootView;
    private ItemCart itemCart;
    // private ArrayList<Item> copy;
    private ArrayList<Integer> listOfItemPos;
    private int currItemPos, currQtyVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_activity_item_cart);
        setContentView(R.layout.activity_item_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doAddItems();
            }
        });

        rootView = findViewById(R.id.root_view);

        doRetrieveState();
        ItemCart.ADD_CART();

        ArrayList<Item> list = ItemCart.getListFromStringIds(idList);

        itemCart = ItemCart.create();
        itemCart.setListOfItems(list);

        final ListView listView = (ListView) findViewById(R.id.item_list_view);
        listView.setAdapter(itemCart.setAdapter(this, android.R.layout.simple_list_item_1));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currItemPos = position;
                doShowQuantitySelector();
            }
        });

        listView.setLongClickable(true);
        registerForContextMenu(listView);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                currItemPos = position;
                openContextMenu(listView);
                return true;
            }
        });

        doRefreshActivity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                doAddItems();
                return true;
            case R.id.action_logout:
                doLogout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_item_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                doShowQuantitySelector();
                return true;
            case R.id.action_view:
                Item currItem = itemCart.getItem(currItemPos);

                Intent intent = new Intent(this, ViewItemActivity.class);
                intent.putExtra("itemId", currItem.getId());
                intent.putExtra("itemName", currItem.getName());
                intent.putExtra("itemDesc", currItem.getDesc());
                intent.putExtra("itemPrice", currItem.getFormattedPrice());

                intent.putExtra("itemRating", currItem.getRating());
                intent.putExtra("itemImageResource", currItem.getLargeImageResource());

                startActivity(intent);
                return true;
            case R.id.action_remove:

                DialogCreator.create(this, "remove")
                        .setTitle("Remove Item")
                        .setMessage("Item will be removed. Remove item?")
                        .setPositiveButton("Remove")
                        .setNegativeButton("Cancel")
                        .show();

                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            doLogout();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        doSaveState();
    }

    @Override
    protected void onStop() {
        super.onStop();
        doSaveState();
    }

    @Override
    public void doRefreshActivity() {
        itemCart.getAdapter().notifyDataSetChanged();

        TextView tv_empty = (TextView) findViewById(R.id.tv_empty);
        tv_empty.setVisibility((itemCart.isEmpty() ? View.VISIBLE : View.GONE));

        TextView tv_total = (TextView) findViewById(R.id.tv_total);
        tv_total.setText(itemCart.getFormattedTotalPrice());

        currItemPos = currQtyVal = -1;

        SnackBarCreator.show(rootView);
    }

    @Override
    public void doSaveState() {
        SharedPreferences settings = getSharedPreferences(PREFS_TAG, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        idList = ItemCart.getStringIdsFrom(itemCart);
        editor.putString(ID_LIST_TAG, idList);
        editor.apply();
    }

    @Override
    public void doRetrieveState() {
        SharedPreferences settings = getSharedPreferences(PREFS_TAG, MODE_PRIVATE);
        idList = settings.getString(ID_LIST_TAG, "1:3");
    }

    private void doLogout() {
        DialogCreator.create(this, "logout")
                .setTitle("Logout")
                .setMessage("This will logout account. Continue logout?")
                .setPositiveButton("Logout")
                .setNegativeButton("Cancel")
                .show();
    }

    private void doAddItems() {
        ArrayList<Item> list = ItemCart.getCartList();
        // copy of original list
        listOfItemPos = new ArrayList<>();
        DialogCreator.create(this, "add")
                .setTitle("Add to Cart")
                .setMultiChoiceItems(ItemCart.getNamesFrom(list), null)
                .setPositiveButton("Add")
                .setNegativeButton("Cancel")
                .show();

    }

    private void doShowQuantitySelector() {
        DialogCreator.create(ItemCartActivity.this, "quantity")
                .setTitle("Quantity")
                .setView(R.layout.dialog_picker)
                .setPositiveButton("Set Quantity")
                .setNegativeButton("Cancel")
                .show();
    }

    @Override
    public void onClickPositiveButton(String actionId) {
        switch (actionId) {
            case "logout":
                doSaveState();
                SnackBarCreator.set("Logged out successfully.");
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case "add":
                // move copy to orig
                ArrayList<Item> list = itemCart.getListOfItems();

                if (!listOfItemPos.isEmpty()) {
                    // list.clear();
                    SnackBarCreator.set("Item list updated.");
                    for (int i : listOfItemPos) {
                        Item item = ItemCart.getCartItem(i);
                        if (list.contains(item))
                            item.addQuantity();
                        else list.add(item);
                    }
                }

                doRefreshActivity();
                break;
            case "quantity":
                itemCart.getItem(currItemPos).setQuantity(currQtyVal);
                doRefreshActivity();
                break;
            case "remove":
                Item item = itemCart.getItem(currItemPos);
                item.setQuantity(1);
                itemCart.getListOfItems().remove(item);
                SnackBarCreator.set("Item removed.");
                doRefreshActivity();
                break;
        }
    }

    @Override
    public void onClickNegativeButton(String actionId) {
        switch (actionId) {
            case "add":
                break;
        }
    }

    @Override
    public void onClickNeutralButton(String actionId) {

    }

    @Override
    public void onClickMultiChoiceItem(String actionId, int which, boolean isChecked) {
        switch (actionId) {
            case "add":
                if (isChecked)
                    listOfItemPos.add(which);
                else
                    listOfItemPos.remove(Integer.valueOf(which));
                break;
        }
    }

    @Override
    public void onCreateDialogView(View view) {
        NumberPicker numberPicker = (NumberPicker) view.findViewById(R.id.numberPicker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(100);

        currQtyVal = itemCart.getItem(currItemPos).getQuantity();
        numberPicker.setValue(currQtyVal);

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                currQtyVal = newVal;
                SnackBarCreator.set("Item quantity updated.");
            }
        });
    }
}
