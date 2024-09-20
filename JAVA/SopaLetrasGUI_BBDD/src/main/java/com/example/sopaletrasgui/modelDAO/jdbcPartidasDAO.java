package com.example.sopaletrasgui.modelDAO;

/**
 * constructor y métodos con los que hacer peticiones hacia la base de datos y convertir los objetos partidasDTO,
 * interactuar tanto desde la base de datos hacia los objetos java o llamando desde el objeto a la base de datos,
 * para recibir, guardar, modificar(actualizar) partidas, para luego recuperar partidas de la base de datos
 */

import com.example.sopaletrasgui.bbdd.ConexionDB;
import com.example.sopaletrasgui.dto.partidasDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class jdbcPartidasDAO {
    private Connection connect;
    private PreparedStatement ps;
    private ResultSet rs;

    public jdbcPartidasDAO() {
        this.connect = ConexionDB.getInstance().openConection();
    }

    /**
     * método de guardar partidas
     * @param p
     * @throws SQLException
     */

    public void guardarPartida(partidasDTO p) throws SQLException {
        this.ps= this.connect.prepareStatement(
          "INSERT INTO partidas (tablero, palabras_a_encontrar, palabras_encontradas, terminado) VALUES (?,?,?, ?)"
        );
        this.ps.setString(1, p.getTableroasString());
        this.ps.setString(2, (p.getPalabras_a_encontrar_asString()));
        this.ps.setString(3, (p.getPalabras_encontradas_asString()));
        this.ps.setBoolean(4,p.isTerminado());
        this.ps.executeUpdate();
        this.ps.close();

        /**
         * metodo de recuperar partidas
         */
    }
    public ArrayList <partidasDTO> recuperarPartidas () throws SQLException {
        ArrayList<partidasDTO> partidas= new ArrayList<>();
        this.ps= this.connect.prepareStatement("SELECT * FROM partidas");
        this.rs= this.ps.executeQuery();
        while(this.rs.next()){

            partidasDTO partidaencontrada= new partidasDTO();
            partidaencontrada.setId(this.rs.getInt("id"));
            partidaencontrada.setTablero(this.rs.getString("tablero"));
            partidaencontrada.setPalabras_a_encontrar(this.rs.getString("palabras_a_encontrar"));
            partidaencontrada.setPalabras_encontradas(this.rs.getString("palabras_encontradas"));
            partidaencontrada.setTerminado(this.rs.getBoolean("terminado"));
            partidas.add(partidaencontrada);
        }
        this.ps.close();
        this.rs.close();
        return partidas;
    }

    /**
     * metodo de modificar partidas
     * @param p
     * @throws SQLException
     */

    public void modificarPartida(partidasDTO p) throws SQLException{
        this.ps=this.connect.prepareStatement(
                "UPDATE partidas SET tablero = ?, palabras_a_encontrar = ?, palabras_encontradas = ?, terminado = ? WHERE id = ?"
        );
        this.ps.setString(1, p.getTableroasString());
        this.ps.setString(2, p.getPalabras_a_encontrar_asString());
        this.ps.setString(3, p.getPalabras_encontradas_asString());
        this.ps.setBoolean(4, p.isTerminado());
        this.ps.setInt(5, p.getId());
        this.ps.executeUpdate();
        this.ps.close();

    }

}
