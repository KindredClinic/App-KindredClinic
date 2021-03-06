package com.example.kindredlclinic.vistas;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.kindredlclinic.R;
import com.example.kindredlclinic.models.Consulta;
import com.example.kindredlclinic.models.MarcacaoConsulta;
import com.example.kindredlclinic.models.ReceitaMedica;
import com.example.kindredlclinic.models.SingletonKindredClinic;
import com.example.kindredlclinic.utils.ConsultaJsonParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class DetalhesReceitaActivity extends AppCompatActivity {

    public static final String CHAVE_ID = "id";

    private int idReceita;
    private TextView tvReceitaData, tvReceitaConteudo, tvReceitaMedico, tvReceitaMedicamento;
    private EditText data;
    private Consulta consulta;
    private MarcacaoConsulta marcacao;
    private FloatingActionButton fab;
    private SharedPreferences sharedPreferences;

    //Calendario
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private int day,mes,year;

    private ReceitaMedica receitaMedica;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detalhes_receita);

        Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        toolbar.setTitle("Receita Medica");

        tvReceitaData = findViewById(R.id.tvReceitaData);
        tvReceitaConteudo = findViewById(R.id.tvReceitaConteudo);
        tvReceitaMedico = findViewById(R.id.tvReceitaMedico);
        tvReceitaMedicamento = findViewById(R.id.tvReceitaMedicamentos);

        // Recebe o id da marcacao consulta como parâmentro e vai buscar a marcacao ao SingletonGestaoHotel pelo id
        idReceita = getIntent().getIntExtra(CHAVE_ID,-1);


        sharedPreferences = getSharedPreferences("old_user", MODE_PRIVATE);

        //System.out.println("--> " + dataSaida);

        if(idReceita == -1){
            setTitle("Adicionar Consulta");
            System.out.println("--> adicionar");
            fab.setImageResource(R.drawable.ic_adicionar);
        } else {
            mostrarReceita(idReceita);
        }
/*
        /// <----------------------------Calendario--------------------------------->
        ////God all mighty https://www.youtube.com/watch?v=-mJmScTAWyQ
        ///Tem que ter a EditText android:focusable="false" para resultar

        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        mes = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(DetalhesReceitaActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        // alterar a ordem da data dd/mm/yy
                        data.setText(year + "-" + (month+1)  + "-" + day);
                    }
                }, day,mes,year);
                //Impede que o utilizador escolha uma data anterior à atual
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
*/
        // <----------------------------------Fab------------------------------------->
/*
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ReceitaMedicaJsonParser.isConnectionInternet(getApplicationContext())){
                    if(idReceita == -1){
                       SingletonKindredClinic.getInstance(getApplicationContext()).adicionarReceitasAPI(adicionarReceita(), getApplicationContext(), sharedPreferences.getString("username",null), sharedPreferences.getString("password",null));
                        finish();
                    } else {
                       SingletonKindredClinic.getInstance(getApplicationContext()).editarReceitaAPI(editarReceita(), getApplicationContext(), sharedPreferences.getString("username",null), sharedPreferences.getString("password",null));
                        finish();
                    }
                }else{
                    Toast.makeText(DetalhesReceitaActivity.this, R.string.offline, Toast.LENGTH_SHORT).show();
                }
            }
        });
*/
    }
/*
    private ReceitaMedica adicionarReceita(){

        /*int quartoS = Integer.parseInt(quartosSolteiro.getText().toString());
        int quartoD = Integer.parseInt(quartosDuplo.getText().toString());
        int quartoF = Integer.parseInt(quartosFamilia.getText().toString());
        int quartoC = Integer.parseInt(quartosCasal.getText().toString());


        ReceitaMedica auxiliar = new ReceitaMedica(0, "2021-01-08 23:31:08", "Integer.parseInt(spEspecialidade.toString())", 22, 3, 2);
        System.out.println("--> Receita Medica " + auxiliar);
        return auxiliar;
    }

    private ReceitaMedica editarReceita(){

        receitaMedica.setDate(data.getText().toString());
        receitaMedica.setId_medico(Integer.parseInt(tvReceitaMedico.toString()));

        return receitaMedica;
    }

*/
    private void mostrarReceita(int idReceitaAux){

        receitaMedica = SingletonKindredClinic.getInstance(getApplicationContext()).getReceitaMedicaBD(idReceitaAux);
        System.out.println("--> ReceitaSelecionada: " + receitaMedica);
        //setTitle("Reserva");
        //data.setText(receitaMedica.getDate());
        tvReceitaData.setText(receitaMedica.getDate());
        tvReceitaConteudo.setText(Html.fromHtml(String.valueOf(Html.fromHtml(receitaMedica.getConteudo().toString()))));
        tvReceitaMedico.setText(String.valueOf(receitaMedica.getId_medico()));
        tvReceitaMedicamento.setText(String.valueOf(receitaMedica.getId_medicamento()));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if(idReceita != -1) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_detalhes_consulta, menu);
            return super.onCreateOptionsMenu(menu);
        }
        return false;
    }
/*
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.itemRemover){
            //Toast.makeText(this, "Remover", Toast.LENGTH_SHORT).show();
            dialogRemover();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void dialogRemover() {
        if(ConsultaJsonParser.isConnectionInternet(getApplicationContext())){
            AlertDialog.Builder builder;

            builder = new AlertDialog.Builder(this);
            // Contruindo Alert Dialog
            // Título
            builder.setTitle("Cancelar Reserva")
                    // Messagem
                    .setMessage("Pretende mesmo cancelar a reserva?")
                    // 2 Botões
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                           // SingletonKindredClinic.getInstance(getApplicationContext()).removerMarcacaoConsultaAPI(consultaSelecionada, sharedPreferences.getString("username",null), sharedPreferences.getString("password",null));
                            finish();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Não faz nada
                        }
                    })
                    // Icon
                    .setIcon(android.R.drawable.ic_delete)
                    .show();
        } else {
            Toast.makeText(DetalhesReceitaActivity.this, R.string.offline, Toast.LENGTH_SHORT).show();
        }
    }


 */
}