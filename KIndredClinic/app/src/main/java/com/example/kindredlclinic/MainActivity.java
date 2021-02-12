package com.example.kindredlclinic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kindredlclinic.models.SingletonKindredClinic;
import com.example.kindredlclinic.vistas.DetalhesConsultaActivity;
import com.example.kindredlclinic.vistas.InfoFragment;
import com.example.kindredlclinic.vistas.ListaFragment;
import com.example.kindredlclinic.vistas.ReceitaFragment;
import com.example.kindredlclinic.vistas.UtenteActivity;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static final String CHAVE_EMAIL = "EMAIL";
    public static final String CHAVE_ID = "IDCLIENTE";
    private static final String SECCAO_INFO_USER = "SECCAO_INFO_USER";
    private String email;
    private String idCliente;
    private NavigationView navigationView;
    private DrawerLayout drawer;

    private FragmentManager fragmentManager;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.nav_view);
        drawer = findViewById(R.id.drawer_layout);

        /*ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.str_navigation_drawer_open,    R.string.str_navigation_drawer_close);
        toggle.syncState();
        drawer.addDrawerListener(toggle);*/

        carregarCabecalho();

        // Vai busacar os detalhes do ultimo utilizador autenticado
        sharedPreferences = getSharedPreferences("old_user",MODE_PRIVATE);

        // Carrega o Fragmento
        fragmentManager = getSupportFragmentManager();

        // A activity escuta este Listener
        navigationView.setNavigationItemSelectedListener(this);

        /*carregamentoFragmentoInicial();*/

    }

    private void carregarCabecalho() {

        // Usando sharedPreferences
        sharedPreferences = getSharedPreferences(SECCAO_INFO_USER, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Obtem o email por parametro
        email = getIntent().getStringExtra(CHAVE_EMAIL);
//email = getIntent().getStringExtra(CHAVE_EMAIL).toString();
        // Obtem o email por parametro
        idCliente = getIntent().getStringExtra(CHAVE_ID);

        //System.out.println("--> Main ID: " + idCliente);

        if(email == null){
            email = sharedPreferences.getString(SECCAO_INFO_USER, "Não Existe");
        } else {
            // Coloca o valor, neste caso o email, na sharedPreferences
            editor.putString(SECCAO_INFO_USER, email);
            editor.apply();
        }

        // Atraves do navigation view vai buscar a posição no header
        View view = navigationView.getHeaderView(0);
        // Procura pelo ID do email
        TextView textViewUser = view.findViewById(R.id.tvEmail);
        // Escreve o email na TextView
        textViewUser.setText(email);
    }


    /*private void carregamentoFragmentoInicial(){
        navigationView.setCheckedItem(R.id.nav_estadoReservas);
        Fragment fragment = new InfoFragment();
        fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();
        setTitle("Quem Somos?");
    }*/

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment fragment = null;

        switch (menuItem.getItemId()) {

            case R.id.nav_perfil:
                Intent utente = new Intent(context, UtenteActivity.class);
                context.startActivity(utente);
               // setTitle(menuItem.getTitle());
                break;

            case R.id.nav_consultas:
                fragment = new ListaFragment();
                setTitle(menuItem.getTitle());
                break;
            /*case R.id.nav_servicoQuartos:
                fragment = new ServicoQuartosFragment();
                setTitle(menuItem.getTitle());
                break;
            case R.id.nav_classificacao:
                fragment = new ClassificacaoFragment();
                setTitle(menuItem.getTitle());
                break;*/

            case R.id.nav_receitas:
                fragment = new ReceitaFragment();
                setTitle(menuItem.getTitle());
                break;

            case R.id.nav_quemSomos:
                fragment = new InfoFragment();
                setTitle(menuItem.getTitle());
                break;

            case R.id.nav_ondeEstamos:
                String uri = "http://maps.google.com/maps?q=loc:" + 39.734823  + "," + -8.821746;
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
                break;

            case R.id.nav_email:
                //Melhorar mais tarde
                Intent intent = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
                //Intent intent = new Intent(Intent.ACTION_SENDTO); force open outlook predefinido
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"adatel.19.20@gmail.com"});
                startActivity(Intent.createChooser(intent,"Selecione a Aplicação de Email"));
                break;

            case R.id.nav_telemovel:
                if(isPermissionGranted()){
                    call_action();
                }
                break;
            case R.id.nav_terminarSessao:
                SingletonKindredClinic.getInstance(context).idUtenteNull();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                finish();
            default:
                System.out.println("-->Nav Default");
        }
        // Se o fragmento já tiver algo
        if (fragment != null) {
            // Então substitui o que lá está
            fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    public void call_action(){
        try{
            String telefone = "913724082";
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + telefone));
            startActivity(callIntent);
        }catch(Exception  e){
            Toast.makeText(this, "Erro de chamada", Toast.LENGTH_SHORT).show();
            System.out.println("--> Erro Call: " + e.toString());
        }
    }


    public boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    call_action();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

}
