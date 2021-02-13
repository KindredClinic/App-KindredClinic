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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kindredlclinic.R;
import com.example.kindredlclinic.adaptadores.ListaAdaptador;
import com.example.kindredlclinic.adaptadores.ListaExameAdaptador;
import com.example.kindredlclinic.listeners.MarcacaoExamesListener;
import com.example.kindredlclinic.models.MarcacaoConsulta;
import com.example.kindredlclinic.models.MarcacaoExame;
import com.example.kindredlclinic.models.SingletonKindredClinic;
import com.example.kindredlclinic.utils.MarcacaoConsultaJsonParser;
import com.example.kindredlclinic.utils.MarcacaoExameJsonParser;

import java.util.ArrayList;

public class ExameFragment extends Fragment implements MarcacaoExamesListener {

    private ArrayList<MarcacaoExame> listaMarcacaoExame;
    private ListView lvlistaExame;
    private SearchView searchView;
    private MarcacaoExame idMarcacaoExame;
    private ListaExameAdaptador listaAdaptador;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_lista, container, false);

        lvlistaExame = rootView.findViewById(R.id.lvLista);

        // <--------------------- Ao selecionar um item da List View --------------------->

        lvlistaExame.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                MarcacaoExame tempExame = (MarcacaoExame) parent.getItemAtPosition(position);
                //Toast.makeText(getContext(), "AQUI: " + tempReserva.getNumQuartos(), Toast.LENGTH_SHORT).show();
                //idReserva = SingletonGestaoHotel.getInstance(getContext()).getReservaBD(tempReserva.getId());

                System.out.println("--> idExame: " + tempExame.getId());

                Intent intent = new Intent(getContext(), DetalhesExameActivity.class);
                intent.putExtra(DetalhesExameActivity.CHAVE_ID, tempExame.getId());
                startActivity(intent);
            }
        });

        // Fragment à escuta do Listener
        SingletonKindredClinic.getInstance(getContext()).setMarcacaoExamesListener(this);
        SingletonKindredClinic.getInstance(getContext()).getAllMarcacaoExamesAPI(getContext(), MarcacaoExameJsonParser.isConnectionInternet(getContext()));

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

                ArrayList<MarcacaoExame> tempListaLivros = new ArrayList<>();

                for (MarcacaoExame tempMarcacao : SingletonKindredClinic.getInstance(getContext()).getMarcacaoExamesBD()) {
                    if(tempMarcacao.getDate().toLowerCase().contains(newText.toLowerCase())){
                        tempListaLivros.add(tempMarcacao);
                    }
                }
                lvlistaExame.setAdapter(new ListaExameAdaptador(getContext(), tempListaLivros));
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
        SingletonKindredClinic.getInstance(getContext()).getMarcacaoExamesBD();
    }


    @Override
    public void onRefreshListaMarcacaoExames(ArrayList<MarcacaoExame> listaMarcacaoExames) {
        //System.out.println("--> onRefreshListaReservas: " + listaReservas);

        listaAdaptador = new ListaExameAdaptador(getContext(), listaMarcacaoExames);
        lvlistaExame.setAdapter(listaAdaptador);
        //listaReservaAdaptador.refresh(listaReservas);
    }

    @Override
    public void onUpdateListaMarcacaoExamesBD(MarcacaoExame marcacaoExame, int operacao) {

    }
}
