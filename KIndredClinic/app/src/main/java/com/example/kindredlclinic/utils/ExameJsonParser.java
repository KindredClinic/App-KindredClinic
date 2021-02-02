package com.example.kindredlclinic.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.kindredlclinic.models.Exame;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ExameJsonParser {

    // Devolve um Array de Exame vindo da API
    public static ArrayList<Exame> parserJsonExame(JSONArray response, Context context){

        System.out.println("--> ExameJsonParser: " + response);
        ArrayList<Exame> tempListaExame = new ArrayList<Exame>();

        try {
            for (int i = 0; i < response.length(); i++){

                JSONObject exame = (JSONObject)response.get(i);

                int idExame = exame.getInt("id");
                String date = exame.getString("date");                               // Nomes
                String conteudo = exame.getString("conteudo");                       // iguais aos
                int id_marcacao = exame.getInt("id_marcacao");                       // que estão
                int id_medico = exame.getInt("id_medico");                           // na API
                int id_utente = exame.getInt("id_utente");

                Exame auxExame = new Exame(idExame, date, conteudo, id_marcacao, id_medico, id_utente);

                tempListaExame.add(auxExame);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return tempListaExame;
    }


    // Devolve um Exame vinda da API
    public static Exame parserJsonExame(String response, Context context){

        System.out.println("--> PARSER ADICIONAR: " + response);
        Exame auxExame = null;

        try {
            JSONObject exame = new JSONObject(response);

            int idExame = exame.getInt("id");
            String date = exame.getString("date");                               // Nomes
            String conteudo = exame.getString("conteudo");                       // iguais aos
            int id_marcacao = exame.getInt("id_marcacao");                       // que estão
            int id_medico = exame.getInt("id_medico");                           // na API
            int id_utente = exame.getInt("id_utente");

            auxExame = new Exame(idExame, date, conteudo, id_marcacao, id_medico, id_utente);

        }
        catch (JSONException e)
        {
            e.printStackTrace();
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return auxExame;
    }

    // Método que verifica se existe acesso à internet
    public static boolean isConnectionInternet(Context context){

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

}
