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
public class ConnectSQLServer {

    public static Connection ConnectionDB() throws Exception {

        String jdbcClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://192.200.9.182:1433;database=BRLAS400;user=sa;password=sa";

        Class.forName(jdbcClassName);
        return DriverManager.getConnection(url, "sa", "sa");
    }

    public static Connection LoginDB(String user, String pass) throws Exception {

        String jdbcClassName = "com.ibm.jtopenlite.database.jdbc.JDBCDriver";
        String url = "jdbc:jtopenlite://192.200.9.190";

        Class.forName(jdbcClassName);
        return DriverManager.getConnection(url, user, pass);
    }

}
