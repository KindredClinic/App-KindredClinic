package com.example.kindredlclinic.listeners;

import com.example.kindredlclinic.models.ReceitaMedica;

import java.util.ArrayList;

public interface ReceitasMedicasListener {

    void onRefreshListaReceitaMedica(ArrayList<ReceitaMedica> listaReceitaMedica);
    void onUpdateListaReceitaMedicaBD(ReceitaMedica receitaMedica , int operacao);
}
