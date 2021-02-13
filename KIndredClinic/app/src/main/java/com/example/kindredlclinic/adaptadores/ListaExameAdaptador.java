package com.example.kindredlclinic.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kindredlclinic.R;
import com.example.kindredlclinic.models.MarcacaoExame;

import java.util.ArrayList;

public class ListaExameAdaptador extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<MarcacaoExame> marcacaoExames;

    public ListaExameAdaptador(Context context, ArrayList<MarcacaoExame> marcacaoExames){
        this.context = context;
        this.marcacaoExames = marcacaoExames;
    }

    @Override
    public int getCount() {
        return marcacaoExames.size();
    }

    @Override
    public Object getItem(int position) {
        return marcacaoExames.get(position);
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

        viewHolder.update(marcacaoExames.get(position));

        return convertView;
    }

    private class ViewHolderLista{

        private TextView idExame;
        private TextView medico;
        private TextView date;
        private TextView status;

        // ID's do layout item_reserva
        public ViewHolderLista(View convertView){

            idExame = convertView.findViewById(R.id.tvIdMarcacao);
            date = convertView.findViewById(R.id.tvDataMarcacao);
            medico = convertView.findViewById(R.id.tvMedicoMarcacao);
            status = convertView.findViewById(R.id.tvStatusMarcacao);
        }

        public void update(MarcacaoExame exame){
            idExame.setText(String.valueOf(exame.getId()));
            medico.setText(String.valueOf(exame.getId_medico()));
            date.setText(String.valueOf(exame.getDate()));
            status.setText(exame.getStatus());
        }
    }
}
