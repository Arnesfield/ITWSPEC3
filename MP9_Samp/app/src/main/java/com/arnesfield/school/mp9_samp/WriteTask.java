package com.arnesfield.school.mp9_samp;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by User on 07/01.
 */

public class WriteTask extends AsyncTask<Void, Void, Integer> {

    public static final String ACTION_ADD = "add";
    public static final String ACTION_UPDATE = "update";
    public static final String ACTION_DELETE = "delete";

    public interface OnWriteTaskListener {
        void setValues(ContentValues contentValues, String actionId) throws Exception;
        void onDone(String actionId);
    }

    // instance
    private final Context context;
    private final String actionId;
    private final ProgressDialog progressDialog;

    // constructor
    public WriteTask(Context context, String actionId) {
        this.context = context;
        this.actionId = actionId;
        this.progressDialog = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        progressDialog.dismiss();
        ((OnWriteTaskListener)context).onDone(actionId);
    }

    @Override
    protected Integer doInBackground(Void... params) {

        String strUrl = "";
        switch (actionId) {
            case ACTION_ADD:
                strUrl = MyConfig.ADD_URL;
                break;
            case ACTION_UPDATE:
                strUrl = MyConfig.UPDATE_URL;
                break;
            case ACTION_DELETE:
                strUrl = MyConfig.DELETE_URL;
                break;
        }


        try {
            URL url = new URL(strUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            OutputStream outputStream = new BufferedOutputStream(httpURLConnection.getOutputStream());
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

            ContentValues contentValues = new ContentValues();

            ((OnWriteTaskListener)context).setValues(contentValues, actionId);
            String postString = getPostStringFor(contentValues);

            bufferedWriter.write(postString);
            Log.i("pin", postString);

            // clear
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            int responseCode = httpURLConnection.getResponseCode();
            httpURLConnection.disconnect();

            return responseCode;
        } catch (Exception ignored) {Log.i("pin", ignored.getMessage());}
        return null;
    }

    private String getPostStringFor(ContentValues contentValues) throws UnsupportedEncodingException {
        StringBuilder stringBuilder = new StringBuilder();
        boolean flag = true;

        for (Map.Entry<String, Object> value : contentValues.valueSet()) {
            stringBuilder.append( flag ? "" : "&" );
            flag = false;
            stringBuilder.append(URLEncoder.encode(value.getKey(), "UTF-8"));
            stringBuilder.append("=");
            stringBuilder.append(URLEncoder.encode(value.getValue().toString(), "UTF-8"));
        }

        return stringBuilder.toString();
    }
}
