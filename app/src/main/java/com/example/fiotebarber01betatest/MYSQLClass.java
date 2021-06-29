package com.example.fiotebarber01betatest;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MYSQLClass {
    public Connection MYSQLConnection() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Connection xCONNECT = null;

        try {
            String xDRIVER = "com.mysql.jdbc.Driver";
            String xHOST = "jdbc:mysql://192.168.0.3/";
            String xDATABASE = "fiote_test_db";
            String xUSERDB = "root";
            String xPASSWORDDB = null;

            Class.forName(xDRIVER).newInstance();
            xCONNECT = DriverManager.getConnection(xHOST + xDATABASE, xUSERDB, xPASSWORDDB);

            /*Log.e(null, "Conexão estabelecida.\nHOST: " + xHOST + "\nUSER: " + xUSERDB + "\nSENHA: " + xPASSWORDDB + "\nBase de dados: " + xDATABASE + "\n");*/
        } catch (ClassNotFoundException e) {
            Log.e(null, "Ops! Classe não localizada.");
        } catch (SQLException e) {
            Log.e(null, "Ops! SQL error.");
        } catch (Exception e) {
            Log.e(null, "Ops! Exception error.");
        }
        return xCONNECT;
    }
}
