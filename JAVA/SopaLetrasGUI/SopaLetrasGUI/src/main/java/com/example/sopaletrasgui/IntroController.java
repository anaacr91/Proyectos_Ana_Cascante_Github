package com.example.sopaletrasgui;

import com.example.sopaletrasgui.exception.CustomException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class IntroController {

    @FXML
    TextField insertWords;

    @FXML
    private Button generatePuzzle;

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
            controller.init(insertWords.getText());
            Stage stage = (Stage) insertWords.getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            scene.getStylesheets().add(SoupApplication.class.getResource("game.css").toExternalForm());
            stage.show();

        }catch (CustomException e) {
            System.out.println(e.getMessage());
            Alert c = new Alert(Alert.AlertType.INFORMATION);
            c.setContentText(String.format("The word doesnt fit"));
            c.show();
        }catch (Exception e){

            Alert c = new Alert(Alert.AlertType.INFORMATION);
            c.setContentText(String.format("Some error may happened"));
            c.show();
        }
    }
}
