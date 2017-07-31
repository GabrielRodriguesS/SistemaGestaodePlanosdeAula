package com.sgpa.models;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.sgpa.utils.GsonUtils;
import com.sgpa.utils.WebClient;

import java.io.Serializable;
import java.util.ArrayList;


public class PlanosDeAula implements Serializable {

    private Long id;
    private String titulo;
    private String subtitulo;
    private String descricao;
    private ArrayList<Momentos> momentos;

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

    public PlanosDeAula save() {
        String json = GsonUtils.getInstance().setObject(this);
        WebClient webClient = new WebClient("planoDeAula/save", json);
        Thread thread = new Thread(webClient);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return (PlanosDeAula) GsonUtils.getInstance().getObject(webClient.getJson(), PlanosDeAula.class);
    }

    public PlanosDeAula edit() {
        String json = GsonUtils.getInstance().setObject(this);
        WebClient webClient = new WebClient("planoDeAula/edit/" + this.getId(), json);
        Thread thread = new Thread(webClient);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return (PlanosDeAula) GsonUtils.getInstance().getObject(webClient.getJson(), PlanosDeAula.class);
    }

    public void delete() {
        String json = GsonUtils.getInstance().setObject(this);
        WebClient webClient = new WebClient("planoDeAula/delete/" + this.getId(), json);
        Thread thread = new Thread(webClient);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public PlanosDeAula show(Long id) {
        WebClient webClient = new WebClient("planoDeAula/show/" + id);
        Thread thread = new Thread(webClient);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return (PlanosDeAula) GsonUtils.getInstance().getObject(webClient.getJson(), PlanosDeAula.class);
    }
}
