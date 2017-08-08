package com.sgpa.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.sgpa.R;
import com.sgpa.models.Momentos;
import com.sgpa.models.PlanosDeAula;
import com.sgpa.utils.ViewUtils;

import java.util.ArrayList;

public class PlanoDeAulaActivity extends AppCompatActivity implements View.OnClickListener {

    // TODO: adicionar as coisas da progressbar
    protected PlanosDeAula planoDeAula;
    protected boolean edit;
    static final int ADD_MOMENTO_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plano_de_aula);
        findViewById(R.id.save_plano_button).setOnClickListener(this);
        findViewById(R.id.add_momentos_button).setOnClickListener(this);
        if (getIntent().hasExtra("planoDeAula")) {
            this.planoDeAula = (PlanosDeAula) getIntent().getExtras().get("planoDeAula");
            this.inflateAllInputs();
        }
        if (getIntent().hasExtra("edit")) {
            this.edit = (boolean) getIntent().getExtras().get("edit");
            findViewById(R.id.add_momentos_button).setVisibility(View.GONE);
        } else {
            this.planoDeAula = new PlanosDeAula();
        }
    }

    public void createPlanoDeAula() {
        this.getAttributesFromView();
        this.planoDeAula = this.planoDeAula.save(getApplicationContext());
        this.goToMainView();
    }

    public void editPlanodeAula() {
        this.getAttributesFromView();
        ArrayList<Momentos> momentos = this.planoDeAula.getMomentos();
        this.planoDeAula = this.planoDeAula.edit(getApplicationContext());
        this.planoDeAula.setMomentos(momentos);
        this.goToMainView();
    }

    public void createAndAddMomentosPlanoDeAula() {
        this.getAttributesFromView();
        this.planoDeAula = this.planoDeAula.save(getApplicationContext());
        this.goToAddMomentosView();
    }

    private void inflateAllInputs() {
        View view = getWindow().getDecorView().getRootView();
        this.setInput(ViewUtils.getEditText(view, R.id.titulo), this.planoDeAula.getTitulo());
        this.setInput(ViewUtils.getEditText(view, R.id.descricao), this.planoDeAula.getDescricao());
        this.setInput(ViewUtils.getEditText(view, R.id.subtitulo), this.planoDeAula.getSubtitulo());
    }

    private void setInput(EditText editText, String value) {
        editText.setText(value);
    }

    private void getAttributesFromView() {
        View view = getWindow().getDecorView().getRootView();
        this.planoDeAula.setTitulo(ViewUtils.getValue(view, R.id.titulo));
        this.planoDeAula.setSubtitulo(ViewUtils.getValue(view, R.id.subtitulo));
        this.planoDeAula.setDescricao(ViewUtils.getValue(view, R.id.descricao));
    }

    private void goToMainView() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("planoDeAula", this.planoDeAula);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    private void goToAddMomentosView() {
        Intent momentosView = new Intent(getApplicationContext(), MomentosActivity.class);
        momentosView.putExtra("plano_de_aula_id", this.planoDeAula.getId());
        startActivityForResult(momentosView, ADD_MOMENTO_REQUEST);
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ADD_MOMENTO_REQUEST == requestCode) {
            if (resultCode == RESULT_OK) {
                Momentos momento = (Momentos) data.getExtras().get("momento");
                if (data.hasExtra("momento")) {
                    this.planoDeAula.getMomentos().add(momento);
                }
                Intent returnIntent = new Intent(getApplicationContext(), ViewPlanoDeAulaActivity.class);
                returnIntent.putExtra("planoDeAula", this.planoDeAula);
                setResult(RESULT_OK, returnIntent);
                startActivity(returnIntent);
            }
        }
    }
}
