package com.sgpa.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.sgpa.R;
import com.sgpa.models.Momentos;
import com.sgpa.models.Recursos;
import com.sgpa.utils.ViewUtils;

import java.util.ArrayList;


public class MomentosActivity extends AppCompatActivity {

    private Momentos momento;
    private ArrayList<Recursos> recursos;
    private long planoDeAulaId;
    static final int PICK_RECURSO_REQUEST = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_momentos);
        if (getIntent().hasExtra("plano_de_aula_id")) {
            this.planoDeAulaId = getIntent().getLongExtra("plano_de_aula_id", 0);
        }
        this.momento = new Momentos();
        this.recursos = new ArrayList<Recursos>();
    }

    public void getAttributesFromView(View view){
        View rootView = getWindow().getDecorView().getRootView();
        this.momento.setNome(ViewUtils.getValue(rootView, R.id.titulo));
        this.momento.setTexto(ViewUtils.getValue(rootView, R.id.texto));
        this.momento = this.momento.save(this.planoDeAulaId);
        if(!this.recursos.isEmpty()){
            for (Recursos recurso: this.recursos) {
                recurso.setMomento(this.momento);
                recurso.save();
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
            }
        }
    }
}
