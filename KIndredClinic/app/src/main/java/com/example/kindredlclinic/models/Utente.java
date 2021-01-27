package com.example.kindredlclinic.models;

public class Utente {
    private int nif, telemovel, num_sns;
    private String nome, morada, sexo, email;

    private int id_user;

    public Utente(String nome, int nif, int telemovel,  String morada, int num_sns, String sexo, String email, int id_user) {
        this.nome = nome;
        this.nif = nif;
        this.telemovel = telemovel;
        this.morada = morada;
        this.num_sns = num_sns;
        this.sexo = sexo;
        this.email = email;
        this.id_user = id_user;
    }

    public int getNif() {
        return nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    public int getTelemovel() {
        return telemovel;
    }

    public void setTelemovel(int telemovel) {
        this.telemovel = telemovel;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNum_sns() {
        return num_sns;
    }

    public void setNum_sns(int num_sns) {
        this.num_sns = num_sns;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }
}
