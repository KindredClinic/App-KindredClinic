package com.example.kindredlclinic.listeners;

import com.example.kindredlclinic.models.Exame;

import java.util.ArrayList;

public interface ExamesListener {

    void onRefreshListaExame(ArrayList<Exame> listaExame);
    void onUpdateListaExameBD(Exame exane , int operacao);
}
