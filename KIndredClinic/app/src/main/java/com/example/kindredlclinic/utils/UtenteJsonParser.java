package com.example.kindredlclinic.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.kindredlclinic.models.Utente;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UtenteJsonParser {

    // Devolve um Array de Utente vindo da API
    public static ArrayList<Utente> parserJsonUtentes(JSONArray response, Context context){

        System.out.println("--> UtenteJsonParser: " + response);
        ArrayList<Utente> tempListaUtente = new ArrayList<Utente>();

        try {
            for (int i = 0; i < response.length(); i++){

                JSONObject utente = (JSONObject)response.get(i);

                //int idUtente = utente.getInt("id");
                String nome = utente.getString("nome");
                int nif = utente.getInt("nif");
                int telemovel = utente.getInt("telemovel");
                String morada = utente.getString("morada");
                int num_sns = utente.getInt("num_sns");
                String sexo = utente.getString("sexo");
                String email = utente.getString("email");
                int id_user = utente.getInt("id_user");

                Utente auxUtente = new Utente(nome, nif, telemovel, morada, num_sns, sexo, email, id_user);

                tempListaUtente.add(auxUtente);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return tempListaUtente;
    }


    // Devolve um Utente vinda da API
    public static Utente parserJsonUtentes(String response, Context context){

        System.out.println("--> PARSER ADICIONAR: " + response);
        Utente auxUtente = null;

        try {
            JSONObject utente = new JSONObject(response);

            //int idUtente = utente.getInt("id");
            String nome = utente.getString("nome");
            int nif = utente.getInt("nif");
            int telemovel = utente.getInt("telemovel");
            String morada = utente.getString("morada");
            int num_sns = utente.getInt("num_sns");
            String sexo = utente.getString("sexo");
            String email = utente.getString("email");
            int id_user = utente.getInt("id_user");

            auxUtente = new Utente(nome, nif, telemovel, morada, num_sns, sexo, email, id_user);

        }
        catch (JSONException e)
        {
            e.printStackTrace();
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return auxUtente;
    }


    // Método que verifica se existe acesso à internet
    public static boolean isConnectionInternet(Context context){

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

}
