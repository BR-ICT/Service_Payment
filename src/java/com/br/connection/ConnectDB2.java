/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.connection;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Wattana
 */
public class ConnectDB2 {

    public static Connection ConnectionDB() throws Exception {
        
        String jdbcClassName = "com.ibm.as400.access.AS400JDBCDriver";
        String url = "jdbc:as400://192.200.9.190;";

//        String jdbcClassName = "com.ibm.jtopenlite.database.jdbc.JDBCDriver";
//        String url = "jdbc:jtopenlite://192.200.9.190";

        Class.forName(jdbcClassName);
        return DriverManager.getConnection(url, "M3SRVICT", "ICT12345");
    }

    public static Connection LoginDB(String user, String pass) throws Exception {

        String jdbcClassName = "com.ibm.jtopenlite.database.jdbc.JDBCDriver";
        String url = "jdbc:jtopenlite://192.200.9.190";

        Class.forName(jdbcClassName);
        return DriverManager.getConnection(url, user, pass);
    }

}
