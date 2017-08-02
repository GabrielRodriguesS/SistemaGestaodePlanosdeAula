package com.sgpa.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

public class ThreadUtils {

    public static String postMethod(Context context, Object object, String url) {
        String retorno = "";
        if (isConnected(context)) {
            String jsonObject = "";
            jsonObject = GsonUtils.getInstance().setObject(object);
            WebClient webClient = new WebClient(url, jsonObject);
            Thread t = new Thread(webClient);
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retorno = webClient.getRetornoJson();

        } else {
            Toast.makeText(context, "Sem conexão com a internet", Toast.LENGTH_SHORT).show();
        }
        return retorno;
    }

    public static String getMethod(Context context, String url) {
        String retorno = "";
        if (isConnected(context)) {
            WebClient webClient = new WebClient(url);
            Thread thread = new Thread(webClient);
            thread.start();
            try {
                thread.join();
                retorno = webClient.getRetornoJson();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, "Sem conexão com a internet", Toast.LENGTH_SHORT).show();
        }
        return retorno;
    }

    private static boolean isConnected(Context context) {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        conectado = conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected();
        return conectado;
    }
}
