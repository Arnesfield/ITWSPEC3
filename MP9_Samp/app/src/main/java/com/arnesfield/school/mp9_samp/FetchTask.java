package com.arnesfield.school.mp9_samp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by User on 06/30.
 */

public class FetchTask extends AsyncTask<Void, Void, String> {

    // instance
    private final Context context;
    private final ProgressDialog progressDialog;

    // constructor
    public FetchTask(Context context) {
        this.context = context;
        progressDialog =  new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setTitle("Fetching Items");
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();

        ((MainActivity)context).parseJSON(s);
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            URL url = new URL(MyConfig.FETCH_URL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            // use string builder to add lines from the php file in FETCH_URL
            StringBuilder stringBuilder = new StringBuilder();
            String line = "";

            while ((line = bufferedReader.readLine()) != null)
                stringBuilder.append(line);

            // close connections
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            // return fetched lines
            return stringBuilder.toString();
        } catch (Exception ignored) {}

        return null;
    }
}
