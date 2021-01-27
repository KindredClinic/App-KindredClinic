package com.example.kindredlclinic.listeners;

import com.example.kindredlclinic.models.Medicamentos;

import java.util.ArrayList;

public interface MedicamentosListener {

    void onRefreshListaMedicamentos(ArrayList<Medicamentos> listaMedicamentos);
    void onUpdateListaMedicamentosBD(Medicamentos medicamentos , int operacao);
}
