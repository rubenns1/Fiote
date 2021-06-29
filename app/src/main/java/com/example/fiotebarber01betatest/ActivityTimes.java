package com.example.fiotebarber01betatest;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ActivityTimes extends AppCompatActivity{
    Spinner spinner; Button btnFinish, btnClose, btnVoltar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_times);

        MYSQLClass MYSQLClass = new MYSQLClass();
        Intent intent = getIntent();
        String getMail = intent.getStringExtra("sendMAIL");
        int getDAY = Integer.parseInt(getIntent().getExtras().get("sendDAY").toString());

        spinner = (Spinner)findViewById(R.id.listTimes);
        btnFinish = (Button)findViewById(R.id.btnFinish);
        btnVoltar = (Button)findViewById(R.id.btnVoltar);
        btnClose = (Button)findViewById(R.id.btnClose);

        String getTimesD = "SELECT * FROM "+getDAY+"J WHERE DISPONIBILIDADE = 1;";

        Connection MYSQLConnect = MYSQLClass.MYSQLConnection();
        try {
            Statement statement = MYSQLConnect.createStatement();
            ResultSet resultSet = statement.executeQuery(getTimesD);

            ArrayList<String> listTime = new ArrayList<String>();
            while (resultSet.next()) {
                        String items = resultSet.getString("HORA");
                        listTime.add(items);
            }
            resultSet.close();
            String[] arrayList = listTime.toArray(new String[0]);
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listTime);
            spinner.setAdapter(arrayAdapter);
            statement.close();
            resultSet.close();
        }catch (Exception e) {
            return;
        }

        btnVoltar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                String xDelete = "DELETE FROM AGENDA WHERE LOGIN = '"+B64.toBase64(getMail)+"';";
                Connection MYSQLConnect = MYSQLClass.MYSQLConnection();
                try{
                    Statement statement = MYSQLConnect.createStatement();
                    statement.executeUpdate(xDelete);
                    statement.close();
                    finish();
                }catch(Exception e){ return; }
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                String xDelete = "DELETE FROM AGENDA WHERE LOGIN = '"+B64.toBase64(getMail)+"';";
                Connection MYSQLConnect = MYSQLClass.MYSQLConnection();
                try {
                    Statement statement = MYSQLConnect.createStatement();
                    statement.executeUpdate(xDelete);
                    statement.close();
                    Finish();
                }catch (Exception e) { return; }
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendFinish();
            }
        });
    }

    public void sendFinish(){
        MYSQLClass MYSQLClass = new MYSQLClass();
        Intent intent = getIntent();
        String getMAIL = intent.getStringExtra("sendMAIL");
        int getDAY = Integer.parseInt(getIntent().getExtras().get("sendDAY").toString());
        String getNome = "SELECT * FROM AGENDA WHERE LOGIN = '" + B64.toBase64(getMAIL)+ "';";


        Connection MYSQLConnect = MYSQLClass.MYSQLConnection();
        try{
            Statement statement = MYSQLConnect.createStatement();
            ResultSet resultSet = statement.executeQuery(getNome);
            resultSet.next();
            String setUpdate = "UPDATE " + getDAY + "J SET DISPONIBILIDADE = 0, NOME = '"
                    + resultSet.getString("NOME") + "' WHERE HORA = '" + spinner.getSelectedItem().toString() + "';";
            String setUpdateAgenda = "UPDATE AGENDA SET HORA = '"+spinner.getSelectedItem().toString()+"' WHERE LOGIN = '"+B64.toBase64(getMAIL)+"';";
            PreparedStatement preparedStatement = MYSQLConnect.prepareStatement(setUpdate);
            preparedStatement.execute();
            preparedStatement.execute(setUpdateAgenda);
            statement.close();
            resultSet.close();

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(ActivityTimes.this);
            alertDialog.setTitle("Informação");
            alertDialog.setIcon(R.drawable.confirmation);
            alertDialog.setMessage("Agendamento realizado com sucesso!\n\nDeseja visualizar informações de rotas do estabeleciomento?");

            alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which){
                    Intent showMap = new Intent(getApplicationContext(), OtherInformations.class);
                    startActivity(showMap);
                }
            });

            alertDialog.setNegativeButton("Finalizar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Finish();
                }
            });
            alertDialog.show();
            
    } catch (Exception e) { return; }
    }

    public void Finish(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}