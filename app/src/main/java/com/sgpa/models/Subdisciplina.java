package com.sgpa.models;


import com.google.gson.annotations.SerializedName;

public class Subdisciplina {

    private Long id;

    private String nome;

    @SerializedName("disciplinas_id")
    private Long disciplinaId;

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

    public Long getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(Long disciplinaId) {
        this.disciplinaId = disciplinaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subdisciplina)) return false;

        Subdisciplina that = (Subdisciplina) o;

        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Subdisciplina{" +
                "nome='" + nome + '\'' +
                '}';
    }
}
