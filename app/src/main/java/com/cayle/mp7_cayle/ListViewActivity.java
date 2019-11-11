package com.cayle.mp7_cayle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity implements DialogCreator.DialogActionListener {

    private static final String PREF_NAME = "caylePref", LIST = "list";

    private ArrayList<Integer> listOfItemPos;
    private static int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listOfItemPos = new ArrayList<>();

        Item.addDefaultItems();

        // set shared pref
        SharedPreferences settings = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String def = settings.getString(LIST, "Burger|Fries");

        Item.setListBasedOnString(def);

        final ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter( Item.setItemAdapter(this, android.R.layout.simple_list_item_1) );
        registerForContextMenu(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentPosition = position;
                openContextMenu(listView);
            }
        });

        updateList();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                doAddItems();
                return true;
            case R.id.logout:
                doLogout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_item_context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.remove:
                // remove current position and update list
                Item.removeMyItem(currentPosition);
                currentPosition = -1;
                Toast.makeText(this, "Removed item.", Toast.LENGTH_SHORT).show();
                updateList();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void updateList() {
        SharedPreferences settings = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(LIST, Item.getStringOfNames());
        editor.apply();

        Item.getItemAdapter().notifyDataSetChanged();

        TextView tv_total = (TextView) findViewById(R.id.tv_total);
        tv_total.setText("Total: " + Item.getFormattedTotal());

        TextView tv_msg = (TextView) findViewById(R.id.tv_msg);
        tv_msg.setVisibility(Item.isMyListEmpty() ? View.VISIBLE : View.GONE);

    }

    private void doAddItems() {
        DialogCreator.create(this, "add")
                .setTitle("Add Items to Cart")
                .setMultiChoiceItems(Item.getStringArrayCart(), null)
                .setPositiveButton("Add to Cart")
                .setNegativeButton("Cancel")
                .show();
    }

    private void doLogout() {
        DialogCreator.create(this, "logout")
                .setTitle("Logout")
                .setMessage("Do you wish to logout?")
                .setPositiveButton("Yes")
                .setNegativeButton("No")
                .show();
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
    public void onClickPositiveButton(String actionId) {
        switch (actionId) {
            case "add":
                for (Integer integer : listOfItemPos) {
                    Item.addToList(integer);
                }
                listOfItemPos.clear();
                Toast.makeText(this, "Updated list.", Toast.LENGTH_SHORT).show();
                updateList();
                break;
            case "logout":
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(this, "Logged out successfully.", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }

    @Override
    public void onClickNegativeButton(String actionId) {

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

    }
}
