package com.sgpa.models;


import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.sgpa.utils.GsonUtils;
import com.sgpa.utils.ThreadUtils;

import java.io.Serializable;

public class Recursos implements Serializable {

    private Long id;
    private String link;
    @SerializedName("momento")
    private Momentos momento = new Momentos();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Momentos getMomento() {
        return momento;
    }

    public void setMomento(Momentos momento) {
        this.momento = momento;
    }

    @Override
    public String toString() {
        return this.getLink();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recursos)) return false;

        Recursos recursos = (Recursos) o;

        return id != null ? id.equals(recursos.id) : recursos.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public Recursos save(Context context) {
        String retorno = ThreadUtils.postMethod(context, this, "recurso/save/" + this.getMomento().getId());
        return (Recursos) GsonUtils.getInstance().getObject(retorno, this.getClass());
    }

    public Recursos save(Context context, long momentoId) {
        String retorno = ThreadUtils.postMethod(context, this, "recurso/save/" + momentoId);
        return (Recursos) GsonUtils.getInstance().getObject(retorno, this.getClass());
    }

    public Recursos edit(Context context) {
        String retorno = ThreadUtils.postMethod(context, this, "recurso/edit/" + this.getId());
        return (Recursos) GsonUtils.getInstance().getObject(retorno, this.getClass());
    }

    public void delete(Context context) {
        ThreadUtils.postMethod(context, this, "recurso/delete/" + this.getId());
    }
}
