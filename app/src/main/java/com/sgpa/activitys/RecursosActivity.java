package com.sgpa.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sgpa.R;
import com.sgpa.models.Recursos;
import com.sgpa.utils.ViewUtils;

public class RecursosActivity extends AppCompatActivity {

    private Recursos recurso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recursos);
    }

    public void getAttributesFromView(View view){
        this.recurso.setLink(ViewUtils.getValue(view, R.id.link));
    }
}
