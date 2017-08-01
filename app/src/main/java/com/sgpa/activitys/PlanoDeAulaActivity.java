package com.sgpa.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.sgpa.R;
import com.sgpa.models.PlanosDeAula;
import com.sgpa.utils.ViewUtils;

public class PlanoDeAulaActivity extends AppCompatActivity implements View.OnClickListener {

    // TODO: adicionar as coisas da progressbar
    protected PlanosDeAula planosDeAula;
    protected boolean edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plano_de_aula);
        findViewById(R.id.save_plano_button).setOnClickListener(this);
        findViewById(R.id.add_momentos_button).setOnClickListener(this);
        if (getIntent().hasExtra("planoDeAula")) {
            this.planosDeAula = (PlanosDeAula) getIntent().getExtras().get("planoDeAula");
            this.inflateAllInputs();
        }
        if (getIntent().hasExtra("edit")) {
            this.edit = (boolean) getIntent().getExtras().get("edit");
            findViewById(R.id.add_momentos_button).setVisibility(View.GONE);
        } else {
            this.planosDeAula = new PlanosDeAula();
        }
    }

    public void createPlanoDeAula() {
        this.getAttributesFromView();
        this.planosDeAula = this.planosDeAula.save();
        this.goToMainView();
    }

    public void editPlanodeAula() {
        this.getAttributesFromView();
        this.planosDeAula = this.planosDeAula.edit();
        this.goToMainView();
    }

    public void createAndAddMomentosPlanoDeAula() {
        this.getAttributesFromView();
        this.planosDeAula = this.planosDeAula.save();
        this.goToAddMomentosView();
    }

    private void inflateAllInputs() {
        View view = getWindow().getDecorView().getRootView();
        this.setInput(ViewUtils.getEditText(view, R.id.titulo), this.planosDeAula.getTitulo());
        this.setInput(ViewUtils.getEditText(view, R.id.descricao), this.planosDeAula.getDescricao());
        this.setInput(ViewUtils.getEditText(view, R.id.subtitulo), this.planosDeAula.getSubtitulo());

    }

    private void setInput(EditText editText, String value) {
        editText.setText(value);
    }

    private void getAttributesFromView() {
        View view = getWindow().getDecorView().getRootView();
        this.planosDeAula.setTitulo(ViewUtils.getValue(view, R.id.titulo));
        this.planosDeAula.setSubtitulo(ViewUtils.getValue(view, R.id.subtitulo));
        this.planosDeAula.setDescricao(ViewUtils.getValue(view, R.id.descricao));
    }

    private void goToMainView() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("plano_de_aula", this.planosDeAula);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    private void goToAddMomentosView() {
        Intent momentosView = new Intent(getApplicationContext(), MomentosActivity.class);
        momentosView.putExtra("plano_de_aula_id", this.planosDeAula.getId());
        startActivity(momentosView);
        finish();
    }

    @Override
    public void onClick(View view) {
        if (this.edit) {
            this.editPlanodeAula();
        } else {
            if (view.getId() == R.id.add_momentos_button) {
                this.createAndAddMomentosPlanoDeAula();
            } else {
                this.createPlanoDeAula();
            }
        }
    }
}
