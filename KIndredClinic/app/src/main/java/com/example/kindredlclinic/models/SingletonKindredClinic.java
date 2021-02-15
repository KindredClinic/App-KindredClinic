package com.example.kindredlclinic.models;

import android.content.Context;
import android.util.Base64;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kindredlclinic.listeners.ConsultasListener;
import com.example.kindredlclinic.listeners.EspecialidadesListener;
import com.example.kindredlclinic.listeners.ExamesListener;
import com.example.kindredlclinic.listeners.MarcacaoConsultasListener;
import com.example.kindredlclinic.listeners.MarcacaoExamesListener;
import com.example.kindredlclinic.listeners.MedicamentosListener;
import com.example.kindredlclinic.listeners.MedicosListener;
import com.example.kindredlclinic.listeners.ReceitasMedicasListener;
import com.example.kindredlclinic.listeners.UsersListener;
import com.example.kindredlclinic.listeners.UtentesListener;
import com.example.kindredlclinic.utils.ConsultaJsonParser;
import com.example.kindredlclinic.utils.MarcacaoConsultaJsonParser;
import com.example.kindredlclinic.utils.MarcacaoExameJsonParser;
import com.example.kindredlclinic.utils.ReceitaMedicaJsonParser;
import com.example.kindredlclinic.utils.UtenteJsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SingletonKindredClinic implements ConsultasListener, EspecialidadesListener, ExamesListener, MarcacaoConsultasListener, MedicamentosListener, MedicosListener, ReceitasMedicasListener, UsersListener, UtentesListener {

    private static RequestQueue volleyQueue = null;

    private String idUtente = null;
    private String mUrlAPIUSERS = "http://192.168.0.12:8081/api/users";
    private String mUrlAPIMEDICOS = "http://192.168.0.12:8081/api/medicos";
    private String mUrlAPIMARCACAOEXAME = "http://192.168.0.12:8081/api/marcacaoexames";
    private String mUrlAPIMARCACAOCONSULTA = "http://192.168.0.12:8081/api/marcacaoconsultas";
    private String mUrlAPIRECEITA = "http://192.168.0.12:8081/api/receita";
    private String mUrlAPIUTENTE = "http://192.168.0.12:8081/api/utentes";
    private String mUrlAPITIPOPRODUTO = "http://192.168.1.75:8081/api/tipoprodutos";
    private String mUrlAPIQUARTOS = "http://192.168.1.75:8081/api/quartos";
    private String mUrlAPITIPOQUARTO = "http://192.168.1.75:8081/api/tipoquartos";
    private String mUrlAPIRESERVAQUARTO = "http://192.168.1.75:8081/api/reservaquartos";
    private String mUrlAPILINHAPRODUTO = "http://192.168.1.75:8081/api/linhaprodutos";
    private String mUrlAPICLASSIFICACAO = "http://192.168.1.75:8081/api/classificacoes";



    private ArrayList<User> users;
    private ArrayList<Utente> utentes;
    private ArrayList<Medico> medicos;
    private ArrayList<Consulta> consultas;
    private ArrayList<Exame> exames;
    private ArrayList<MarcacaoConsulta> marcacaoConsultas;
    private ArrayList<MarcacaoExame> marcacaoExames;
    private ArrayList<Medicamentos> medicamentos;
    private ArrayList<ReceitaMedica> receitaMedicas;
    private ArrayList<Especialidade> especialidades;

    private static SingletonKindredClinic INSTANCE = null;
    private ClinicDBHelper clinicDBHelper = null;

    private UsersListener userListener;
    private ConsultasListener consultasListener;
    private EspecialidadesListener especialidadesListener;
    private ExamesListener examesListener;
    private MarcacaoExamesListener marcacaoExamesListener;
    private MarcacaoConsultasListener marcacaoConsultasListener;
    private MedicamentosListener medicamentosListener;
    private MedicosListener medicosListener;
    private ReceitasMedicasListener receitasMedicasListener;
    private UtentesListener utentesListener;

    //Verificacao
    private String user;
    private String pass;

    public static synchronized SingletonKindredClinic getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SingletonKindredClinic(context);
            volleyQueue = Volley.newRequestQueue(context);
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
        marcacaoExames = new ArrayList<>();
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

    // <----------------------------------- CONSULTAS ----------------------------------->

    public ArrayList<Consulta> getConsultasBD() {
        return consultas;
    }

    public Consulta getConsultaBD(long idConsulta) {
        for (Consulta c : consultas) {
            if (c.getId() == idConsulta) {
                return c;
            }
        }
        return null;
    }

    // <----------------------------------- MARCACAO CONSULTAS ----------------------------------->

    public ArrayList<MarcacaoConsulta> getMarcacaoConsultasBD() {
        return marcacaoConsultas;
    }

    public MarcacaoConsulta getMarcacaoConsultaBD(long idMarcacao) {
        for (MarcacaoConsulta mc : marcacaoConsultas) {
            if (mc.getId() == idMarcacao) {
                return mc;
            }
        }
        return null;
    }

    public void adicionarMarcacaoConsultaBD(MarcacaoConsulta marcacaoConsulta) {
        marcacaoConsultas.add(marcacaoConsulta);
    }

    public void adicionarMarcacaoConsultasBD(ArrayList<MarcacaoConsulta> marcacaoConsultas) {
        for (MarcacaoConsulta marcacaoConsulta: marcacaoConsultas) {
            clinicDBHelper.adicionarMarcacaoConsultaBD(marcacaoConsulta);
        }
    }

    public void removerMarcacaoConsultaBD(int idMarcacao) {
        MarcacaoConsulta auxMarcacao = getMarcacaoConsultaBD(idMarcacao);
        marcacaoConsultas.remove(auxMarcacao);
    }

    public void guardarReservaBD(MarcacaoConsulta marcacaoConsulta) {
        if (!marcacaoConsultas.contains(marcacaoConsulta)) {
            return;
        }
        MarcacaoConsulta auxReserva = getMarcacaoConsultaBD(marcacaoConsulta.getId());
        auxReserva.setDate(marcacaoConsulta.getDate());
        auxReserva.setId_especialidade(marcacaoConsulta.getId_especialidade());
        auxReserva.setId_medico(marcacaoConsulta.getId_medico());
        auxReserva.setId_utente(marcacaoConsulta.getId_utente());
        auxReserva.setStatus(marcacaoConsulta.getStatus());
        //auxReserva.setNumQuartos(reserva.getNumQuartos());
    }

    // <----------------------------------- MARCACAO EXAMES ----------------------------------->

    public ArrayList<MarcacaoExame> getMarcacaoExamesBD(){
        return marcacaoExames;
    }

    public MarcacaoExame getMarcacaoExameBD(long idExame){
        for (MarcacaoExame mx : marcacaoExames){
            if(mx.getId() == idExame){
                return  mx;
            }
        }
        return null;
    }

    // <----------------------------------- RECEITAS ----------------------------------->

    public ArrayList<ReceitaMedica> getReceitaMedicasBD(){
        return receitaMedicas;
    }

    public ReceitaMedica getReceitaMedicaBD(long idReceita){
        for(ReceitaMedica rm : receitaMedicas){
            if(rm.getId() == idReceita){
                return rm;
            }
        }
        return null;
    }

    public void adicionarReceitaMedicaBD(ReceitaMedica receitaMedica){
        receitaMedicas.add(receitaMedica);
    }

    public void adicionarReceitaMedicaBD(ArrayList<ReceitaMedica> receitaMedicas){
        for(ReceitaMedica receitaMedica: receitaMedicas){
            clinicDBHelper.adicionarReceitaMedicaBD(receitaMedica);
        }
    }

    public void removerReceitaMedicaBD(int idReceita){
        ReceitaMedica auxReceita = getReceitaMedicaBD(idReceita);
        receitaMedicas.remove(auxReceita);
    }

    public void guardarReceitaBD(ReceitaMedica receitaMedica){
        if(!receitaMedicas.contains(receitaMedica)){
            return;
        }
        ReceitaMedica auxReceita = getReceitaMedicaBD(receitaMedica.getId());
        auxReceita.setDate(receitaMedica.getDate());
        auxReceita.setConteudo(receitaMedica.getConteudo());
        auxReceita.setId_medico(receitaMedica.getId_medico());
        auxReceita.setId_utente(receitaMedica.getId_utente());
        auxReceita.setId_medicamento(receitaMedica.getId_medicamento());
    }

    //<----------------------------- Métodos para atualizarem a API ----------------------------->

    // <----------------------------------- USERS ----------------------------------->
    public String getUsersAPI(final Context context, boolean isConected, final String username, final String password){
        //Toast.makeText(context, "Is Connected", Toast.LENGTH_SHORT).show();

        if(!isConected){
            Toast.makeText(context, "Offline", Toast.LENGTH_SHORT).show();
            users = clinicDBHelper.getAllUsersBD();

            if(userListener != null){
                userListener.onRefreshListaUser(users);
            }
        }else {
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

    // <----------------------------------- CONSULTAS ----------------------------------->

    // Vai buscar as consultas todas à API
    public void getAllConsultasAPI(final Context context, boolean isConnected){

        //Toast.makeText(context, "ISCONNECTED: " + isConnected, Toast.LENGTH_SHORT).show();
        if(!isConnected){
            Toast.makeText(context, "Dispositivo Offline", Toast.LENGTH_SHORT).show();
            consultas = clinicDBHelper.getAllConsultasBD();

            if(consultasListener != null){
                consultasListener.onRefreshListaConsulta(consultas);
            }
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPIUSERS + "/" + idUtente + "/consultas", null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    consultas = ConsultaJsonParser.parserJsonConsultas(response, context);
                    //System.out.println("--> RESPOSTA: " + response);
                    //adicionarConsultasBD(consultas);

                    if(consultasListener != null){
                        consultasListener.onRefreshListaConsulta(consultas);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("--> ERRO: getAllConsultasAPI: " + error.getMessage());
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

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
            volleyQueue.add(req);
        }

    }

    // <--------------------------------------- MARCACAO CONSULTAS --------------------------------------->

    // Vai buscar todas as Marcacoes Consultas à API
    public void getAllMarcacaoConsultasAPI(final Context context, boolean isConnected){

        //Toast.makeText(context, "ISCONNECTED: " + isConnected, Toast.LENGTH_SHORT).show();
        if(!isConnected){
            Toast.makeText(context, "NotConnected", Toast.LENGTH_SHORT).show();
            marcacaoConsultas = clinicDBHelper.getAllMarcacoesConsultaBD();

            if(marcacaoConsultasListener != null){
                marcacaoConsultasListener.onRefreshListaMarcacaoConsultas(marcacaoConsultas);
            }
        } else {
            //Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show();
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPIMARCACAOCONSULTA, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    marcacaoConsultas = MarcacaoConsultaJsonParser.parserJsonMarcacaoConsulta(response, context);
                    // adicionarMarcacaoConsultasBD(marcacaoConsultas);

                    if(marcacaoConsultasListener != null){
                        marcacaoConsultasListener.onRefreshListaMarcacaoConsultas(marcacaoConsultas);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("--> ERRO: getAllMarcacaoConsultasAPI: " + error.getMessage());
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

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
            volleyQueue.add(req);
        }
    }

    public void adicionarMarcacaoConsultaAPI(final MarcacaoConsulta marcacaoConsulta, final Context context, final String username, final String password){
        System.out.println("--> RESPOSTA ADD POST : " + marcacaoConsulta);
        StringRequest req = new StringRequest(Request.Method.POST, mUrlAPIMARCACAOCONSULTA + "/adicionarconulta", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println("--> RESPOSTA ADD POST: " + response);

                if(marcacaoConsultasListener != null){
                    marcacaoConsultasListener.onUpdateListaMarcacaoConsultasBD(MarcacaoConsultaJsonParser.parserJsonMarcacaoConsulta(response, context), 1);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("--> ERRO: adicionarMarcacaoConsultaAPI: " + error.getMessage());
            }
        }){
            protected Map<String, String> getParams(){

                Map<String, String> params = new HashMap<>();
                params.put("id", marcacaoConsulta.getId() + "");
                params.put("date", marcacaoConsulta.getDate() + "");
                params.put("id_especialidade", marcacaoConsulta.getId_especialidade() + "");
                params.put("id_medico", marcacaoConsulta.getId_medico() + "");
                params.put("id_utente", marcacaoConsulta.getId_utente() + "");
                params.put("status", marcacaoConsulta.getStatus() + "");

                return params;
            }
        };
        volleyQueue.add(req);
    }

    // Remove a Marcacao da API
    public void removerMarcacaoConsultaAPI(final MarcacaoConsulta marcacaoConsulta){

        final StringRequest req = new StringRequest(Request.Method.DELETE, mUrlAPIMARCACAOCONSULTA + '/' + marcacaoConsulta.getId(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println("--> RESPOSTA REMOVER: " + response);

                if(marcacaoConsultasListener != null){
                    marcacaoConsultasListener.onUpdateListaMarcacaoConsultasBD(marcacaoConsulta,3);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println("--> ERRO: removerMarcacaoConsultaAPI: " + error.getMessage());
            }
        });
        volleyQueue.add(req);
    }

    // Atualiza a Marcacao na API
    public void editarMarcacaoAPI(final MarcacaoConsulta marcacaoConsulta, final Context context, final String username, final String password){

        StringRequest req = new StringRequest(Request.Method.PUT, mUrlAPIMARCACAOCONSULTA + '/' + marcacaoConsulta.getId(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println("--> editarMarcacaoAPI: " + response);

                if(marcacaoConsultasListener != null){
                    marcacaoConsultasListener.onUpdateListaMarcacaoConsultasBD(MarcacaoConsultaJsonParser.parserJsonMarcacaoConsulta(response, context), 2);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println("--> ERRO: editarMarcacaoAPI: " + error.getMessage());
            }
        }){
            protected Map<String, String> getParams(){

                Map<String, String> params = new HashMap<>();
                params.put("date", marcacaoConsulta.getDate() + "");
                params.put("id_medico", marcacaoConsulta.getId_medico() + "");
                params.put("id_especialidade", marcacaoConsulta.getId_especialidade() + "");

                return params;
            }
        };
        volleyQueue.add(req);
    }

    public void setMarcacaoConsultasListener(MarcacaoConsultasListener marcacaoConsultasListener){

        this.marcacaoConsultasListener = marcacaoConsultasListener;
    }

    // <--------------------------------------- MARCACAO EXAMES --------------------------------------->

    // Vai buscar todas as Marcacoes Exames à API
    public void getAllMarcacaoExamesAPI(final Context context, boolean isConnected){

        //Toast.makeText(context, "ISCONNECTED: " + isConnected, Toast.LENGTH_SHORT).show();
        if(!isConnected){
            Toast.makeText(context, "NotConnected", Toast.LENGTH_SHORT).show();
            marcacaoExames = clinicDBHelper.getAllMarcacoesExameBD();

            if(marcacaoExamesListener != null){
                marcacaoExamesListener.onRefreshListaMarcacaoExames(marcacaoExames);
            }
        } else {
            //Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show();
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPIMARCACAOEXAME, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    marcacaoExames = MarcacaoExameJsonParser.parserJsonMarcacaoExame(response, context);
                    // adicionarMarcacaoConsultasBD(marcacaoConsultas);

                    if(marcacaoExamesListener != null){
                        marcacaoExamesListener.onRefreshListaMarcacaoExames(marcacaoExames);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("--> ERRO: getAllMarcacaoExamesAPI: " + error.getMessage());
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

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
            volleyQueue.add(req);
        }
    }

    public void adicionarMarcacaoExameAPI(final MarcacaoExame marcacaoExame, final Context context, final String username, final String password){

        StringRequest req = new StringRequest(Request.Method.POST, mUrlAPIMARCACAOEXAME, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println("--> RESPOSTA ADD POST: " + response);

                if(marcacaoExamesListener != null){
                    marcacaoExamesListener.onUpdateListaMarcacaoExamesBD(MarcacaoExameJsonParser.parserJsonMarcacaoExame(response, context), 1);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("--> ERRO: adicionarMarcacaoExameAPI: " + error.getMessage());
            }
        }){
            protected Map<String, String> getParams(){

                Map<String, String> params = new HashMap<>();
                params.put("date", marcacaoExame.getDate() + "");
                params.put("id_medico", marcacaoExame.getId_medico() + "");
                params.put("id_especialidade", marcacaoExame.getId_especialidade() + "");

                return params;
            }
        };
        volleyQueue.add(req);
    }

    // Atualiza a Marcacao na API
    public void editarMarcacaoExameAPI(final MarcacaoExame marcacaoExame, final Context context, final String username, final String password){

        StringRequest req = new StringRequest(Request.Method.PUT, mUrlAPIMARCACAOEXAME + '/' + marcacaoExame.getId(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println("--> editarMarcacaoExameAPI: " + response);

                if(marcacaoExamesListener != null){
                    marcacaoExamesListener.onUpdateListaMarcacaoExamesBD(MarcacaoExameJsonParser.parserJsonMarcacaoExame(response, context), 2);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println("--> ERRO: editarMarcacaoExameAPI: " + error.getMessage());
            }
        }){
            protected Map<String, String> getParams(){

                Map<String, String> params = new HashMap<>();
                params.put("date", marcacaoExame.getDate() + "");
                params.put("id_medico", marcacaoExame.getId_medico() + "");
                params.put("id_especialidade", marcacaoExame.getId_especialidade() + "");

                return params;
            }
        };
        volleyQueue.add(req);
    }

    public void setMarcacaoExamesListener(MarcacaoExamesListener marcacaoExamesListener){

        this.marcacaoExamesListener = marcacaoExamesListener;
    }

    // <----------------------------------- MEDICOS ----------------------------------->

    // Vai buscar as consultas todas à API
    public void getAllMKedicosAPI(final Context context, boolean isConnected){

        //Toast.makeText(context, "ISCONNECTED: " + isConnected, Toast.LENGTH_SHORT).show();
        if(!isConnected){
            Toast.makeText(context, "Dispositivo Offline", Toast.LENGTH_SHORT).show();
            medicos = clinicDBHelper.getAllMedicosBD();

            if(medicosListener != null){
                medicosListener.onRefreshListaMedico(medicos);
            }
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPIMEDICOS + "/" + idUtente + "/medicos", null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    consultas = ConsultaJsonParser.parserJsonConsultas(response, context);
                    //System.out.println("--> RESPOSTA: " + response);
                    //adicionarConsultasBD(consultas);

                    if(consultasListener != null){
                        consultasListener.onRefreshListaConsulta(consultas);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("--> ERRO: getAllConsultasAPI: " + error.getMessage());
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

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
            volleyQueue.add(req);
        }

    }

    // <----------------------------------- UTENTES  ----------------------------------->

    public JSONObject getAllUtentesAPI(final Context context, boolean isConnected) {
        JSONObject save = new JSONObject();
        //Toast.makeText(context, "Is Connected", Toast.LENGTH_SHORT).show();
        if(!isConnected){
            Toast.makeText(context, "Offline", Toast.LENGTH_SHORT).show();
            utentes = clinicDBHelper.getAllUtentesBD();

            if(utentesListener != null){
                utentesListener.onRefreshListaUtente(utentes);
            }
        }else {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, mUrlAPIUTENTE,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //System.out.println("-->" + response);
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response);
                                save.put("nome",jsonObject.getString("nome"));
                                save.put("nif",jsonObject.getString("nif"));
                                save.put("sexo",jsonObject.getString("sexo"));
                                save.put("telemovel",jsonObject.getString("telemovel"));
                                save.put("morada",jsonObject.getString("morada"));
                                save.put("email",jsonObject.getString("email"));
                                save.put("num_sns",jsonObject.getString("num_sns"));
                                //System.out.println("--> save " + save);
                                //idUtente = jsonObject.getString("id");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            //System.out.println("--> id: " + jsonObject);

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
        return save;
    }

    // <----------------------------------- RECEITAS ----------------------------------->

    // Vai buscar as consultas todas à API
    public void getAllReceitasAPI(final Context context, boolean isConnected){

        //Toast.makeText(context, "ISCONNECTED: " + isConnected, Toast.LENGTH_SHORT).show();
        if(!isConnected){
            Toast.makeText(context, "Dispositivo Offline", Toast.LENGTH_SHORT).show();
            receitaMedicas = clinicDBHelper.getAllReceitaMedicaBD();

            if(receitasMedicasListener != null){
                receitasMedicasListener.onRefreshListaReceitaMedica(receitaMedicas);
            }
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPIRECEITA, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    receitaMedicas = ReceitaMedicaJsonParser.parserJsonResulta(response, context);
                    //System.out.println("--> RESPOSTA: " + response);
                    //adicionarConsultasBD(consultas);

                    if(receitasMedicasListener != null){
                        receitasMedicasListener.onRefreshListaReceitaMedica(receitaMedicas);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("--> ERRO: getAllConsultasAPI: " + error.getMessage());
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

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
            volleyQueue.add(req);
        }

    }

    public void adicionarReceitasAPI(final ReceitaMedica receitaMedica, final Context context, final String username, final String password){

        StringRequest req = new StringRequest(Request.Method.POST, mUrlAPIRECEITA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println("--> RESPOSTA ADD POST: " + response);

                if(receitasMedicasListener != null){
                    receitasMedicasListener.onUpdateListaReceitaMedicaBD(ReceitaMedicaJsonParser.parserJsonResulta(response, context), 1);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("--> ERRO: adicionarReceitaMedicaAPI: " + error.getMessage());
            }
        }){
            protected Map<String, String> getParams(){

                Map<String, String> params = new HashMap<>();
                params.put("date", receitaMedica.getDate() + "");
                params.put("id_medico", receitaMedica.getId_medico() + "");
                params.put("id_utente", receitaMedica.getId_utente() + "");
                params.put("id_medicamentos", receitaMedica.getId_medicamento() + "");

                return params;
            }
        };
        volleyQueue.add(req);
    }

    // Atualiza a Marcacao na API
    public void editarReceitaAPI(final ReceitaMedica receitaMedica, final Context context, final String username, final String password){

        StringRequest req = new StringRequest(Request.Method.PUT, mUrlAPIRECEITA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println("--> editarReceitaAPI: " + response);

                if(receitasMedicasListener != null){
                    receitasMedicasListener.onUpdateListaReceitaMedicaBD(ReceitaMedicaJsonParser.parserJsonResulta(response, context), 2);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println("--> ERRO: editarMarcacaoAPI: " + error.getMessage());
            }
        }){
            protected Map<String, String> getParams(){

                Map<String, String> params = new HashMap<>();
                params.put("date", receitaMedica.getDate() + "");
                params.put("id_medico", receitaMedica.getId_medico() + "");
                params.put("id_utente", receitaMedica.getId_utente() + "");
                params.put("id_medicamentos", receitaMedica.getId_medicamento() + "");

                return params;
            }
        };
        volleyQueue.add(req);
    }


    public void setReceitasListener(ReceitasMedicasListener receitasMedicasListener){

        this.receitasMedicasListener = receitasMedicasListener;
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
