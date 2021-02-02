package com.example.kindredlclinic.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.kindredlclinic.models.Consulta;
import com.example.kindredlclinic.models.Especialidade;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EspecialidadeJsonParser {

    // Devolve um Array de Consultas vindo da API
    public static ArrayList<Especialidade> parserJsonEspecialidade(JSONArray response, Context context){

        System.out.println("--> EspecialidadeJsonParser: " + response);
        ArrayList<Especialidade> tempListaEspecialidade = new ArrayList<Especialidade>();

        try {
            for (int i = 0; i < response.length(); i++){

                JSONObject especialidade = (JSONObject)response.get(i);

                int idEspecialidade = especialidade.getInt("id");
                String tipo = especialidade.getString("tipo");

                Especialidade auxEspecialidade = new Especialidade(idEspecialidade, tipo);

                tempListaEspecialidade.add(auxEspecialidade);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return tempListaEspecialidade;
    }

    // Devolve uma Reserva vinda da API
    public static Especialidade parserJsonEspecialidade(String response, Context context){

        System.out.println("--> PARSER ADICIONAR: " + response);
        Especialidade auxEspecialidade = null;

        try {
            JSONObject especialidade = new JSONObject(response);

            int idEspecialidade = especialidade.getInt("id");
            String tipo = especialidade.getString("tipo");

            auxEspecialidade = new Especialidade(idEspecialidade, tipo);

        }
        catch (JSONException e)
        {
            e.printStackTrace();
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return auxEspecialidade;
    }

    // Método que verifica se existe acesso à internet
    public static boolean isConnectionInternet(Context context){

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }
}
