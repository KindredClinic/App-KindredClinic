package com.example.kindredlclinic.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kindredlclinic.R;
import com.example.kindredlclinic.models.ReceitaMedica;

import java.util.ArrayList;

public class ListaReceitaAdaptador extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<ReceitaMedica> receitaMedicas;

    public ListaReceitaAdaptador(Context context, ArrayList<ReceitaMedica> receitaMedicas){
        this.context = context;
        this.receitaMedicas = receitaMedicas;
    }

    @Override
    public int getCount() {
        return receitaMedicas.size();
    }

    @Override
    public Object getItem(int position) {
        return receitaMedicas.get(position);
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
            convertView = inflater.inflate(R.layout.item_receita, null);
        }

        ViewHolderLista viewHolder = (ViewHolderLista) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new ViewHolderLista(convertView);
            convertView.setTag(viewHolder);
        }

        viewHolder.update(receitaMedicas.get(position));
        return convertView;
    }

    private class ViewHolderLista{

        private TextView idReceita;
        private TextView data;
        private TextView medico;

        // ID's do layout item_reserva
        public ViewHolderLista(View convertView){
            idReceita = convertView.findViewById(R.id.tvIdReceita);
            data = convertView.findViewById(R.id.tvDataReceita_Receita);
            medico = convertView.findViewById(R.id.tvMedicoReceita_Receita);
        }

        public void update(ReceitaMedica receita){
            idReceita.setText(String.valueOf(receita.getId()));
            medico.setText(String.valueOf(receita.getId_medico()));
            data.setText(String.valueOf(receita.getDate()));
        }

    }
}
