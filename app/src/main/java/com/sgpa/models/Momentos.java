package com.sgpa.models;


import com.google.gson.annotations.SerializedName;
import com.sgpa.utils.WebClient;

import java.io.Serializable;
import java.util.ArrayList;

public class Momentos implements Serializable {

    private Long id;
    private String nome;
    private String texto;
    @SerializedName("planos_de_aula_id")
    private PlanosDeAula planosDeAula;
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

    public PlanosDeAula getPlanosDeAula() {
        return planosDeAula;
    }

    public void setPlanosDeAula(PlanosDeAula planosDeAula) {
        this.planosDeAula = planosDeAula;
    }

    public ArrayList<Recursos> getRecursos() {
        return recursos;
    }

    public void setRecursos(ArrayList<Recursos> recursos) {
        this.recursos = recursos;
    }

    @Override
    public String toString() {
        return "Momentos{" +
                "nome='" + nome + '\'' +
                ", texto='" + texto + '\'' +
                '}';
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

    public void save(){
        //TODO Transformar em JSONObject e Salvar pelo WebClient
    }
}
