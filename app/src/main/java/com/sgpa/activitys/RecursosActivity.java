package com.sgpa.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sgpa.R;
import com.sgpa.models.Recursos;
import com.sgpa.utils.ViewUtils;

public class RecursosActivity extends AppCompatActivity {

    private Recursos recurso;
    private long momentoId;
    private boolean isAddActivity;
    private boolean isEditActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recursos);
        this.recurso = new Recursos();
        if (getIntent().hasExtra("isAddActivity")) {
            this.isAddActivity = getIntent().getBooleanExtra("isAddActivity", false);
            if (getIntent().hasExtra("momentoId")) {
                this.momentoId = getIntent().getLongExtra("momentoId", 0);
            }
        }
        if (getIntent().hasExtra("isEditActivity")) {
            this.isEditActivity = (boolean) getIntent().getExtras().get("isEditActivity");
        } if(getIntent().hasExtra("recurso")){
            this.recurso = (Recursos) getIntent().getExtras().get("recurso");
            inflateAllInputs();
        }
    }

    private void inflateAllInputs() {
        View view = getWindow().getDecorView().getRootView();
        this.setInput(ViewUtils.getEditText(view, R.id.link), this.recurso.getLink());
    }

    private void setInput(EditText editText, String value) {
        editText.setText(value);
    }

    public void getAttributesFromView(View view) {
        View rootView = getWindow().getDecorView().getRootView();
        this.recurso.setLink(ViewUtils.getValue(rootView, R.id.link));
        Intent returnIntent = new Intent();
        if (isAddActivity) {
            this.recurso = this.recurso.save(this.momentoId);
        } else {
            if (isEditActivity) {
                this.recurso = this.recurso.edit();
                Toast.makeText(this, "Recurso atualizado com sucesso", Toast.LENGTH_SHORT).show();
            }
        }
        returnIntent.putExtra("recurso", this.recurso);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
