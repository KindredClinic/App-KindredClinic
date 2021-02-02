package com.example.kindredlclinic.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.kindredlclinic.models.Consulta;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ConsultaJsonParser {

    // Devolve um Array de Consultas vindo da API
    public static ArrayList<Consulta> parserJsonConsultas(JSONArray response, Context context){

        System.out.println("--> ConsultaJsonParser: " + response);
        ArrayList<Consulta> tempListaConsultas = new ArrayList<Consulta>();

        try {
            for (int i = 0; i < response.length(); i++){

                JSONObject consulta = (JSONObject)response.get(i);

                int idConsulta = consulta.getInt("id");
                String date = consulta.getString("date");                               // Nomes
                String conteudo = consulta.getString("conteudo");                       // iguais aos
                int id_marcacao = consulta.getInt("id_marcacao");                       // que estão
                int id_medico = consulta.getInt("id_medico");                           // na API
                int id_utente = consulta.getInt("id_utente");

                Consulta auxConsulta = new Consulta(idConsulta, date, conteudo, id_marcacao, id_medico, id_utente);

                tempListaConsultas.add(auxConsulta);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return tempListaConsultas;
    }


    // Devolve uma Reserva vinda da API
    public static Consulta parserJsonConsultas(String response, Context context){

        System.out.println("--> PARSER ADICIONAR: " + response);
        Consulta auxConsulta = null;

        try {
            JSONObject consulta = new JSONObject(response);

            int idConsulta = consulta.getInt("id");
            String date = consulta.getString("date");                               // Nomes
            String conteudo = consulta.getString("conteudo");                       // iguais aos
            int id_marcacao = consulta.getInt("id_marcacao");                       // que estão
            int id_medico = consulta.getInt("id_medico");                           // na API
            int id_utente = consulta.getInt("id_utente");

            auxConsulta = new Consulta(idConsulta, date, conteudo, id_marcacao, id_medico, id_utente);

        }
        catch (JSONException e)
        {
            e.printStackTrace();
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return auxConsulta;
    }


    // Método que verifica se existe acesso à internet
    public static boolean isConnectionInternet(Context context){

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

}
