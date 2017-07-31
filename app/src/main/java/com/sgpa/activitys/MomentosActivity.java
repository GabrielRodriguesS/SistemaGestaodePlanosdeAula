package com.sgpa.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.sgpa.R;
import com.sgpa.models.Momentos;
import com.sgpa.models.Recursos;
import com.sgpa.utils.ViewUtils;

import java.util.ArrayList;


public class MomentosActivity extends AppCompatActivity {

    private Momentos momento;
    private ArrayList<Recursos> recursos;
    protected ArrayAdapter<Recursos> recursosArrayAdapter;
    protected ListView recursosListView;
    private long planoDeAulaId;
    private boolean isEditActivity;
    static final int PICK_RECURSO_REQUEST = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().hasExtra("isEditActivity")) {
            setContentView(R.layout.activity_edit_momentos);
            this.isEditActivity = (boolean) getIntent().getExtras().get("isEditActivity");
        } else {
            setContentView(R.layout.activity_create_momentos);
            this.momento = new Momentos();
            this.recursosListView = (ListView) findViewById(R.id.list_recursos);
            this.recursosArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
            this.recursosListView.setAdapter(this.recursosArrayAdapter);
        }
        if(getIntent().hasExtra("momento")){
            this.momento = (Momentos) getIntent().getExtras().get("momento");
        } else {
            if (getIntent().hasExtra("plano_de_aula_id")) {
                this.planoDeAulaId = getIntent().getLongExtra("plano_de_aula_id", 0);
            }
        }
        this.recursos = new ArrayList<Recursos>();
    }

    public void getAttributesFromView(View view){
        View rootView = getWindow().getDecorView().getRootView();
        this.momento.setNome(ViewUtils.getValue(rootView, R.id.titulo));
        this.momento.setTexto(ViewUtils.getValue(rootView, R.id.texto));
        if(isEditActivity) {
            this.momento = this.momento.edit();
            Intent returnIntent = new Intent();
            returnIntent.putExtra("momento", this.momento);
            Toast.makeText(this, "Momento atualizado com sucesso", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK, returnIntent);
        } else {
            this.momento = this.momento.save(this.planoDeAulaId);
            if(!this.recursos.isEmpty()){
                for (Recursos recurso: this.recursos) {
                    recurso.setMomento(this.momento);
                    recurso.save();
                }
            }
        }
        finish();
    }

    public void adicionarRecurso(View view) {
        Intent momentosView = new Intent(getApplicationContext(), RecursosActivity.class);
        startActivityForResult(momentosView, PICK_RECURSO_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_RECURSO_REQUEST) {
            if (resultCode == RESULT_OK) {
                Recursos recurso = (Recursos) data.getExtras().get("recurso");
                recursos.add(recurso);
                this.recursosArrayAdapter.add(recurso);
                this.recursosArrayAdapter.notifyDataSetChanged();
            }
        }
    }
}
