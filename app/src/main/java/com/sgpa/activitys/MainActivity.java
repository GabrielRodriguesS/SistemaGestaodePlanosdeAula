package com.sgpa.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.sgpa.R;
import com.sgpa.models.PlanosDeAula;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static final int ADD_EDIT_DELETE_PLANO_DE_AULA_REQUEST = 0;
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
        this.planoDeAulaList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PlanosDeAula planoDeAulaEditar = listFromJson.get(position);
                Intent planoDeAulaView = new Intent(parent.getContext(), ViewPlanoDeAulaActivity.class);
                planoDeAulaView.putExtra("plano_de_aula_id", planoDeAulaEditar.getId());
                planoDeAulaView.putExtra("position", position);
                startActivityForResult(planoDeAulaView, ADD_EDIT_DELETE_PLANO_DE_AULA_REQUEST);
            }
        });
    }

    private void inflateList() {
        ArrayList returnList = PlanosDeAula.getAll(getApplicationContext());
        if (returnList != null)
            this.listFromJson = PlanosDeAula.getAll(getApplicationContext());
        if (!listFromJson.isEmpty()) {
            this.planosDeAulaAdapter.addAll(listFromJson);
            this.planoDeAulaList.setAdapter(planosDeAulaAdapter);
        } else {
            Toast.makeText(this, "Sem planos de aula cadastrados", Toast.LENGTH_SHORT).show();
        }
    }

    public void createPlanoDeAula(View view) {
        Intent viewActivity = new Intent(this, PlanoDeAulaActivity.class);
        startActivityForResult(viewActivity, ADD_EDIT_DELETE_PLANO_DE_AULA_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == ADD_EDIT_DELETE_PLANO_DE_AULA_REQUEST) {
                if (data.hasExtra("position")) {
                    listFromJson.remove(data.getIntExtra("position", 0));
                }
                if (data.hasExtra("planoDeAula")) {
                    PlanosDeAula planosDeAula = (PlanosDeAula) data.getExtras().get("planoDeAula");
                    this.listFromJson.add(planosDeAula);
                }
                this.updateAdapter();
            }
        }
    }


    public void updateAdapter() {
        this.planosDeAulaAdapter.clear();
        this.planosDeAulaAdapter.addAll(this.listFromJson);
        this.planosDeAulaAdapter.notifyDataSetChanged();
    }
}
