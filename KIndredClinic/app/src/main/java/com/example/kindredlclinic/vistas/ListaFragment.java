package com.example.kindredlclinic.vistas;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kindredlclinic.adaptadores.ListaAdaptador;
import com.example.kindredlclinic.listeners.ConsultasListener;
import com.example.kindredlclinic.listeners.MarcacaoConsultasListener;
import com.example.kindredlclinic.models.Consulta;
import com.example.kindredlclinic.R;
import com.example.kindredlclinic.models.MarcacaoConsulta;
import com.example.kindredlclinic.models.SingletonKindredClinic;
import com.example.kindredlclinic.utils.ConsultaJsonParser;
import com.example.kindredlclinic.utils.MarcacaoConsultaJsonParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListaFragment extends Fragment implements MarcacaoConsultasListener {

    private ArrayList<MarcacaoConsulta> listaMarcacaoConsulta;
    private ListView lvlistaConsultas;
    private SearchView searchView;
    private MarcacaoConsulta idMarcacaoConsulta;
    private ListaAdaptador listaAdaptador;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_lista, container, false);

        lvlistaConsultas = rootView.findViewById(R.id.lvLista);
        //listaReservas = SingletonGestaoHotel.getInstance(getContext()).getReservasBD();
        //lvlistaReservas.setAdapter(new ListaReservaAdaptador(getContext(), listaReservas));

        //  <----------- Floating Button ----------->

       // FloatingActionButton fab = rootView.findViewById(R.id.fab);
/*
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), DetalhesConsultaActivity.class);
                startActivity(intent);
            }
        });

*/
        // <--------------------- Ao selecionar um item da List View --------------------->

        lvlistaConsultas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                MarcacaoConsulta tempConsulta = (MarcacaoConsulta) parent.getItemAtPosition(position);
                //Toast.makeText(getContext(), "AQUI: " + tempReserva.getNumQuartos(), Toast.LENGTH_SHORT).show();
                //idReserva = SingletonGestaoHotel.getInstance(getContext()).getReservaBD(tempReserva.getId());

                Intent intent = new Intent(getContext(), DetalhesConsultaActivity.class);
                intent.putExtra(DetalhesConsultaActivity.CHAVE_ID, tempConsulta.getId());
                startActivity(intent);
            }
        });


        // Fragment à escuta do Listener
        SingletonKindredClinic.getInstance(getContext()).setMarcacaoConsultasListener(this);
        SingletonKindredClinic.getInstance(getContext()).getAllMarcacaoConsultasAPI(getContext(), MarcacaoConsultaJsonParser.isConnectionInternet(getContext()));

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        // Para carregar o menu usa-se o Inflater
        inflater.inflate(R.menu.menu_pesquisa, menu);
        // Vai buscar aquele item
        MenuItem itemPesquisa = menu.findItem(R.id.itemPesquisa);
        searchView = (SearchView)itemPesquisa.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            // Método chamado a cada letra que se insere
            @Override
            public boolean onQueryTextChange(String newText) {

                ArrayList<MarcacaoConsulta> tempListaLivros = new ArrayList<>();

                for (MarcacaoConsulta tempMarcacao : SingletonKindredClinic.getInstance(getContext()).getMarcacaoConsultasBD()) {
                    if(tempMarcacao.getDate().toLowerCase().contains(newText.toLowerCase())){
                        tempListaLivros.add(tempMarcacao);
                    }
                }
                lvlistaConsultas.setAdapter(new ListaAdaptador(getContext(), tempListaLivros));
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.itemPesquisa){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {

        if(searchView != null){
            searchView.onActionViewCollapsed();
        }
        super.onResume();
        SingletonKindredClinic.getInstance(getContext()).getMarcacaoConsultasBD();
    }

    @Override
    public void onRefreshListaMarcacaoConsultas(ArrayList<MarcacaoConsulta> listaMarcacaoConsulta) {

        //System.out.println("--> onRefreshListaReservas: " + listaReservas);

        listaAdaptador = new ListaAdaptador(getContext(), listaMarcacaoConsulta);
        lvlistaConsultas.setAdapter(listaAdaptador);
        //listaReservaAdaptador.refresh(listaReservas);
    }

    @Override
    public void onUpdateListaMarcacaoConsultasBD(MarcacaoConsulta marcacaoConsulta, int operacao) {

    }

}
