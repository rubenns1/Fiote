package com.example.fiotebarber01betatest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterActivity extends AppCompatActivity {
    EditText txt_REG_NOME, txt_REG_SOBRENOME, txt_REG_CPF, txt_REG_CELULAR, txt_REG_NASCIMENTO, txt_REG_LOGIN, txt_REG_SENHA;
    Button btn_REG_VOLTAR, btn_REG_SALVAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        txt_REG_NOME = (EditText) findViewById(R.id.txt_REG_NOME);
        txt_REG_SOBRENOME = (EditText) findViewById(R.id.txt_REG_SOBRENOME);
        txt_REG_CPF = (EditText) findViewById(R.id.txt_REG_CPF);
        txt_REG_CELULAR = (EditText) findViewById(R.id.txt_REG_CELULAR);
        txt_REG_NASCIMENTO = (EditText) findViewById(R.id.txt_REG_NASCIMENTO);
        txt_REG_LOGIN = (EditText) findViewById(R.id.txt_REG_LOGIN);
        txt_REG_SENHA = (EditText) findViewById(R.id.txt_REG_SENHA);
        btn_REG_VOLTAR = (Button) findViewById(R.id.btn_REG_VOLTAR);
        btn_REG_SALVAR = (Button) findViewById(R.id.btn_REG_SALVAR);

        btn_REG_VOLTAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_REG_SALVAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txt_REG_NOME.getText().toString().length() == 0 && txt_REG_NOME.getText().toString().isEmpty()) {
                    txt_REG_NOME.setFocusable(true);
                    txt_REG_NOME.setError("Por favor, informe o seu nome.");
                } else if (txt_REG_SOBRENOME.length() == 0 && txt_REG_SOBRENOME.getText().toString().isEmpty()) {
                    txt_REG_SOBRENOME.setFocusable(true);
                    txt_REG_SOBRENOME.setError("Por favor, informe seu sobrenome.");
                } else if (txt_REG_CPF.getText().toString().length() == 0 && txt_REG_CPF.getText().toString().isEmpty()) {
                    txt_REG_CPF.setFocusable(true);
                    txt_REG_CPF.setError("Por favor, informe seu CPF.");
                } else if (txt_REG_CPF.getText().toString().length() < 14) {
                    txt_REG_CPF.setFocusable(true);
                    txt_REG_CPF.setError("Número de CPF inválido.");
                } else if (txt_REG_CELULAR.getText().toString().length() == 0 && txt_REG_CELULAR.getText().toString().isEmpty()) {
                    txt_REG_CELULAR.setFocusable(true);
                    txt_REG_CELULAR.setError("Por favor, informe o seu número de telefone.");
                } else if (txt_REG_CELULAR.getText().toString().length() < 11) {
                    txt_REG_CELULAR.setFocusable(true);
                    txt_REG_CELULAR.setError("Por favor informe um número de telefone valido.");
                } else if (txt_REG_NASCIMENTO.getText().toString().length() == 0 && txt_REG_NASCIMENTO.getText().toString().isEmpty()) {
                    txt_REG_NASCIMENTO.setFocusable(true);
                    txt_REG_NASCIMENTO.setError("Por favor informe sua data de nascimento.");
                } else if (txt_REG_NASCIMENTO.getText().toString().length() < 10) {
                    txt_REG_NASCIMENTO.setFocusable(true);
                    txt_REG_NASCIMENTO.setError("Data de nascimento inválida.");
                } else if (txt_REG_LOGIN.getText().toString().length() == 0 && txt_REG_LOGIN.getText().toString().isEmpty()) {
                    txt_REG_LOGIN.setFocusable(true);
                    txt_REG_LOGIN.setError("Por favor, informe um e-mail válido.");
                } else if (txt_REG_SENHA.getText().toString().length() == 0 && txt_REG_SENHA.getText().toString().isEmpty()) {
                    txt_REG_SENHA.setFocusable(true);
                    txt_REG_SENHA.setError("Por favor defina uma senha.");
                } else {
                    ConnectToMYSQL ConnectMYSQL = new ConnectToMYSQL();
                    ConnectMYSQL.execute();
                    finish();
                }
            }
        });
    }

    private class ConnectToMYSQL extends AsyncTask<String, String, String> {
        String xDRIVER = "com.mysql.jdbc.Driver";
        String xHOST = "jdbc:mysql://192.168.0.3/";
        String xDATABASE = "fiote_test_db";
        String xUSERDB = "root";
        String xPASSWORDDB = null;
        String xRESULT = "";

        int setACESSLEVEL = 1; /*  */
        String getTXT_REG_NOME = "'" + txt_REG_NOME.getText().toString() + "',";
        String getTXT_REG_SOBRENOME = "'" + txt_REG_SOBRENOME.getText().toString() + "',";
        String getTXT_REG_CPF = "'" + B64.toBase64(txt_REG_CPF.getText().toString()) + "',";
        String getTXT_REG_CELULAR = "'" + B64.toBase64(txt_REG_CELULAR.getText().toString()) + "',";
        String getTXT_REG_NASCIMENTO = "'" + txt_REG_NASCIMENTO.getText().toString() + "',";
        String getTXT_REG_LOGIN = "'" + B64.toBase64(txt_REG_LOGIN.getText().toString()) + "', ";
        String getTXT_REG_SENHA = "'" + B64.toBase64(txt_REG_SENHA.getText().toString()) + "', " + setACESSLEVEL + ");";

        String i = "INSERT into USERS(NOME, SOBRENOME, CPF, CELULAR, NASCIMENTO, LOGIN, SENHA, NIVEL) VALUES (" +
                getTXT_REG_NOME +
                getTXT_REG_SOBRENOME +
                getTXT_REG_CPF +
                getTXT_REG_CELULAR +
                getTXT_REG_NASCIMENTO +
                getTXT_REG_LOGIN +
                getTXT_REG_SENHA;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(RegisterActivity.this, "Aguarde, estamos trabalhando nisso...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Class.forName(xDRIVER).newInstance();
                Connection xCONNECTION = DriverManager.getConnection(xHOST + xDATABASE, xUSERDB, xPASSWORDDB);
                PreparedStatement xPREPARE = xCONNECTION.prepareStatement(i);

                int x = xPREPARE.executeUpdate();
                xCONNECTION.close();

            }
            catch (ClassNotFoundException e) { return null; } catch (SQLException e) { return null; } catch (Exception e) { return null; }
            return xRESULT;
        }

        @Override
        protected void onPostExecute(String xPOST){
            Toast.makeText(RegisterActivity.this, "Usuário cadastro com sucesso.", Toast.LENGTH_SHORT).show();
        }
    }
}