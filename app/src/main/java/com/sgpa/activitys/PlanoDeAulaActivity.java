package com.sgpa.activitys;

import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.sgpa.R;
import com.sgpa.models.PlanosDeAula;
import com.sgpa.utils.GsonUtils;
import com.sgpa.utils.ViewUtils;
import com.sgpa.utils.WebClient;

public class PlanoDeAulaActivity extends AppCompatActivity {

    protected PlanosDeAula planosDeAula;
    protected ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plano_de_aula);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        if (getIntent().hasExtra("planoDeAula")) {
            this.planosDeAula = (PlanosDeAula) getIntent().getExtras().get("planoDeAula");
            this.inflateAllInputs();
        } else {
            this.planosDeAula = new PlanosDeAula();
        }
    }

    public void createPlanoDeAula(View view) {
        this.getAttributesFromView();
        this.loadingToSavePlanoDeAula();
        Intent mainView = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainView);
    }

    public void createAndAddMomentosPlanoDeAula(View view) {
        this.getAttributesFromView();
        this.loadingToSavePlanoDeAula();
        Intent momentosView = new Intent(getApplicationContext(), MomentosActivity.class);
        momentosView.putExtra("planoDeAula", this.planosDeAula);
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

    private void loadingToSavePlanoDeAula() {
        /*progressBar.setVisibility(View.VISIBLE);
        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 0, 100);
        animation.setDuration(15000);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
        */
        String json = GsonUtils.getInstance().setObject(this.planosDeAula);
        WebClient webClient = new WebClient("planoDeAula/save", json);
        Thread thread = new Thread(webClient);
        thread.start();
    }
}
