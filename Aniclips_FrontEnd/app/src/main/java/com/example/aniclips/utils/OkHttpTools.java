package com.example.aniclips.utils;

import android.app.Activity;
import android.util.Log;

import java.io.IOException;
import java.net.ConnectException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpTools {

    private static final String BASE_URL = Constantes.API_BASE_URL_PORTATIL;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static String post (String url, String json) {
        String resp;

        try {

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(3, TimeUnit.SECONDS)
                    .build();

            RequestBody requestBody = RequestBody.create(JSON, json);

            Request request = new Request.Builder()
                    .url(BASE_URL + url)
                    .post(requestBody)
                    .build();

            Response response = client.newCall(request).execute();
            resp = response.body().string();

        } catch (ConnectException e) {
            e.printStackTrace();
            resp = "{\"status\":\"ERROR\",\"message\":\"ConnectException\"}";
        } catch (IOException e) {
            e.printStackTrace();
            resp = "{\"status\":\"ERROR\",\"message\":\"IOException\"}";
        } catch (Exception e) {
            e.printStackTrace();
            resp = "{\"status\":\"ERROR\",\"message\":\"Exception\"}";
        }

        return resp;
    }

    public static String postWithToken(String url, String json, String jwtToken) {
        String resp;

        try {

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(3, TimeUnit.SECONDS)
                    .build();

            RequestBody requestBody = RequestBody.create(JSON, json);

            Request request = new Request.Builder()
                    .url(BASE_URL + url)
                    .post(requestBody)
                    .addHeader("Authorization", "Bearer " + jwtToken)
                    .build();

            Response response = client.newCall(request).execute();
            resp = response.body().string();


        } catch (ConnectException e) {
            e.printStackTrace();
            resp = "{\"status\":\"ERROR\",\"message\":\"ConnectException\"}";
        } catch (IOException e) {
            e.printStackTrace();
            resp = "{\"status\":\"ERROR\",\"message\":\"IOException\"}";
        } catch (Exception e) {
            e.printStackTrace();
            resp = "{\"status\":\"ERROR\",\"message\":\"Exception\"}";
        }

        return resp;
    }

    public static String get(String url, Activity activity) {

        String resp = "";

        if (Checks.isOnline(activity)) {

            try {
                Log.d("url", BASE_URL + url);

                OkHttpClient.Builder b = new OkHttpClient.Builder();
                b.connectTimeout(3, TimeUnit.SECONDS);
                OkHttpClient client = b
                        .build();

                Request request = new Request.Builder()
                        .url(BASE_URL + url)
                        .build();

                Response response = client.newCall(request).execute();

                resp = response.body().string();

                Log.d("resp", resp);

            } catch (ConnectException e) {
                e.printStackTrace();
                resp = "{\"mod\" : \"init\", \"status\" : \"ERROR\", \"message\" : \"ConnectException\"}";
            } catch (IOException e) {
                e.printStackTrace();
                resp = "{\"mod\" : \"init\", \"status\" : \"ERROR\", \"message\" : \"IOException\"}";
            } catch (Exception e) {
                e.printStackTrace();
                resp = "{\"mod\" : \"init\", \"status\" : \"ERROR\", \"message\" : \"Exception\"}";
            }

        } else {
            resp = "{\"internet\": false}";
        }

        return resp;
    }
    public static String postBusinesses(String url, String json) {
        String resp;

        try {
            Log.d("url", BASE_URL + "/businesses");

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(3, TimeUnit.SECONDS)
                    .build();

            RequestBody requestBody = RequestBody.create(JSON, json);

            Request request = new Request.Builder()
                    .url(BASE_URL + "/businesses/")
                    .post(requestBody)
                    .build();

            Response response = client.newCall(request).execute();
            resp = response.body().string();

            Log.d("resp", resp);

        } catch (ConnectException e) {
            e.printStackTrace();
            resp = "{\"status\":\"ERROR\",\"message\":\"ConnectException\"}";
        } catch (IOException e) {
            e.printStackTrace();
            resp = "{\"status\":\"ERROR\",\"message\":\"IOException\"}";
        } catch (Exception e) {
            e.printStackTrace();
            resp = "{\"status\":\"ERROR\",\"message\":\"Exception\"}";
        }

        return resp;
    }

}
