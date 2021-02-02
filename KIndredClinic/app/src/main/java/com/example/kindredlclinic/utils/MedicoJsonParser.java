package com.example.kindredlclinic.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.kindredlclinic.models.Consulta;
import com.example.kindredlclinic.models.Medico;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MedicoJsonParser {


    // Devolve um Array de Medico vindo da API
    public static ArrayList<Medico> parserJsonMedico(JSONArray response, Context context){

        System.out.println("--> MedicoJsonParser: " + response);
        ArrayList<Medico> tempListaMedicos = new ArrayList<Medico>();

        try {
            for (int i = 0; i < response.length(); i++){

                JSONObject medicos = (JSONObject)response.get(i);

                int idMedico = medicos.getInt("id");
                String nome = medicos.getString("nome");
                int nif = medicos.getInt("nif");
                int telefone = medicos.getInt("telefone");
                int num_ordem_medico = medicos.getInt("num_ordem_medico");
                String sexo = medicos.getString("sexo");
                int id_especialidade = medicos.getInt("id_especialidade");
                int id_user = medicos.getInt("id_user");

                Medico auxMedico = new Medico(nome, nif, telefone, num_ordem_medico, sexo,id_especialidade,id_user);

                tempListaMedicos.add(auxMedico);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return tempListaMedicos;
    }

    // Devolve um Medico vinda da API
    public static Medico parserJsonMedico(String response, Context context){

        System.out.println("--> PARSER ADICIONAR: " + response);
        Medico auxMedico = null;

        try {
            JSONObject medicos = new JSONObject(response);

            int idMedico = medicos.getInt("id");
            String nome = medicos.getString("nome");
            int nif = medicos.getInt("nif");
            int telefone = medicos.getInt("telefone");
            int num_ordem_medico = medicos.getInt("num_ordem_medico");
            String sexo = medicos.getString("sexo");
            int id_especialidade = medicos.getInt("id_especialidade");
            int id_user = medicos.getInt("id_user");

            auxMedico = new Medico(nome, nif, telefone, num_ordem_medico, sexo,id_especialidade,id_user);

        }
        catch (JSONException e)
        {
            e.printStackTrace();
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return auxMedico;
    }


    // Método que verifica se existe acesso à internet
    public static boolean isConnectionInternet(Context context){

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }


}
