package com.sgpa.utils;


import com.google.gson.Gson;
import com.sgpa.models.PlanosDeAula;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class GsonUtils {

    Gson gson = new Gson();

    public ArrayList getList(String json, Type typeList){
        return gson.fromJson(json, typeList);
    }

    public Object getObject(String json, Class clazz){
        return gson.fromJson(json, clazz);
    }
}

