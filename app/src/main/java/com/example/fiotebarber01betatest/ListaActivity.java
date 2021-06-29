package com.example.fiotebarber01betatest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class ListaActivity extends Activity{

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        List<AgendamentosModelo> listaAgendamentos;
        listaAgendamentos = getALLAG();

        ArrayAdapter arrayAdapter = new ListAdapter(this, listaAgendamentos);

        listView = (ListView) findViewById(R.id.Lista);
        listView.setAdapter(arrayAdapter);
    }

    public List<AgendamentosModelo> getALLAG() {
        MYSQLClass MYSQLClass = new MYSQLClass();
        List<AgendamentosModelo> list = new LinkedList<AgendamentosModelo>();
        String sqlCommand = "SELECT * FROM AGENDA ORDER BY DIA, HORA;";
        Connection MYSQLConnect = MYSQLClass.MYSQLConnection();
        try{
            Statement statement = MYSQLConnect.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlCommand);

            while(resultSet.next()){
                AgendamentosModelo item = new AgendamentosModelo();

                item.setNome(resultSet.getString("NOME").toString());
                item.setSobrenome(resultSet.getString("SOBRENOME").toString());
                item.setData(resultSet.getString("DIA").toString());
                item.setCelular(B64.fromBase64(resultSet.getString("CELULAR").toString()));
                item.setHora(resultSet.getString("HORA").toString());
                item.setLogin(B64.fromBase64(resultSet.getString("LOGIN".toString())));
                list.add(item);
            }
        } catch (Exception e) {
            return null;
        }
        return list;
    }
}