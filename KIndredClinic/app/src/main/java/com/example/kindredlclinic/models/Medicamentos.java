package com.example.kindredlclinic.models;

public class Medicamentos {
    private int id, miligramas;
    private String nome, descricao;

    public Medicamentos(int id, String nome, int miligramas, String descricao) {
        this.id = id;
        this.nome = nome;
        this.miligramas = miligramas;
        this.descricao = descricao;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getMiligramas() {
        return miligramas;
    }

    public void setMiligramas(int miligramas) {
        this.miligramas = miligramas;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
