package com.sgpa.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sgpa.R;
import com.sgpa.models.Momentos;
import com.sgpa.models.PlanosDeAula;
import com.sgpa.utils.ViewUtils;

public class ViewPlanoDeAulaActivity extends AppCompatActivity {

    static final int EDIT_PLANO_DE_AULA_REQUEST = 0;
    protected ArrayAdapter<Momentos> momentosAdapter;
    protected ListView momentosListView;
    private PlanosDeAula planosDeAula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_plano_de_aula);
        this.momentosListView = (ListView) findViewById(R.id.list_momentos);
        this.momentosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        if (getIntent().hasExtra("plano_de_aula_id")) {
            Long id = getIntent().getLongExtra("plano_de_aula_id", 0);
            this.planosDeAula = new PlanosDeAula();
            this.planosDeAula = this.planosDeAula.show(id);
        }
        if (getIntent().hasExtra("plano_de_aula")) {
            this.planosDeAula = (PlanosDeAula) getIntent().getExtras().get("plano_de_aula");
        }
        this.inflateAllInputs();
        // TODO: adicionar o suporte ao editar dos momentos
        // TODO: implementar o visualizar do momento talvez com um longclick com a opção de ver e editar
        this.momentosListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent momentosView = new Intent(parent.getContext(), ViewMomentoActivity.class);
                momentosView.putExtra("momento_id", planosDeAula.getMomentos().get(position).getId());
                startActivity(momentosView);
            }
        });

    }

    private void inflateAllInputs() {
        View view = getWindow().getDecorView().getRootView();
        this.setText(ViewUtils.getTextView(view, R.id.titulo), "Titulo: " + this.planosDeAula.getTitulo());
        this.setText(ViewUtils.getTextView(view, R.id.descricao), "Descrição: " + this.planosDeAula.getDescricao());
        this.setText(ViewUtils.getTextView(view, R.id.sub_titulo), "Subtitulo: " + this.planosDeAula.getSubtitulo());
        if (!this.planosDeAula.getMomentos().isEmpty()) {
            this.momentosAdapter.addAll(this.planosDeAula.getMomentos());
            this.momentosListView.setAdapter(this.momentosAdapter);
        }
    }

    private void setText(TextView textView, String value) {
        textView.setText(value);
    }

    public void deletePlanoDeAula(View view) {
        this.planosDeAula.delete();
        Toast.makeText(this, "Plano deletado com sucesso", Toast.LENGTH_SHORT).show();
        setResult(Activity.RESULT_OK);
        finish();
    }

    public void editPlanoDeAula(View view) {
        Intent planoDeAulaView = new Intent(this, PlanoDeAulaActivity.class);
        planoDeAulaView.putExtra("planoDeAula", this.planosDeAula);
        planoDeAulaView.putExtra("edit", true);
        startActivityForResult(planoDeAulaView, EDIT_PLANO_DE_AULA_REQUEST);
    }

    public void addMomentos(View view){
        Intent momentosView = new Intent(getApplicationContext(), MomentosActivity.class);
        momentosView.putExtra("plano_de_aula_id", this.planosDeAula.getId());
        startActivity(momentosView);
        finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (EDIT_PLANO_DE_AULA_REQUEST == requestCode) {
            if(resultCode == RESULT_OK){
                if(data.hasExtra("plano_de_aula")) {
                    this.planosDeAula = (PlanosDeAula) data.getExtras().get("plano_de_aula");
                    View view = getWindow().getDecorView().getRootView();
                    this.setText(ViewUtils.getTextView(view, R.id.titulo), "Titulo: " + this.planosDeAula.getTitulo());
                    this.setText(ViewUtils.getTextView(view, R.id.descricao), "Descrição: " + this.planosDeAula.getDescricao());
                    this.setText(ViewUtils.getTextView(view, R.id.sub_titulo), "Subtitulo: " + this.planosDeAula.getSubtitulo());
                }
            }
        }
    }
}
