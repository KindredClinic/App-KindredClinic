package com.example.kindredlclinic.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.kindredlclinic.models.ReceitaMedica;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReceitaMedicaJsonParser {


    // Devolve um Array de ReceitaMedica vindo da API
    public static ArrayList<ReceitaMedica> parserJsonResulta(JSONArray response, Context context){

        System.out.println("--> ReceitaMedicaJsonParser: " + response);
        ArrayList<ReceitaMedica> tempListaReceitaMedica = new ArrayList<ReceitaMedica>();

        try {
            for (int i = 0; i < response.length(); i++){

                JSONObject receita = (JSONObject)response.get(i);

                int idReceita = receita.getInt("id");
                String date = receita.getString("date");                               // Nomes
                String conteudo = receita.getString("conteudo");                       // iguais aos
                int id_medicamento = receita.getInt("id_medicamentos");                       // que estão
                int id_medico = receita.getInt("id_medico");                           // na API
                int id_utente = receita.getInt("id_utente");

                ReceitaMedica auxReceitaMedica = new ReceitaMedica(idReceita, date, conteudo, id_medicamento, id_medico, id_utente);

                tempListaReceitaMedica.add(auxReceitaMedica);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return tempListaReceitaMedica;
    }


    // Devolve uma Reserva vinda da API
    public static ReceitaMedica parserJsonResulta(String response, Context context){

        System.out.println("--> PARSER ADICIONAR: " + response);
        ReceitaMedica auxReceitaMedica = null;

        try {
            JSONObject receita = new JSONObject(response);

            int idReceita = receita.getInt("id");
            String date = receita.getString("date");                               // Nomes
            String conteudo = receita.getString("conteudo");                       // iguais aos
            int id_medicamento = receita.getInt("id_medicamentos");                       // que estão
            int id_medico = receita.getInt("id_medico");                           // na API
            int id_utente = receita.getInt("id_utente");


            auxReceitaMedica = new ReceitaMedica(idReceita, date, conteudo, id_medicamento, id_medico, id_utente);

        }
        catch (JSONException e)
        {
            e.printStackTrace();
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return auxReceitaMedica;
    }


    // Método que verifica se existe acesso à internet
    public static boolean isConnectionInternet(Context context){

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

}
