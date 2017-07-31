package com.sgpa.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.sgpa.R;
import com.sgpa.models.PlanosDeAula;
import com.sgpa.utils.GsonUtils;
import com.sgpa.utils.WebClient;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    protected ArrayAdapter<PlanosDeAula> planosDeAulaAdapter;
    protected ArrayList<PlanosDeAula> listFromJson;
    protected ListView planoDeAulaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_plano_de_aula);
        this.planosDeAulaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        this.planoDeAulaList = (ListView) findViewById(R.id.list_plano_de_aula);
        this.listFromJson = new ArrayList();
        this.inflateList();
        registerForContextMenu(this.planoDeAulaList);
        this.planoDeAulaList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent planoDeAulaView = new Intent(parent.getContext(), ViewPlanoDeAula.class);
                planoDeAulaView.putExtra("plano_de_aula_id", listFromJson.get(position).getId());
                startActivity(planoDeAulaView);
            }
        });

        /*this.planoDeAulaList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });*/
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.list_plano_de_aula) {
            menu.add(Menu.NONE, 0, 0, R.string.editar);
            menu.add(Menu.NONE, 1, 1, R.string.deletar);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        PlanosDeAula planosDeAula = this.listFromJson.get(info.position);
        int menuItemIndex = item.getItemId();
        if (menuItemIndex == 0) {
            Intent planoDeAulaView = new Intent(this, PlanoDeAulaActivity.class);
            planoDeAulaView.putExtra("planoDeAula", planosDeAula);
            planoDeAulaView.putExtra("edit", true);
            startActivity(planoDeAulaView);
            return true;
        } else {
            String json = GsonUtils.getInstance().setObject(planosDeAula);
            WebClient webClient = new WebClient("planoDeAula/delete/" + planosDeAula.getId(), json);
            Thread thread = new Thread(webClient);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Toast.makeText(this, "Plano deletado com sucesso", Toast.LENGTH_SHORT).show();
            this.planosDeAulaAdapter.remove(planosDeAula);
            this.planosDeAulaAdapter.notifyDataSetChanged();
            return true;
        }
    }

    private void inflateList() {
        Type type = new TypeToken<ArrayList<PlanosDeAula>>() {
        }.getType();
        WebClient webClient = new WebClient("planoDeAula/getAll");
        Thread thread = new Thread(webClient);
        thread.start();
        try {
            thread.join();
            String listaJson = webClient.getJson();
            if (listaJson != null) {
                listFromJson = GsonUtils.getInstance().getList(listaJson, type);
                if (!listFromJson.isEmpty()) {
                    planosDeAulaAdapter.addAll(listFromJson);
                    planoDeAulaList.setAdapter(planosDeAulaAdapter);
                } else {
                    Toast.makeText(this, "Sem planos de aula cadastrados", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Problemas de conexão com o servidor", Toast.LENGTH_SHORT).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void createPlanoDeAula(View view) {
        Intent viewActivity = new Intent(this, PlanoDeAulaActivity.class);
        startActivity(viewActivity);
    }
}
