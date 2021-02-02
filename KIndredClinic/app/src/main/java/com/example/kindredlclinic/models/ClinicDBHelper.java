package com.example.kindredlclinic.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.ArrayList;

public class ClinicDBHelper extends SQLiteOpenHelper{

    private static final int DB_VERSION = 8;
    private static final String DB_NAME = "kindredclinic";
    private static final String TABLE_USER = "User";
    private static final String TABLE_UTENTE = "Utente";
    private static final String TABLE_CONSULTA = "Consulta";
    private static final String TABLE_EXAME = "Exame";
    private static final String TABLE_RECEITAMEDICA = "Receitamedica";
    private static final String TABLE_MEDICOS = "Medicos";
    private static final String TABLE_MEDICAMENTOS = "Medicamentos";
    private static final String TABLE_ESPECIALIDADE = "Especialidade";
    private static final String TABLE_MARCACAOCONSULTA = "Marcacaoconsulta";
    
    ///Campos da Tabela User
    private static final String ID = "id";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";

    ///Campos da Tabela Utente
    private static final String NOME = "nome";
    private static final String NIF = "nif";
    private static final String SEXO = "sexo";
    private static final String TELEMOVEL = "telemovel";
    private static final String MORADA = "morada";
    private static final String NUM_SNS = "num_sns";
    private static final String ID_USER = "id_user";

    ///Campos da Tabela Consulta
    private static final String ID_CONSULTA = "id";
    private static final String CONTEUDO = "conteudo";
    private static final String DATE = "date";
    private static final String ID_MARCACAO = "id_marcacao";
    private static final String ID_MEDICO = "id_medico";
    private static final String ID_UTENTE = "id_utente";

    ///Campos da Tabela Exame
    private static final String ID_EXAME = "id";
    private static final String EXAME_CONTEUDO = "conteudo";
    private static final String EXAME_DATE = "date";
    private static final String EXAME_ID_MARCACAO = "id_marcacao";
    private static final String EXAME_ID_MEDICO = "id_medico";
    private static final String EXAME_ID_UTENTE = "id_utente";

    ///Campos da Tabela Receita Medica
    private static final String ID_RECEITAMEDICA = "id";
    private static final String RECEITA_CONTEUDO = "conteudo";
    private static final String RECEITA_DATE = "date";
    private static final String RECEITA_ID_MEDICAMENTOS = "id_medicamentos";
    private static final String RECEITA_ID_MEDICO = "id_medico";
    private static final String RECEITA_ID_UTENTE = "id_utente";

    ///Campos da Tabela Medicos
    private static final String NOME_MEDICO = "nome";
    private static final String NIF_MEDICO = "nif";
    private static final String SEXO_MEDICO = "sexo";
    private static final String TELEFONE = "telefone";
    private static final String NUM_ORDEM_MEDICO = "num_ordem_medico";
    private static final String ID_ESPECIALIDADE = "id_especialidade";
    private static final String ID_USER_MEDICO = "id_user";

    ///Campos da Tabela Medicamentos
    private static final String MEDICAMENTO_ID = "id";
    private static final String NOME_MEDICAMENTO = "username";
    private static final String MILIGRAMAS = "password";
    private static final String DESCRICAO = "email";

    ///Campos da Tabela Especialidade
    private static final String ESPECIALIDADE_ID = "id";
    private static final String TIPO = "tipo";

    ///Campos da Tabela Marcacao Consulta
    private static final String ID_MARCACAO_CONSULTA = "id";
    private static final String MARCACAO_CONSULTA_DATE = "date";
    private static final String MARCACAO_CONSULTA_ID_ESPECIALIDADE = "id_marcacao";
    private static final String MARCACAO_CONSULTA_ID_MEDICO = "id_medico";
    private static final String MARCACAO_CONSULTA_ID_UTENTE = "id_utente";

    private SQLiteDatabase database;

    public ClinicDBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        this.database = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable = "CREATE TABLE " + TABLE_USER
                + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USERNAME + " TEXT NOT NULL, "
                + PASSWORD + " TEXT NOT NULL, "
                + EMAIL + " TEXT NOT NULL);";
        db.execSQL(createUserTable);

        String createUtenteTable = "CREATE TABLE " + TABLE_UTENTE
                + "("
                + NOME + " TEXT NOT NULL, "
                + NIF + " TEXT NOT NULL, "
                + SEXO + " TEXT NOT NULL, "
                + TELEMOVEL + " TEXT NOT NULL, "
                + MORADA + " TEXT NOT NULL, "
                + EMAIL + " TEXT NOT NULL, "
                + NUM_SNS + " TEXT NOT NULL, "
                + ID_USER + " TEXT NOT NULL);";
        db.execSQL(createUtenteTable);

        String createConsultaTable = "CREATE TABLE " + TABLE_CONSULTA
                + "(" + ID_CONSULTA + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CONTEUDO + " TEXT NOT NULL, "
                + DATE + " TEXT NOT NULL, "
                + ID_MEDICO + " TEXT NOT NULL, "
                + ID_UTENTE + " TEXT NOT NULL, "
                + ID_MARCACAO + " TEXT NOT NULL);";
        db.execSQL(createConsultaTable);

        String createExameTable = "CREATE TABLE " + TABLE_EXAME
                + "(" + ID_EXAME + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EXAME_CONTEUDO + " TEXT NOT NULL, "
                + EXAME_DATE + " TEXT NOT NULL, "
                + EXAME_ID_MARCACAO + " TEXT NOT NULL, "
                + EXAME_ID_UTENTE + " TEXT NOT NULL, "
                + EXAME_ID_MEDICO + " TEXT NOT NULL);";
        db.execSQL(createExameTable);

        String createReceitaMedicaTable = "CREATE TABLE " + TABLE_RECEITAMEDICA
                + "(" + ID_RECEITAMEDICA + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + RECEITA_CONTEUDO + " TEXT NOT NULL, "
                + RECEITA_DATE + " TEXT NOT NULL, "
                + RECEITA_ID_MEDICAMENTOS + " TEXT NOT NULL, "
                + RECEITA_ID_MEDICO + " TEXT NOT NULL, "
                + RECEITA_ID_UTENTE + " TEXT NOT NULL);";
        db.execSQL(createReceitaMedicaTable);

        String createMedicoTable = "CREATE TABLE " + TABLE_MEDICOS
                + "("
                + NOME_MEDICO + " TEXT NOT NULL, "
                + NIF_MEDICO + " TEXT NOT NULL, "
                + SEXO_MEDICO + " TEXT NOT NULL, "
                + TELEFONE + " TEXT NOT NULL, "
                + NUM_ORDEM_MEDICO + " TEXT NOT NULL, "
                + ID_ESPECIALIDADE + " TEXT NOT NULL, "
                + NUM_SNS + " TEXT NOT NULL, "
                + ID_USER_MEDICO + " TEXT NOT NULL);";
        db.execSQL(createMedicoTable);

        String createMedicamentosTable = "CREATE TABLE " + TABLE_MEDICAMENTOS
                + "(" + MEDICAMENTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NOME_MEDICAMENTO + " TEXT NOT NULL, "
                + MILIGRAMAS + " TEXT NOT NULL, "
                + DESCRICAO + " TEXT NOT NULL);";
        db.execSQL(createMedicamentosTable);

        String createEspecialidadeTable = "CREATE TABLE " + TABLE_ESPECIALIDADE
                + "(" + ESPECIALIDADE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TIPO + " TEXT NOT NULL);";
        db.execSQL(createEspecialidadeTable);

        String createMarcacaoConsultaTable = "CREATE TABLE " + TABLE_MARCACAOCONSULTA
                + "(" + ID_MARCACAO_CONSULTA + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MARCACAO_CONSULTA_DATE + " TEXT NOT NULL, "
                + MARCACAO_CONSULTA_ID_ESPECIALIDADE + " TEXT NOT NULL, "
                + MARCACAO_CONSULTA_ID_MEDICO + " TEXT NOT NULL, "
                + MARCACAO_CONSULTA_ID_UTENTE + " TEXT NOT NULL);";
        db.execSQL(createMarcacaoConsultaTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UTENTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONSULTA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECEITAMEDICA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICAMENTOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ESPECIALIDADE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MARCACAOCONSULTA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        this.onCreate(db);
    }

    public ArrayList<User> getAllUsersBD(){
        ArrayList<User> tempUsers = new ArrayList<>();

        Cursor cursor = this.database.query(TABLE_USER, new String[]{
                        ID,
                        USERNAME,
                        PASSWORD,
                        EMAIL},
                null,null,null, null, null);

        if(cursor.moveToNext()){
            do{
                User auxUser = new User(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
                tempUsers.add(auxUser);
            }while (cursor.moveToNext());
        }
        return tempUsers;
    }

    public void adicionarUserBD(User user){

        ContentValues values = new ContentValues();
        values.put(USERNAME, user.getUsername());
        values.put(PASSWORD, user.getPassword());
        values.put(EMAIL, user.getEmail());

        this.database.insert(TABLE_USER, null, values);
    }

    public boolean guardarUserBD(User user){
        ContentValues values = new ContentValues();
        values.put(USERNAME, user.getUsername());
        values.put(PASSWORD, user.getPassword());
        values.put(EMAIL, user.getEmail());

        return this.database.update(TABLE_USER,values,"id = ?", new String[]{"" + user.getId()}) > 0;
    }

    public boolean removerUserBD(int id){
        return (this.database.delete(TABLE_USER, "id = ?", new String[]{"" + id}) == 1);
    }

    public void removerAllUsers(){
        this.database.delete(TABLE_USER, null,null);
    }

    // <------------------- Utente ------------------->

    public ArrayList<Utente> getAllUtentesBD(){
        ArrayList<Utente> tempUtente = new ArrayList<>();

        Cursor cursor = this.database.query(TABLE_UTENTE, new String[]{
                NOME,
                NIF,
                TELEMOVEL,
                MORADA,
                NUM_SNS,
                SEXO,
                EMAIL,
                ID_USER},null,null,null,null,null);

        if(cursor.moveToNext()){
            do{
                Utente auxUtente = new Utente(cursor.getString(0),cursor.getInt(1), cursor.getInt(2),
                        cursor.getString(3),cursor.getInt(4),cursor.getString(5),
                        cursor.getString(6), cursor.getInt(7));
            }while (cursor.moveToNext());
        }
        return tempUtente;
    }

    public void adicionarUtenteBD(Utente utente){

        ContentValues values = new ContentValues();

        values.put(NOME, utente.getNome());
        values.put(NIF, utente.getNif());
        values.put(TELEMOVEL, utente.getTelemovel());
        values.put(MORADA, utente.getMorada());
        values.put(NUM_SNS, utente.getNum_sns());
        values.put(SEXO, utente.getSexo());
        values.put(EMAIL, utente.getEmail());
        values.put(ID_USER, utente.getId_user());

        this.database.insert(TABLE_UTENTE, null , values);
    }

    public boolean guardarUtenteBD(Utente utente){

        ContentValues values = new ContentValues();
        values.put(NOME, utente.getNome());
        values.put(NIF, utente.getNif());
        values.put(TELEMOVEL, utente.getTelemovel());
        values.put(MORADA, utente.getMorada());
        values.put(NUM_SNS, utente.getNum_sns());
        values.put(SEXO, utente.getSexo());
        values.put(EMAIL, utente.getEmail());
        values.put(ID_USER, utente.getId_user());

        return this.database.update(TABLE_UTENTE, values, "id = ?", new String[]{"" + utente.getId_user()}) > 0;
    }

    public boolean removerUtenteBD(int id_user){
        return  (this.database.delete(TABLE_UTENTE, "id = ?", new String[]{"" + id_user}) == 1);
    }

    public void removerALLUtentesDB(){
        this.database.delete(TABLE_UTENTE, null, null);
    }

    // <------------------- CONSULTA ------------------->

    public ArrayList<Consulta> getAllConsultasBD(){
        ArrayList<Consulta> tempConsulta = new ArrayList<>();

        Cursor cursor = this.database.query(TABLE_CONSULTA, new String[]{
                ID_CONSULTA,
                DATE,
                CONTEUDO,
                ID_MARCACAO,
                ID_MEDICO,
                ID_UTENTE},null,null,null,null,null);

        if(cursor.moveToNext()){
            do{
                Consulta auxConsulta = new Consulta(cursor.getInt(0),cursor.getString(1), cursor.getString(2),
                        cursor.getInt(3),cursor.getInt(4), cursor.getInt(5));
            }while (cursor.moveToNext());
        }
        return tempConsulta;
    }

    public void adicionarConsultaBD(Consulta consulta){

        ContentValues values = new ContentValues();
        values.put(ID_CONSULTA, consulta.getId());
        values.put(DATE, consulta.getDate());
        values.put(CONTEUDO, consulta.getConteudo());
        values.put(ID_MARCACAO, consulta.getId_marcacao());
        values.put(ID_MEDICO, consulta.getId_medico());
        values.put(ID_UTENTE, consulta.getId_utente());

        this.database.insert(TABLE_CONSULTA, null , values);
    }

    public boolean guardarConsultaBD(Consulta consulta){

        ContentValues values = new ContentValues();
        values.put(ID_CONSULTA, consulta.getId());
        values.put(DATE, consulta.getDate());
        values.put(CONTEUDO, consulta.getConteudo());
        values.put(ID_MARCACAO, consulta.getId_marcacao());
        values.put(ID_MEDICO, consulta.getId_medico());
        values.put(ID_UTENTE, consulta.getId_utente());

        return this.database.update(TABLE_CONSULTA, values, "id = ?", new String[]{"" + consulta.getId()}) > 0;
    }

    public boolean removerConsultaBD(int id_consulta){
        return  (this.database.delete(TABLE_CONSULTA, "id = ?", new String[]{"" + id_consulta}) == 1);
    }

    public void removerALLConsultasDB(){
        this.database.delete(TABLE_CONSULTA, null, null);
    }

    // <------------------- EXAME ------------------->

    public ArrayList<Exame> getAllExamesBD(){
        ArrayList<Exame> tempExame = new ArrayList<>();

        Cursor cursor = this.database.query(TABLE_EXAME, new String[]{
                ID_EXAME,
                EXAME_DATE,
                EXAME_CONTEUDO,
                EXAME_ID_MARCACAO,
                EXAME_ID_MEDICO,
                EXAME_ID_UTENTE},null,null,null,null,null);

        if(cursor.moveToNext()){
            do{
                Exame auxExame = new Exame(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getInt(3), cursor.getInt(4), cursor.getInt(5));
            }while (cursor.moveToNext());
        }
        return tempExame;
    }

    public void adicionarExameBD(Exame exame){

        ContentValues values = new ContentValues();
        values.put(ID_EXAME, exame.getId());
        values.put(EXAME_DATE, exame.getDate());
        values.put(EXAME_CONTEUDO, exame.getConteudo());
        values.put(EXAME_ID_MARCACAO, exame.getId_marcacao());
        values.put(EXAME_ID_MEDICO, exame.getId_medico());
        values.put(EXAME_ID_UTENTE, exame.getId_utente());

        this.database.insert(TABLE_EXAME, null , values);
    }

    public boolean guardarExameBD(Exame exame){

        ContentValues values = new ContentValues();
        values.put(ID_EXAME, exame.getId());
        values.put(EXAME_DATE, exame.getDate());
        values.put(EXAME_CONTEUDO, exame.getConteudo());
        values.put(EXAME_ID_MARCACAO, exame.getId_marcacao());
        values.put(EXAME_ID_MEDICO, exame.getId_medico());
        values.put(EXAME_ID_UTENTE, exame.getId_utente());

        return this.database.update(TABLE_EXAME, values, "id = ?", new String[]{"" + exame.getId()}) > 0;
    }

    public boolean removerExameBD(int id_exame){
        return  (this.database.delete(TABLE_EXAME, "id = ?", new String[]{"" + id_exame}) == 1);
    }

    public void removerALLExamesDB(){
        this.database.delete(TABLE_EXAME, null, null);
    }


    // <------------------- RECEITA MEDICA ------------------->

    public ArrayList<ReceitaMedica> getAllReceitaMedicaBD(){
        ArrayList<ReceitaMedica> tempReceitaMedica = new ArrayList<>();

        Cursor cursor = this.database.query(TABLE_EXAME, new String[]{
                ID_RECEITAMEDICA,
                RECEITA_DATE,
                RECEITA_CONTEUDO,
                RECEITA_ID_MEDICAMENTOS,
                RECEITA_ID_MEDICO,
                RECEITA_ID_UTENTE},null,null,null,null,null);

        if(cursor.moveToNext()){
            do{
                ReceitaMedica auxReceitaMedica = new ReceitaMedica(cursor.getInt(0),cursor.getString(1), cursor.getString(2),
                        cursor.getInt(3),cursor.getInt(4), cursor.getInt(5));
            }while (cursor.moveToNext());
        }
        return tempReceitaMedica;
    }

    public void adicionarReceitaMedicaBD(ReceitaMedica receitaMedica){

        ContentValues values = new ContentValues();
        values.put(ID_RECEITAMEDICA, receitaMedica.getId());
        values.put(RECEITA_DATE, receitaMedica.getDate());
        values.put(RECEITA_CONTEUDO, receitaMedica.getConteudo());
        values.put(RECEITA_ID_MEDICAMENTOS, receitaMedica.getId_medicamento());
        values.put(RECEITA_ID_MEDICO, receitaMedica.getId_medico());
        values.put(RECEITA_ID_UTENTE, receitaMedica.getId_utente());

        this.database.insert(TABLE_RECEITAMEDICA, null , values);
    }

    public boolean guardarReceitaMedicaBD(ReceitaMedica receitaMedica){

        ContentValues values = new ContentValues();
        values.put(ID_RECEITAMEDICA, receitaMedica.getId());
        values.put(RECEITA_DATE, receitaMedica.getDate());
        values.put(RECEITA_CONTEUDO, receitaMedica.getConteudo());
        values.put(RECEITA_ID_MEDICAMENTOS, receitaMedica.getId_medicamento());
        values.put(RECEITA_ID_MEDICO, receitaMedica.getId_medico());
        values.put(RECEITA_ID_UTENTE, receitaMedica.getId_utente());

        return this.database.update(TABLE_RECEITAMEDICA, values, "id = ?", new String[]{"" + receitaMedica.getId()}) > 0;
    }

    public boolean removerReceitaMedicaBD(int id_receitaMedica){
        return  (this.database.delete(TABLE_RECEITAMEDICA, "id = ?", new String[]{"" + id_receitaMedica}) == 1);
    }

    public void removerALLReceitasMedicasDB(){
        this.database.delete(TABLE_RECEITAMEDICA, null, null);
    }


    // <------------------- MEDICOS ------------------->

    public ArrayList<Medico> getAllMedicosBD(){
        ArrayList<Medico> tempMedico = new ArrayList<>();

        Cursor cursor = this.database.query(TABLE_MEDICOS, new String[]{
                NOME_MEDICO,
                NIF_MEDICO,
                TELEFONE,
                NUM_ORDEM_MEDICO,
                SEXO_MEDICO,
                ID_ESPECIALIDADE,
                ID_USER_MEDICO},null,null,null,null,null);

        if(cursor.moveToNext()){
            do{
                Medico auxMedico = new Medico(cursor.getString(0),cursor.getInt(1), cursor.getInt(2),
                        cursor.getInt(3),cursor.getString(4),cursor.getInt(5),
                        cursor.getInt(6));
            }while (cursor.moveToNext());
        }
        return tempMedico;
    }

    public void adicionarMedicoBD(Medico medico){

        ContentValues values = new ContentValues();

        values.put(NOME_MEDICO, medico.getNome());
        values.put(NIF_MEDICO, medico.getNif());
        values.put(TELEFONE, medico.getTelefone());
        values.put(NUM_ORDEM_MEDICO, medico.getNum_ordem_medico());
        values.put(SEXO_MEDICO, medico.getSexo());
        values.put(ID_ESPECIALIDADE, medico.getId_especialidade());
        values.put(ID_USER_MEDICO, medico.getId_user());

        this.database.insert(TABLE_MEDICOS, null , values);
    }

    public boolean guardarMedicoBD(Medico medico){

        ContentValues values = new ContentValues();
        values.put(NOME_MEDICO, medico.getNome());
        values.put(NIF_MEDICO, medico.getNif());
        values.put(TELEFONE, medico.getTelefone());
        values.put(NUM_ORDEM_MEDICO, medico.getNum_ordem_medico());
        values.put(SEXO_MEDICO, medico.getSexo());
        values.put(ID_ESPECIALIDADE, medico.getId_especialidade());
        values.put(ID_USER_MEDICO, medico.getId_user());

        return this.database.update(TABLE_MEDICOS, values, "id = ?", new String[]{"" + medico.getId_user()}) > 0;
    }

    public boolean removerMedicoBD(int id_user){
        return  (this.database.delete(TABLE_MEDICOS, "id = ?", new String[]{"" + id_user}) == 1);
    }


    // <------------------- MEDICAMENTOS ------------------->

    public ArrayList<Medicamentos> getAllMedicamentosBD(){
        ArrayList<Medicamentos> tempMedicamentos = new ArrayList<>();

        Cursor cursor = this.database.query(TABLE_MEDICAMENTOS, new String[]{
                MEDICAMENTO_ID,
                NOME_MEDICAMENTO,
                MILIGRAMAS,
                DESCRICAO},null,null,null,null,null);

        if(cursor.moveToNext()){
            do{
                Medicamentos auxMedico = new Medicamentos(cursor.getInt(0),cursor.getString(1), cursor.getInt(2),
                        cursor.getString(3));
            }while (cursor.moveToNext());
        }
        return tempMedicamentos;
    }

    public void adicionarMedicamentoBD(Medicamentos medicamentos){

        ContentValues values = new ContentValues();

        values.put(MEDICAMENTO_ID, medicamentos.getId());
        values.put(NOME_MEDICAMENTO, medicamentos.getNome());
        values.put(MILIGRAMAS, medicamentos.getMiligramas());
        values.put(DESCRICAO, medicamentos.getDescricao());


        this.database.insert(TABLE_MEDICAMENTOS, null , values);
    }

    public boolean guardarMedicamentoBD(Medicamentos medicamentos){

        ContentValues values = new ContentValues();
        values.put(MEDICAMENTO_ID, medicamentos.getId());
        values.put(NOME_MEDICAMENTO, medicamentos.getNome());
        values.put(MILIGRAMAS, medicamentos.getMiligramas());
        values.put(DESCRICAO, medicamentos.getDescricao());

        return this.database.update(TABLE_MEDICAMENTOS, values, "id = ?", new String[]{"" + medicamentos.getId()}) > 0;
    }

    public boolean removerMedicamentoBD(int id_medicamento){
        return  (this.database.delete(TABLE_MEDICAMENTOS, "id = ?", new String[]{"" + id_medicamento}) == 1);
    }


    // <------------------- ESPECIALIDADE ------------------->

    public ArrayList<Especialidade> getAllEspecialidadesBD(){
        ArrayList<Especialidade> tempEspecialidades = new ArrayList<>();

        Cursor cursor = this.database.query(TABLE_ESPECIALIDADE, new String[]{
                ESPECIALIDADE_ID,
                TIPO},null,null,null,null,null);

        if(cursor.moveToNext()){
            do{
                Especialidade auxMedico = new Especialidade(cursor.getInt(0),cursor.getString(1));
            }while (cursor.moveToNext());
        }
        return tempEspecialidades;
    }

    public void adicionarEspecialidadeBD(Especialidade especialidade){

        ContentValues values = new ContentValues();

        values.put(ESPECIALIDADE_ID, especialidade.getId());
        values.put(TIPO, especialidade.getTipo());


        this.database.insert(TABLE_ESPECIALIDADE, null , values);
    }

    public boolean guardarEspecialidaeBD(Especialidade especialidade){

        ContentValues values = new ContentValues();
        values.put(ESPECIALIDADE_ID, especialidade.getId());
        values.put(TIPO, especialidade.getTipo());

        return this.database.update(TABLE_ESPECIALIDADE, values, "id = ?", new String[]{"" + especialidade.getId()}) > 0;
    }

    public boolean removerEspecialidadeBD(int id_especialidade){
        return  (this.database.delete(TABLE_ESPECIALIDADE, "id = ?", new String[]{"" + id_especialidade}) == 1);
    }


    // <------------------- MARCACAO CONSULTA ------------------->

    public ArrayList<MarcacaoConsulta> getAllMarcacoesConsultaBD(){
        ArrayList<MarcacaoConsulta> tempMarcacaoConsulta = new ArrayList<>();

        Cursor cursor = this.database.query(TABLE_MARCACAOCONSULTA, new String[]{
                ID_MARCACAO_CONSULTA,
                MARCACAO_CONSULTA_DATE,
                MARCACAO_CONSULTA_ID_MEDICO,
                MARCACAO_CONSULTA_ID_ESPECIALIDADE,
                MARCACAO_CONSULTA_ID_UTENTE},null,null,null,null,null);

        if(cursor.moveToNext()){
            do{
                MarcacaoConsulta auxMarcacaoConsulta = new MarcacaoConsulta(cursor.getInt(0), cursor.getString(1), cursor.getInt(2),
                        cursor.getInt(3), cursor.getInt(4));
            }while (cursor.moveToNext());
        }
        return tempMarcacaoConsulta;
    }

    public void adicionarMarcacaoConsultaBD(MarcacaoConsulta marcacaoConsulta){

        ContentValues values = new ContentValues();
        values.put(ID_MARCACAO_CONSULTA, marcacaoConsulta.getId());
        values.put(MARCACAO_CONSULTA_DATE, marcacaoConsulta.getDate());
        values.put(MARCACAO_CONSULTA_ID_MEDICO, marcacaoConsulta.getId_medico());
        values.put(MARCACAO_CONSULTA_ID_ESPECIALIDADE, marcacaoConsulta.getId_especialidade());
        values.put(MARCACAO_CONSULTA_ID_UTENTE, marcacaoConsulta.getId_utente());

        this.database.insert(TABLE_MARCACAOCONSULTA, null , values);
    }

    public boolean guardarMarcacaoConsultaBD(MarcacaoConsulta marcacaoConsulta){

        ContentValues values = new ContentValues();
        values.put(ID_MARCACAO_CONSULTA, marcacaoConsulta.getId());
        values.put(MARCACAO_CONSULTA_DATE, marcacaoConsulta.getDate());
        values.put(MARCACAO_CONSULTA_ID_MEDICO, marcacaoConsulta.getId_medico());
        values.put(MARCACAO_CONSULTA_ID_ESPECIALIDADE, marcacaoConsulta.getId_especialidade());
        values.put(MARCACAO_CONSULTA_ID_UTENTE, marcacaoConsulta.getId_utente());

        return this.database.update(TABLE_MARCACAOCONSULTA, values, "id = ?", new String[]{"" + marcacaoConsulta.getId()}) > 0;
    }

    public boolean removerMarcacaoConsultaBD(int id_marcacaoConsulta){
        return  (this.database.delete(TABLE_MARCACAOCONSULTA, "id = ?", new String[]{"" + id_marcacaoConsulta}) == 1);
    }

    public void removerALLMarcacoesConsultaDB(){
        this.database.delete(TABLE_MARCACAOCONSULTA, null, null);
    }
}
