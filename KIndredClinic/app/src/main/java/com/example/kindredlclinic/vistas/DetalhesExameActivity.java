package com.example.kindredlclinic.vistas;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kindredlclinic.R;
import com.example.kindredlclinic.models.MarcacaoExame;
import com.example.kindredlclinic.models.SingletonKindredClinic;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetalhesExameActivity extends AppCompatActivity {

    public static final String CHAVE_ID = "idExame";
    public static int Teste = 0;

    private int idExame;
    private TextView data, medico, especialidade, status;
    private MarcacaoExame marcacao;
    private FloatingActionButton fab;
    private SharedPreferences sharedPreferences;

    private MarcacaoExame exameSelecionado;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detalhes_consulta);

        data = findViewById(R.id.tvConsultaData);
        medico = findViewById(R.id.tvConsultaMedico);
        especialidade = findViewById(R.id.tvConsultaEspecialidade);
        status = findViewById(R.id.tvConsultaStatus);

        fab = findViewById(R.id.fab);

        System.out.println("--> chave " + CHAVE_ID);

        // Recebe o id da marcacao consulta como parâmentro e vai buscar a marcacao ao SingletonGestaoHotel pelo id
        idExame = getIntent().getIntExtra(CHAVE_ID,-1);

        sharedPreferences = getSharedPreferences("old_user", MODE_PRIVATE);

        if(idExame == -1){
            setTitle("Adicionar Consulta");
            System.out.println("--> adicionar");
            fab.setImageResource(R.drawable.ic_adicionar);
        } else {

            //System.out.println("--> Reserva: detalhes  " + idReserva);
            mostrarExame(idExame);
            //System.out.println("Reserva: " + idReserva);
            // fab.setImageResource(R.drawable.ic_alterar);
        }

    }

    private void mostrarExame(int idExame){

        exameSelecionado = SingletonKindredClinic.getInstance(getApplicationContext()).getMarcacaoExameBD(idExame);
        System.out.println("--> ExameSelecionada: " + exameSelecionado);

        data.setText(exameSelecionado.getDate());
        medico.setText(String.valueOf(exameSelecionado.getId_medico()));
        especialidade.setText(String.valueOf(exameSelecionado.getId_especialidade()));
        status.setText(exameSelecionado.getStatus());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if(idExame != -1) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_detalhes_consulta, menu);
            return super.onCreateOptionsMenu(menu);
        }
        return false;
    }

}
