package com.sgpa.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
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

public class ViewMomentoActivity extends AppCompatActivity {

    static final int ADD_EDIT_RECURSO_REQUEST = 0;
    static final int EDIT_MOMENTO_REQUEST = 1;
    protected ArrayAdapter<Recursos> recursosArrayAdapter;
    protected ListView recursosListView;
    private Momentos momento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_momento);
        this.momento = new Momentos();
        this.recursosListView = (ListView) findViewById(R.id.list_recursos);
        this.recursosArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        if (getIntent().hasExtra("momento_id")) {
            Long id = getIntent().getLongExtra("momento_id", 0);
            this.momento = this.momento.show(getApplicationContext(), id);
            this.inflateAllInputs();
        }
        registerForContextMenu(this.recursosListView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.list_recursos) {
            menu.add(Menu.NONE, 0, 0, R.string.editar);
            menu.add(Menu.NONE, 1, 1, R.string.deletar);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Recursos recurso = (Recursos) this.recursosListView.getItemAtPosition(info.position);
        int menuItemIndex = item.getItemId();
        if (menuItemIndex == 0) {
            Intent recursosActivity = new Intent(this, RecursosActivity.class);
            recursosActivity.putExtra("recurso", recurso);
            recursosActivity.putExtra("isEditAcitvity", true);
            this.recursosArrayAdapter.remove(recurso);
            startActivityForResult(recursosActivity, ADD_EDIT_RECURSO_REQUEST);
            return true;
        } else {
            recurso.delete(getApplicationContext());
            Toast.makeText(this, "Recurso deletado com sucesso", Toast.LENGTH_SHORT).show();
            this.recursosArrayAdapter.remove(recurso);
            this.recursosArrayAdapter.notifyDataSetChanged();
            return true;
        }
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
        this.momento.delete(getApplicationContext());
        Toast.makeText(this, "Momento deletado com sucesso", Toast.LENGTH_SHORT).show();
        Intent planoDeAulaView = new Intent(this, ViewPlanoDeAulaActivity.class);
        planoDeAulaView.putExtra("momento", this.momento);
        setResult(RESULT_OK);
        finish();
    }

    public void adicionarRecurso(View view) {
        Intent recursosView = new Intent(this, RecursosActivity.class);
        recursosView.putExtra("momentoId", this.momento.getId());
        recursosView.putExtra("isAddActivity", true);
        startActivityForResult(recursosView, ADD_EDIT_RECURSO_REQUEST);
    }

    public void editMomento(View view) {
        Intent planoDeAulaView = new Intent(this, MomentosActivity.class);
        planoDeAulaView.putExtra("momento", this.momento);
        planoDeAulaView.putExtra("isEditActivity", true);
        startActivityForResult(planoDeAulaView, EDIT_MOMENTO_REQUEST);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == ADD_EDIT_RECURSO_REQUEST) {
                Recursos recurso = (Recursos) data.getExtras().get("recurso");
                this.recursosArrayAdapter.add(recurso);
                this.recursosArrayAdapter.notifyDataSetChanged();

            }
            if (requestCode == EDIT_MOMENTO_REQUEST) {
                Momentos novoMomento = (Momentos) data.getExtras().get("momento");
                this.momento = novoMomento;
                Intent returnIntent = new Intent();
                returnIntent.putExtra("momento", this.momento);
                setResult(Activity.RESULT_OK, returnIntent);
                View view = getWindow().getDecorView().getRootView();
                this.setText(ViewUtils.getTextView(view, R.id.titulo), "Titulo: " + this.momento.getNome());
                this.setText(ViewUtils.getTextView(view, R.id.descricao), "Descrição: " + this.momento.getTexto());
            }
        }
    }
}
