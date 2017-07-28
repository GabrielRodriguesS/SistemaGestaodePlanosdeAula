package com.sgpa.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WebClient implements Runnable {

    private String url;
    private String json;

    public WebClient(String url) {
        this.url = url;
    }

    public void getJsonFromWebService() throws IOException {
        OkHttpClient client = new OkHttpClient();
        //Request request = new Request.Builder().url("http://10.2.3.117:80/"+this.getUrl()).build();
        Request request = new Request.Builder().url("http://192.168.0.102:80/"+this.getUrl()).build();
        Response response = client.newCall(request).execute();
        this.setJson(response.body().string());
    }

    @Override
    public void run() {
        try {
            getJsonFromWebService();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
