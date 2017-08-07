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
import com.sgpa.models.PlanosDeAula;
import com.sgpa.utils.ViewUtils;

import static com.sgpa.activitys.ViewMomentoActivity.EDIT_MOMENTO_REQUEST;

public class ViewPlanoDeAulaActivity extends AppCompatActivity {

    static final int EDIT_PLANO_DE_AULA_REQUEST = 2;
    static final int ADD_MOMENTO_REQUEST = 1;
    protected ArrayAdapter<Momentos> momentosAdapter;
    protected ListView momentosListView;
    private PlanosDeAula planoDeAula;
    private boolean foiEditado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_plano_de_aula);
        this.momentosListView = (ListView) findViewById(R.id.list_momentos);
        this.momentosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        if (getIntent().hasExtra("plano_de_aula_id")) {
            Long id = getIntent().getLongExtra("plano_de_aula_id", 0);
            this.planoDeAula = new PlanosDeAula();
            this.planoDeAula = this.planoDeAula.show(getApplicationContext(), id);
        }
        if (getIntent().hasExtra("plano_de_aula")) {
            this.planoDeAula = (PlanosDeAula) getIntent().getExtras().get("plano_de_aula");
        }
        this.inflateAllInputs();
        this.momentosListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent momentosView = new Intent(parent.getContext(), ViewMomentoActivity.class);
                momentosView.putExtra("momento_id", planoDeAula.getMomentos().get(position).getId());
                momentosView.putExtra("position", position);
                startActivityForResult(momentosView, ADD_MOMENTO_REQUEST);
            }
        });
        this.foiEditado = false;
        registerForContextMenu(this.momentosListView);
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
        Momentos momento = (Momentos) this.momentosListView.getItemAtPosition(info.position);
        int menuItemIndex = item.getItemId();
        if (menuItemIndex == 0) {
            Intent momentosViewActivity = new Intent(this, ViewMomentoActivity.class);
            momentosViewActivity.putExtra("momento_id", momento.getId());
            momentosViewActivity.putExtra("isEditAcitvity", true);
            startActivityForResult(momentosViewActivity, EDIT_MOMENTO_REQUEST);
            return true;
        } else {
            momento.delete(getApplicationContext());
            Toast.makeText(this, "Momento deletado com sucesso", Toast.LENGTH_SHORT).show();
            int position = this.momentosAdapter.getPosition(momento);
            this.momentosListView.removeViewAt(position);
            this.momentosAdapter.remove(momento);
            this.momentosAdapter.notifyDataSetChanged();
            return true;
        }
    }


    private void inflateAllInputs() {
        View view = getWindow().getDecorView().getRootView();
        this.setText(ViewUtils.getTextView(view, R.id.titulo), "Titulo: " + this.planoDeAula.getTitulo());
        this.setText(ViewUtils.getTextView(view, R.id.descricao), "Descrição: " + this.planoDeAula.getDescricao());
        this.setText(ViewUtils.getTextView(view, R.id.sub_titulo), "Subtitulo: " + this.planoDeAula.getSubtitulo());
        if (this.planoDeAula.getId() != null || !this.planoDeAula.getMomentos().isEmpty()) {
            this.momentosAdapter.addAll(this.planoDeAula.getMomentos());
            this.momentosListView.setAdapter(this.momentosAdapter);
        }
    }

    private void setText(TextView textView, String value) {
        textView.setText(value);
    }

    public void deletePlanoDeAula(View view) {
        this.planoDeAula.delete(getApplicationContext());
        Toast.makeText(this, "Plano deletado com sucesso", Toast.LENGTH_SHORT).show();
        int position = getIntent().getIntExtra("position", 0);
        setResult(Activity.RESULT_OK, getIntent().putExtra("position", position));
        finish();
    }

    public void editPlanoDeAula(View view) {
        Intent planoDeAulaView = new Intent(this, PlanoDeAulaActivity.class);
        planoDeAulaView.putExtra("planoDeAula", this.planoDeAula);
        planoDeAulaView.putExtra("edit", true);
        startActivityForResult(planoDeAulaView, EDIT_PLANO_DE_AULA_REQUEST);
        this.foiEditado = true;
    }

    public void addMomentos(View view) {
        Intent momentosView = new Intent(getApplicationContext(), MomentosActivity.class);
        momentosView.putExtra("plano_de_aula_id", this.planoDeAula.getId());
        startActivityForResult(momentosView, ADD_MOMENTO_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (EDIT_PLANO_DE_AULA_REQUEST == requestCode) {
                if (data.hasExtra("planoDeAula")) {
                    this.planoDeAula = (PlanosDeAula) data.getExtras().get("planoDeAula");
                    this.inflateAllInputs();
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("planoDeAula", this.planoDeAula);
                    setResult(Activity.RESULT_OK, returnIntent);
                }
            }
            if (ADD_MOMENTO_REQUEST == requestCode) {
                Momentos momento = (Momentos) data.getExtras().get("momento");
                if (data.hasExtra("momento")) {
                    this.planoDeAula.getMomentos().add(momento);
                    this.updateAdapter();
                } else {
                    this.planoDeAula.getMomentos().remove(momento);
                    this.updateAdapter();
                }
            }
        }
    }

    public void updateAdapter() {
        this.momentosAdapter.clear();
        this.momentosAdapter.addAll(this.planoDeAula.getMomentos());
        this.momentosAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        if (this.foiEditado) {
            Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
            mainIntent.putExtra("planoDeAula", this.planoDeAula);
            mainIntent.putExtra("position", getIntent().getIntExtra("position", 0));
            setResult(Activity.RESULT_OK, mainIntent);
        }
        finish();
    }
}

