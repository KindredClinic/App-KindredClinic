package com.example.kindredlclinic.vistas;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.kindredlclinic.R;
import com.example.kindredlclinic.models.SingletonKindredClinic;
import com.example.kindredlclinic.models.Utente;
import com.example.kindredlclinic.utils.UtenteJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

public class UtenteActivity extends AppCompatActivity {

    private int idUtente;
    private TextView tvNome, tvNif, tvTelemovel, tvmorada, tvNumSns, tvSexo, tvEmail;
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        JSONObject id = SingletonKindredClinic.getInstance(getApplicationContext()).getAllUtentesAPI(getApplicationContext(), UtenteJsonParser.isConnectionInternet(getApplicationContext()));

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setContentView(R.layout.activity_detalhes_utente);

                Toolbar toolbar = findViewById(R.id.toolbar);
                toolbar.setTitle("Perfil");
                //setSupportActionBar(toolbar);
                //setTitle("Perfil");

                tvNome = findViewById(R.id.tvUtenteNome);
                tvNif = findViewById(R.id.tvUtenteNif);
                tvTelemovel = findViewById(R.id.tvUtenteTelemovel);
                tvmorada = findViewById(R.id.tvUtenteMorada);
                tvNumSns = findViewById(R.id.tvUtenteNumUtente);
                tvSexo = findViewById(R.id.tvUtenteSexo);
                tvEmail = findViewById(R.id.tvUtenteEmail);

                sharedPreferences = getSharedPreferences("old_user", MODE_PRIVATE);

                System.out.println("--> start " + id);

                try {
                    tvNome.setText(id.getString("nome"));
                    tvNif.setText(String.valueOf(id.getString("nif")));
                    tvTelemovel.setText(String.valueOf(id.getString("telemovel")));
                    tvmorada.setText(id.getString("morada"));
                    tvNumSns.setText(String.valueOf(id.getString("num_sns")));
                    tvSexo.setText(id.getString("sexo"));
                    tvEmail.setText(id.getString("email"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, 1250);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if(idUtente != -1) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_detalhes_consulta, menu);
            return super.onCreateOptionsMenu(menu);
        }
        return false;
    }

}
