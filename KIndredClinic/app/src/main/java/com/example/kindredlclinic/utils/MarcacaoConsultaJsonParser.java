package com.example.kindredlclinic.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.kindredlclinic.models.Consulta;
import com.example.kindredlclinic.models.MarcacaoConsulta;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MarcacaoConsultaJsonParser {

    // Devolve um Array de Marcacao Consulta vindo da API
    public static ArrayList<MarcacaoConsulta> parserJsonMarcacaoConsulta(JSONArray response, Context context){

        System.out.println("--> Marcacao Consulta JsonParser: " + response);
        ArrayList<MarcacaoConsulta> tempListaMarcacao = new ArrayList<MarcacaoConsulta>();

        try {
            for (int i = 0; i < response.length(); i++) {

                JSONObject marConsulta = (JSONObject) response.get(i);

                int idMarConsulta = marConsulta.getInt("id");
                String data = marConsulta.getString("date");
                int idEspecialidade = marConsulta.getInt("id_especialidade");
                int idMedico = marConsulta.getInt("id_medico");
                int idUtente = marConsulta.getInt("id_utente");

                MarcacaoConsulta auxMarcacaoCon = new MarcacaoConsulta(idMarConsulta,data,idEspecialidade,idMedico,idUtente);

                tempListaMarcacao.add(auxMarcacaoCon);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return tempListaMarcacao;
    }


    // Devolve uma Marcacao Consulta vinda da API
    public static MarcacaoConsulta parserJsonMarcacaoConsulta(String response, Context context){

        System.out.println("--> PARSER ADICIONAR: " + response);
        MarcacaoConsulta auxConsulta = null;

        try {
            JSONObject marConsulta = new JSONObject(response);

            int idMarConsulta = marConsulta.getInt("id");
            String data = marConsulta.getString("date");
            int idEspecialidade = marConsulta.getInt("id_marcacao");
            int idMedico = marConsulta.getInt("id_medico");
            int idUtente = marConsulta.getInt("id_utente");

            auxConsulta = new MarcacaoConsulta(idMarConsulta, data, idEspecialidade, idMedico, idUtente);

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
