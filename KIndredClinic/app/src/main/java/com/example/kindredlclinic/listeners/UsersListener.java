package com.example.kindredlclinic.listeners;

import com.example.kindredlclinic.models.User;

import java.util.ArrayList;

public interface UsersListener {

    void onRefreshListaUser(ArrayList<User> listaUsers);
    void onUpdateListaUserBD(User user , int operacao);
}
