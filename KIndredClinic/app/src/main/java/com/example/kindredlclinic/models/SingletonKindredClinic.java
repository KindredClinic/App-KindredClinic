package com.example.kindredlclinic.models;

import android.content.Context;

import com.example.kindredlclinic.listeners.ConsultasListener;
import com.example.kindredlclinic.listeners.EspecialidadesListener;
import com.example.kindredlclinic.listeners.ExamesListener;
import com.example.kindredlclinic.listeners.MarcacaoConsultasListener;
import com.example.kindredlclinic.listeners.MedicamentosListener;
import com.example.kindredlclinic.listeners.MedicosListener;
import com.example.kindredlclinic.listeners.ReceitasMedicasListener;
import com.example.kindredlclinic.listeners.UsersListener;
import com.example.kindredlclinic.listeners.UtentesListener;

import java.util.ArrayList;

public class SingletonKindredClinic implements ConsultasListener, EspecialidadesListener, ExamesListener, MarcacaoConsultasListener, MedicamentosListener, MedicosListener, ReceitasMedicasListener, UsersListener, UtentesListener {

    private ArrayList<User> users;
    private ArrayList<Utente> utentes;
    private ArrayList<Medico> medicos;
    private ArrayList<Consulta> consultas;
    private ArrayList<Exame> exames;
    private ArrayList<MarcacaoConsulta> marcacaoConsultas;
    private ArrayList<Medicamentos> medicamentos;
    private ArrayList<ReceitaMedica> receitaMedicas;
    private ArrayList<Especialidade> especialidades;

    private static SingletonKindredClinic INSTANCE = null;
    private ClinicDBHelper clinicDBHelper = null;

    private UsersListener userListener;
    private ConsultasListener reservasListener;
    private EspecialidadesListener pedidosListener;
    private ExamesListener produtosListener;
    private MarcacaoConsultasListener quartosListener;
    private MedicamentosListener tipoQuartosListener;
    private MedicosListener tipoProdutosListener;
    private ReceitasMedicasListener linhaProdutosListener;
    private UtentesListener classificacoesListener;

    //Verificacao
    private String user;
    private String pass;

    public static synchronized SingletonKindredClinic getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SingletonKindredClinic(context);
        }
        return INSTANCE;
    }

    private SingletonKindredClinic(Context context) {

        users = new ArrayList<>();
        utentes = new ArrayList<>();
        medicos = new ArrayList<>();
        consultas = new ArrayList<>();
        exames = new ArrayList<>();
        marcacaoConsultas = new ArrayList<>();
        medicamentos = new ArrayList<>();
        receitaMedicas = new ArrayList<>();
        especialidades = new ArrayList<>();

        clinicDBHelper = new ClinicDBHelper(context);
    }

    @Override
    public void onRefreshListaConsulta(ArrayList<Consulta> listaConsulta) {

    }

    @Override
    public void onUpdateListaConsultaBD(Consulta consulta, int operacao) {

    }

    @Override
    public void onRefreshListaEspecialidades(ArrayList<Especialidade> listaEspecialidades) {

    }

    @Override
    public void onUpdateListaEspecialidadesBD(Especialidade especialidade, int operacao) {

    }

    @Override
    public void onRefreshListaExame(ArrayList<Exame> listaExame) {

    }

    @Override
    public void onUpdateListaExameBD(Exame exane, int operacao) {

    }

    @Override
    public void onRefreshListaMarcacaoConsultas(ArrayList<MarcacaoConsulta> listaMarcacaoConsultas) {

    }

    @Override
    public void onUpdateListaMarcacaoConsultasBD(MarcacaoConsulta marcacaoConsulta, int operacao) {

    }

    @Override
    public void onRefreshListaMedicamentos(ArrayList<Medicamentos> listaMedicamentos) {

    }

    @Override
    public void onUpdateListaMedicamentosBD(Medicamentos medicamentos, int operacao) {

    }

    @Override
    public void onRefreshListaMedico(ArrayList<Medico> listaMedico) {

    }

    @Override
    public void onUpdateListaMedicoBD(Medico medico, int operacao) {

    }

    @Override
    public void onRefreshListaReceitaMedica(ArrayList<ReceitaMedica> listaReceitaMedica) {

    }

    @Override
    public void onUpdateListaReceitaMedicaBD(ReceitaMedica receitaMedica, int operacao) {

    }

    @Override
    public void onRefreshListaUser(ArrayList<User> listaUsers) {

    }

    @Override
    public void onUpdateListaUserBD(User user, int operacao) {

    }

    @Override
    public void onRefreshListaUtente(ArrayList<Utente> listaUtentes) {

    }

    @Override
    public void onUpdateListaUtenteBD(Utente utente, int operacao) {

    }
}
