package com.example.kindredlclinic.models;

public class Medico {
    private int nif, telefone, num_ordem_medico;
    private String nome, sexo;

    private int id_user, id_especialidade;

    public Medico(String nome, int nif, int telefone, int num_ordem_medico, String sexo, int id_especialidade, int id_user) {
        this.nome = nome;
        this.nif = nif;
        this.telefone = telefone;
        this.num_ordem_medico = num_ordem_medico;
        this.sexo = sexo;
        this.id_especialidade = id_especialidade;
        this.id_user = id_user;
    }

    public int getNif() {
        return nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getNum_ordem_medico() {
        return num_ordem_medico;
    }

    public void setNum_ordem_medico(int num_ordem_medico) {
        this.num_ordem_medico = num_ordem_medico;
    }

    public int getId_especialidade() {
        return id_especialidade;
    }

    public void setId_especialidade(int id_especialidade) {
        this.id_especialidade = id_especialidade;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }
}
