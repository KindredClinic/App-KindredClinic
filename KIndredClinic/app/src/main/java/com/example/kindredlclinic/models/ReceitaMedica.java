package com.example.kindredlclinic.models;

public class ReceitaMedica {
    private int id;
    private String date, conteudo;

    private int id_medicamento, id_medico, id_utente;

    public ReceitaMedica(int id, String date, String conteudo, int id_medicamento, int id_medico, int id_utente) {
        this.id = id;
        this.date = date;
        this.conteudo = conteudo;
        this.id_medicamento = id_medicamento;
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

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public int getId_medicamento() {
        return id_medicamento;
    }

    public void setId_medicamento(int id_medicamento) {
        this.id_medicamento = id_medicamento;
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
