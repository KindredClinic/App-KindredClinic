package com.example.kindredlclinic.models;

import android.content.Context;
import android.util.Base64;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.kindredlclinic.listeners.ConsultasListener;
import com.example.kindredlclinic.listeners.EspecialidadesListener;
import com.example.kindredlclinic.listeners.ExamesListener;
import com.example.kindredlclinic.listeners.MarcacaoConsultasListener;
import com.example.kindredlclinic.listeners.MedicamentosListener;
import com.example.kindredlclinic.listeners.MedicosListener;
import com.example.kindredlclinic.listeners.ReceitasMedicasListener;
import com.example.kindredlclinic.listeners.UsersListener;
import com.example.kindredlclinic.listeners.UtentesListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SingletonKindredClinic implements ConsultasListener, EspecialidadesListener, ExamesListener, MarcacaoConsultasListener, MedicamentosListener, MedicosListener, ReceitasMedicasListener, UsersListener, UtentesListener {

    private static RequestQueue volleyQueue = null;

    private String idUtente = null;
    private String mUrlAPIUSERS = " http://localhost:8081/api/users";
    private String mUrlAPIPROFILES = "http://192.168.1.67:8081/api/profiles";
    private String mUrlAPIPEDIDOS = "http://192.168.1.67:8081/api/pedidos";
    private String mUrlAPIRESERVAS = "http://192.168.1.67:8081/api/reservas";
    private String mUrlAPIPRODUTOS = "http://192.168.1.67:8081/api/produtos";
    private String mUrlAPITIPOPRODUTO = "http://192.168.1.67:8081/api/tipoprodutos";
    private String mUrlAPIQUARTOS = "http://192.168.1.67:8081/api/quartos";
    private String mUrlAPITIPOQUARTO = "http://192.168.1.67:8081/api/tipoquartos";
    private String mUrlAPIRESERVAQUARTO = "http://192.168.1.67:8081/api/reservaquartos";
    private String mUrlAPILINHAPRODUTO = "http://192.168.1.67:8081/api/linhaprodutos";
    private String mUrlAPICLASSIFICACAO = "http://192.168.1.67:8081/api/classificacoes";



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

    // <--------------  Métodos para garantir que os dados da BD estão atualizados com os dados vindos da API -------------->

    // <----------------------------------- USERS ----------------------------------->

    public User getUserBD(int id) {
        for (User u : users) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    public ArrayList<User> getUsersBD() {
        return users = clinicDBHelper.getAllUsersBD();
    }

    public void adicionarUserBD(User user) {
        clinicDBHelper.adicionarUserBD(user);
    }

    public void adicionarUsersBD(ArrayList<User> users) {
        clinicDBHelper.removerAllUsers();
    }

    public void removerUserBD(int id) {
        User auxUser = getUserBD(id);

        if (auxUser != null) {
            if (clinicDBHelper.removerUserBD(auxUser.getId())) {
                users.remove(auxUser);
                System.out.println("--> User removido");
            }
        }
    }

    public void guardarUserBD(User user) {
        if (!users.contains(user)) {
            return;
        }

        User auxUser = getUserBD(user.getId());
        auxUser.setUsername(user.getUsername());
        auxUser.setEmail(user.getEmail());
        auxUser.setPassword(user.getPassword());

        if (clinicDBHelper.guardarUserBD(auxUser)) {
            System.out.println("--> USer Guardado na BD");
        }
    }

    public void idUtenteNull() {
        idUtente = null;
    }

    // <----------------------------------- Utente ----------------------------------->

    public Utente getUtenteBD(int id_user) {
        for (Utente u : utentes) {
            if (u.getId_user() == id_user) {
                return u;
            }
        }
        return null;
    }

    public ArrayList<Utente> getUtentesBD() {
        utentes = clinicDBHelper.getAllUtentesBD();
        return utentes;
    }

    public void adicionarUtenteBD(Utente utente) {
        clinicDBHelper.adicionarUtenteBD(utente);
    }

    public void adicionarUtentesBD(ArrayList<Utente> utentes) {
        clinicDBHelper.removerALLUtentesDB();
    }

    public void removerUtenteBD(int id_user) {
        Utente auxUtente = getUtenteBD(id_user);

        if (auxUtente != null) {
            if (clinicDBHelper.removerUtenteBD(auxUtente.getId_user())) {
                utentes.remove(auxUtente);
                System.out.println("--> Utente removido");
            }
        }
    }

    public void guardarProfileBD(Utente utente) {
        if (!utentes.contains(utente)) {
            return;
        }

        Utente auxUtente = getUtenteBD(utente.getId_user());
        auxUtente.setNome(utente.getNome());
        auxUtente.setNif(utente.getNif());
        auxUtente.setTelemovel(utente.getTelemovel());
        auxUtente.setEmail(utente.getEmail());
        auxUtente.setMorada(utente.getMorada());
        auxUtente.setSexo(utente.getSexo());
        auxUtente.setNum_sns(utente.getNum_sns());

        if (clinicDBHelper.guardarUtenteBD(auxUtente)) {
            System.out.println("--> Utente Guardado");
        }
    }

    //<----------------------------- Métodos para atualizarem a API ----------------------------->

    // <----------------------------------- USERS ----------------------------------->
    public String getUsersAPI(final Context context, boolean isConected, final String username, final String password) {
        //Toast.makeText(context, "Is Connected", Toast.LENGTH_SHORT).show();

        if(!isConected){
            Toast.makeText(context, "Dispositivo Offline", Toast.LENGTH_SHORT).show();
            users = clinicDBHelper.getAllUsersBD();

            if(userListener != null){
                userListener.onRefreshListaUser(users);
            }}else {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, mUrlAPIUSERS,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println("-->" + response);
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response);
                                idUtente = jsonObject.getString("id");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            System.out.println("--> id: " + idUtente);

                            System.out.println("--> OK");
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("--> Error: Invalido - " + error.getMessage());
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    user = username;
                    pass = password;

                    String loginString = user + ":" + pass;

                    byte[] loginStringBytes = null;

                    try {
                        loginStringBytes = loginString.getBytes("UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    String loginStringb64 = Base64.encodeToString(loginStringBytes, Base64.NO_WRAP);

                    //  Authorization: Basic $auth
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Authorization", "Basic " + loginStringb64);
                    return headers;
                }
            };
            volleyQueue.add(stringRequest);
        }
        return idUtente;

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
