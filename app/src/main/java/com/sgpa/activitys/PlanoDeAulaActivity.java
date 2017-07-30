package com.sgpa.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.sgpa.R;
import com.sgpa.models.PlanosDeAula;
import com.sgpa.utils.GsonUtils;
import com.sgpa.utils.ViewUtils;
import com.sgpa.utils.WebClient;

public class PlanoDeAulaActivity extends AppCompatActivity implements View.OnClickListener {

    protected PlanosDeAula planosDeAula;
    protected boolean edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plano_de_aula);
        FloatingActionButton saveButton = (FloatingActionButton) findViewById(R.id.save_plano_button);
        saveButton.setOnClickListener(this);
        if (getIntent().hasExtra("planoDeAula")) {
            this.planosDeAula = (PlanosDeAula) getIntent().getExtras().get("planoDeAula");
            this.inflateAllInputs();
        } else if (getIntent().hasExtra("edit")) {
            this.edit = (boolean) getIntent().getExtras().get("edit");
        } else {
            this.planosDeAula = new PlanosDeAula();
        }
    }

    public void createPlanoDeAula() {
        this.getAttributesFromView();
        this.loadingToSavePlanoDeAula();
        this.goToMainView();
    }

    public void editPlanodeAula() {
        this.getAttributesFromView();
        this.loadingToEditPlanoDeAula();
        this.goToMainView();
    }

    public void createAndAddMomentosPlanoDeAula(View view) {
        this.getAttributesFromView();
        this.loadingToSavePlanoDeAula();
        Intent momentosView = new Intent(getApplicationContext(), MomentosActivity.class);
        momentosView.putExtra("planos_de_aula_id", this.planosDeAula.getId());
        startActivity(momentosView);
    }

    private void inflateAllInputs() {
        View view = findViewById(R.id.activity_create_plano_de_aula);
        this.setInput(ViewUtils.getEditText(view, R.id.titulo), this.planosDeAula.getTitulo());
        this.setInput(ViewUtils.getEditText(view, R.id.descricao), this.planosDeAula.getDescricao());
        this.setInput(ViewUtils.getEditText(view, R.id.sub_titulo), this.planosDeAula.getSubtitulo());

    }

    private void setInput(EditText editText, String value) {
        editText.setText(value);
    }

    private void getAttributesFromView() {
        View view = findViewById(R.id.activity_create_plano_de_aula);
        this.planosDeAula.setTitulo(ViewUtils.getValue(view, R.id.titulo));
        this.planosDeAula.setSubtitulo(ViewUtils.getValue(view, R.id.sub_titulo));
        this.planosDeAula.setDescricao(ViewUtils.getValue(view, R.id.descricao));
    }

        // TODO: adicionar as coisas da progressbar aqui
    private void loadingToSavePlanoDeAula() {
        String json = GsonUtils.getInstance().setObject(this.planosDeAula);
        WebClient webClient = new WebClient("planoDeAula/save", json);
        Thread thread = new Thread(webClient);
        thread.start();
        try {
            thread.join();
            this.planosDeAula = (PlanosDeAula) GsonUtils.getInstance().getObject(webClient.getJson(), PlanosDeAula.class);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void loadingToEditPlanoDeAula() {
        String json = GsonUtils.getInstance().setObject(this.planosDeAula);
        WebClient webClient = new WebClient("planoDeAula/edit/" + this.planosDeAula.getId(), json);
        Thread thread = new Thread(webClient);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void goToMainView(){
        Intent mainView = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainView);
    }

    @Override
    public void onClick(View view) {
        if (this.edit) {
            this.editPlanodeAula();
        } else {
            this.createPlanoDeAula();
        }
    }
}
