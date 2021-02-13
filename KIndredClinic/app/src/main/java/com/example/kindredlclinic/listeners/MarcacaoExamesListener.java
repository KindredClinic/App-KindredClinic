package com.example.kindredlclinic.listeners;

import com.example.kindredlclinic.models.MarcacaoExame;

import java.util.ArrayList;

public interface MarcacaoExamesListener {

    void onRefreshListaMarcacaoExames(ArrayList<MarcacaoExame> listaMarcacaoExames);
    void onUpdateListaMarcacaoExamesBD(MarcacaoExame marcacaoExame , int operacao);
}
