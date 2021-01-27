/*
package com.example.kindredlclinic.vistas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kindredlclinic.MainActivity;
import com.example.kindredlclinic.R;
import com.example.kindredlclinic.models.ClinicDBHelper;
import com.example.kindredlclinic.models.SingletonKindredClinic;


public class LoginActivity extends AppCompatActivity {


    private EditText editTextUser;
    private EditText editTextPassword;
    private SharedPreferences sharedPreferences;
    Context context = this;

    private String user;
    private String password;


    TextView registar;

    ClinicDBHelper clinicDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        //retira a status bar
        getSupportActionBar().hide();

        registar = findViewById(R.id.tvLinktoRegistar);
        editTextUser = findViewById(R.id.editTextUser);
        editTextPassword = findViewById(R.id.editTextPassword);

        sharedPreferences = getSharedPreferences("old_user", MODE_PRIVATE);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.CHAVE_EMAIL, sharedPreferences.getString("username",null));

        if(sharedPreferences.contains("username") && sharedPreferences.contains("password")){
            String recebido = SingletonKindredClinic.getInstance(context).getUsersAPI(context,
                    ReservaJsonParser.isConnectionInternet(context), sharedPreferences.getString("username",null), sharedPreferences.getString("password",null));

            startActivity(intent);
        }
    }

    public void onClick(View v) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://10.200.19.150:8080/site/signup"));
        startActivity(browserIntent);
    }

    public void onClickLogin(View view) {

        user = String.valueOf(editTextUser.getText());
        password = String.valueOf(editTextPassword.getText());

        // Se a password nao for válida
        if (!isPasswordValida(password)) {
            editTextPassword.setError(getString(R.string.password_invalida));
            return;
        }

        // recebe o id do utilizador
        String recebido = SingletonKindredClinic.getInstance(context).getUsersAPI(context,
                ReservaJsonParser.isConnectionInternet(context), user, password);


        System.out.println("--> Recevido: " + recebido);
        // se o user existir vai devolver o valor do id
        // se nao existir devolve null
        // bug: devolve sempre null primeiro, tem que se clicar duas vezes
        if(!(recebido == null)){

            //Guardar o user e password confirmados pela autenticacao
            SharedPreferences.Editor saveLogin = sharedPreferences.edit();
            saveLogin.putString("username", user);
            saveLogin.putString("password", password);
            saveLogin.commit();

            //iniciar para a main activity
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(MainActivity.CHAVE_EMAIL, user);
            startActivity(intent);
        }
** *********

    }


    private boolean isPasswordValida(String password) {
        if(password == null){
            return false;
        }
        return password.length() > 4;
    }

}
*/
