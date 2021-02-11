package com.example.kindredlclinic.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kindredlclinic.R;
import com.example.kindredlclinic.models.MarcacaoConsulta;

import java.util.ArrayList;


public class ListaAdaptador extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<MarcacaoConsulta> marcacaoConsulta;

    public ListaAdaptador(Context context, ArrayList<MarcacaoConsulta> marcacaoConsultas){
        this.context = context;
        this.marcacaoConsulta = marcacaoConsultas;
    }

    @Override
    public int getCount() {
        return marcacaoConsulta.size();
    }

    @Override
    public Object getItem(int position) {
        return marcacaoConsulta.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater == null){
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_consulta, null);
        }

        ViewHolderLista viewHolder = (ViewHolderLista) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new ViewHolderLista(convertView);
            convertView.setTag(viewHolder);
        }

        viewHolder.update(marcacaoConsulta.get(position));
        return convertView;
    }

    private class ViewHolderLista{

        private TextView idConsulta;
        private TextView medico;
        private TextView date;

        // ID's do layout item_reserva
        public ViewHolderLista(View convertView){

            idConsulta = convertView.findViewById(R.id.tvIdReceita);
            date = convertView.findViewById(R.id.tvNumQuartos);
            medico = convertView.findViewById(R.id.tvDataReceitas);

        }

        public void update(MarcacaoConsulta consulta){
            idConsulta.setText(String.valueOf(consulta.getId()));
            medico.setText(String.valueOf(consulta.getId_medico()));
            date.setText(String.valueOf(consulta.getDate()));
        }
    }
}
