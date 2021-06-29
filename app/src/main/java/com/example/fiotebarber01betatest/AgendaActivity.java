package com.example.fiotebarber01betatest;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

public class AgendaActivity extends AppCompatActivity {
    DatePicker datePicker;
    Button btn_AG_CONTINUE, btn_AG_SAIR;

@RequiresApi(api = Build.VERSION_CODES.O)
protected void onCreate(Bundle savedInstanceState){
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getSupportActionBar().hide();
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_agenda);

    datePicker = (DatePicker) findViewById(R.id.comp_DATE);
    btn_AG_CONTINUE = (Button) findViewById(R.id.btn_AG_CONTINUE);
    btn_AG_SAIR = (Button) findViewById(R.id.btn_AG_SAIR);

    Calendar calendar = Calendar.getInstance();
    Calendar setMin = (Calendar)calendar.clone();
    setMin.add(Calendar.DATE, -2);
    Calendar setMax = (Calendar)calendar.clone();
    setMax.add(Calendar.DATE, 27);
    datePicker.setMinDate(setMin.getTimeInMillis());
    datePicker.setMaxDate(setMax.getTimeInMillis());

    btn_AG_CONTINUE.setText("Continuar");

    datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener(){
        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            if (datePicker.getDayOfMonth() < 10){
                btn_AG_CONTINUE.setText("Agendar 0" + datePicker.getDayOfMonth() + "/0" + (datePicker.getMonth() + 1) + "/" + datePicker.getYear());
                btn_AG_CONTINUE.setPadding(270, 0 , 270, 0);
            } else {
                btn_AG_CONTINUE.setText("Agendar " + datePicker.getDayOfMonth() + "/0" + (datePicker.getMonth() + 1) + "/" + datePicker.getYear());
                btn_AG_CONTINUE.setPadding(270, 0 , 270, 0);
            }
        }
    });

    btn_AG_SAIR.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    });

    btn_AG_CONTINUE.setOnClickListener(new View.OnClickListener() {
        public void onClick(View view) {
            if (datePicker.getYear() < 2021 || datePicker.getYear() > 2021) {
                Toast.makeText(AgendaActivity.this, "Impossível agendar para este ano.", Toast.LENGTH_LONG).show();
            } else if (datePicker.getDayOfMonth() == 6 || datePicker.getDayOfMonth() == 13 || datePicker.getDayOfMonth() == 20 || datePicker.getDayOfMonth() == 27) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AgendaActivity.this);
                alertDialog.setTitle("Informação");
                alertDialog.setMessage("\nNão há horários de funcionamento para dia "+datePicker.getDayOfMonth()+"/0"+(datePicker.getMonth()+1)+" (Domingo).");
                alertDialog.setIcon(R.drawable.close);
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                alertDialog.show();
            }else{
                ContinueAG();
            }
        }

        public void ContinueAG() {
            Intent intent = getIntent();
            String getLOGIN = intent.getStringExtra("sendLOGIN");
            MYSQLClass MYSQLClass= new MYSQLClass();

            Connection MYSQLConnect = MYSQLClass.MYSQLConnection();
            try {
                String xQUERY_SELECT = "SELECT * FROM USERS WHERE LOGIN = '" + B64.toBase64(getLOGIN) + "';";

                Statement statement = MYSQLConnect.createStatement();
                ResultSet resultSet = statement.executeQuery(xQUERY_SELECT);

                if (resultSet.next()) {
                    String getDbNOME = resultSet.getString("NOME");
                    String getDbSOBRENOME = resultSet.getString("SOBRENOME");
                    String getDbCELULAR = resultSet.getString("CELULAR");
                    String xInsert = "INSERT INTO AGENDA SET NOME = '"+getDbNOME+"', SOBRENOME = '"+getDbSOBRENOME+"', DIA = '"+datePicker.getYear()+"/"+(datePicker.getMonth()+1)+"/"+datePicker.getDayOfMonth()+
                            "', LOGIN = '"+B64.toBase64(getLOGIN)+"', CELULAR= '"+getDbCELULAR+"';";
                    statement.execute(xInsert);
                }
                Intent sendIntent = new Intent(getApplicationContext(), ActivityTimes.class);
                sendIntent.putExtra("sendMAIL", getLOGIN);
                sendIntent.putExtra("sendDAY", datePicker.getDayOfMonth());
                startActivity(sendIntent);
                statement.close();
                resultSet.close();
            } catch (MySQLIntegrityConstraintViolationException ex) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AgendaActivity.this);
                alertDialog.setTitle("Falha");
                alertDialog.setIcon(R.drawable.close);

                alertDialog.setMessage("Ops!\n\nCliente com um agendamento ativo.\nSolicite ao administrador a remoção do seu agendamento ativo para remarcar um outro horário.");

                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                alertDialog.show();

            } catch (Exception e) { return; }
        }
    });
    }
}