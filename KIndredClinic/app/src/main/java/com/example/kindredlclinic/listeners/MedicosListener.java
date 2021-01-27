package com.example.kindredlclinic.listeners;


import com.example.kindredlclinic.models.Medico;

import java.util.ArrayList;

public interface MedicosListener {

    void onRefreshListaMedico(ArrayList<Medico> listaMedico);
    void onUpdateListaMedicoBD(Medico medico , int operacao);
}
