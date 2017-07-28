package com.sgpa.models;

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

    @Override
    public String toString() {
        return "Titulo: " + this.getTitulo() + " \nSubtitulo " + this.getSubtitulo();
    }


}
