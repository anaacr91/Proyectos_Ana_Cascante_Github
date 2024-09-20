package com.example.sopaletrasgui;

import com.example.sopaletrasgui.dto.partidasDTO;
import com.example.sopaletrasgui.exception.CustomException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class IntroController {

    @FXML
    TextField insertWords;
    @FXML
    private Button oldgame;

    /**
     * The method onMouseClick calls the FXMLLoader, that runs the controller and starts the stage, who calls the scene and the ccss
     * @throws IOException: in the case the word doesnt fit or other error may happened.
     */
    @FXML
    protected void onMouseClick() throws IOException {

        try {
            FXMLLoader loader = new FXMLLoader(SoupApplication.class.getResource("game.fxml"));
            Parent root = loader.load();
            GameController controller = loader.getController();
            partidasDTO partida = new partidasDTO();
            partida.setPalabras_a_encontrar(insertWords.getText().replaceAll("\\s",""));
            controller.init(partida);

            Stage stage = (Stage) insertWords.getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            scene.getStylesheets().add(SoupApplication.class.getResource("game.css").toExternalForm());
            stage.show();

        }catch (CustomException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            Alert c = new Alert(Alert.AlertType.INFORMATION);
            c.setContentText(String.format("The word doesnt fit"));
            c.show();
        }catch (Exception e){
            e.printStackTrace();
            Alert c = new Alert(Alert.AlertType.INFORMATION);
            c.setContentText(e.getMessage());
            c.show();
        }
    }

    /**
     * este metodo onmouseclick2 es el metodo del segundo boton playoldgame, que llama
     * a la siguiente escena y recoge datos de las partidas anteriores y las envia a la siguiente pantalla
     * @param event
     * @throws IOException
     */

    @FXML
    protected void onMouseClick2(ActionEvent event) throws IOException{
        try {
            FXMLLoader loader = new FXMLLoader(SoupApplication.class.getResource("oldgames.fxml"));
            Parent root = loader.load();
            OldGameController controller = loader.getController();
            partidasDTO partida = new partidasDTO();
            partida.setPalabras_a_encontrar(insertWords.getText());
            Stage stage =(Stage) oldgame.getScene().getWindow();
            controller.start(stage);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Alert c = new Alert(Alert.AlertType.INFORMATION);
            c.setContentText(e.getMessage());
            c.show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
