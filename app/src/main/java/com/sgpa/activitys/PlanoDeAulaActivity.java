package com.sgpa.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.sgpa.R;
import com.sgpa.models.PlanosDeAula;
import com.sgpa.utils.ViewUtils;

public class PlanoDeAulaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private PlanosDeAula planosDeAula;
    //private ArrayList<>
    //// TODO: 24/07/17  Terminar de criar o spinner 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plano_de_aula);
        if (getIntent().hasExtra("planoDeAula")) {
            this.planosDeAula = (PlanosDeAula) getIntent().getExtras().get("planoDeAula");
            this.inflateAllInputs();
        }else{
            this.inflateSpinner();
        }
        Spinner spinner = (Spinner) findViewById(R.id.disciplina);
        spinner.setOnItemSelectedListener(this);
    }

    private void inflateAllInputs() {
        View view = findViewById(R.id.activity_create_plano_de_aula);
        this.setInput(ViewUtils.getEditText(view,R.id.titulo), this.planosDeAula.getTitulo());
        this.setInput(ViewUtils.getEditText(view,R.id.descricao), this.planosDeAula.getDescricao());
        this.setInput(ViewUtils.getEditText(view,R.id.sub_titulo), this.planosDeAula.getSubtitulo());

    }

    private void setInput(EditText editText, String value){
        editText.setText(value);
    }

    private void inflateSpinner(){
        Spinner subDescricao = (Spinner) findViewById(R.id.sub_disciplina);
        new Thread(new Runnable() {

            public void run() {

            }
        }).start();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
