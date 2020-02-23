package com.rehapp.rehappmovil.rehapp.Utils;



import android.content.Context;
import android.net.ConnectivityManager;

public class DetectInternetConnection {

    public static boolean checkInternetConnection(Context context) {

        ConnectivityManager con_manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return (con_manager.getActiveNetworkInfo() != null
                && con_manager.getActiveNetworkInfo().isConnected());
    }

}
