package com.example.kindredlclinic.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.kindredlclinic.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserJsonParser {


    // Devolve um Array de Users vindo da API
    public static ArrayList<User> parserJsonUsers(JSONArray response, Context context){

        System.out.println("--> UserJsonParser: " + response);
        ArrayList<User> tempListaUsers = new ArrayList<User>();

        try {
            for (int i = 0; i < response.length(); i++){

                JSONObject users = (JSONObject)response.get(i);

                int idUser = users.getInt("id");
                String username = users.getString("username");
                String email = users.getString("email");
                String password = users.getString("password");

                User auxUser = new User(idUser, username, email, password);

                tempListaUsers.add(auxUser);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return tempListaUsers;
    }


    // Devolve um User vinda da API
    public static User parserJsonUsers(String response, Context context){

        System.out.println("--> PARSER ADICIONAR: " + response);
        User auxUser = null;

        try {
            JSONObject users = new JSONObject(response);

            int idUser = users.getInt("id");
            String username = users.getString("username");
            String email = users.getString("email");
            String password = users.getString("password");

            auxUser = new User(idUser, username, email, password);

        }
        catch (JSONException e)
        {
            e.printStackTrace();
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return auxUser;
    }


    // Método que verifica se existe acesso à internet
    public static boolean isConnectionInternet(Context context){

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

}
