package com.example.fiotebarber01betatest;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
    EditText txt_MAIN_USER, txt_MAIN_SENHA;
    Button btn_MAIN_LOGIN, btn_MAIN_REGISTER, btn_MAIN_RECOVERY;

@Override
public void onCreate(Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getSupportActionBar().hide();
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    btn_MAIN_LOGIN = (Button) findViewById(R.id.btn_MAIN_LOGIN);
    btn_MAIN_REGISTER = (Button) findViewById(R.id.btn_MAIN_REGISTER);
    btn_MAIN_RECOVERY = (Button) findViewById(R.id.btn_MAIN_RECOVERY);
    txt_MAIN_USER = (EditText) findViewById(R.id.txt_MAIN_LOGIN);
    txt_MAIN_SENHA = (EditText) findViewById(R.id.txt_MAIN_SENHA);

btn_MAIN_LOGIN.setOnClickListener(new View.OnClickListener() {
    public void onClick(View view) { xCHECK(); }
});

btn_MAIN_REGISTER.setOnClickListener(new View.OnClickListener() {
    public void onClick(View view){
        tRegister();
    }
});

btn_MAIN_RECOVERY.setOnClickListener(new View.OnClickListener(){
    public void onClick(View view){
        tRecovery();
        }
    });
}

protected void tRegister(){
    Intent intent = new Intent(this, RegisterActivity.class);
    startActivity(intent);
}

protected void tRecovery(){
    Intent intent = new Intent(this, RecoveryActivity.class);
    startActivity(intent);
}

protected void xCHECK(){
    if (txt_MAIN_USER.getText().length() == 0 && (txt_MAIN_USER.getText().toString().isEmpty())) {
        txt_MAIN_USER.setFocusable(true);
        txt_MAIN_USER.setError("Por favor informe seu e-mail.");
    } else if (txt_MAIN_SENHA.getText().length() == 0 && (txt_MAIN_SENHA.getText().toString().isEmpty())) {
        txt_MAIN_SENHA.setFocusable(true);
        txt_MAIN_SENHA.setError("Por favor informe uma senha.");
    }
    else{
        xAUTH();
    }
}

public void xAUTH() {
    MYSQLClass MYSQLClass = new MYSQLClass();
    String getMYSQLUSER = txt_MAIN_USER.getText().toString();
    String getMYSQLPW = txt_MAIN_SENHA.getText().toString();
    String NIVEL = "0";

    String xQUERYLOGIN = "SELECT * FROM USERS WHERE LOGIN = '" + (B64.toBase64(getMYSQLUSER)) + "' AND SENHA = '" + (B64.toBase64(getMYSQLPW)) + "';";

    Connection xCONNECT = MYSQLClass.MYSQLConnection();
    try {
        Statement statement = xCONNECT.createStatement();
        ResultSet resultSet = statement.executeQuery(xQUERYLOGIN);
        resultSet.next();

        if (resultSet.getString("LOGIN").equals(B64.toBase64(getMYSQLUSER.toString())) &&
                resultSet.getString("SENHA").equals(B64.toBase64(getMYSQLPW.toString())))
            if (resultSet.getString("NIVEL").equals(NIVEL.toString())) {
                Toast.makeText(MainActivity.this, "Carregando, aguarde...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, ListaActivity.class);
                startActivity(intent);
                resultSet.close();
            } else {
                {
                    Toast.makeText(MainActivity.this, "Carregando, aguarde...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, AgendaActivity.class);
                    intent.putExtra("sendLOGIN", txt_MAIN_USER.getText().toString());
                    startActivity(intent);
                    statement.close();
                    resultSet.close();
                }
            }
        else {
            statement.close();
            resultSet.close();
        }
    } catch (SQLException e) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Autenticação");
        alertDialog.setIcon(R.drawable.close);
        alertDialog.setMessage("Ops!\n\nDados para " + getMYSQLUSER + " estão incorretos!\n\nTente novamente!");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        alertDialog.show();
        txt_MAIN_SENHA.setText(null);
        }
    }
}