package com.sgpa.models;


import com.google.gson.annotations.SerializedName;
import com.sgpa.utils.GsonUtils;
import com.sgpa.utils.WebClient;

import java.io.Serializable;

public class Recursos implements Serializable {

    private Long id;
    private String link;
    @SerializedName("momento")
    private Momentos momento;

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
        return this.getId().toString();
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

    public void save() {
        String object = GsonUtils.getInstance().setObject(this);
        WebClient webClient = new WebClient("recurso/save/"+this.getMomento().getId(), object);
        Thread t = new Thread(webClient);
        t.start();
    }

    public void save(long momentoId) {
        String object = GsonUtils.getInstance().setObject(this);
        WebClient webClient = new WebClient("recurso/save/"+momentoId, object);
        Thread t = new Thread(webClient);
        t.start();
    }
}
