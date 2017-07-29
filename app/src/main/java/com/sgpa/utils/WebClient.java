package com.sgpa.utils;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WebClient implements Runnable {

    private static final String IP = "192.168.0.102";
    private String url;
    private String json;
    private boolean isPostMethod;

    public WebClient(String url) {
        this.url = url;
        this.setPostMethod(false);
    }

    public WebClient(String url, String json) {
        this.url = url;
        this.json = json;
        this.setPostMethod(true);
    }

    private void getJsonFromWebService() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://" + IP + "/" + this.getUrl())
                .addHeader("content-type", "application/json; charset=utf-8")
                .build();
        Response response = client.newCall(request).execute();
        this.setJson(response.body().string());
    }

    private void postJsonFromWebService() throws IOException {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = (JsonObject) parser.parse(getJson());
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(JSON, jsonObject.toString());

        Request request = new Request.Builder()
                .url("http://" + IP + ":80/" + this.getUrl())
                .post(requestBody)
                .addHeader("content-type", "application/json; charset=utf-8")
                .build();
        Response response = client.newCall(request).execute();
        this.setJson(response.body().string());
    }

    @Override
    public void run() {
        try {
            if (isPostMethod()) {
                postJsonFromWebService();
            } else {
                getJsonFromWebService();
            }
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

    public boolean isPostMethod() {
        return isPostMethod;
    }

    public void setPostMethod(boolean postMethod) {
        isPostMethod = postMethod;
    }
}
