package application.controller;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.IOException;

public class playerController {

    @FXML
    private Pane _player;


    public void initialize(){

        Media video = new Media(ListController.getSelectedFile().toURI().toString());
        MediaPlayer player = new MediaPlayer(video);
        player.setAutoPlay(true);
        MediaView mediaView = new MediaView(player);

        player.setOnReady(new Runnable() {
            @Override public void run() {
                //
            }
        });
        _player.getChildren().add(mediaView);

    }











    @FXML
    private void handleReturnButton(ActionEvent event) throws IOException {

        Parent listViewParent = FXMLLoader.load(Main.class.getResource("resources/home.fxml"));
        Scene listViewScene = new Scene(listViewParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(listViewScene);
        window.show();
    }



}
