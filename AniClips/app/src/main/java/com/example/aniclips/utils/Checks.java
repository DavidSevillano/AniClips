package com.example.aniclips.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Checks {
    public static boolean isOnline(final Activity activity) {
        boolean conectado;

        ConnectivityManager cm =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        conectado = netInfo != null && netInfo.isConnectedOrConnecting();

        return conectado;
    }
}
