 package com.example.fiotebarber01betatest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends ArrayAdapter<AgendamentosModelo>{

    private Context context;
    private List<AgendamentosModelo> listaAgendamentos = null;

    public ListAdapter(Context context, List<AgendamentosModelo> listaAgendamentos){
        super(context, 0, listaAgendamentos);
        this.listaAgendamentos = listaAgendamentos;
        this.context = context;
    }

    @Override
    public View getView(int pos, View view, ViewGroup viewGroup){
        AgendamentosModelo agendamentosModelo = listaAgendamentos.get(pos);

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.activity_lista, viewGroup, false);
        }

        TextView textViewNome = (TextView)view.findViewById(R.id.Nome);
        textViewNome.setText(String.valueOf(agendamentosModelo.getNome(null)+" "+agendamentosModelo.getSobrenome()));
        TextView textViewData = (TextView)view.findViewById(R.id.Data);
        textViewData.setText(String.valueOf("Data do Agendamento: " +agendamentosModelo.getData()));
        TextView textViewHora = (TextView)view.findViewById(R.id.Hora);
        textViewHora.setText(String.valueOf("Hora do Agendamento: " +agendamentosModelo.getHora()));
        TextView textViewCelular = (TextView)view.findViewById(R.id.Celular);
        textViewCelular.setText(String.valueOf("E-mail para Contato: " +agendamentosModelo.getLogin()));
        TextView textViewLogin = (TextView)view.findViewById(R.id.Login);
        textViewLogin.setText(String.valueOf("Telefone para Contato: " +agendamentosModelo.getCelular()));
        return view;
    }
}
