package com.sgpa.models;


import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.sgpa.utils.GsonUtils;
import com.sgpa.utils.ThreadUtils;

import java.io.Serializable;
import java.util.ArrayList;

public class Momentos implements Serializable {

    private Long id;
    private String nome;
    private String texto;
    @SerializedName("planoDeAula")
    private PlanosDeAula planoDeAula = new PlanosDeAula();
    @SerializedName("recursos")
    private ArrayList<Recursos> recursos = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public PlanosDeAula getPlanoDeAula() {
        return planoDeAula;
    }

    public void setPlanoDeAula(PlanosDeAula planoDeAula) {
        this.planoDeAula = planoDeAula;
    }

    public ArrayList<Recursos> getRecursos() {
        return recursos;
    }

    public void setRecursos(ArrayList<Recursos> recursos) {
        this.recursos = recursos;
    }

    @Override
    public String toString() {
        return "Nome: " + this.getNome();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Momentos)) return false;

        Momentos momentos = (Momentos) o;

        return id != null ? id.equals(momentos.id) : momentos.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public Momentos save(Context context, long planoDeAulaId) {
        String retorno = ThreadUtils.postMethod(context, this, "momento/save/" + planoDeAulaId);
        return (Momentos) GsonUtils.getInstance().getObject(retorno, this.getClass());
    }

    public Momentos edit(Context context) {
        String retorno = ThreadUtils.postMethod(context, this, "momento/edit/" + getId());
        return (Momentos) GsonUtils.getInstance().getObject(retorno, this.getClass());
    }

    public void delete(Context context) {
        ThreadUtils.postMethod(context, this, "momento/delete/" + this.getId());
    }

    public Momentos show(Context context, long id) {
        String retorno = ThreadUtils.getMethod(context, "momento/show/" + id);
        return (Momentos) GsonUtils.getInstance().getObject(retorno, this.getClass());
    }
}
