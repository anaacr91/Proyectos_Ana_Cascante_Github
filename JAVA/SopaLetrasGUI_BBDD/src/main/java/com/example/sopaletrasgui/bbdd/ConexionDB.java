package com.example.sopaletrasgui.bbdd;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * constructor y métodos con la estructura de la conexión a la base de datos xampp
 */

public class ConexionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/bbddsopaletras?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static ConexionDB instance;
    private Connection connect;

    private ConexionDB(){
        this.connect= this.openConection();
    }

    public static ConexionDB getInstance(){
        if (ConexionDB.instance==null){
            ConexionDB.instance= new ConexionDB();
        }
        return ConexionDB.instance;
    }

    public void closeConnection() throws SQLException{
        this.connect.close();
    }

    public Connection openConection(){
        Connection con = null;
        try {
            con= DriverManager.getConnection(URL, USER, PASSWORD);

        }catch (SQLException e){
            e.printStackTrace();
        }
        return con;
    }
}

