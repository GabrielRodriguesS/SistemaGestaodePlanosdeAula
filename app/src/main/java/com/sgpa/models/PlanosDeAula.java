package com.sgpa.models;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.google.gson.reflect.TypeToken;
import com.sgpa.utils.GsonUtils;
import com.sgpa.utils.ThreadUtils;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class PlanosDeAula implements Serializable {

    private Long id;
    private String titulo;
    private String subtitulo;
    private String descricao;
    private ArrayList<Momentos> momentos;

    public static ArrayList<PlanosDeAula> getAll(Context context) {
        Type type = new TypeToken<ArrayList<PlanosDeAula>>() {
        }.getType();
        String retorno = ThreadUtils.getMethod(context, "planoDeAula/getAll");
        return GsonUtils.getInstance().getList(retorno, type);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ArrayList<Momentos> getMomentos() {
        return momentos;
    }

    public void setMomentos(ArrayList<Momentos> momentos) {
        this.momentos = momentos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlanosDeAula)) return false;

        PlanosDeAula that = (PlanosDeAula) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return titulo.hashCode();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public String toString() {
        return "Titulo: " + this.getTitulo() + "\n" + this.getSubtitulo();
    }

    public PlanosDeAula save(Context context) {
        String retorno = ThreadUtils.postMethod(context, this, "planoDeAula/save");
        return (PlanosDeAula) GsonUtils.getInstance().getObject(retorno, this.getClass());
    }

    public PlanosDeAula edit(Context context) {
        String retorno = ThreadUtils.postMethod(context, this, "planoDeAula/edit/"+ this.getId());
        return (PlanosDeAula) GsonUtils.getInstance().getObject(retorno, this.getClass());
    }

    public void delete(Context context) {
        ThreadUtils.postMethod(context, this, "planoDeAula/delete/" + this.getId());
    }

    public PlanosDeAula show(Context context, long id) {
        String retorno = ThreadUtils.getMethod(context, "planoDeAula/show/" + id);
        return (PlanosDeAula) GsonUtils.getInstance().getObject(retorno, this.getClass());
    }
}
