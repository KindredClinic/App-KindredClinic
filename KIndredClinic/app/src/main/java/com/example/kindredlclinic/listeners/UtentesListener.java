package com.example.kindredlclinic.listeners;

import com.example.kindredlclinic.models.Utente;

import java.util.ArrayList;

public interface UtentesListener {

    void onRefreshListaUtente(ArrayList<Utente> listaUtentes);
    void onUpdateListaUtenteBD(Utente utente , int operacao);
}
