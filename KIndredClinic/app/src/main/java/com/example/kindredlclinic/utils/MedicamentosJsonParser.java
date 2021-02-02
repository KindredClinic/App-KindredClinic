package com.example.kindredlclinic.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.kindredlclinic.models.Consulta;
import com.example.kindredlclinic.models.Medicamentos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MedicamentosJsonParser {

    // Devolve um Array de Consultas vindo da API
    public static ArrayList<Medicamentos> parserJsonMedicamentos(JSONArray response, Context context){

        System.out.println("--> MedicamentosJsonParser: " + response);
        ArrayList<Medicamentos> tempListaMedicamentos = new ArrayList<Medicamentos>();

        try {
            for (int i = 0; i < response.length(); i++){

                JSONObject medicamentos = (JSONObject)response.get(i);

                int idMedicamentos = medicamentos.getInt("id");
                String nome = medicamentos.getString("nome");
                int miligramas = medicamentos.getInt("miligramas");
                String descricao = medicamentos.getString("descricao");

                Medicamentos auxMedicamentos = new Medicamentos(idMedicamentos, nome, miligramas, descricao);

                tempListaMedicamentos.add(auxMedicamentos);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return tempListaMedicamentos;
    }


    // Devolve uma Reserva vinda da API
    public static Medicamentos parserJsonMedicamentos(String response, Context context){

        System.out.println("--> PARSER ADICIONAR: " + response);
        Medicamentos auxMedicamentos = null;

        try {
            JSONObject medicamentos = new JSONObject(response);

            int idMedicamentos = medicamentos.getInt("id");
            String nome = medicamentos.getString("nome");
            int miligramas = medicamentos.getInt("miligramas");
            String descricao = medicamentos.getString("descricao");

            auxMedicamentos = new Medicamentos(idMedicamentos, nome, miligramas, descricao);

        }
        catch (JSONException e)
        {
            e.printStackTrace();
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return auxMedicamentos;
    }


    // Método que verifica se existe acesso à internet
    public static boolean isConnectionInternet(Context context){

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }


}
