package com.sgpa.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class PlanosDeAula implements Serializable {

    private Long id;

    private String titulo;

    private String subtitulo;

    private String descricao;

    @SerializedName("professores_id")
    private Long professorId;

    public PlanosDeAula() {
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

    public Long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Long professorId) {
        this.professorId = professorId;
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
        return "Titulo: " + this.titulo + " Id: " + this.id;
    }


}
