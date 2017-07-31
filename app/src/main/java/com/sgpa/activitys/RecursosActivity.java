package com.sgpa.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sgpa.R;
import com.sgpa.models.Recursos;
import com.sgpa.utils.ViewUtils;

public class RecursosActivity extends AppCompatActivity {

    private Recursos recurso;
    private long momentoId;
    private boolean isAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recursos);
        if (getIntent().hasExtra("isAddActivity")) {
            this.isAdd = getIntent().getBooleanExtra("isAddActivity", false);
            if (getIntent().hasExtra("momentoId")) {
                this.momentoId = getIntent().getLongExtra("momentoId", 0);
            }
        }
        this.recurso = new Recursos();
    }

    public void getAttributesFromView(View view) {
        View rootView = getWindow().getDecorView().getRootView();
        this.recurso.setLink(ViewUtils.getValue(rootView, R.id.link));
        if (isAdd) {
            this.recurso.save(this.momentoId);
        }
        Intent returnIntent = new Intent();
        returnIntent.putExtra("recurso", this.recurso);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
