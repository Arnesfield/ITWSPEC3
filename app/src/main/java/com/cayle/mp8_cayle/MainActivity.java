package com.cayle.mp8_cayle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static int currentItemPosition = -1;
    private ArrayAdapter<Item> adapter;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = DBHelper.createHelper(this);

        adapter = new Item.ItemAdapter(this, android.R.layout.simple_list_item_1, db.getItems());

        final ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        registerForContextMenu(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentItemPosition = position;
                openContextMenu(listView);
            }
        });

        doRefreshActivity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                doAction("add");
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
            case R.id.view:
                doAction("view");
                return true;
            case R.id.update:
                doAction("update");
                return true;
            case R.id.delete:
                doAction("delete");
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        doRefreshActivity();
    }

    private void doAction(String action) {
        Intent intent;
        switch (action) {
            case "view":
                intent = new Intent(this, ViewActivity.class);
                intent.putExtra("pos", currentItemPosition);
                startActivity(intent);
                break;
            case "add":
            case "update":
                intent = new Intent(this, ActionActivity.class);
                intent.putExtra("pos", currentItemPosition);
                intent.putExtra("action", action);
                startActivity(intent);
                break;
            case "delete":
                db.deleteItem(currentItemPosition);
                Toast.makeText(this, "Item deleted.", Toast.LENGTH_SHORT).show();
                break;
        }
        currentItemPosition = -1;
        doRefreshActivity();
    }

    public void doRefreshActivity() {
        // refresh
        db.getItems();
        adapter.notifyDataSetChanged();

        TextView tv_msg = (TextView) findViewById(R.id.tv_msg);
        tv_msg.setVisibility(db.isListEmpty() ? View.VISIBLE : View.GONE);
    }
}
