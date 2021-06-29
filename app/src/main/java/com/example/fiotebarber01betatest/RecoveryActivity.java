package com.example.fiotebarber01betatest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

public class RecoveryActivity extends AppCompatActivity {
    EditText txt_RECOVERY_CPF, txt_RECOVERY_NASC, txt_RECOVERY_LOGIN;
    Button btn_RECOVERY, btn_RECOVERY_VOLTAR;
    TextView lbl_RECOVERY_LOGIN, lbl_RECOVERY_SENHA;

@Override
protected void onCreate(Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getSupportActionBar().hide();
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recovery);

    txt_RECOVERY_CPF = (EditText) findViewById(R.id.txt_RECOVERY_CPF);
    txt_RECOVERY_NASC = (EditText) findViewById(R.id.txt_RECOVERY_NASC);
    txt_RECOVERY_LOGIN = (EditText) findViewById(R.id.txt_RECOVERY_LOGIN);
    btn_RECOVERY = (Button) findViewById(R.id.btn_RECOVERY);
    btn_RECOVERY_VOLTAR = (Button) findViewById(R.id.btn_RECOVERY_VOLTAR);
    lbl_RECOVERY_LOGIN = (TextView) findViewById(R.id.lbl_RECOVERY_LOGIN);
    lbl_RECOVERY_SENHA = (TextView) findViewById(R.id.lbl_RECOVERY_SENHA);

    btn_RECOVERY.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view){
        if(txt_RECOVERY_CPF.getText().length() == 0 && (txt_RECOVERY_CPF.getText().toString().isEmpty())){
            txt_RECOVERY_CPF.setFocusable(true);
            txt_RECOVERY_CPF.setError("Por favor, informe um número de CPF valído.");
        }
        else if(txt_RECOVERY_CPF.getText().length() < 14){
            txt_RECOVERY_CPF.setFocusable(true);
            txt_RECOVERY_CPF.setError("CPF Inválido.");
        }
        else if(txt_RECOVERY_NASC.getText().length() == 0 &&(txt_RECOVERY_NASC.getText().toString().isEmpty())){
            txt_RECOVERY_NASC.setFocusable(true);
            txt_RECOVERY_NASC.setError("Por favor, informa sua data de nascimento.");
        }
        else if (txt_RECOVERY_NASC.getText().length() < 10){
            txt_RECOVERY_NASC.setFocusable(true);
            txt_RECOVERY_NASC.setError("Data de nascimento inválida.");
        }
        else if(txt_RECOVERY_LOGIN.getText().length() == 0 && (txt_RECOVERY_LOGIN.getText().toString().isEmpty())){
            txt_RECOVERY_LOGIN.setFocusable(true);
            txt_RECOVERY_LOGIN.setError("Por favor, informe seu e-mail de login.");
        }
        else
        {
            RecoveryAccount();
        }
    }
    });

    btn_RECOVERY_VOLTAR.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            finish();
        }
    });
}

public void RecoveryAccount() {
    MYSQLClass MYSQLClass = new MYSQLClass();
    String getCPF = txt_RECOVERY_CPF.getText().toString();
    String getNASCIMENTO = txt_RECOVERY_NASC.getText().toString();
    String getLOGIN = txt_RECOVERY_LOGIN.getText().toString();

    String requestQuery = ("SELECT * FROM USERS WHERE CPF = '" + (B64.toBase64(getCPF)) + "' AND NASCIMENTO = '" + getNASCIMENTO + "' AND LOGIN = '" + (B64.toBase64(getLOGIN)) + "';");

    Connection MYSQLConnect = MYSQLClass.MYSQLConnection();
    try {
        Statement statement = MYSQLConnect.createStatement();
        ResultSet resultSet = statement.executeQuery(requestQuery);
        resultSet.next();

        if (resultSet.getString("CPF").equals(B64.toBase64(getCPF)) ||
                resultSet.getString("NASCIMENTO").equals(getNASCIMENTO) ||
                resultSet.getString("LOGIN").equals(B64.toBase64(getLOGIN))) {
            lbl_RECOVERY_LOGIN.setText("Login: " + (B64.fromBase64(resultSet.getString("LOGIN"))));
            lbl_RECOVERY_SENHA.setText("Senha: " + (B64.fromBase64(resultSet.getString("SENHA"))));
            statement.close();
            resultSet.close();
        } else {
            lbl_RECOVERY_LOGIN.setText("Oops! Não foram encontradas informações para");
            lbl_RECOVERY_SENHA.setText(getLOGIN);
            statement.close();
            resultSet.close();
        }
    } catch (Exception e) {
        lbl_RECOVERY_LOGIN.setText("Oops! Não foram encontradas informações para");
        lbl_RECOVERY_SENHA.setText(getLOGIN);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(RecoveryActivity.this);
        alertDialog.setTitle("Recuperação de dados");
        alertDialog.setIcon(R.drawable.close);
        alertDialog.setMessage("Ops!\n\nNão foram encontradas informações para " + getLOGIN + "\n\nTente novamente.");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

        alertDialog.show();
        }
    }
}