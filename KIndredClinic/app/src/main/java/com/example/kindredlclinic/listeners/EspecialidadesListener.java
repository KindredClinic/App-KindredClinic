package com.example.kindredlclinic.listeners;

import com.example.kindredlclinic.models.Especialidade;

import java.util.ArrayList;

public interface EspecialidadesListener {

    void onRefreshListaEspecialidades(ArrayList<Especialidade> listaEspecialidades);
    void onUpdateListaEspecialidadesBD(Especialidade especialidade , int operacao);
}
