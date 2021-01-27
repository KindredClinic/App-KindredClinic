package com.example.kindredlclinic.listeners;

import com.example.kindredlclinic.models.MarcacaoConsulta;

import java.util.ArrayList;

public interface MarcacaoConsultasListener {

    void onRefreshListaMarcacaoConsultas(ArrayList<MarcacaoConsulta> listaMarcacaoConsultas);
    void onUpdateListaMarcacaoConsultasBD(MarcacaoConsulta marcacaoConsulta , int operacao);
}
