package com.sgpa.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.sgpa.R;
import com.sgpa.models.Momentos;
import com.sgpa.utils.ViewUtils;


public class MomentosActivity extends AppCompatActivity {

    private Momentos momento;
    private long planoDeAulaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_momentos);
        if (getIntent().hasCategory("plano_de_aula_id")) {
            this.planoDeAulaId = getIntent().getLongExtra("plano_de_aula_id", 0);
        }
    }

    public void getAttributesFromView(View view){
        this.momento.setNome(ViewUtils.getValue(view, R.id.titulo));
        this.momento.setTexto(ViewUtils.getValue(view, R.id.texto));
        this.momento.save();
    }
}
