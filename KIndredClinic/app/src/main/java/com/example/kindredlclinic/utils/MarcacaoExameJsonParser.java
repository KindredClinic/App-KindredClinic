package com.example.kindredlclinic.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.kindredlclinic.models.MarcacaoConsulta;
import com.example.kindredlclinic.models.MarcacaoExame;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MarcacaoExameJsonParser {

    // Devolve um Array de Marcacao Consulta vindo da API
    public static ArrayList<MarcacaoExame> parserJsonMarcacaoExame(JSONArray response, Context context){

        System.out.println("--> Marcacao Exame JsonParser: " + response);
        ArrayList<MarcacaoExame> tempListaMarcacao = new ArrayList<MarcacaoExame>();

        try {
            for (int i = 0; i < response.length(); i++) {

                JSONObject marExame = (JSONObject) response.get(i);

                int idMarExame = marExame.getInt("id");
                String data = marExame.getString("date");
                int idEspecialidade = marExame.getInt("id_especialidade");
                int idMedico = marExame.getInt("id_medico");
                int idUtente = marExame.getInt("id_utente");
                String status = marExame.getString("status");

                MarcacaoExame auxMarcacaoEx = new MarcacaoExame(idMarExame,data,idEspecialidade,idMedico,idUtente,status);

                tempListaMarcacao.add(auxMarcacaoEx);
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
    public static MarcacaoExame parserJsonMarcacaoExame(String response, Context context){

        System.out.println("--> PARSER ADICIONAR: " + response);
        MarcacaoExame auxExame = null;

        try {
            JSONObject marExame = new JSONObject(response);

            int idMarExame = marExame.getInt("id");
            String data = marExame.getString("date");
            int idEspecialidade = marExame.getInt("id_marcacao");
            int idMedico = marExame.getInt("id_medico");
            int idUtente = marExame.getInt("id_utente");
            String status = marExame.getString("status");

            auxExame = new MarcacaoExame(idMarExame, data, idEspecialidade, idMedico, idUtente,status);

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
