package com.example.kindredlclinic.models;

public class MarcacaoConsulta {

    private int id;
    private String date;

    private int id_especialidade, id_medico, id_utente;

    public MarcacaoConsulta(int id, String date, int id_especialidade, int id_medico, int id_utente) {
        this.id = id;
        this.date = date;
        this.id_especialidade = id_especialidade;
        this.id_medico = id_medico;
        this.id_utente = id_utente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId_especialidade() {
        return id_especialidade;
    }

    public void setId_especialidade(int id_especialidade) {
        this.id_especialidade = id_especialidade;
    }

    public int getId_medico() {
        return id_medico;
    }

    public void setId_medico(int id_medico) {
        this.id_medico = id_medico;
    }

    public int getId_utente() {
        return id_utente;
    }

    public void setId_utente(int id_utente) {
        this.id_utente = id_utente;
    }
}
