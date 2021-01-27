package com.example.kindredlclinic.listeners;

import com.example.kindredlclinic.models.Consulta;

import java.util.ArrayList;

public interface ConsultasListener {

    void onRefreshListaConsulta(ArrayList<Consulta> listaConsulta);
    void onUpdateListaConsultaBD(Consulta consulta , int operacao);
}
