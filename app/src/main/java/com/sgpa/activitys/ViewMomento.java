package com.sgpa.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sgpa.R;
import com.sgpa.models.Momentos;
import com.sgpa.models.Recursos;
import com.sgpa.utils.ViewUtils;

public class ViewMomento extends AppCompatActivity {

    protected ArrayAdapter<Recursos> recursosArrayAdapter;
    protected ListView recursosListView;
    private Momentos momento;
    static final int ADD_RECURSO_REQUEST = 0;
    static final int EDIT_MOMENTO_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_momento);
        this.recursosListView = (ListView) findViewById(R.id.list_recursos);
        this.recursosArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        if (getIntent().hasExtra("momento_id")) {
            Long id = getIntent().getLongExtra("momento_id", 0);
            this.momento = this.momento.show(id);
            this.inflateAllInputs();
        }

        this.recursosListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent recursosView = new Intent(parent.getContext(), RecursosActivity.class);
                recursosView.putExtra("recurso_id", momento.getRecursos().get(position).getId());
                startActivity(recursosView);
            }
        });
    }

    private void inflateAllInputs() {
        View view = getWindow().getDecorView().getRootView();
        this.setText(ViewUtils.getTextView(view, R.id.titulo), "Titulo: " + this.momento.getNome());
        this.setText(ViewUtils.getTextView(view, R.id.descricao), "Descrição: " + this.momento.getTexto());
        if (!this.momento.getRecursos().isEmpty()) {
            this.recursosArrayAdapter.addAll(this.momento.getRecursos());
            this.recursosListView.setAdapter(this.recursosArrayAdapter);
        }
    }

    private void setText(TextView textView, String value) {
        textView.setText(value);
    }

    public void deleteMomento(View view) {
        this.momento.delete();
        Toast.makeText(this, "Momento deletado com sucesso", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }

    public void adicionarRecurso(View view) {
        Intent planoDeAulaView = new Intent(this, RecursosActivity.class);
        planoDeAulaView.putExtra("momento_id", this.momento.getId());
        planoDeAulaView.putExtra("isAddActivity", true);
        startActivityForResult(planoDeAulaView, ADD_RECURSO_REQUEST);
    }

    public void editMomento(View view) {
        Intent planoDeAulaView = new Intent(this, MomentosActivity.class);
        planoDeAulaView.putExtra("momento", this.momento);
        planoDeAulaView.putExtra("isEditActivity", true);
        startActivityForResult(planoDeAulaView, EDIT_MOMENTO_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_RECURSO_REQUEST) {
            if (resultCode == RESULT_OK) {
                Recursos recurso = (Recursos) data.getExtras().get("recurso");
                this.recursosArrayAdapter.add(recurso);
                this.recursosArrayAdapter.notifyDataSetChanged();
            }
        }
        if (requestCode == EDIT_MOMENTO_REQUEST) {
            if(requestCode == RESULT_OK) {
                Momentos momento = (Momentos) data.getExtras().get("momento");
                this.momento = momento;
                View view = getWindow().getDecorView().getRootView();
                this.setText(ViewUtils.getTextView(view, R.id.titulo), "Titulo: " + this.momento.getNome());
                this.setText(ViewUtils.getTextView(view, R.id.descricao), "Descrição: " + this.momento.getTexto());
            }
        }
    }
}
