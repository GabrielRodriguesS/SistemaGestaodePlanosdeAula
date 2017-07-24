package com.sgpa.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sgpa.R;
import com.sgpa.models.PlanosDeAula;
import com.sgpa.utils.GsonUtils;
import com.sgpa.utils.WebClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    protected ArrayAdapter<PlanosDeAula> planosDeAulaAdapter;
    protected ArrayList<PlanosDeAula> listFromJson = new ArrayList();
    protected ListView planoDeAulaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_plano_de_aula);
        this.planosDeAulaAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1);
        this.planoDeAulaList = (ListView) findViewById(R.id.list_plano_de_aula);
        this.inflateList();
        this.planoDeAulaList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent viewActivity = new Intent(parent.getContext(), PlanoDeAulaActivity.class);
                viewActivity.putExtra("planoDeAula", listFromJson.get(position));
                startActivity(viewActivity);
            }
        });
    }

    private void inflateList() {
        Type type = new TypeToken<ArrayList<PlanosDeAula>>(){}.getType();
        WebClient webClient = new WebClient("planoDeAula/getAll");
        GsonUtils gson = new GsonUtils();
        Thread thread = new Thread(webClient);
        thread.start();
        try {
            thread.join();
            String listaJson = webClient.getJson();
            listFromJson = gson.getList(listaJson, type);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //todo criar um tratamento de exceçao para o erro mais famoso do Java (nullPointerException)
        if(!listFromJson.isEmpty() || listFromJson != null) {
            planosDeAulaAdapter.addAll(listFromJson);
            planoDeAulaList.setAdapter(planosDeAulaAdapter);
        }
    }
}