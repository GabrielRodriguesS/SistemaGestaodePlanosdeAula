package com.sgpa.models;


import com.google.gson.annotations.SerializedName;
import com.sgpa.utils.GsonUtils;
import com.sgpa.utils.WebClient;

import java.io.Serializable;
import java.util.ArrayList;

public class Momentos implements Serializable {

    private Long id;
    private String nome;
    private String texto;
    @SerializedName("planoDeAula")
    private PlanosDeAula planoDeAula;
    @SerializedName("recursos")
    private ArrayList<Recursos> recursos;

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
        return this.getId().toString();
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

    public Momentos save(long planoDeAulaId){
        String object = GsonUtils.getInstance().setObject(this);
        WebClient webClient = new WebClient("momento/save/"+planoDeAulaId, object);
        Thread t = new Thread(webClient);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Momentos m = (Momentos) GsonUtils.getInstance().getObject(webClient.getRetornoJson(), Momentos.class);
        return m;
    }

    public Momentos edit(){
        String object = GsonUtils.getInstance().setObject(this);
        WebClient webClient = new WebClient("momento/edit/"+getId(), object);
        Thread t = new Thread(webClient);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return (Momentos) GsonUtils.getInstance().getObject(webClient.getRetornoJson(), Momentos.class);
    }

    public void delete() {
        String json = GsonUtils.getInstance().setObject(this);
        WebClient webClient = new WebClient("momento/delete/" + this.getId(), json);
        Thread thread = new Thread(webClient);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Momentos show(long id) {
        WebClient webClient = new WebClient("momento/show/" + id);
        Thread thread = new Thread(webClient);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return (Momentos) GsonUtils.getInstance().getObject(webClient.getRetornoJson(), Momentos.class);
    }
}
