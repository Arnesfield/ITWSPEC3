package com.cayle.fetch_activity_cayle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new MyTask().execute();
    }

    private void parseJSON(String jsonString) {
        try {
            final ArrayList<Food> list = new ArrayList<>();

            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("foods");

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);

                String name = jsonObject.getString("name");
                String desc = jsonObject.getString("desc");
                double price = jsonObject.getDouble("price");

                list.add(new Food(name, desc, price, Food.IMAGE_RESOURCES[0][i], Food.IMAGE_RESOURCES[1][i]));
            }

            // set adapter
            ListView listView = (ListView) findViewById(R.id.list_view);
            listView.setAdapter(new Food.FoodAdapter(this, android.R.layout.simple_list_item_1, list));

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MainActivity.this, ViewActivity.class);

                    Food food = list.get(position);

                    intent.putExtra("name", food.getName());
                    intent.putExtra("desc", food.getDesc());
                    intent.putExtra("price", food.getFormattedPrice());
                    intent.putExtra("imageResource", food.getLargeImageResource());

                    MainActivity.this.startActivity(intent);
                }
            });

            TextView tv_msg = (TextView) findViewById(R.id.tv_msg);
            tv_msg.setVisibility(list.isEmpty() ? View.VISIBLE : View.GONE);
        } catch (Exception e) {}
    }

    public class MyTask extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL("http://192.168.1.10/excluded/sites/android/fetch-activity/foods.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder stringBuilder = new StringBuilder();
                String line = "";

                while ((line = bufferedReader.readLine()) != null)
                    stringBuilder.append(line);

                return stringBuilder.toString();
            } catch (Exception e) {}

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String jsonString) {
            super.onPostExecute(jsonString);
            progressDialog.dismiss();
            parseJSON(jsonString);
        }
    }

}
