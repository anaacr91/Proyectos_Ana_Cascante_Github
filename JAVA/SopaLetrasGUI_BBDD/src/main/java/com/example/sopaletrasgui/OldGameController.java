package com.example.sopaletrasgui;

import com.example.sopaletrasgui.dto.partidasDTO;
import com.example.sopaletrasgui.exception.CustomException;
import com.example.sopaletrasgui.modelDAO.jdbcPartidasDAO;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class OldGameController extends Application {
    @FXML
    TextField gameid;

    @FXML
    ListView<String> ID;
    ArrayList<partidasDTO> partidaDTO;

    /**
     * metodo de inicio para recuperar las partidas del listview
     * utilizando un arraylist que utiliza listview, para seleccionar el id e ir a la partida
     * @param stage
     * @throws IOException
     * @throws SQLException
     */

    public void start(Stage stage) throws IOException, SQLException {
        jdbcPartidasDAO partidasDao = new jdbcPartidasDAO();
        partidaDTO= partidasDao.recuperarPartidas();
        ObservableList<String>listviewContent = FXCollections.observableArrayList();

        for ( partidasDTO partida: partidaDTO){
            listviewContent.add("id: "+partida.getId() +" terminado: "+partida.isTerminado() );
        }

        ID.setItems(listviewContent);
        ID.setOnMouseClicked(event -> {
            if (event.getClickCount()==2){
                String selectedvalue = ID.getSelectionModel().getSelectedItem();
                int idSeleccionado= Integer.parseInt(selectedvalue.substring(4,selectedvalue.indexOf("t")).trim());
                goToOldGame(idSeleccionado);
            }
        });

    }

    /**
     * método que va al método oldgame al clickar el boton search
     * @throws IOException
     */

    @FXML
    protected void onMouseClick3() throws IOException{
        goToOldGame(0);
    }

    /**
     * método que recorre todas las partidas obtenidas de la Base de Datos
     * si la id del usuario coincide con la id de un elemento, accedemos a esa partida
     * en caso que la id este mal y no se haya encontrado, captura el error
     * en caso que la partida este terminada, captura el error
     * después va al juego anterior usando la vista games.fxml
     * @param Id
     */

    private void goToOldGame(int Id)  {
        int idABuscar= 0;
        if (Id!=0){
            idABuscar=Id;
        }else{
            idABuscar=Integer.parseInt(gameid.getText());
        }
        try {
            partidasDTO partidaencontrada= null;
            for (partidasDTO partidadto: partidaDTO){

                if(idABuscar==partidadto.getId()){

                    partidaencontrada=partidadto;
                    break;
                }

            }
            if(partidaencontrada==null){
                throw new IOException("no se ha encontrado la ID del juego");

            }
            if (partidaencontrada.isTerminado()){
                throw new IOException("no puedes entrar porque la partida esta terminada");
            }


            Stage stage =(Stage) gameid.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
            Parent root = loader.load();
            GameController controller = loader.getController();
            controller.init(partidaencontrada);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }catch (IOException e){
            Alert c = new Alert(Alert.AlertType.INFORMATION);
           c.setContentText(e.getMessage());
           c.show();
        }
    }
}

