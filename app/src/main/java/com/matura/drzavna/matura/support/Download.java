package com.matura.drzavna.matura.support;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Download extends AsyncTask<String, Object, String>
{
    Context c;

    public Download(Context c)
    {
        this.c = c;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {

        HttpURLConnection urlConnection = null;
        String data = null;
        BufferedReader reader = null;

        try {
            URL url = new URL("https://drzavnamatura.000webhostapp.com/"+strings[0]+".php");
            urlConnection = (HttpURLConnection) url.openConnection();
            String basicAuth = strings[1];
            urlConnection.addRequestProperty ("maturatoken", basicAuth);
            urlConnection.setRequestMethod("GET");
            urlConnection.setUseCaches(false);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();
            System.out.println(stringBuilder.toString());

            String json = stringBuilder.toString().substring(stringBuilder.toString().indexOf("<body>") + 6, stringBuilder.toString().indexOf("<div style"));
            if(!json.contains("[")) {
                JSONObject o = new JSONObject(json);
                if (o.getString("status").equals("500"))
                    return "failed";
            }

            return json;

        } catch (IOException e) {
            e.printStackTrace();
            return "failed";
        }
        catch (Exception ee){
            ee.printStackTrace();
            return "failed";
        }
    }

    public boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }
}
