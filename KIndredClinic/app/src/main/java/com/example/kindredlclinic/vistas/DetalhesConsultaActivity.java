package com.example.kindredlclinic.vistas;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kindredlclinic.R;
import com.example.kindredlclinic.models.Consulta;
import com.example.kindredlclinic.models.MarcacaoConsulta;
import com.example.kindredlclinic.models.SingletonKindredClinic;
import com.example.kindredlclinic.utils.ConsultaJsonParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.Calendar;


public class DetalhesConsultaActivity extends AppCompatActivity {

    public static final String CHAVE_ID = "idConsulta";

    private int idConsulta;
    private TextView data, medico, especialidade;
    private MarcacaoConsulta marcacao;
    private FloatingActionButton fab;
    private SharedPreferences sharedPreferences;

    //Calendario
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private int day,mes,year;

    private MarcacaoConsulta consultaSelecionada;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detalhes_consulta);

        data = findViewById(R.id.tvConsultaData);
        medico = findViewById(R.id.tvConsultaMedico);
        especialidade = findViewById(R.id.tvConsultaEspecialidade);
        fab = findViewById(R.id.fab);

        // Recebe o id da marcacao consulta como parâmentro e vai buscar a marcacao ao SingletonGestaoHotel pelo id
        idConsulta = getIntent().getIntExtra(CHAVE_ID,-1);


        sharedPreferences = getSharedPreferences("old_user", MODE_PRIVATE);

        //System.out.println("--> " + dataSaida);

        if(idConsulta == -1){
            setTitle("Adicionar Consulta");
            System.out.println("--> adicionar");
            fab.setImageResource(R.drawable.ic_adicionar);
        } else {

            //System.out.println("--> Reserva: detalhes  " + idReserva);
            mostrarReserva(idConsulta);
            //System.out.println("Reserva: " + idReserva);
           // fab.setImageResource(R.drawable.ic_alterar);
        }

        /*/// <----------------------------Calendario--------------------------------->
        ////God all mighty https://www.youtube.com/watch?v=-mJmScTAWyQ
        ///Tem que ter a EditText android:focusable="false" para resultar

        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        mes = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(DetalhesConsultaActivity.this, new DatePickerDialog.OnDateSetListener() {
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
        });*/

        // <----------------------------------Fab------------------------------------->

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ConsultaJsonParser.isConnectionInternet(getApplicationContext())){
                    if(idConsulta == -1){
                       SingletonKindredClinic.getInstance(getApplicationContext()).adicionarMarcacaoConsultaAPI(adicionarConsulta(), getApplicationContext(), sharedPreferences.getString("username",null), sharedPreferences.getString("password",null));
                        finish();
                    } else {
                       SingletonKindredClinic.getInstance(getApplicationContext()).editarMarcacaoAPI(editarConsulta(), getApplicationContext(), sharedPreferences.getString("username",null), sharedPreferences.getString("password",null));
                        finish();
                    }
                }else{
                    Toast.makeText(DetalhesConsultaActivity.this, R.string.offline, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private MarcacaoConsulta adicionarConsulta(){

        /*int quartoS = Integer.parseInt(quartosSolteiro.getText().toString());
        int quartoD = Integer.parseInt(quartosDuplo.getText().toString());
        int quartoF = Integer.parseInt(quartosFamilia.getText().toString());
        int quartoC = Integer.parseInt(quartosCasal.getText().toString());
*/

        MarcacaoConsulta auxiliar = new MarcacaoConsulta(0, "teste date", Integer.parseInt(especialidade.toString()), 0, 0);
        System.out.println("--> Marcacao Consulta" + auxiliar);
        return auxiliar;
    }

    private MarcacaoConsulta editarConsulta(){

        consultaSelecionada.setDate(data.getText().toString());
        consultaSelecionada.setId_medico(Integer.parseInt(medico.toString()));
        consultaSelecionada.setId_especialidade(Integer.parseInt(especialidade.toString()));

        return consultaSelecionada;
    }


    private void  mostrarReserva(int idConsulta){

        consultaSelecionada = SingletonKindredClinic.getInstance(getApplicationContext()).getMarcacaoConsultaBD(idConsulta);
        System.out.println("--> ConsultaSelecionada: " + consultaSelecionada);
        //setTitle("Reserva");
        data.setText( consultaSelecionada.getDate());
        medico.setText(String.valueOf(consultaSelecionada.getId_medico()));
        especialidade.setText(String.valueOf(consultaSelecionada.getId_especialidade()));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if(idConsulta != -1) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_detalhes_consulta, menu);
            return super.onCreateOptionsMenu(menu);
        }
        return false;
    }

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
            Toast.makeText(DetalhesConsultaActivity.this, R.string.offline, Toast.LENGTH_SHORT).show();
        }
    }
}
