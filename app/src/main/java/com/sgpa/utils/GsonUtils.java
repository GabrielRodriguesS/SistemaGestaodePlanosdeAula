package com.sgpa.utils;


import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class GsonUtils {

    private static GsonUtils instance = new GsonUtils();
    private Gson gson = new Gson();

    private GsonUtils() {
    }

    public static GsonUtils getInstance(){
        return instance;
    }

    public ArrayList getList(String json, Type typeList){
        return gson.fromJson(json, typeList);
    }

    public Object getObject(String json, Class clazz){
        return gson.fromJson(json, clazz);
    }
}

